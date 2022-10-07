package com.kefeng.class19;


/**
 * 在象棋盘上，在规定的步数内，马从某位置跳到另外位置的跳法有多少种？
 */
public class HorseJump {

    public static int jump(int a, int b, int K) {
        return process(0, 0, K, a, b);
    }

    /**
     * 马从（x,y）位置跳到(a,b)位置的跳法，在规定的步数内跳
     *
     * @param x
     * @param y
     * @param rest 剩余多少步要跳
     * @param a
     * @param b
     * @return
     */
    public static int process(int x, int y, int rest, int a, int b) {
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }
        if (rest == 0) {
            return (x == a && y == b) ? 1 : 0;
        }
        int ways = process(x + 2, y + 1, rest - 1, a, b);
        ways += process(x + 1, y + 2, rest - 1, a, b);
        ways += process(x - 1, y + 2, rest - 1, a, b);
        ways += process(x - 2, y + 1, rest - 1, a, b);
        ways += process(x - 2, y - 1, rest - 1, a, b);
        ways += process(x - 1, y - 2, rest - 1, a, b);
        ways += process(x + 1, y - 2, rest - 1, a, b);
        ways += process(x + 2, y - 1, rest - 1, a, b);
        return ways;
    }

    /**
     * 动态规划求解马跳跃问题，有三个转移变量，需要三维状态转移数组。
     * @param a 要跳跃到的X轴位置
     * @param b 要跳跃到的Y轴位置
     * @param K 跳跃的剩余步数
     * @return
     */
    public static int jumpDp(int a, int b, int K) {
        int[][][] dp = new int[10][9][K + 1];
        dp[a][b][0] = 1;
        for (int rest = 1; rest <= K; rest++) {
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 9; y++) {
                    int ways = pick(dp, x + 1, y + 2, rest - 1);
                    ways += pick(dp, x + 2, y + 1, rest - 1);
                    ways += pick(dp, x + 2, y - 1, rest - 1);
                    ways += pick(dp, x + 1, y - 2, rest - 1);
                    ways += pick(dp, x - 1, y - 2, rest - 1);
                    ways += pick(dp, x - 2, y - 1, rest - 1);
                    ways += pick(dp, x - 2, y + 1, rest - 1);
                    ways += pick(dp, x - 1, y + 2, rest - 1);
                    dp[x][y][rest] = ways;
                }
            }
        }
        return dp[0][0][K];
    }

    public static int pick(int[][][] dp, int x, int y, int rest) {
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }
        return dp[x][y][rest];
    }

    public static void main(String[] args) {
        int x = 7;
        int y = 7;
        int steps = 10;
        System.out.println(jump(x, y, steps));
        System.out.println(jumpDp(x, y, steps));
    }
}
