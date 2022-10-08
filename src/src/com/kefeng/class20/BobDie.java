package com.kefeng.class20;


/**
 * Bob在N*M的棋盘中上下左右自由行走，Bob初始位置在（row，col）位置，走出棋盘意味着Bob死亡，问K步后Bob还存活的概率是多少？
 */
public class BobDie {

    public static double livePosibility(int row, int col, int K, int N, int M) {
        /**假设棋盘无限大，则Bob一直存活，经过K步后，有4^K种可能性*/
        return (double) process(row, col, K, N, M) / Math.pow(4, K);
    }

    /**
     * 暴力递归求Bob在K步后的存活次数
     * 目前在（row，col）位置，还有rest步要走，走完rest步后还在棋盘中，则获得一个生存点，返回不同走法总的生存点
     */
    public static long process(int row, int col, int rest, int N, int M) {
        if (row < 0 || row == N || col < 0 || col == M) {
            return 0;
        }
        if (rest == 0) {
            return 1;
        }
        long up = process(row + 1, col, rest - 1, N, M);
        long down = process(row - 1, col, rest - 1, N, M);
        long left = process(row, col - 1, rest - 1, N, M);
        long right = process(row, col + 1, rest - 1, N, M);
        return up + down + left + right;
    }

    public static double livePosibility1(int row, int col, int K, int N, int M) {
        long[][][] dp = new long[N][M][K + 1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                dp[i][j][0] = 1;
            }
        }
        for (int rest = 1; rest <= K; rest++) {
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < M; c++) {
                    dp[r][c][rest] = pick(dp, r - 1, c, rest - 1, N, M);
                    dp[r][c][rest] += pick(dp, r + 1, c, rest - 1, N, M);
                    dp[r][c][rest] += pick(dp, r, c + 1, rest - 1, N, M);
                    dp[r][c][rest] += pick(dp, r, c + 1, rest - 1, N, M);
                }
            }
        }
        return (double) dp[row][col][K] / Math.pow(4, K);
    }

    public static long pick(long[][][] dp, int row, int col, int rest, int N, int M) {
        if (row < 0 || row == N || col < 0 || col == M) {
            return 0;
        }
        return dp[row][col][rest];
    }

    public static void main(String[] args) {
        double ans1 = livePosibility(6, 6, 10, 50, 50);
        double ans2 = livePosibility1(6, 6, 10, 50, 50);
        if (ans1 == ans2) {
            System.out.println("finished");
        }
    }

}
