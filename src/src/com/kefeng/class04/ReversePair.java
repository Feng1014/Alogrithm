package com.kefeng.class04;


/**
 * 给定数组，如果任意元素右边值小于该元素，则称该元素为和右边元素能构成逆序对，问数组中存在多少个逆序对
 */
public class ReversePair {

    public static int reversePair(int[] arr) {
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

    /**
     * 从后向前合并两个子数组
     * @param arr
     * @param L
     * @param mid
     * @param R
     * @return
     */
    public static int merge(int[] arr, int L, int mid, int R) {
        int[] help = new int[R - L + 1];
        int res = 0;
        /**定义左数组的最后一个位置*/
        int p1 = mid;
        /**定义右数组的最后一个位置*/
        int p2 = R;
        int index = help.length - 1;
        while (p1 >= L && p2 > mid) {
            /**如果左数组的元素P1大于右数组的元素P2，那么右数组中所有小于P2的元素都可以和P1构成逆序对*/
            res += arr[p1] > arr[p2] ? (p2 - mid) : 0;
            help[index--] = arr[p1] > arr[p2] ? arr[p1--] : arr[p2--];
        }
        while (p1 >= L) {
            help[index--] = arr[p1--];
        }
        while (p2 > mid) {
            help[index--] = arr[p2--];
        }
        for (int i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
        return res;
    }

    public static void main(String[] args) {
        int[] arr = {5, 4, 3, 2, 1};
        System.out.println(reversePair(arr));
    }

}
