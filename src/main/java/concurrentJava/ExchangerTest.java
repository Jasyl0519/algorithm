package concurrentJava;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

import static java.awt.image.DataBuffer.TYPE_INT;


/**
 *
 * 板车A装了3辆新车,板车B没有装任何东西，2辆车来到某个点，车辆A和车辆B装载的车辆互换。
 *
 */
public class ExchangerTest {
    Exchanger<DataBuffer> exchanger = new Exchanger<DataBuffer>();
    DataBuffer initialEmptyBuffer = new DataBuffer();
    DataBuffer initialFullBuffer =  new DataBuffer();

    class FillingLoop implements Runnable {
        public void run() {
            DataBuffer currentBuffer = initialEmptyBuffer;
            try {
                while (currentBuffer != null) {
                    if (currentBuffer.isFull()) {
                        currentBuffer = exchanger.exchange(currentBuffer);
                    } else {
                        int num = (int) (1 + Math.random() * 100);
                        Car car = new Car("car-" + num);
                        //TimeUnit.SECONDS.sleep(2);
                        currentBuffer.add(car);
                        System.out.println("装了新车" + car.getName() + "到板车A！");
                    }
                }
            } catch (InterruptedException ex) {
            }
        }
    }

    class EmptyingLoop implements Runnable {
        public void run() {
            DataBuffer currentBuffer = initialFullBuffer;
            try {
                while (currentBuffer != null) {
                    if (currentBuffer.isEmpty()) {
                        System.out.println("板车B等待装车！");
                        currentBuffer = exchanger.exchange(currentBuffer);
                        System.out.println("两辆板车交换成功！");
                    } else {
                        Car car = currentBuffer.remove();
                        System.out.println("板车B上新车" + car.getName() + "卸货完成！");
                    }
                }
            } catch (InterruptedException ex) {
            }
        }
    }

    void start() {
        new Thread(new FillingLoop()).start();
        new Thread(new EmptyingLoop()).start();
    }

    class DataBuffer {
        private ArrayList<Car> cars;
        public DataBuffer() {
            cars = new ArrayList();
        }
        public boolean isEmpty() {
            return cars.isEmpty();
        }
        //只能装3辆车
        public boolean isFull() {
            return cars.size() == 3;
        }
        public void add(Car car) {
            cars.add(car);
        }
        public Car remove() {
            return cars.remove(0);
        }
    }

    class Car {
        private String name;
        public Car(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) {
        ExchangerTest exchangerTest = new ExchangerTest();
        exchangerTest.start();
    }
}



