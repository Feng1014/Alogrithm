package com.kefeng.class28;

/**
 * 寻找无序数组中第K小的数
 */
public class FindMinKth {

    public static int minKth(int[] arr, int K) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process(arr, 0, arr.length - 1, K - 1);
    }

    public static int process(int[] arr, int L, int R, int index) {
        if (L == R) {
            return arr[L];
        }
        int pivot = arr[L + (int) (Math.random() * (R - L + 1))];
        int[] part = partition(arr, L, R, pivot);
        if (index <= part[1] && index >= part[0]) {
            return arr[index];
        } else if (index > part[1]) {
            return process(arr, part[1] + 1, R, index);
        } else {
            return process(arr, L, part[0] - 1, index);
        }
    }

    public static int minKth1(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int L = 0;
        int R = arr.length - 1;
        int pivot = 0;
        int[] part = null;
        int index = k - 1;
        while (L < R) {
            pivot = arr[L + (int) (Math.random() * (R - L + 1))];
            part = partition(arr, L, R, pivot);
            if (index < part[0]) {
                R = part[0] - 1;
            } else if (index > part[1]) {
                L = part[1] + 1;
            } else {
                return pivot;
            }
        }
        return arr[L];
    }

    public static int[] partition(int[] arr, int L, int R, int pivot) {
        int less = L - 1;
        int more = R + 1;
        int cur = L;
        while (cur < more) {
            if (arr[cur] < pivot) {
                swap(arr, ++less, cur++);
            } else if (arr[cur] > pivot) {
                swap(arr, cur, --more);
            } else {
                cur++;
            }
        }
        return new int[]{less + 1, more - 1};
    }

    public static void swap(int[] arr, int L, int R) {
        int temp = arr[L];
        arr[L] = arr[R];
        arr[R] = temp;
    }

    public static int[] generateArray(int maxSize, int maxValue) {
        int N = (int) (Math.random() * maxSize) + 1;
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    public static void main(String[] args) {
        int maxSize = 10;
        int maxValue = 20;
        int testTimes = 1000;
        for (int i = 0; i < testTimes; i++) {
            int[] ar = generateArray(maxSize, maxValue);
            int K = (int) (Math.random() * maxSize) + 1;
            int ans1 = minKth(ar, K);
            int ans2 = minKth1(ar, K);
            if (ans1 != ans2) {
                System.out.println("error");
            }
        }
        System.out.println("finished");
//        int[] arr = new int[]{5, 8, 2, 6, 1, 3, 7};
//        int k = 4;
//        System.out.println(minKth(arr, k));
//        System.out.println(minKth1(arr, k));
    }

}
