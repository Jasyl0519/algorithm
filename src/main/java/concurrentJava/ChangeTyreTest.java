package concurrentJava;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Author: jasonz
 * date: 2021-01-25 17:54
 */
public class ChangeTyreTest {

    static CountDownLatch countDownLatch = new CountDownLatch(4);

    static ExecutorService executorService = Executors.newFixedThreadPool(4);


    public static void main(String[] args) throws InterruptedException {

        System.out.println("赛车进站！");

        changeTyre();

        countDownLatch.await();
        executorService.shutdown();

        System.out.println("轮胎更换完毕，出发！");


    }

    static void changeTyre() {

        for (int i = 0; i < 4; i++) {
            int cnt = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {

                    if (cnt == 1) {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("轮胎" + cnt + "更换完成！");

                    countDownLatch.countDown();
                }
            });
        }
    }
}
