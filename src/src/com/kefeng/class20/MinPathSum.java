package com.kefeng.class20;


import java.util.Arrays;

/**
 * 二维矩阵中，从左上走到右下最小的路径和。只能向下或者向右前进
 */
public class MinPathSum {

    /**普通的动态规划*/
    public static int minSum(int[][] m){
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0){
            return 0;
        }
        int row = m.length;
        int col = m[0].length;
        int[][] dp = new int[row][col];
        dp[0][0] = m[0][0];
        for (int i = 1; i < col; i++) {
            dp[0][i] = dp[0][i - 1] + m[0][i];
        }
        for (int j = 1; j < row; j++) {
            dp[j][0] = dp[j - 1][0] + m[j][0];
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + m[i][j];
            }
        }
        return dp[row - 1][col - 1];
    }

    /**优化动态规划，对二维空间压缩成一维空间*/
    public static int minSum1(int[][] m){
        if (m == null || m.length == 0 || m[0].length == 0 || m[0] == null){
            return 0;
        }
        int row = m.length;
        int col = m[0].length;
        int[] arr = new int[col];
        arr[0] = m[0][0];
        /**对矩阵第一行数组，每到一个位置仅依赖于该位置左边的路径和*/
        for (int i = 1; i < col; i++) {
            arr[i] = arr[i - 1] + m[0][i];
        }
        /**从矩阵第二行开始的元素，这行首位值依赖上行首位值；其余元素依赖这行左边元素和该位置上一行元素*/
        for (int i = 1; i < row; i++) {
            arr[0] += m[i][0];
            for (int j = 1; j < col; j++) {
                arr[j] = Math.min(arr[j], arr[j - 1]) + m[i][j];
            }
        }
        return arr[col - 1];
    }

    public static int[][] generateRandomMatrix(int rowSize, int colSize){
        if (rowSize < 0 || colSize < 0){
            return null;
        }
        int[][] arr = new int[rowSize][colSize];
        for (int i = 0; i != arr.length; i++) {
            for (int j = 0; j != arr[0].length; j++) {
                arr[i][j] = (int) (Math.random() * 100);
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        int row = 10;
        int col = 10;
        int[][] m = generateRandomMatrix(row, col);
//        for (int i = 0; i < row; i++) {
//            for (int j = 0; j < col; j++) {
//                System.out.print(m[i][j] + " ");
//            }
//            System.out.println();
//        }
//        String str = Arrays.deepToString(m);
//        System.out.println(str);
        System.out.println(minSum(m));
        System.out.println(minSum1(m));
    }

}
