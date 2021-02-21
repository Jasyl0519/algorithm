package concurrentJava;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Exchanger;

/**
 * Overview: The core algorithm is, for an exchange "slot",
 * and a participant (caller) with an item:
 *
 * for (;;) {
 *   if (slot is empty) {                       // offer
 *     place item in a Node;
 *     if (can CAS slot from empty to node) {
 *       wait for release;
 *       return matching item in node;
 *     }
 *   }
 *   else if (can CAS slot from node to empty) { // release
 *     get the item in node;
 *     set matching item in node;
 *     release waiting thread;
 *   }
 *   // else retry on CAS failure
 * }
 *
 * This is among the simplest forms of a "dual data structure" --
 * see Scott and Scherer's DISC 04 paper and
 * http://www.cs.rochester.edu/research/synchronization/pseudocode/duals.html
 *
 * This works great in principle. But in practice, like many
 * algorithms centered on atomic updates to a single location, it
 * scales horribly when there are more than a few participants
 * using the same Exchanger. So the implementation instead uses a
 * form of elimination arena, that spreads out this contention by
 * arranging that some threads typically use different slots,
 * while still ensuring that eventually, any two parties will be
 * able to exchange items. That is, we cannot completely partition
 * across threads, but instead give threads arena indices that
 * will on average grow under contention and shrink under lack of
 * contention. We approach this by defining the Nodes that we need
 * anyway as ThreadLocals, and include in them per-thread index
 * and related bookkeeping state. (We can safely reuse per-thread
 * nodes rather than creating them fresh each time because slots
 * alternate between pointing to a node vs null, so cannot
 * encounter ABA problems. However, we do need some care in
 * resetting them between uses.)
 *
 * Implementing an effective arena requires allocating a bunch of
 * space, so we only do so upon detecting contention (except on
 * uniprocessors, where they wouldn't help, so aren't used).
 * Otherwise, exchanges use the single-slot slotExchange method.
 * On contention, not only must the slots be in different
 * locations, but the locations must not encounter memory
 * contention due to being on the same cache line (or more
 * generally, the same coherence unit).  Because, as of this
 * writing, there is no way to determine cacheline size, we define
 * a value that is enough for common platforms.  Additionally,
 * extra care elsewhere is taken to avoid other false/unintended
 * sharing and to enhance locality, including adding padding (via
 * sun.misc.Contended) to Nodes, embedding "bound" as an Exchanger
 * field, and reworking some park/unpark mechanics compared to
 * LockSupport versions.
 *
 * The arena starts out with only one used slot. We expand the
 * effective arena size by tracking collisions; i.e., failed CASes
 * while trying to exchange. By nature of the above algorithm, the
 * only kinds of collision that reliably indicate contention are
 * when two attempted releases collide -- one of two attempted
 * offers can legitimately fail to CAS without indicating
 * contention by more than one other thread. (Note: it is possible
 * but not worthwhile to more precisely detect contention by
 * reading slot values after CAS failures.)  When a thread has
 * collided at each slot within the current arena bound, it tries
 * to expand the arena size by one. We track collisions within
 * bounds by using a version (sequence) number on the "bound"
 * field, and conservatively reset collision counts when a
 * participant notices that bound has been updated (in either
 * direction).
 *
 * The effective arena size is reduced (when there is more than
 * one slot) by giving up on waiting after a while and trying to
 * decrement the arena size on expiration. The value of "a while"
 * is an empirical matter.  We implement by piggybacking on the
 * use of spin->yield->block that is essential for reasonable
 * waiting performance anyway -- in a busy exchanger, offers are
 * usually almost immediately released, in which case context
 * switching on multiprocessors is extremely slow/wasteful.  Arena
 * waits just omit the blocking part, and instead cancel. The spin
 * count is empirically chosen to be a value that avoids blocking
 * 99% of the time under maximum sustained exchange rates on a
 * range of test machines. Spins and yields entail some limited
 * randomness (using a cheap xorshift) to avoid regular patterns
 * that can induce unproductive grow/shrink cycles. (Using a
 * pseudorandom also helps regularize spin cycle duration by
 * making branches unpredictable.)  Also, during an offer, a
 * waiter can "know" that it will be released when its slot has
 * changed, but cannot yet proceed until match is set.  In the
 * mean time it cannot cancel the offer, so instead spins/yields.
 * Note: It is possible to avoid this secondary check by changing
 * the linearization point to be a CAS of the match field (as done
 * in one case in the Scott & Scherer DISC paper), which also
 * increases asynchrony a bit, at the expense of poorer collision
 * detection and inability to always reuse per-thread nodes. So
 * the current scheme is typically a better tradeoff.
 *
 * On collisions, indices traverse the arena cyclically in reverse
 * order, restarting at the maximum index (which will tend to be
 * sparsest) when bounds change. (On expirations, indices instead
 * are halved until reaching 0.) It is possible (and has been
 * tried) to use randomized, prime-value-stepped, or double-hash
 * style traversal instead of simple cyclic traversal to reduce
 * bunching.  But empirically, whatever benefits these may have
 * don't overcome their added overhead: We are managing operations
 * that occur very quickly unless there is sustained contention,
 * so simpler/faster control policies work better than more
 * accurate but slower ones.
 *
 * Because we use expiration for arena size control, we cannot
 * throw TimeoutExceptions in the timed version of the public
 * exchange method until the arena size has shrunken to zero (or
 * the arena isn't enabled). This may delay response to timeout
 * but is still within spec.
 *
 * Essentially all of the implementation is in methods
 * slotExchange and arenaExchange. These have similar overall
 * structure, but differ in too many details to combine. The
 * slotExchange method uses the single Exchanger field "slot"
 * rather than arena array elements. However, it still needs
 * minimal collision detection to trigger arena construction.
 * (The messiest part is making sure interrupt status and
 * InterruptedExceptions come out right during transitions when
 * both methods may be called. This is done by using null return
 * as a sentinel to recheck interrupt status.)
 *
 * As is too common in this sort of code, methods are monolithic
 * because most of the logic relies on reads of fields that are
 * maintained as local variables so can't be nicely factored --
 * mainly, here, bulky spin->yield->block/cancel code), and
 * heavily dependent on intrinsics (Unsafe) to use inlined
 * embedded CAS and related memory access operations (that tend
 * not to be as readily inlined by dynamic compilers when they are
 * hidden behind other methods that would more nicely name and
 * encapsulate the intended effects). This includes the use of
 * putOrderedX to clear fields of the per-thread Nodes between
 * uses. Note that field Node.item is not declared as volatile
 * even though it is read by releasing threads, because they only
 * do so after CAS operations that must precede access, and all
 * uses by the owning thread are otherwise acceptably ordered by
 * other operations. (Because the actual points of atomicity are
 * slot CASes, it would also be legal for the write to Node.match
 * in a release to be weaker than a full volatile write. However,
 * this is not done because it could allow further postponement of
 * the write, delaying progress.)
 */
public class ExchangerStudy {

    private static ArrayBlockingQueue<String> initialFillQueue
            = new ArrayBlockingQueue<>(5);
    private static ArrayBlockingQueue<String> initialEmptyQueue
            = new ArrayBlockingQueue<>(5);
    private static Exchanger<ArrayBlockingQueue<String>> exchanger
            = new Exchanger<>();

    /** 填充缓存队列的线程 */
    static class FillingRunnable implements Runnable {
        @Override
        public void run() {
            ArrayBlockingQueue<String> current = initialEmptyQueue;
            try {
                while (current != null) {
                    String str = UUID.randomUUID().toString();
                    System.out.println("生产了一个序列：" + str + ">>>>>加入到交换区");
                    Thread.sleep(2000);
                    try {
                        current.add(str);
                    } catch (IllegalStateException e) {
                        System.out.println("队列已满，换一个空的");
                        current = exchanger.exchange(current);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /** 填充缓存队列的线程 */
    static class EmptyingRunnable implements Runnable {
        @Override
        public void run() {
            ArrayBlockingQueue<String> current = initialFillQueue;
            try {
                while (current != null) {
                    if (!current.isEmpty()) {
                        String str = current.poll();
                        System.out.println("消耗一个数列：" + str);
                    } else {
                        System.out.println("队列空了，换个满的");
                        current = exchanger.exchange(current);
                        System.out.println("换满的成功~~~~~~~~~~~~~~~~~~~~~~current=" + current);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new FillingRunnable()).start();
        new Thread(new EmptyingRunnable()).start();

    }
}