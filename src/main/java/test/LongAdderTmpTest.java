package test;

import java.util.concurrent.ConcurrentHashMap;

public class LongAdderTmpTest {

    public static void main(String[] args) {

        ConcurrentHashMap map = new ConcurrentHashMap();
        map.put("1", "2");
        map.size();


        System.out.printf("%x\n", 12345);//按16进制输出
        System.out.printf("%#x", 12345);// "d"表示输出带有十六进制标志的整数。

        String s=Integer.toBinaryString(3510593);

        System.out.println();

        System.out.println(s);

        String s1=Integer.toBinaryString(Float.floatToIntBits(3510593.0f));

        System.out.println(s1);


        int a = 1; int b = 2;
        b = a ^ b;
        a = a ^ b;
        b = a ^ b;

        System.out.println(a);
        System.out.println(b);




    }
}
