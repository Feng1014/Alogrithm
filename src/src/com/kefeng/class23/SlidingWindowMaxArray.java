package com.kefeng.class23;


import java.util.LinkedList;

/**
 * 给定一个数组arr，给定窗口大小N，求窗口滑过时窗口内的最大值
 * 例如数组[4,3,5,4,3,3,6,7]，N=3，求得[5,5,5,4,6,7]
 */
public class SlidingWindowMaxArray {

    /**
     * 暴力从左到右寻找滑动窗口内的最大值
     * @param arr
     * @param W
     * @return
     */
    public static int[] right(int[] arr, int W) {
        if (arr == null || arr.length < W || W < 1) {
            return null;
        }
        int N = arr.length;
        int[] res = new int[N - W + 1];
        int index = 0;
        int L = 0;
        int R = W - 1;
        while (R < N) {
            int max = arr[L];
            for (int i = L + 1; i <= R; i++) {
                max = Math.max(max, arr[i]);
            }
            res[index++] = max;
            L++;
            R++;
        }
        return res;
    }

    /**
     * 使用双端队列保存滑动窗口内的最大值，滑动窗口长度固定，更新队列时只需在队列尾部添加一个元素，在队列头部弹出一个元素即可。
     * @param arr
     * @param W
     * @return
     */
    public static int[] maxSliding(int[] arr, int W) {
        if (arr == null || arr.length < W || W < 1) {
            return null;
        }
        LinkedList<Integer> qmax = new LinkedList<>();
        int N = arr.length;
        int[] res = new int[N - W + 1];
        int index = 0;
        for (int R = 0; R < N; R++) {
            while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[R]) {
                qmax.pollLast();
            }
            qmax.addLast(R);
            if (qmax.peekFirst() == R - W) {
                qmax.pollFirst();
            }
            if (R >= W - 1) {
                res[index++] = arr[qmax.peekFirst()];
            }
        }
        return res;
    }

    public static int[] generateArray(int maxLen, int maxValue) {
        int[] arr = new int[maxLen];
        for (int i = 0; i < maxLen; i++) {
            arr[i] = (int) (Math.random() * maxValue);
        }
        return arr;
    }

    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 20;
        int testTimes = 100;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateArray(maxLen, maxValue);
            int W = (int) (Math.random() * maxValue) + 1;
            int[] result1 = maxSliding(arr, W);
            int[] result2 = right(arr, W);
            if (!isEqual(result1, result2)) {
                System.out.println("error");
            }
        }
        System.out.println("finished");
    }

}
