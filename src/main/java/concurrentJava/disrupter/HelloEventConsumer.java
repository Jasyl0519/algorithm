package concurrentJava.disrupter;

import com.lmax.disruptor.WorkHandler;

/**
 * Description:
 * Author: jasonz
 * date: 2021-08-29 下午11:12
 */
public class HelloEventConsumer implements WorkHandler<HelloEvent> {
    @Override
    public void onEvent(HelloEvent event) throws Exception {
        System.out.println(Thread.currentThread().getName() +  " hello " + event.getValue());

    }
}
