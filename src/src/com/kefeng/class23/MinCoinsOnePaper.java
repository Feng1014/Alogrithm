package com.kefeng.class23;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

/**
 * 给定一个货币数组，每个值认为是一张货币，其中的值是正数。再给定一个数aim，返回组成aim的最少货币数
 */
public class MinCoinsOnePaper {

    public static int minCoins(int[] arr, int aim) {
        return process(arr, 0, aim);
    }

    /**
     * 暴力递归的定义，index位置开始分配rest钱，最少的货币数是多少
     *
     * @param arr   货币数组
     * @param index 开始索引的位置
     * @param rest  剩余需要分配的钱
     * @return index位置开始分配rest所需的最少货币数
     */
    public static int process(int[] arr, int index, int rest) {
        if (rest < 0) {
            return Integer.MAX_VALUE;
        }
        if (index == arr.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        } else {
            int p1 = process(arr, index + 1, rest);
            int p2 = process(arr, index + 1, rest - arr[index]);
            if (p2 != Integer.MAX_VALUE) {
                p2++;
            }
            return Math.min(p1, p2);
        }
    }

    /**
     * 动态规划求 从index索引开始分配rest所需的最少货币数
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
                int p1 = dp[index + 1][rest];
                int p2 = rest - arr[index] >= 0 ? dp[index + 1][rest - arr[index]] : Integer.MAX_VALUE;
                if (p2 != Integer.MAX_VALUE) {
                    p2++;
                }
                dp[index][rest] = Math.min(p1, p2);
            }
        }
        return dp[0][aim];
    }

    /**
     * 每种货币的面值和个数的对象
     */
    public static class Info {
        public int[] coins;
        public int[] zhangs;

        public Info(int[] c, int[] z) {
            coins = c;
            zhangs = z;
        }
    }

    /**
     * 把货币数组转为对象的方法
     *
     * @param arr
     * @return
     */
    public static Info getInfo(int[] arr) {
        HashMap<Integer, Integer> counts = new HashMap<>();
        for (int ar : arr
        ) {
            if (!counts.containsKey(ar)) {
                counts.put(ar, 1);
            } else {
                counts.put(ar, counts.get(ar) + 1);
            }
        }
        int N = arr.length;
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

    public static int minCoinsDp1(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] zhangs = info.zhangs;
        int N = coins.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int i = 1; i <= aim; i++) {
            dp[N][i] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                for (int zhang = 1; zhang * coins[index] <= rest && zhang <= zhangs[index]; zhang++) {
                    if (rest - zhang * coins[index] >= 0 &&
                            dp[index + 1][rest - zhang * coins[index]] != Integer.MAX_VALUE) {
                        dp[index][rest] = Math.min(dp[index][rest], zhang + dp[index + 1][rest - zhang * coins[index]]);
                    }
                }
            }
        }
        return dp[0][aim];
    }

    /**
     * 滑动窗口求aim的最少货币数
     * @param arr
     * @param aim
     * @return
     */
    public static int minCoinsDp2(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] zhangs = info.zhangs;
        int N = coins.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int i = 1; i <= aim; i++) {
            dp[N][i] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int mod = 0; mod < Math.min(aim + 1, coins[index]); mod++) {
                /**当前面值X，则将mod,mod+X,mod+2*X,mod+3*X...放入滑动窗口*/
                LinkedList<Integer> W = new LinkedList<>();
                W.add(mod);
                dp[index][mod] = dp[index + 1][mod];
                for (int r = mod + coins[index]; r <= aim; r += coins[index]) {
                    while (!W.isEmpty() && (dp[index + 1][W.peekLast()] == Integer.MAX_VALUE ||
                            dp[index + 1][W.peekLast()] + compensate(W.peekLast(), r, coins[index]) >= dp[index + 1][r])) {
                        W.pollLast();
                    }
                    W.addLast(r);
                    int overdue = r - coins[index] * (zhangs[index] + 1);
                    if (W.peekFirst() == overdue) {
                        W.pollFirst();
                    }
                    dp[index][r] = dp[index + 1][W.peekFirst()] + compensate(W.pollFirst(), r, coins[index]);
                }
            }
        }
        return dp[0][aim];
    }

    public static int compensate(int pre, int cur, int coin) {
        return (cur - pre) / coin;
    }

    public static int[] generateArray(int maxLen, int maxValue) {
        int[] arr = new int[maxLen];
        for (int i = 0; i < maxLen; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 20;
        int testTimes = 1000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = minCoins(arr, aim);
            int ans2 = minCoinsDp(arr, aim);
            int ans3 = minCoinsDp1(arr, aim);
            int ans4 = minCoinsDp2(arr, aim);
            if (ans1 != ans2 || ans1 != ans3 ) {
                System.out.println("error");
            }
        }
        System.out.println("finished");
    }

}
