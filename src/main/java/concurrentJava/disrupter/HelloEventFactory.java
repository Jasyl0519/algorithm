package concurrentJava.disrupter;

import com.lmax.disruptor.EventFactory;

/**
 * Description:
 * Author: jasonz
 * date: 2021-08-29 下午11:01
 */
public class HelloEventFactory implements EventFactory<HelloEvent> {

    @Override
    public HelloEvent newInstance() {
        return new HelloEvent();
    }
}