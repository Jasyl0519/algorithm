package concurrentJava;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;

/**
 * Description:
 * Author: weiwei.xie
 * date: 2020-12-26 17:51
 */
public class Test {


    public static void main(String[] args) {
        AtomicInteger ai = new AtomicInteger(0);

        accumulateAndGet(10, Integer::sum, ai);
        System.out.println(ai.get());
    }


    private static final int accumulateAndGet(int x, IntBinaryOperator accumulatorFunction, AtomicInteger ai) {
        return ai.updateAndGet(prev -> accumulatorFunction.applyAsInt(prev, x));
    }

    
}
