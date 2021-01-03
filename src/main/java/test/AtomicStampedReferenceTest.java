package test;

import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceTest {
    private static AtomicStampedReference<User> stampedReference;

    public static void main(String[] args) {
        User user1 = new User(1L, "user1");
        int stamp1 = 1;
        stampedReference = new AtomicStampedReference(user1, stamp1);
        System.out.println(stampedReference.getReference());//输出User{id=1, name='user1'}
        System.out.println(stampedReference.getStamp());//1

        User user2 = new User(2L, "user2");
        int stamp2 = stampedReference.getStamp() + 1;
        stampedReference.compareAndSet(user1, user2, stamp1, stamp2);
        System.out.println(stampedReference.getReference());//输出User{id=2, name='user2'}
        System.out.println(stampedReference.getStamp());//2

        int stamp3 = stampedReference.getStamp() + 1;
        stampedReference.compareAndSet(user2, user1, stamp2, stamp3);
        System.out.println(stampedReference.getReference());//输出User{id=1, name='user1'}
        System.out.println(stampedReference.getStamp());//3


        User user4 = new User(4L, "user4");
        int stamp4 = stampedReference.getStamp() + 1;
        stampedReference.compareAndSet(user1, user4, stamp1, stamp4);
        System.out.println(stampedReference.getReference());//输出User{id=1, name='user1'}
        System.out.println(stampedReference.getStamp());//3
    }
}
