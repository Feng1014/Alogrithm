package com.kefeng.class21;


/**
 * 对于整数N，求被裂开的情况种类，要求组成N的序列中，前一个数不能大于后一个数
 * N=5，裂开情况，1+1+1+2，但不能是1+1+2+1
 */
public class SplitNumber {

    public static int ways(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return process(1, n);
    }

    /**
     * @param pre  pre已经裂开，接着从pre往后开始裂
     * @param rest 还有rest需要裂开
     * @return 返回总裂开情况数目
     */
    public static int process(int pre, int rest) {
        if (rest == 0) {
            return 1;
        }
        if (pre > rest) {
            return 0;
        }
        int ways = 0;
        for (int first = pre; first <= rest; first++) {
            ways += process(first, rest - first);
        }
        return ways;
    }

    /**
     * 动态规划裂开数
     *
     * @param n
     * @return
     */
    public static int waysDp(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int[][] dp = new int[n + 1][n + 1];
        for (int pre = 1; pre <= n; pre++) {
            dp[pre][0] = 1;
            dp[pre][pre] = 1;
        }
        for (int pre = n - 1; pre > 0; pre--) {
            for (int rest = pre + 1; rest <= n; rest++) {
                int ways = 0;
                for (int i = pre; i <= rest; i++) {
                    ways += dp[i][rest - i];
                }
                dp[pre][rest] = ways;
            }
        }
        return dp[1][n];
    }

    /**
     * 优化后的动态规划
     *
     * @param n
     * @return
     */
    public static int waysDp1(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int[][] dp = new int[n + 1][n + 1];
        for (int pre = 1; pre <= n; pre++) {
            dp[pre][0] = 1;
            dp[pre][pre] = 1;
        }
        for (int pre = n - 1; pre >= 0; pre--) {
            for (int rest = pre + 1; rest <= n; rest++) {
                dp[pre][rest] = dp[pre + 1][rest];
                dp[pre][rest] += dp[pre][rest - pre];
            }
        }
        return dp[1][n];
    }

    public static void main(String[] args) {
        int test = 55;
        System.out.println(ways(test));
        System.out.println(waysDp(test));
        System.out.println(waysDp1(test));
    }

}
