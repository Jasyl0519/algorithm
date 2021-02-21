package concurrentJava;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Author: weiwei.xie
 * date: 2021-01-25 17:54
 */
public class SemaphoreTest {

    static Semaphore semaphore = new Semaphore(4);
    static ExecutorService executorService = Executors.newFixedThreadPool(7);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 7; i++) {
            int carNum = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    doAddGas(carNum);
                }
            });
        }
    }

    static void doAddGas(int carNum) {
        if (semaphore.tryAcquire()) {
            try {
                addGas(carNum);
            } finally {
                System.out.println("时间:" + new Date() + " 车辆" + carNum + "车辆驶出加油站！");
                semaphore.release();
            }
        } else {
            if (carNum == 5) {
                //第5辆车不愿意排队，看到没有空余的加油位置，直接离开加油站
                System.out.println("时间:" + new Date() + " 车辆" + carNum + "直接离开加油站！");
            } else {
                try {
                    semaphore.acquire();
                    addGas(carNum);
                } catch (InterruptedException e) {
                } finally {
                    System.out.println("时间:" + new Date() + " 车辆" + carNum + "车辆驶出加油站！");
                    semaphore.release();
                }
            }
        }
    }

    static void addGas(int carNum) {
        System.out.println("车辆" + carNum + "开始加油！");
        try {
            if (carNum == 1) {
                //第一辆车加油慢一点
                TimeUnit.SECONDS.sleep(3);
            } else {
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
        }
        System.out.println("时间:" + new Date() + " 车辆" + carNum + "加油完成！");
    }
}
