package concurrentJava;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Author: weiwei.xie
 * date: 2021-08-08 下午6:04
 */
public class ThreadLocalTransfer {
    private static final ThreadLocal<String>  THREAD_LOCAL = new ThreadLocal();

    public static void main(String[] args) throws InterruptedException {
        THREAD_LOCAL.set("张三");

        System.out.println(Thread.currentThread().getName() + ":"+ THREAD_LOCAL.get());


        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ":"+ THREAD_LOCAL.get());
            }
        }).start();

        TimeUnit.SECONDS.sleep(1);

    }
}
