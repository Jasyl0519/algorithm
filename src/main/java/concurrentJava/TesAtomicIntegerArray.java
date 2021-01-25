package concurrentJava;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Description:
 * Author: weiwei.xie
 * date: 2020-12-26 17:51
 */
public class TesAtomicIntegerArray {


    private static Unsafe unsafe;
    public static void main(String[] args) throws Exception {
        Class c = TesAtomicIntegerArray.class.getClassLoader().loadClass("sun.misc.Unsafe");
        Field f = c.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        unsafe = (Unsafe)f.get(c);

        int[] arr = { 0, 1, 2 };
        AtomicIntegerArray aia = new AtomicIntegerArray(arr);


        System.out.println(unsafe.arrayBaseOffset(arr.getClass()));
        System.out.println(unsafe.arrayIndexScale(arr.getClass()));
        System.out.println(aia.get(1));
        System.out.println((1 <<2) + 16);
        //System.out.println(4 & 3);
        //System.out.println(4 | 3);
        //System.out.println(4 ^ 3);
        //System.out.println(31- Integer.numberOfLeadingZeros(1));
        //System.out.println(31- Integer.numberOfLeadingZeros(2));
        //System.out.println(31- Integer.numberOfLeadingZeros(3));
    }


}
