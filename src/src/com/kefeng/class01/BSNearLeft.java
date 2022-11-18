package com.kefeng.class01;

/**
 * 寻找>=value的最左侧的位置
 */
public class BSNearLeft {

    public static int nearLeft(int[] arr, int value) {
        int N = arr.length;
        int L = 0;
        int R = N - 1;
        int index = -1;
        while (L <= R) {
            int mid = L + ((R - L) >> 1);
            if (arr[mid] >= value) {
                index = mid;
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 3, 3, 4, 5, 6, 7, 8, 8, 9};
        int value = 3;
        System.out.println(nearLeft(arr, value));
    }
}
