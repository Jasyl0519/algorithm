package design.Singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description:
 * Author: weiwei.xie
 * date: 2021-07-18 下午4:44
 */
public final class DoubleCheck {

    private volatile static ExecutorService EXECUTOR_SERVICE;

    private DoubleCheck() {

    }


    public static ExecutorService getExecutorService() {
        if (EXECUTOR_SERVICE == null) {
            synchronized (DoubleCheck.class) {
                if (EXECUTOR_SERVICE == null) {

                    EXECUTOR_SERVICE = Executors.newFixedThreadPool(1);
                }
            }
        }
        return EXECUTOR_SERVICE;
    }

}
