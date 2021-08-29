package design.Singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description:
 * Author: jasonz
 * date: 2021-07-18 下午5:11
 */
public final class StaticInnerClass {

    private StaticInnerClass() {
    }

    private static class Holder {
        private static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(1);
    }

    public static ExecutorService getExecutorService() {
        return Holder.EXECUTOR_SERVICE;
    }

}
