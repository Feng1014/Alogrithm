package com.kefeng.class22;


/**
 * N皇后问题，在N*N的矩阵中放置皇后，要求每个皇后和之前放置的皇后不同行，不同列，不同对角线，求所有放置的可能数
 */
public class NQueens {

    public static int putWays(int n) {
        if (n < 1) {
            return 0;
        }
        int[] record = new int[n];
        return process(record, 0, n);
    }

    /**
     * 从i行开始放置皇后，放置到n行，每次放置的结果存在arr数组中，返回从i行开始放置到n行的可能情况数目
     *
     * @param record
     * @param index
     * @param n
     * @return
     */
    public static int process(int[] record, int index, int n) {
        if (index == n) {
            return 1;
        }
        int ways = 0;
        for (int j = 0; j < n; j++) {
            if (isValid(record, index, j)) {
                record[index] = j;
                ways += process(record, index + 1, n);
            }
        }
        return ways;
    }

    /**
     * 第i个皇后放在j位置上，判断第i个皇后和前面i-1个皇后有无冲突
     *
     * @param record
     * @param i
     * @param j
     * @return
     */
    public static boolean isValid(int[] record, int i, int j) {
        for (int k = 0; k < i; k++) {
            if (j == record[k] || Math.abs(j - record[k]) == Math.abs(i - k)) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        int n = 8;
        System.out.println(putWays(n));
    }

}
