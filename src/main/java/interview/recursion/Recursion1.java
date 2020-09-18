package interview.recursion;

/**
 * 题目：1个细胞，每1小时分裂成2个，分裂3次之后，本体会死掉。n个小时之后，有多少个细胞？
 * 比如0小时的时候，培养皿中放入1个细胞，1小时的时候分裂成2个，2小时的时候分裂成4个，3小时本来分裂成8个，但是最先的1个本体已经分裂了3次，它死掉，最终剩下7个。下面是列举的时间以及对应的细胞数：
 * 0 1 2 3
 * 1 2 4 7
 * <p>
 * 将n个小时的细胞数定义为f(n)，像这种知道f(0)，f(1)，f(2)...，要你推算f(n)的题目，是面试的时候经常遇到的一类题目。
 * f(n)的结果与前面已知的f(n-1)或者f(n-2)...f(n-k)有关，重点是要找出f(n)的递推公式，有了递推公式之后，可以很容易用递归来实现，就像斐波拉契数列f(n)=f(n-1)+f(n-2)用递归来实现类似。
 *
 * @author zhengcheng
 * @Description:
 * @date 2020/9/18
 */
public class Recursion1 {

    public static void main(String[] args) {
        for (int i = 4; i < 8; i++) {
            System.out.println(recursion(i));
        }

    }

    public static Integer recursion(int n) {
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return 2;
        }
        if (n == 2) {
            return 4;
        }
        if (n == 3) {
            return 7;
        }
        return recursion(n - 1) * 2 - recursion(n - 4);
    }
}
