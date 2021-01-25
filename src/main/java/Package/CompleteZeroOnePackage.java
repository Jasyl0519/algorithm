package Package;

/**
 * Description:
 * Author: weiwei.xie
 * date: 2020-11-04 23:55
 */
public class CompleteZeroOnePackage {

    public static void main(String[] args) {
        int[] weight = { 1, 2, 3, 4, 5};
        int[] value = { 5, 1, 3, 2, 4};
        int v = 10;
        int n = 5;


        completeZeroOne(n, v, weight, value);

    }

    public  static void completeZeroOne(int n, int v, int[] weight, int[] value) {

        int[] dp = new int[v+1];
        for (int i = 0; i < n; i++) {
            for (int j = weight[i]; j <= v; j++) {

                dp[j] = Math.max(dp[j-weight[i]]+value[i], dp[j]);

            }

        }

        System.out.println(dp[v]);

    }
}
