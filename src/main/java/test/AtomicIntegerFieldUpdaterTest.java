package test;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterTest {

    public static void main(String[] args) {
        User user = new User("user", 20);
        AtomicIntegerFieldUpdater integerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");
        System.out.println(integerFieldUpdater.get(user));

        integerFieldUpdater.addAndGet(user, 1);
        System.out.println(integerFieldUpdater.get(user));

        integerFieldUpdater.incrementAndGet(user);
        System.out.println(integerFieldUpdater.get(user));

        integerFieldUpdater.compareAndSet(user, integerFieldUpdater.get(user), 30);
        System.out.println(integerFieldUpdater.get(user));
    }
}
