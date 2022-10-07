package com.kefeng.class20;


/**
 * 给定一个货币数组，数组里每种面值的货币有无限张，求组成aim的方法有多少种？
 */
public class CoinsWayNoLimit {

    public static int coinWays(int[] arr, int aim) {
        if (aim < 0 || arr == null || arr.length == 0) {
            return 0;
        }
        return process(arr, 0, aim);
    }

    /**递归求解时，需要使用for循环，对每种货币面值遍历一边*/
    public static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return rest == 0 ? 1 : 0;
        }
        int ways = 0;
        for (int zhang = 0; (zhang * arr[index]) <= rest; zhang++) {
            ways += process(arr, index + 1, rest - (zhang * arr[index]));
        }
        return ways;
    }

    /**动态规划求解*/
    public static int coinWaysDp(int[] arr, int aim){
        if (aim < 0 || arr == null || arr.length == 0){
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ways = 0;
                for (int zhang = 0; (zhang * arr[index]) <= rest; zhang++) {
                    ways += dp[index + 1][rest - (zhang * arr[index])];
                }
                dp[index][rest] = ways;
            }
        }
        return dp[0][aim];
    }

    /**优化动态规划*/
    public static int coinWaysDp1(int[] arr, int aim){
        if (arr == null || arr.length == 0 || aim < 0){
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N +1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                /**dp[index][rest]位置的值是index+1行上所有相隔arr[index]间距元素值相加的和，
                 * dp[index][rest - arr[index]]的值恰好是dp[index][rest]的一部分
                 * 所以只需要把dp[index + 1][rest]+dp[index][rest - arr[index]]就是dp[index][rest]的值*/
                dp[index][rest] = dp[index + 1][rest];
                if (rest - arr[index] >= 0){
                    dp[index][rest] += dp[index][rest - arr[index]];
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
        int maxLen = 10;
        int maxValue = 20;
        int testTimes = 1000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = coinWays(arr, aim);
            int ans2 = coinWaysDp(arr, aim);
            int ans3 = coinWaysDp1(arr, aim);
            if (ans1 != ans2 || ans2 != ans3){
                System.out.println("error");
            }
        }
        System.out.println("finished");
    }

}
