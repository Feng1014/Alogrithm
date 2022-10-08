package com.kefeng.class21;


/**
 * 有一只野怪，血量N，每砍一刀损失的血量在[0,M]之间，问K刀后，野怪被砍死的概率是多少？
 */
public class KillMonster {

    public static double right(int N, int M, int K) {
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }
        long all = (long) Math.pow(M + 1, K);
        long kill = process(K, M, N);
        return (double) ((double) kill / (double) all);
    }

    /**
     * @param times 还有times刀可以砍
     * @param M     每次砍消耗【0，M】的血量
     * @param hp    野怪还剩hp的血量
     * @return 返回砍死的情况数
     */
    public static long process(int times, int M, int hp) {
        if (hp <= 0) {
            return (long) Math.pow(M + 1, times);
        }
        if (times == 0) {
            return hp < 0 ? 1 : 0;
        }
        long ways = 0;
        for (int i = 0; i <= M; i++) {
            ways += process(times - 1, M, hp - i);
        }
        return ways;
    }

    /**
     * 动态规划求解
     *
     * @param N
     * @param M
     * @param K
     * @return
     */
    public static double rightDp(int N, int M, int K) {
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }
        long all = (long) Math.pow(M + 1, K);
        long[][] dp = new long[K + 1][N + 1];
        dp[0][0] = 1;
        for (int times = 1; times <= K; times++) {
            dp[times][0] = (long) Math.pow(M + 1, times);
            for (int hp = 1; hp <= N; hp++) {
                long ways = 0;
                for (int i = 0; i <= M; i++) {
                    if (hp - i >= 0) {
                        ways += dp[times - 1][hp - i];
                    } else {
                        ways += (long) Math.pow(M + 1, times - 1);
                    }
                }
                dp[times][hp] = ways;
            }
        }
        long kill = dp[K][N];
        return (double) (kill / all);
    }

    public static double rightDp1(int N, int M, int K) {
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }
        long all = (long) Math.pow(M + 1, K);
        long[][] dp = new long[K + 1][N + 1];
        dp[0][0] = 1;
        for (int times = 1; times <= K; times++) {
            dp[times][0] = (long) Math.pow(M + 1, times);
            for (int hp = 1; hp <= N; hp++) {
                dp[times][hp] = dp[times][hp - 1] + dp[times - 1][hp];
                if (hp - 1 - M >= 0) {
                    dp[times][hp] -= dp[times - 1][hp - 1 - M];
                } else {
                    dp[times][hp] -= Math.pow(M + 1, times - 1);
                }
            }
        }
        long kill = dp[K][N];
        return (double) (kill / all);
    }

    public static void main(String[] args) {
        int NMax = 10;
        int KMax = 10;
        int MMax = 10;
        int testTimes = 100;
        for (int i = 0; i < testTimes; i++) {
            int N = (int) (Math.random() * NMax);
            int M = (int) (Math.random() * MMax);
            int K = (int) (Math.random() * KMax);
            double ans1 = right(N, M, K);
            double ans2 = rightDp(N, M, K);
            double ans3 = rightDp1(N, M, K);
            if (ans1 != ans3 || ans1 != ans2) {
                System.out.println("error");
            }
        }
        System.out.println("finished");
    }

}
