package concurrent.test;

import java.util.Date;

/**
 * @author zhengcheng
 * @date 2021/4/29 11:24 上午
 */
public class MyThread implements Runnable {

    private static volatile boolean flag = true;
    @Override
    public synchronized void run() {

        while (flag) {

            System.out.println(System.nanoTime() + Thread.currentThread().getName() + ":" + flag);
        }

        System.out.println(System.nanoTime() + Thread.currentThread().getName() + ":" + flag);

    }

    public void stop() {
        System.out.println(System.nanoTime() + Thread.currentThread().getName() + "before修改flag为false");
        flag = false;
        System.out.println(System.nanoTime() + Thread.currentThread().getName() + "alter修改flag为false");

    }
}
