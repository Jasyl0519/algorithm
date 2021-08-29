package concurrentJava.disrupter;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;

/**
 * Description:
 * 一个生产者 多个消费者。多个消费者各自处理事件
 * https://zhuanlan.zhihu.com/p/45575008
 *
 *
 * Author: jasonz
 * date: 2021-08-29 下午10:59
 */
public class DemoOnePMultiC {



    public static void main(String[] args) throws Exception {
        //指定环形缓冲区的大小，必须是 2 的幂
        int bufferSize = 1024;

        //构建Disruptor
        Disruptor<HelloEvent> disruptor = new Disruptor<>(new HelloEventFactory(),
                bufferSize, DaemonThreadFactory.INSTANCE);

        //连接处理程序
        EventHandler<HelloEvent>[] consumers = new HelloEventHandler[2];
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new HelloEventHandler();
        }
        disruptor.handleEventsWith(consumers);
        disruptor.start();


        RingBuffer<HelloEvent> ringBuffer = disruptor.getRingBuffer();
        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.putLong(0, l);
            ringBuffer.publishEvent((event, sequence, buffer) -> event.set(buffer.getLong(0)), bb);
            Thread.sleep(1000);
        }
    }
}
