package Package;

/**
 * @author zhengcheng
 * @Description:
 * @date 2020/11/3
 */
public class ZeroOnePackage {


    /**
     * @param n   物品梳理
     * @param v   背包容量
     * @param weight 重量
     * @param value  价值
     */
    public static void zeroOnePack(int n, int v, int[] weight, int[] value) {

        /**
         * n+1 因为有0这个哨兵
         * v+1 因为有0这个哨兵
         */
        int[][] dp = new int[n + 1][v + 1];

        /**
         * 物品n循环,为什么从1开始,因为0开始的都初始化为0了
         */
        for (int i = 1; i <= n; i++) {
            /**
             * 背包容量循环
             */
            for (int j = 1; j <= v; j++) {

                if (weight[i] > j) {
                    /**
                     * 第i个容量大于当前背包容量(j),说明放不下了
                     * 那就是第i个物品背包容量为j的最大价值==第i-1个物品背包容量为j的最大价值
                     */
                    dp[i][j] = dp[i - 1][j];
                } else {
                    /**
                     * 第i个容量小于等于当前背包容量(j),说明放的下
                     * 那就是第i个物品背包容量为j的最大价值==max(不放第i个物品的最大价值, 放第i个物品的最大价值)
                     * 不放第i个物品的最大价值 == 第i-1个物品背包容量为j的最大价值
                     * 放第i个物品的最大价值 == (第i-1个物品背包容量为j-weight[i])的价值+value[i]
                     */
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]);
                }
            }
        }


        /**
         * 最大价值
         */
        int maxValue = dp[n][v];
        System.out.println(maxValue);
        int j = v;
        String numStr = "";
        /**
         * 物品从大到小遍历
         */
        for (int i = n; i > 0; i--) {
            /**
             * 如果dp[i][j] > dp[i - 1][j],说明放了第i个物品,输出i
             * 然后j-weight[i]继续倒推,直到j==0
             */
            if (dp[i][j] > dp[i - 1][j]) {
                numStr = i + " " + numStr;
                j = j - weight[i];
            }
            if (j == 0) {
                break;
            }
        }
        System.out.println(numStr);


    }


    public static void main(String[] args) {
        int[] weight = { 1, 2, 3, 4, 5};
        int[] value = { 5, 1, 3, 2, 4};
        int v = 10;
        int n = 5;

        zeroOnePack(n, v, weight, value);

        traceBack(n, v, weight, value);

    }


    /**
     * 0-1背包问题
     *
     * @param N      物品数量
     * @param W      背包容量
     * @param weight 物品重量
     * @param value  物品价格
     * @return 最大价值
     */
    private static void traceBack(int N, int W, int[] weight, int[] value) {

        int[] dp = new int[W + 1]; // 范围[0,W] 当前背包容量对应的物品总价值

        for (int i = 0; i < N; i++) { // 一件、一件的向包中放入每件物品

            for (int j = W; j >= weight[i]; j--) { //只要背包可以放入就放
                dp[j] = Math.max(dp[j - weight[i]] + value[i], dp[j]); // 比较放入物品之前与放入之后的价值哪个大

            }

        }
        System.out.println(dp[W]);

    }
}
