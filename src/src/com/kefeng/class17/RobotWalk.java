package com.kefeng.class17;


/**
 * 机器人从位置A走向位置B，需要走几步
 * 位置A和位置B在【1，N】的数组上
 */
public class RobotWalk {

    public static int ways(int N, int start, int aim, int K) {
        return process(start, K, aim, N);
    }

    /**
     * @param cur  机器人当前的位置
     * @param rest 机器人还需要走多少步
     * @param aim  机器人的目标位置
     * @param N    数组长度
     * @return
     */
    public static int process(int cur, int rest, int aim, int N) {
        if (rest == 0) {
            return cur == aim ? 1 : 0;
        }
        if (cur == 1) {
            return process(2, rest - 1, aim, N);
        }
        if (cur == N) {
            return process(N - 1, rest - 1, aim, N);
        }
        return process(cur - 1, rest - 1, aim, N) + process(cur + 1, rest - 1, aim, N);
    }

    public static int ways1(int N, int start, int aim, int K) {
        int[][] dp = new int[N + 1][K + 1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= K; j++) {
                dp[i][j] = -1;
            }
        }
        return procecss1(start, K, aim, N, dp);
    }

    public static int procecss1(int cur, int rest, int aim, int N, int[][] dp) {
        if (dp[cur][rest] != -1) {
            return dp[cur][rest];
        }
        int ans = 0;
        if (rest == 0) {
            ans = cur == aim ? 1 : 0;
        } else if (cur == 1) {
            ans = procecss1(2, rest - 1, aim, N, dp);
        } else if (cur == N) {
            ans = procecss1(N - 1, rest - 1, aim, N, dp);
        } else {
            return procecss1(cur - 1, rest - 1, aim, N, dp)
                    + procecss1(cur + 1, rest - 1, aim, N, dp);
        }
        dp[cur][rest] = ans;
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(ways(4, 2, 4, 4));
        System.out.println(ways1(4, 2, 4, 4));
    }
}
