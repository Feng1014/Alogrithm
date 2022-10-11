package com.kefeng.class22;


/**
 * 给定一个数组，分成两部分，求两部分相差最少的分配情况。
 * 如果数组个数是偶数，则要求分成的两个数组个数相同。
 * 如果数组个数是奇数，则要求分成的两个数组个数相差1
 */
public class SplitSumClosedSizeHalf {

    public static int minClosed(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int a : arr
        ) {
            sum += a;
        }
        sum /= 2;
        int N = arr.length;
        if ((N & 1) == 0) {
            return process(arr, 0, N / 2, sum);
        } else {
            return Math.max(process(arr, 0, N / 2, sum), process(arr, 0, N / 2 + 1, sum));
        }
    }

    /**
     * 只需要在原来的分配过程中加上picks参数
     * -1表示无法完成分配，即这种分配情况不存在
     *
     * @param arr   要分配的数组
     * @param index 从index位置开始分配
     * @param picks 表示一定要分配够picks个数
     * @param rest  剩余要分配的数值
     * @return
     */
    public static int process(int[] arr, int index, int picks, int rest) {
        if (index == arr.length) {
            /**如果已经没有数可以分配了，并且要挑选的个数也已经为0了，说明这是一种合理的挑选情况返回0，否则返回-1*/
            return picks == 0 ? 0 : -1;
        } else {
            /**第一种分配情况，不挑选index索引元素，要挑选的个数不用减1，剩余要分配的数不用减inde索引处的值*/
            int p1 = process(arr, index + 1, picks, rest);
            int p2 = -1;
            int next = -1;
            /**第二种分配情况，挑选index索引元素，如果类似[53,2,5]数组，则无法分配*/
            if (arr[index] <= rest) {
                next = process(arr, index + 1, picks - 1, rest - arr[index]);
            }
            /**如果第二种情况下后面可以分配，表示这是个合理的分配*/
            if (next != -1) {
                p2 = arr[index] + next;
            }
            return Math.max(p1, p2);
        }
    }

    /**
     * 动态规划求分配的数组相差最小的情况
     * @param arr
     * @return
     */
    public static int minClosedDp(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int a : arr
        ) {
            sum += a;
        }
        sum /= 2;
        int N = arr.length;
        int M = (N + 1) / 2;
        int[][][] dp = new int[N + 1][M + 1][sum + 1];
        /**用-1表示不存在这种分配情况*/
        for (int index = 0; index <= N; index++) {
            for (int picks = 0; picks <= M; picks++) {
                for (int rest = 0; rest <= sum; rest++) {
                    dp[index][picks][rest] = -1;
                }
            }
        }
        /**由递归终止条件可得没有可分配的元素且没有可挑选次数时，不管还剩多少值需要分配都表示这种情况是合理的一种分配情况*/
        for (int rest = 0; rest <= sum; rest++) {
            dp[N][0][rest] = 0;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int picks = 0; picks <= M; picks++) {
                for (int rest = 0; rest <= sum; rest++) {
                    int p1 = dp[index + 1][picks][rest];
                    int p2 = -1;
                    int next = -1;
                    if (picks - 1 >= 0 && arr[index] <= rest) {
                        next = dp[index + 1][picks - 1][rest - arr[index]];
                    }
                    if (next != -1) {
                        p2 = arr[index] + next;
                    }
                    dp[index][picks][rest] = Math.max(p1, p2);
                }
            }
        }
        if ((N & 1) == 0) {
            return dp[0][M][sum];
        } else {
            return Math.max(dp[0][M][sum], dp[0][M + 1][sum]);
        }
    }

    public static int[] randomArray(int maxLen, int maxValue) {
        int[] arr = new int[maxLen];
        for (int i = 0; i < maxLen; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    public static void main(String[] args) {
        int maxlen = 10;
        int maxValue = 20;
        int testTimes = 100;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = randomArray(maxlen, maxValue);
            int ans1 = minClosed(arr);
            int ans2 = minClosedDp(arr);
            if (ans1 != ans2) {
                System.out.println("error");
            }
        }
        System.out.println("finished");
    }

}
