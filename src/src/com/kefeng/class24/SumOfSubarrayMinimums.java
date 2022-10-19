package com.kefeng.class24;

/**
 * 给定一个数组，求每个子数组最小值的和
 * https://leetcode.cn/problems/sum-of-subarray-minimums/
 */
public class SumOfSubarrayMinimums {

    public static int sumSubarrayMins(int[] arr) {
        int N = arr.length;
        int[] stack = new int[N];
        long ans = 0;
        int[] left = nearLessEqualLeft(arr, stack);
        int[] right = nearLessEqualRight(arr, stack);
        for (int i = 0; i < N; i++) {
            int L = i - left[i];
            int R = right[i] - i;
            ans += L * R * (long) arr[i];
            ans %= 1000000007;
        }
        return (int) ans;
    }

    public static int[] nearLessEqualLeft(int[] arr, int[] stack) {
        int N = arr.length;
        int[] left = new int[N];
        int si = 0;
        for (int i = N - 1; i >= 0; i--) {
            while (si != 0 && arr[i] <= arr[stack[si - 1]]) {
                left[stack[--si]] = i;
            }
            stack[si++] = i;
        }
        while (si != 0) {
            left[stack[--si]] = -1;
        }
        return left;
    }

    public static int[] nearLessEqualRight(int[] arr, int[] stack) {
        int N = arr.length;
        int[] right = new int[N];
        int si = -1;
        for (int i = 0; i < N; i++) {
            while (si != -1 && arr[i] < arr[stack[si]]) {
                right[stack[si--]] = i;
            }
            stack[++si] = i;
        }
        while (si != -1) {
            right[stack[si--]] = N;
        }
        return right;
    }
}
