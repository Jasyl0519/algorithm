package design.Singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description:
 * Author: weiwei.xie
 * date: 2021-07-04 下午10:35
 */
public final class Lzay {

    private static ExecutorService EXECUTOR_SERVICE;


    private Lzay() {

    }


    public static ExecutorService getExecutorService() {
        if (EXECUTOR_SERVICE == null) {
            EXECUTOR_SERVICE = Executors.newFixedThreadPool(1);
        }
        return EXECUTOR_SERVICE;
    }

}
