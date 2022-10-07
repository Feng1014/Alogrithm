package com.kefeng.class18;


/**
 * 背包问题
 */
public class Knapsack {

    /**
     * 给定背包容量，给定数组中的每个货物重量喝价值，在有限的情况下获得的最大价值
     * @param weight 每个货物的重量
     * @param value 每个货物的价值
     * @param bag 背包容量
     * @return
     */
    public static int maxValue(int[] weight, int[] value, int bag){
        if (weight == null || weight.length == 0 || value == null || value.length == 0 || weight.length != value.length){
            return 0;
        }
        return process(weight, value, 0, bag);
    }

    /**
     * 从第index个货物开始，后面的任意货物都可以放入背包
     * @param weight
     * @param value
     * @param index 货物位置
     * @param bag
     * @return
     */
    public static int process(int[] weight, int[] value, int index, int bag){
        if (bag < 0){
            return -1;
        }
        if (index == weight.length){
            return 0;
        }
        int v1 = process(weight, value, index + 1, bag);
        int v2 = 0;
        int next = process(weight, value, index + 1, bag - weight[index]);
        if (next != -1){
            v2 = value[index] + next;
        }
        return Math.max(v1, v2);
    }

    /**
     * 使用动态规划求解最大价值
     * @param weight
     * @param value
     * @param bag
     * @return
     */
    public static int maxValueDp(int[] weight, int[] value, int bag){
        if (weight == null || weight.length == 0 || value == null || value.length == 0 || weight.length != value.length){
            return 0;
        }
        int N = weight.length;
        int[][] dp = new int[N + 1][bag + 1];
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= bag; rest++) {
                int v1 = dp[index + 1][rest];
                int v2 = 0;
                int next = rest - weight[index] < 0 ? -1 : dp[index + 1][rest - weight[index]];
                if (next != -1){
                    v2 = value[index] + next;
                }
                dp[index][rest] = Math.max(v1, v2);
            }
        }
        return dp[0][bag];
    }

    public static void main(String[] args) {
        int[] weights = { 3, 2, 4, 7, 3, 1, 7 };
        int[] values = { 5, 6, 3, 19, 12, 4, 2 };
        int bag = 15;
        System.out.println(maxValue(weights, values, bag));
        System.out.println(maxValueDp(weights, values, bag));
    }
}
