package concurrentJava;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Author: jasonz
 * date: 2021-01-25 17:54
 */
public class ChangeTyreV3Test {
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(4, new Runnable() {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程" + Thread.currentThread().getName() + "轮胎更换完毕，出发！");
        }
    });

    static ExecutorService executorService = Executors.newFixedThreadPool(4);


    public static void main(String[] args) throws InterruptedException {
        System.out.println("赛车进站！");
        changeTyre();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("驾驶员休息！");


    }


    static void changeTyre() {
        for (int i = 0; i < 4; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {

                    System.out.println("线程" + Thread.currentThread().getName() + "更换轮胎完成！");
                    try {
                        cyclicBarrier.await();
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程" + Thread.currentThread().getName() + "更换轮胎完成之后，修理工休息！");
                }
            });
        }
    }
}
