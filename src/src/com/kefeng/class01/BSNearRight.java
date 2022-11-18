package com.kefeng.class01;

/**
 * 寻找<=value的最右位置
 */
public class BSNearRight {

    public static int nearRight(int[] arr, int value) {
        int N = arr.length;
        int L = 0;
        int R = N - 1;
        int index = -1;
        while (L <= R) {
            int mid = L + ((R - L) >> 1);
            if (arr[mid] <= value) {
                index = mid;
                L = mid + 1;
            } else {
                R = mid - 1;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        int[] arr = {2, 4, 5, 6, 7, 9, 12, 23};
        int value = 10;
        System.out.println(nearRight(arr, value));
    }
}
