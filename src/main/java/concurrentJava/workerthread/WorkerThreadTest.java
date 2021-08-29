package concurrentJava.workerthread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Author: weiwei.xie
 * date: 2021-08-15 下午2:16
 */
public class WorkerThreadTest {
    public static void main(String[] args) {
        //创建一个3个工人的流水线
        Pipeline pipeline = new Pipeline(3, new ArrayBlockingQueue(100));
        //创建一个client生成零件
        Client client1 = new Client(pipeline, "client1");
        new Thread(client1).start();
    }
}

class Mobile {
    //组装
    public void assemble(String name) {
        System.out.println(name + "组装手机");
    }
}

class Client implements Runnable {
    private String name;
    private Pipeline pipeline;
    public Client(Pipeline pipeline, String name) {
        this.pipeline = pipeline;
        this.name = name;
    }
    @Override
    public void run() {
        //把零件放到流水线上，满了就阻塞
        while (true) {
            Mobile mobile = new Mobile();
            try {
                pipeline.put(mobile);
                TimeUnit.SECONDS.sleep(1);
                System.out.println(name + "生产零件");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Worker implements Runnable {
    private String name;
    private Pipeline pipeline;
    public Worker(Pipeline pipeline, String name) {
        this.pipeline = pipeline;
        this.name = name;
    }
    @Override
    public void run() {
        //工人从流水线取零件组装手机，没有零件就阻塞
        while (true) {
            Mobile mobile = null;
            try {
                mobile = pipeline.get();
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mobile.assemble(name);
        }
    }
}

class Pipeline {
    private BlockingQueue<Mobile> queue;
    public Pipeline(int workerNum, BlockingQueue queue) {
        this.queue = queue;
        //初始化worker线程
        for (int i = 0; i < workerNum; i++) {
            new Thread(new Worker(this, "worker"+i)).start();
        }
    }
    public void put(Mobile mobile) throws InterruptedException {
        queue.put(mobile);
    }
    public Mobile get() throws InterruptedException {
        return queue.take();
    }
}
