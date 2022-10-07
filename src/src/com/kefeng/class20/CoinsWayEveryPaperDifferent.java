package com.kefeng.class20;


/**
 * 给定一个货币数组，每个值是一张货币，再给定一个值aim，求使用货币数组构成aim的方法数
 */
public class CoinsWayEveryPaperDifferent {

    public static int coinWays(int[] arr, int aim) {
        return process(arr, 0, aim);
    }

    /**
     * 从左往右的递归模型，从index位置的所有货币构成rest值有多少种
     */
    public static int process(int[] arr, int index, int rest) {
        if (rest < 0) {
            return 0;
        }
        if (index == arr.length) {
            return rest == 0 ? 1 : 0;
        }
        return process(arr, index + 1, rest) + process(arr, index + 1, rest - arr[index]);
    }

    /**
     * 动态规划求使用货币数组中所有元素组成aim的方法数
     */
    public static int coinWaysDp(int[] arr, int aim) {
        if (aim == 0) {
            return 1;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest < aim; rest++) {
                dp[index][rest] = dp[index + 1][rest] + (rest - arr[index] >= 0 ? dp[index + 1][rest - arr[index]] : 0);
            }
        }
        return dp[0][aim];
    }

    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) ((Math.random() * maxValue) + 1);
        }
        return arr;
    }

    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 30;
        int testTimes = 1000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            if (coinWays(arr, aim) == coinWaysDp(arr, aim)){
                System.out.println("finished");
            }
        }
    }

}
