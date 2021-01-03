package test;

import java.util.concurrent.atomic.LongAdder;

public class LongAdderTest {

    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();

        System.out.println(longAdder.longValue());
        longAdder.add(10);
        System.out.println(longAdder.longValue());
        longAdder.increment();
        System.out.println(longAdder.longValue());
        longAdder.decrement();
        System.out.println(longAdder.longValue());

    }
}
