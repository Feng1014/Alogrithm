package com.kefeng.class25;

/**
 * 有一头成熟母牛，第二年生一头牛，小牛需要3年才成熟（能生小牛），第三年母牛接着生小牛，第四年母牛接着生小牛，第五年母牛在第二年生的小牛成熟也能生小牛，假设所有的牛都不会死，问N年后一共有多少头牛？
 */
public class FibonacciProblem {

    /**递归形式求n年后所有牛的数量*/
    public static int c1(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        return c1(n - 1) + c1(n - 3);
    }

    /**斐波那契数列求n年后所有牛的数量*/
    public static int c2(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        /**base数组为3阶矩阵*/
        int[][] base = {{1, 1, 0}, {0, 0, 1}, {1, 0, 0}};
        /**对base数组求n-3次方得到新数组res*/
        int[][] res = matrixMultiply(base, n - 3);
        /**3表示第三年的牛头数，2表示第二年的牛头数，1表示第一年的牛头数*/
        return 3 * res[0][0] + 2 * res[1][0] + res[2][0];
    }

    /**求base矩阵的n次方*/
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

    /**两个矩阵相乘*/
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
        System.out.println(c1(testTimes));
        System.out.println(c2(testTimes));
    }

}


