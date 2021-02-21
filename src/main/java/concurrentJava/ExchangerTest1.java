package concurrentJava;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Author: weiwei.xie
 * date: 2021-02-21 下午5:04
 */
public class ExchangerTest1 {

    static Exchanger<String> exchanger = new Exchanger<String>();
    static ExecutorService executorService = Executors.newFixedThreadPool(4);


    public static void main(String[] args) {

        for (int i = 0; i < 2; i++) {
            int index = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        String value =
                                exchanger.exchange("我是线程 " + Thread.currentThread().getName() + "index=" + index);
                        System.out.println("我是线程 " + Thread.currentThread().getName() + "得到=" + value);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });

        }

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
