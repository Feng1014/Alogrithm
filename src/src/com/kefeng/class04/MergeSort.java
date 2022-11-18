package com.kefeng.class04;

/**
 * 归并排序
 */
public class MergeSort {

    /**
     * 递归归并排序
     *
     * @param arr
     */
    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    public static void process(int[] arr, int L, int R) {
        if (L == R) {
            return;
        }
        int mid = L + ((R - L) >> 1);
        process(arr, L, mid);
        process(arr, mid + 1, R);
        merge(arr, L, mid, R);
    }

    public static void merge(int[] arr, int L, int mid, int R) {
        int[] h = new int[R - L + 1];
        int p1 = L;
        int p2 = mid + 1;
        int index = 0;
        while (p1 <= mid && p2 <= R) {
            h[index++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= mid) {
            h[index++] = arr[p1++];
        }
        while (p2 <= R) {
            h[index++] = arr[p2++];
        }
        for (int i = 0; i < h.length; i++) {
            arr[L + i] = h[i];
        }
    }

    /**
     * 归并排序的非递归实现
     *
     * @param arr
     */
    public static void mergeSort1(int[] arr) {
        int N = arr.length;
        if (arr == null || N < 2) {
            return;
        }
        /**步长 1，2，4，8*/
        int mergeSize = 1;
        while (mergeSize < N) {
            /**当前左组的第一个位置*/
            int L = 0;
            while (L < N) {
                /**当前左组的最后一个位置*/
                int M = L + mergeSize - 1;
                /**如果当前左组的最后一个位置已经超出序列范围，则跳过这个步长，执行下一个步长*/
                if (M >= N) {
                    break;
                }
                /**当前右组的最后一个位置。需要当前左组最后一个位置+步长和序列范围-1二者相比，选择较小的一个*/
                int R = Math.min(M + mergeSize, N - 1);
                merge(arr, L, M, R);
                /**更新左组*/
                L = R + 1;
            }
            /**防止溢出*/
            if (mergeSize > N / 2) {
                break;
            }
            mergeSize <<= 1;
        }
    }

//    public static void mergeSort1(int[] arr) {
//        if (arr == null || arr.length < 2) {
//            return;
//        }
//        int N = arr.length;
//        int mergeSize = 1;
//        while (mergeSize < N) {
//            int L = 0;
//            while (L < N) {
//                if (mergeSize >= N - L) {
//                    break;
//                }
//                int M = L + mergeSize - 1;
//                int R = M + Math.min(mergeSize, N - M - 1);
//                merge(arr, L, M, R);
//                L = R + 1;
//            }
//            if (mergeSize > N / 2) {
//                break;
//            }
//            mergeSize <<= 1;
//        }
//    }

    public static void main(String[] args) {
        int[] arr = {5, 9, 4, 2, 3, 45, 21, 954, 10, 1};
//        mergeSort(arr);
        mergeSort1(arr);

        for (int i = 0; i < args.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
