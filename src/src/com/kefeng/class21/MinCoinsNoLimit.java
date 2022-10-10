package com.kefeng.class21;


/**
 * 给定货币数组，每种面值的货币有无限张，给定一个aim，问组成aim的货币张数最少的情况是多少张。
 */
public class MinCoinsNoLimit {

    public static int minCoins(int[] arr, int aim) {
        return process(arr, 0, aim);
    }

    /**
     * 使用Integer.MAX_VALUE表示无法组成aim
     *
     * @param arr   货币数组
     * @param index 从index位置开始组成aim
     * @param rest  还有rest钱需要被组成
     * @return 返回index索引处还有rest钱被组成，所需的最少货币张数
     */
    public static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        } else {
            int ways = Integer.MAX_VALUE;
            for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
                int next = process(arr, index + 1, rest - zhang * arr[index]);
                if (next != Integer.MAX_VALUE) {
                    ways = Math.min(ways, zhang + next);
                }
            }
            return ways;
        }
    }

    /**
     * 动态规划求解aim面值的货币最小的组成张数
     *
     * @param arr
     * @param aim
     * @return
     */
    public static int minCoinsDp(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int i = 1; i <= aim; i++) {
            dp[N][i] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ways = Integer.MAX_VALUE;
                /**对于index索引rest钱的组成即dp[index][rest]，依赖的是dp[index+1][rest-zhang*arr[index]]...
                 * 例如面值为5需要组成30钱的最少货币张数。不需要5，下一种货币组成30；需要1张5，下一种货币组成25；...；求这每种情况下的最少货币张数*/
                for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
                    int next = dp[index + 1][rest - zhang * arr[index]];
                    if (next != Integer.MAX_VALUE) {
                        ways = Math.min(ways, zhang + next);
                    }
                }
                dp[index][rest] = ways;
            }
        }
        return dp[0][aim];
    }

    /**
     * 优化的动态规划求解aim面值的货币最小的组成张数
     *
     * @param arr
     * @param aim
     * @return
     */
    public static int minCoinsDp1(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int i = 1; i <= aim; i++) {
            dp[N][i] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                /**优化动态规划不需要for循环。对于index索引rest钱的组成观察index索引rest - arr[index]钱位置，看二者什么关系
                 * 例如面值为5需要组成30钱的最少货币张数。不需要5，下一种货币组成30；需要1张5，下一种货币组成25；需要两张5，下一种货币组成20...求这每种情况下的最少货币张数
                 * 面值为5需要组成25钱的最少货币张数，同样比较了组成20钱的最少张数
                 * 所以面值为5需要组成30钱的最少货币张数=【面值为5需要组成25钱的最少货币张数+1】和【不需要5，下一种货币组成30的最少张数】二者的最小值*/
                dp[index][rest] = dp[index + 1][rest];
                if (rest - arr[index] >= 0 && dp[index][rest - arr[index]] != Integer.MAX_VALUE) {
                    dp[index][rest] = Math.min(dp[index][rest], dp[index][rest - arr[index]] + 1);
                }
            }
        }
        return dp[0][aim];
    }

    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 30;
        int testTimes = 100;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = minCoins(arr, aim);
            int ans2 = minCoinsDp(arr, aim);
            int ans3 = minCoinsDp1(arr, aim);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("error");
            }
        }
        System.out.println("finished");
    }

}
