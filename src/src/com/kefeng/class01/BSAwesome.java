package com.kefeng.class01;

/**
 * 求局部最小值。某个数小于该数左边的值，小于该数右边的值，则称该数为局部最小值
 */
public class BSAwesome {

    public static int getLessIndex(int[] arr) {
        int N = arr.length;
        if (arr == null || arr.length == 0) {
            return -1;
        }
        if (arr.length == 1 || arr[0] < arr[1]) {
            return 0;
        }
        if (arr[N - 1] < arr[N - 2]) {
            return N - 1;
        }
        int L = 1;
        int R = N - 2;
        int mid = 0;
        while (L < R) {
            mid = (L + R) / 2;
            if (arr[mid] > arr[mid + 1]) {
                L = mid + 1;
            } else if (arr[mid] > arr[mid - 1]) {
                R = mid - 1;
            } else {
                return mid;
            }
        }
        return L;
    }

    public static void main(String[] args) {
        int[] arr = {7, 5, 1, 6, 5, 8, 3, 9};
        System.out.println(getLessIndex(arr));
    }
}
