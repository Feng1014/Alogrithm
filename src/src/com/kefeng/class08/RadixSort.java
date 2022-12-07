package com.kefeng.class08;

import java.util.Arrays;

/**
 * 基数排序
 */
public class RadixSort {

    /**
     * 基数排序 下述算法只适用于非负数的排序中
     *
     * @param arr
     */
    public static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1, maxBits(arr));
    }

    /**
     * 计算出一个数组中最大数的位数
     *
     * @param arr
     * @return
     */
    public static int maxBits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int value : arr
        ) {
            max = Math.max(max, value);
        }
        int ans = 0;
        while (max != 0) {
            ans++;
            max /= 10;
        }
        return ans;
    }

    /**
     * 在[L,R]区间上执行基数排序
     *
     * @param arr
     * @param L
     * @param R
     * @param digits
     */
    public static void process(int[] arr, int L, int R, int digits) {
        /**数组中每个数的每一位都是【0，9】的区间，故取固定大小为10的排序桶*/
        final int radix = 10;
        int i = 0, j = 0;
        /**辅助数组，表示有R-L+1个数*/
        int[] help = new int[R - L + 1];
        /**基数排序按照最大数的位数作为循环条件*/
        for (int d = 1; d <= digits; d++) {
            /**排序桶的大小是10，取值范围是【0，9】*/
            int[] count = new int[radix];
            /**依次遍历数组中的每个数*/
            for (i = L; i <= R; i++) {
                /**求数组每个元素的从右向左第d位的值。例如32从右向左第1位的值是2，则在排序桶的第二位添加32这个元素*/
                j = getDigits(arr[i], d);
                count[j]++;
            }
            /**求排序桶的前缀和。假设count[5]=8，则表示在第d位上排序时，有8个数小于等于[0,5]这个区间*/
            for (i = 1; i < radix; i++) {
                count[i] += count[i - 1];
            }
            /**计算出排序桶前缀和后，在原数组中从右向左，对每个数进行排序*/
            for (i = R; i >= L; i--) {
                /**计算出数组元素的第d位数值*/
                j = getDigits(arr[i], d);
                /**count[j]表示小于当前元素的个数，故把当前元素放置在count[j]-1的位置上，count[j]需要减一*/
                help[count[j] - 1] = arr[i];
                count[j]--;
            }
            /**将help数组的元素拷贝到原数组中*/
            for (i = L, j = 0; i <= R; i++, j++) {
                arr[i] = help[j];
            }
        }
    }

    public static int getDigits(int x, int d) {
        return ((x / (int) (Math.pow(10, d - 1))) % 10);
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * (maxSize + 1))];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    public static void Comparator(int[] arr) {
        Arrays.sort(arr);
    }

    public static boolean isEquals(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int maxSize = 20;
        int maxValue = 30;
        int testTimes = 1000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            radixSort(arr);
            int[] arr1 = copyArray(arr);
            Comparator(arr1);
            if (!isEquals(arr, arr1)) {
                System.out.println("error");
            }
        }
        System.out.println("finished");
    }

}
