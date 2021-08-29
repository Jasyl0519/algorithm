package concurrentJava;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;

/**
 * Description:
 * Author: jasonz
 * date: 2020-12-26 17:51
 */
public class Test {


    public static void main(String[] args) {

        List<Integer> list = Lists.newArrayList(1,2,3,4,5,6,7,8,9,10);

        List<Integer> result = Lists.newArrayList();
        list.parallelStream().forEach((n) -> {
            result.add(n);

        });

        System.out.println(result);

        System.out.println(0x61c88647);




    }


    private static final int accumulateAndGet(int x, IntBinaryOperator accumulatorFunction, AtomicInteger ai) {
        return ai.updateAndGet(prev -> accumulatorFunction.applyAsInt(prev, x));
    }

    
}
