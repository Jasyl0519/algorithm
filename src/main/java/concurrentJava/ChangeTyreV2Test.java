package concurrentJava;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Author: weiwei.xie
 * date: 2021-01-25 17:54
 */
public class ChangeTyreV2Test {

    static CountDownLatch signal = new CountDownLatch(1);

    static CountDownLatch doSignal = new CountDownLatch(4);

    static ExecutorService executorService = Executors.newFixedThreadPool(4);


    public static void main(String[] args) throws InterruptedException {

        System.out.println("赛车进站！");
        
        changeTyre();

        TimeUnit.SECONDS.sleep(2);


        signal.countDown();
        System.out.println("开始更换！");

        doSignal.await();

        executorService.shutdown();

        System.out.println("轮胎更换完毕，出发！");


    }

    static void changeTyre() {

        for (int i = 0; i < 4; i++) {
            int cnt = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程" + cnt + "等待更换轮胎！");
                    try {
                        signal.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("线程" + cnt + "更换轮胎完成！");

                    doSignal.countDown();
                }
            });
        }
    }
}
