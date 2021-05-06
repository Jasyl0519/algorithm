package concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AQSTest {

    static ReentrantLock lock = new ReentrantLock();
    static ReentrantReadWriteLock lock1 = new ReentrantReadWriteLock();
    static CountDownLatch latch = new CountDownLatch(4);

    public static void main(String[] args) throws InterruptedException {

        ReentrantReadWriteLock.ReadLock readLock = lock1.readLock();
        readLock.lock();

        latch.countDown();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    latch.countDown();
                    System.out.println("thread1 队列长度：" + lock.getQueueLength());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //lock.unlock();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(10);
                    latch.countDown();
                    System.out.println("thread2 队列长度：" + lock.getQueueLength());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //lock.unlock();
                }
            }
        });

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(15);
                    latch.countDown();
                    System.out.println("thread3 队列长度：" + lock.getQueueLength());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //lock.unlock();
                }
            }
        });

        Thread thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(20);
                    latch.countDown();
                    System.out.println("thread4 队列长度：" + lock.getQueueLength());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //lock.unlock();
                }
            }
        });


        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        latch.await();
        System.out.println("队列长度" + lock.getQueueLength());








    }
}
