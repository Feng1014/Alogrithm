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
        /**根据递归条件，只有剩余刀数为0且剩余血量为0，野怪被砍死。这是野怪被砍死的情况*/
        dp[0][0] = 1;
        for (int times = 1; times <= K; times++) {
            /**在times刀数下，野怪的血量已经为0了，但此时还需要砍times刀。此时每砍一刀不管掉多少血，野怪都是死亡状态，所以求（M+1）的times次方即是dp[times][0]的情况数*/
            dp[times][0] = (long) Math.pow(M + 1, times);
            for (int hp = 1; hp <= N; hp++) {
                long ways = 0;
                for (int i = 0; i <= M; i++) {
                    /**每砍一刀掉血在【0，7】范围内，在剩余血量是5再砍3刀的情况下
                     * dp[3][5]依赖于dp[2][5...-2],dp[2][-1],dp[2][-2]血量越界
                     * 但是仍然要砍，和56行的情况一样。（M+1）的times次方就是血量越界下野怪被砍死的情况*/
                    if (hp - i >= 0) {
                        ways += dp[times - 1][hp - i];
                    } else {
                        ways += Math.pow(M + 1, times - 1);
                    }
                }
                dp[times][hp] = ways;
            }
        }
        long kill = dp[K][N];
        return (double) ((double) kill / (double) all);
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
                /**优化后的动态规划不再使用for循环进行累加。例如在每砍一刀掉血在【0，5】范围内，剩7血砍3刀情况即dp[3][7]，
                 * 依赖于dp[2][7...2]所有的情况，累加dp[2][7...2]即可得到dp[3][7]的野怪被砍死的情况数
                 * dp[3][6]同样依赖dp[2][6...1],dp[3][6]和dp[3][7]的依赖有重叠部分
                 * dp[3][7]=dp[3][6]+dp[2][7]-dp[2][1],dp[2][1]很有可能越界
                 * 如果每砍一刀掉血在【0，9】范围内，则dp[3][7]=dp[3][6]+dp[2][7]+dp[2][-3]
                 * 此时dp[2][-3]已经越界，野怪被砍死的情况就是（M+1）的times次方*/
                dp[times][hp] = dp[times][hp - 1] + dp[times - 1][hp];
                /**hp - 1 - M没越界*/
                if (hp - 1 - M >= 0) {
                    dp[times][hp] -= dp[times - 1][hp - 1 - M];
                } else {
                    /**hp - 1 - M越界*/
                    dp[times][hp] -= Math.pow(M + 1, times - 1);
                }
            }
        }
        long kill = dp[K][N];
        return (double) ((double) kill / (double) all);
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
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("error");
            }
        }
        System.out.println("finished");
    }

}
