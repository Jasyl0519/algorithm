package concurrent.test;

/**
 * @author zhengcheng
 * @date 2021/4/29 11:24 上午
 */
public class VolatileDemo {

    public static void main(String[] args) {
        MyThread myThread1 = new MyThread();
        MyThread myThread2 = new MyThread();

        Thread t1 = new Thread(myThread1, "A");
        t1.start();

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(System.nanoTime() + "main 开始执行");
        myThread2.stop();
        System.out.println(System.nanoTime() + "main 开始执行结束");

    }
}
