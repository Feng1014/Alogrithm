package com.kefeng.class24;


import java.util.Stack;

/**
 * 子数组的最小乘积定义为这个数组中的最小值*子数组所有元素的和，求某个数组所有子数组的最小乘积的最大值
 * https://leetcode.cn/problems/maximum-subarray-min-product/
 */
public class AllTimesMinToMax {

    /**
     * 传统的从左到右依次遍历，两个for循环分别代表子数组的左右边界，在每个子数组中找到最小值和子数组的和然后相乘，最后求总的最大子数组最小乘积
     * @param nums
     * @return
     */
    public static int maxSum(int[] nums) {
        int maxValue = Integer.MIN_VALUE;
        int N = nums.length;
        for (int i = 0; i < N; i++) {
            for (int j = i; j < N; j++) {
                int minNum = Integer.MAX_VALUE;
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    sum += nums[k];
                    minNum = Math.min(minNum, nums[k]);
                }
                maxValue = Math.max(maxValue, sum * minNum);
            }
        }
        return maxValue;
    }

    /**
     * 使用单调栈解决，遍历数组时，以某个数组元素为中心，寻找该元素左右两侧均小于该元素的值，那么以该元素为最小值的子数组即是不包含左右两侧小于该元素的所有元素
     * 【3，4，2，5，1，6】以元素2为最小值的子数组是【3，4，2，5】，左侧均大于2，右侧1小于2，所以子数组有边界到5为止。
     * @param nums
     * @return
     */
    public static int maxSumMinProduct(int[] nums) {
        int N = nums.length;
        int[] sum = new int[N];
        sum[0] = nums[0];
        for (int i = 1; i < N; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }
        int max = Integer.MIN_VALUE;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < N; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
                int j = stack.pop();
                max = Math.max(max, (stack.isEmpty() ? sum[i - 1] : sum[i - 1] - sum[stack.peek()]) * nums[j]);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int j = stack.pop();
            max = Math.max(max, (stack.isEmpty() ? sum[N - 1] : sum[N - 1] - sum[stack.peek()]) * nums[j]);
        }
        return max;
    }

    public static int[] generateArray() {
        int N = (int) (Math.random() * 10) + 10;
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * 100) + 1;
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTimes = 1000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateArray();
            int ans1 = maxSumMinProduct(arr);
            int ans2 = maxSum(arr);
            if (ans1 != ans2) {
                System.out.println("error");
            }
        }
        System.out.println("finished");
    }

}
