package com.kefeng.class20;


import java.util.HashMap;
import java.util.Map.Entry;

/**
 * 给定货币数组，每种面值货币有若干张，给定aim，求使用货币数组中的货币组成aim的方法数
 * 【1，2，1，2，1，1】，qim=4.有1+1+1+1=4，1+1+2=4，2+2=4，这三种方法
 */
public class CoinsWaySameValueSamePapper {

    /**定义货币面值和大小的数据结构*/
    public static class Info {
        public int[] coins;
        public int[] zhangs;

        public Info(int[] c, int[] z) {
            coins = c;
            zhangs = z;
        }
    }

    /**给定一个数组，转换成每种面值货币对应的张数*/
    public static Info getInfo(int[] arr) {
        HashMap<Integer, Integer> counts = new HashMap<>();
        for (int value : arr
        ) {
            if (!counts.containsKey(value)) {
                counts.put(value, 0);
            } else {
                counts.put(value, counts.get(value) + 1);
            }
        }
        int N = counts.size();
        int[] coins = new int[N];
        int[] zhangs = new int[N];
        int index = 0;
        for (Entry<Integer, Integer> entry : counts.entrySet()
        ) {
            coins[index] = entry.getKey();
            zhangs[index++] = entry.getValue();
        }
        return new Info(coins, zhangs);
    }

    public static int coinWays(int[] arr, int aim) {
        if (aim < 0 || arr == null || arr.length == 0) {
            return 0;
        }
        Info info = getInfo(arr);
        return process(info.coins, info.zhangs, 0, aim);
    }

    /**暴力递归求解*/
    public static int process(int[] coins, int[] zhangs, int index, int rest) {
        if (index == coins.length) {
            return rest == 0 ? 1 : 0;
        }
        int ways = 0;
        for (int zhang = 0; zhang < zhangs[index] && zhang * coins[index] <= rest; zhang++) {
            ways += process(coins, zhangs, index + 1, rest - zhang * coins[index]);
        }
        return ways;
    }

    /**动态规划求解*/
    public static int coinWaysDp(int[] arr, int aim){
        if (aim < 0 || arr == null || arr.length == 0){
            return 0;
        }
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] zhangs = info.zhangs;
        int N = coins.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ways = 0;
                for (int zhang = 0; zhang * coins[index] <= rest && zhang < zhangs[index]; zhang++) {
                    ways += dp[index + 1][rest - zhang * coins[index]];
                }
                dp[index][rest] = ways;
            }
        }
        return dp[0][aim];
    }

    /**优化后的动态规划*/
    public static int coinWaysDp1(int[] arr, int aim){
        if (aim < 0 || arr == null || arr.length == 0){
            return 0;
        }
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] zhangs = info.zhangs;
        int N = coins.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest < aim; rest++) {
                /**dp[index][rest]位置的值是index+1行上所有相隔coins[index]间距元素值相加的和，
                 * dp[index][rest - coins[index]]的值恰好是dp[index][rest]的一部分
                 * 但是这里货币张数不是无限的，dp[index][rest]和dp[index][rest - coins[index]]都是在有限的货币张数中累加
                 * 如果使用dp[index + 1][rest]+dp[index][rest - coins[index]]==dp[index][rest]则
                 * dp[index][rest]会多加一个coins[index]的值，所以需要减掉该值*/
                dp[index][rest] = dp[index + 1][rest];
                /**如果dp[index][rest - coins[index]]没有越界，则将二者相加*/
                if (rest - coins[index] >= 0){
                    dp[index][rest] += dp[index][rest - coins[index]];
                }
                /**如果dp[index][rest - coins[index]]累加后的结果没有越界，则减去dp[index + 1][rest - (coins[index] * (zhangs[index] + 1))]*/
                if (rest - coins[index] * (zhangs[index] + 1) >= 0){
                    dp[index][rest] -= dp[index + 1][rest - (coins[index] * (zhangs[index] + 1))];
                }
            }
        }
        return dp[0][aim];
    }

    public static int[] randomArray(int maxLen, int maxValue){
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
        int testTimes = 100;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue) + 1;
            int ans1 = coinWays(arr, aim);
            int ans2 = coinWaysDp(arr, aim);
            int ans3 = coinWaysDp1(arr, aim);
            if (ans1 != ans2 || ans1 != ans3){
                System.out.println("error");
            }
        }
        System.out.println("finished");
    }

}
