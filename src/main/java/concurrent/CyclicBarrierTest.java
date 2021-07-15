package concurrent;

import java.util.concurrent.*;

public class CyclicBarrierTest {


    public static void main(String[] args) throws InterruptedException {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                System.out.println("run " + Thread.currentThread().getName());
            }
        });
        ExecutorService executorService = Executors.newFixedThreadPool(3);


        for (int i = 0; i < 3; i++) {
            int a = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("thread " + a);
                    if (a == 1) {
                        try {

                            TimeUnit.SECONDS.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    System.out.println("threadxx " + a + " " + Thread.currentThread().getName());

                }
            });
        }


        System.out.println("333");
        //executorService.shutdown();
        System.out.println("4444");



    }
}
