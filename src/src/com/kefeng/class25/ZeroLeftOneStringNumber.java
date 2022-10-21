package com.kefeng.class25;

/**
 * 给定N，求N个长度的只含0和1的字符串，满足任意0左边都有1的字符串有多少个？
 */
public class ZeroLeftOneStringNumber {

    /**使用递归*/
    public static int getNums(int n) {
        if (n < 0) {
            return 0;
        }
        return process(1, n);
    }

    /**
     * 递归体
     * @param i 表示从i位置开始计算。i到结束有多少个字符串种类
     * @param n 表示n个0或1的字符串
     * @return
     */
    public static int process(int i, int n) {
        if (i == n - 1) {
            return 2;
        }
        if (i == n) {
            return 1;
        }
        return process(i + 1, n) + process(i + 2, n);
    }

    public static int getNums1(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        int[][] base = {{1, 1}, {1, 0}};
        int[][] res = matrixMultiply(base, n - 2);
        return 2 * res[0][0] + res[1][0];
    }

    public static int[][] matrixMultiply(int[][] base, int n) {
        int[][] res = new int[base.length][base[0].length];
        for (int i = 0; i < base.length; i++) {
            res[i][i] = 1;
        }
        int[][] t = base;
        for (; n != 0; n >>= 1) {
            if ((n & 1) != 0) {
                res = matrixCal(res, t);
            }
            t = matrixCal(t, t);
        }
        return res;
    }

    public static int[][] matrixCal(int[][] p1, int[][] p2) {
        int N = p1.length;
        int M = p2[0].length;
        int k = p2.length;
        int[][] res = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                for (int l = 0; l < k; l++) {
                    res[i][j] += p1[i][l] * p2[l][j];
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int testTimes = 10;
        System.out.println(getNums(testTimes));
        System.out.println(getNums1(testTimes));
    }

}
