package concurrentJava.disrupter;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;

/**
 * Description:
 * Author: jasonz
 * date: 2021-08-29 下午9:05
 */
public class Demo {

    public static void main(String[] args) throws Exception {
        //指定环形缓冲区的大小，必须是 2 的幂
        int bufferSize = 1024;

        //构建Disruptor
        Disruptor<HelloEvent> disruptor = new Disruptor<>(new HelloEventFactory(),
                bufferSize, DaemonThreadFactory.INSTANCE);

        //连接处理程序
        disruptor.handleEventsWith(new HelloEventHandler());
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





