package design.Singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description:
 * Author: weiwei.xie
 * date: 2021-07-18 下午5:30
 */
public enum ExecutorServiceEnum {

    INSTANCE;

    private final ExecutorService executorService;

    ExecutorServiceEnum() {
        this.executorService = Executors.newFixedThreadPool(1);
    }


    public ExecutorService getExecutorService() {
        return executorService;
    }
}
