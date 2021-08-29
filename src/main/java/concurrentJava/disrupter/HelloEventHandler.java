package concurrentJava.disrupter;

import com.lmax.disruptor.EventHandler;

/**
 * Description:
 * 消费者处理器来处理事件
 *
 * Author: jasonz
 * date: 2021-08-29 下午11:01
 */
public class HelloEventHandler implements EventHandler<HelloEvent> {

    @Override
    public void onEvent(HelloEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println(Thread.currentThread().getName() +  " hello " + event.getValue());

    }
}
