package com.kefeng.class22;


/**
 * 一个数组，要求分割成两部分，使得两部分的和相差最小
 */
public class SplitSumClosed {

    public static int minClosed(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int a : arr
        ) {
            sum += a;
        }
        return process(arr, 0, sum / 2);
    }

    /**
     * 数组中index位置之后的元素，参与分配
     *
     * @param arr   要分配的数组
     * @param index 从index位置开始参与分配
     * @param rest  剩余需要分解的值
     * @return
     */
    public static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return 0;
        } else {
            /**不分配index位置的数字，让分配从下一个位置开始*/
            int p1 = process(arr, index + 1, rest);
            int p2 = 0;
            /**分配index位置的数字，但是要判断。例如[53,2,5]数组，求和后的一半值为30，此时若从53开始分配，明显大于30，没有必要分配53，所以在该索引处需要判断*/
            if (arr[index] <= rest) {
                p2 = arr[index] + process(arr, index + 1, rest - arr[index]);
            }
            return Math.max(p1, p2);
        }
    }

    /**
     * 动态规划求解，把递归过程中的重复依赖使用转移方程表示
     * @param arr
     * @return
     */
    public static int minClosedDp(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int sum = 0;
        for (int a : arr
        ) {
            sum += a;
        }
        sum /= 2;
        int[][] dp = new int[N + 1][sum + 1];
        /**递归的终止条件，数组中没有数的时候，不管有什么值需要分配，只能得到0*/
        for (int rest = 0; rest <= sum; rest++) {
            dp[N][rest] = 0;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= sum; rest++) {
                int p1 = dp[index + 1][rest];
                int p2 = 0;
                if (arr[index] <= rest) {
                    p2 = arr[index] + dp[index + 1][rest - arr[index]];
                }
                dp[index][rest] = Math.max(p1, p2);
            }
        }
        return dp[0][sum];
    }

    public static int[] randomArray(int maxLen, int maxValue) {
        int[] arr = new int[maxLen];
        for (int i = 0; i < maxLen; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 20;
        int testTimes = 100;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int ams1 = minClosed(arr);
            int ans2 = minClosedDp(arr);
            if (ams1 != ans2) {
                System.out.println("error");
            }
        }
        System.out.println("finished");
    }

}
