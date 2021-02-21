package concurrentJava;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;

/**
 * Description:
 * Author: weiwei.xie
 * date: 2020-12-26 17:51
 */
public class Test {


    public static void main(String[] args) {

        TimeUnit timeUnit = TimeUnit.SECONDS;


        System.out.println(1 << 10);
        System.out.println(System.nanoTime());
        System.out.println(timeUnit.toNanos(2));
        System.out.println(System.nanoTime() + timeUnit.toNanos(2));
        //AtomicInteger ai = new AtomicInteger(0);
        //
        //accumulateAndGet(10, Integer::sum, ai);
        //System.out.println(ai.get());
    }


    private static final int accumulateAndGet(int x, IntBinaryOperator accumulatorFunction, AtomicInteger ai) {
        return ai.updateAndGet(prev -> accumulatorFunction.applyAsInt(prev, x));
    }

    
}
