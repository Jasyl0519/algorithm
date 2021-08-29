package concurrentJava;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Author: weiwei.xie
 * date: 2021-08-08 下午6:13
 */
public class InheritableThreadLocalTransfer {
    private static final ThreadLocal<String>  THREAD_LOCAL = new InheritableThreadLocal<>();

    /**
     * InheritableThreadLocal values pertaining to this thread. This map is
     * maintained by the InheritableThreadLocal class.
     *
     * @param args
     * @throws InterruptedException
     */
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
