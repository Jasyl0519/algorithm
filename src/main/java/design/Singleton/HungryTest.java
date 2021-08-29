package design.Singleton;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;

/**
 * Description:
 * Author: weiwei.xie
 * date: 2021-07-04 下午10:04
 */
public class HungryTest {

    public static void main(String[] args) {
        ExecutorService executorService = ExecutorServiceEnum.INSTANCE.getExecutorService();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("枚举的创建线程池！");
            }
        });
    }
}
