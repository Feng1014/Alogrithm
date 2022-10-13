package com.kefeng.class23;


import java.util.LinkedList;

/**
 * 给定数组arr，在构成的所有字数组中的最大值与最小值的差<=sum，问有满足条件的字数组有多少个？
 */
public class AllLessNumSubArray {

    /**
     * 从头到尾依次遍历求解满足条件的子数组
     *
     * @param arr
     * @param sum
     * @return
     */
    public static int right(int[] arr, int sum) {
        if (arr == null || arr.length == 0 || sum < 0) {
            return 0;
        }
        int N = arr.length;
        int counts = 0;
        for (int L = 0; L < N; L++) {
            for (int R = L; R < N; R++) {
                int max = arr[L];
                int min = arr[L];
                for (int K = L + 1; K <= R; K++) {
                    max = Math.max(max, arr[K]);
                    min = Math.min(min, arr[K]);
                }
                if (max - min <= sum) {
                    counts++;
                }
            }
        }
        return counts;
    }

    /**
     * 使用双端队列获得不固定长度的滑动窗口中的最值，
     * @param arr
     * @param sum
     * @return
     */
    public static int rightQueue(int[] arr, int sum) {
        if (arr == null || arr.length == 0 || sum < 0) {
            return 0;
        }
        int N = arr.length;
        int counts = 0;
        LinkedList<Integer> qmax = new LinkedList<>();
        LinkedList<Integer> qmin = new LinkedList<>();
        int R = 0;
        for (int L = 0; L < N; L++) {
            /**滑动窗口的右边界，找到第一个不满足最大值-最小值<=sum的右边界R*/
            while (R < N) {
                while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[R]) {
                    qmax.pollLast();
                }
                qmax.addLast(R);
                while (!qmin.isEmpty() && arr[qmin.peekLast()] >= arr[R]) {
                    qmin.pollLast();
                }
                qmin.addLast(R);
                if (arr[qmax.peekFirst()] - arr[qmin.peekFirst()] > sum) {
                    break;
                } else {
                    R++;
                }
            }
            /**找到R，此时在【L，R】范围构成的子数组都满足最大值-最小值<=sum*/
            counts += R - L;
            /**移动左边界*/
            if (qmax.peekFirst() == L) {
                qmax.pollFirst();
            }
            if (qmin.peekFirst() == L) {
                qmin.pollFirst();
            }
        }
        return counts;
    }

    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen) + 1;
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 20;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(maxLen, maxValue);
            int sum = (int) (Math.random() * maxValue);
            int result1 = right(arr, sum);
            int result2 = rightQueue(arr, sum);
            if (result1 != result2) {
                System.out.println("error");
            }
        }
        System.out.println("finished");
    }

}
