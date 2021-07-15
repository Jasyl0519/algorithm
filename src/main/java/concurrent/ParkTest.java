package concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class ParkTest {


    public static void main(String[] args) {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);

                } catch (InterruptedException e) {
                }
                System.out.println("1");
                LockSupport.park(this);
                System.out.println("2");

            }
        });

        thread1.start();

        try {
            TimeUnit.SECONDS.sleep(5);

        } catch (InterruptedException e) {
        }
        System.out.println("3");
        thread1.interrupt();
        System.out.println("4");

        System.out.println(thread1.isInterrupted());
        System.out.println(Thread.interrupted());


    }
}
