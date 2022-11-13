package com.kefeng.class38;

/**
 * 怪兽能力数组d，通过每个怪兽需要的钱为数组p，求小明通过所有的怪兽所需的最少钱数
 */
public class MoneyProblem {

    /**
     * 返回到index位置的怪兽，恰好花费了money的钱所获得的的最大能力
     *
     * @param d     怪兽能力数组
     * @param p     通过怪兽花费钱的数组
     * @param index 第index位置怪兽
     * @param money 恰好花费的钱数
     * @return
     */
    public static long process(int[] d, int[] p, int index, int money) {
        /**如果没遇到怪兽，通过money返回获得的最大能力*/
        if (index < 0) {
            return money == 0 ? 0 : -1;
        }
        /**遇到怪兽，但是不贿赂该怪兽所能获得的最大能力值*/
        long preMaxAbility = process(d, p, index - 1, money);
        long p1 = -1;
        if (preMaxAbility != -1 && preMaxAbility >= d[index]) {
            p1 = preMaxAbility;
        }
        /**遇到怪兽，贿赂该怪兽获得的最大能力值*/
        long preMaxAbility1 = process(d, p, index - 1, money - p[index]);
        long p2 = -1;
        if (preMaxAbility1 != -1) {
            p2 = d[index] + preMaxAbility1;
        }
        return Math.max(p1, p2);
    }

    /**
     * 通过所有怪兽所需要的最小钱数
     *
     * @param d
     * @param p
     * @return
     */
    public static long minMoney(int[] d, int[] p) {
        int allMoney = 0;
        for (int i = 0; i < p.length; i++) {
            allMoney += p[i];
        }
        int N = d.length;
        for (int money = 0; money < allMoney; money++) {
            if (process(d, p, N - 1, money) != -1) {
                return money;
            }
        }
        return allMoney;
    }

    /**
     * 来到index位置时，获得的能力为ability，通过后续所有怪兽所需的最少钱数
     *
     * @param d       怪兽能力数组
     * @param p       通过怪兽所需的钱数组
     * @param index   第index位置
     * @param ability 在index位置处获得的能力
     * @return
     */
    public static long process1(int[] d, int[] p, int index, int ability) {
        if (index == d.length) {
            return 0;
        }
        if (ability < d[index]) {
            return p[index] + process1(d, p, index + 1, ability + d[index]);
        } else {
            return Math.min(p[index] + process1(d, p, index + 1, ability + d[index]),
                    process1(d, p, index + 1, ability));
        }
    }

    public static long minMoney1(int[] d, int[] p) {
        return process1(d, p, 0, 0);
    }

    public static int[][] getRandomArray(int len, int value) {
        int N = (int) (Math.random() * len) + 1;
        int[][] arr = new int[2][N];
        for (int i = 0; i < N; i++) {
            arr[0][i] = (int) (Math.random() * value) + 1;
            arr[1][i] = (int) (Math.random() * value) + 1;
        }
        return arr;
    }

    public static void main(String[] args) {
        int len = 10;
        int value = 20;
        int testTimes = 100;
        for (int i = 0; i < testTimes; i++) {
            int[][] arr = getRandomArray(len, value);
            int[] d = arr[0];
            int[] p = arr[1];
            if (minMoney(d, p) != minMoney1(d, p)) {
                System.out.println("error");
            }
        }
        System.out.println("finished");
    }

}
