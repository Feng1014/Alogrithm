package com.kefeng.class04;

/**
 * 一个数组，索引i和j（i<j），如果arr[i]>arr[j]*2，则称arr[i]和arr[j]为翻转对，求数组中有多少个翻转对
 */
public class BiggerThanRightTwice {

    public static int reversePairs(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    public static int process(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        int mid = L + ((R - L) >> 1);
        return process(arr, L, mid) + process(arr, mid + 1, R) + merge(arr, L, mid, R);
    }

    /***
     * 先找出左区间符合的逆序对，再对两个区间进行合并
     * @param arr
     * @param L
     * @param mid
     * @param R
     * @return
     */
    public static int merge(int[] arr, int L, int mid, int R) {
        int ans = 0;
        /**windowR表示右区间第一个位置*/
        int windowR = mid + 1;
        /**左区间从L开始，一直到mid*/
        for (int i = L; i <= mid; i++) {
            /**右区间上索引值windowR不越界，右区间索引位置上的值*2小于左区间上的值，则符合逆序对*/
            while (windowR <= R && (long) arr[i] > (long) (arr[windowR] << 1)) {
                windowR++;
            }
            /**计算出右区间有多少个符合条件的逆序对*/
            ans += windowR - mid - 1;
        }
        int[] help = new int[R - L + 1];
        int index = 0;
        int p1 = L;
        int p2 = mid + 1;
        while (p1 <= mid && p2 <= R) {
            help[index++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= mid) {
            help[index++] = arr[p1++];
        }
        while (p2 <= R) {
            help[index++] = arr[p2++];
        }
        for (int i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
        return ans;
    }

}
