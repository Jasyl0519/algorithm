package concurrentJava.disrupter;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Description:
 * 一个生产者 多个消费者。多个消费者处理一批事件
 *
 * https://cloud.tencent.com/developer/article/1384103
 *
 *
 * Author: jasonz
 * date: 2021-08-29 下午10:59
 */
public class DemoOnePMultiC2 {



    public static void main(String[] args) throws Exception {
        //指定环形缓冲区的大小，必须是 2 的幂
        int bufferSize = 1024;

        ThreadFactory producerFactory = Executors.defaultThreadFactory();


        //构建Disruptor
        Disruptor<HelloEvent> disruptor = new Disruptor<>(new HelloEventFactory(),
                bufferSize, producerFactory, ProducerType.SINGLE, new YieldingWaitStrategy());

        // 设置消费者
        WorkHandler<HelloEvent>[] consumers = new HelloEventConsumer[5];
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new HelloEventConsumer();
        }
        disruptor.handleEventsWithWorkerPool(consumers);
        // 启动 Disruptor
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
