package design.Singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description:
 * Author: weiwei.xie
 * date: 2021-07-04 下午9:56
 */
public final class Hungry {


    private static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(1);

    private Hungry() {

    }


    public static ExecutorService getExecutorService() {

        return EXECUTOR_SERVICE;
    }

    

}
