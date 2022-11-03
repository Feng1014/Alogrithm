package com.kefeng.class28;

/**
 * 寻找无序数组中第K小的数
 */
public class FindMinKth {

    /**
     * 无序数组中第K小的数，随机选择pivot。
     *
     * @param arr 无序数组
     * @param K   第K小的数
     * @return 第K小的数值
     */
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

    /**
     * 求无序数组第K小数的迭代版本
     *
     * @param arr
     * @param k
     * @return
     */
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

    /**
     * 求无序数组中的第K小的值，使用bfprt算法求解
     * @param arr
     * @param K
     * @return
     */
    public static int minKth2(int[] arr, int K) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return bfprt(arr, 0, arr.length - 1, K - 1);
    }

    /**
     * bfprt算法核心在于，不是随机求pivot，而是通过中位数中的中位数求得pivot，这样可以准确的知道第二次递归时pivot左边或者右边数的个数，
     * 不像第一次算法中的随机生成pivot，这个pivot恰好是第一个数值，第二次递归时仅不需要考虑第一个数，bfprt不存在这种最坏的情况
     * @param arr
     * @param L
     * @param R
     * @param index
     * @return
     */
    public static int bfprt(int[] arr, int L, int R, int index) {
        if (L == R) {
            return arr[L];
        }
        int pivot = medisOfMedis(arr, L, R);
        int[] part = partition(arr, L, R, pivot);
        if (index >= part[0] && index <= part[1]) {
            return arr[index];
        } else if (index > part[1]) {
            return bfprt(arr, part[1] + 1, R, index);
        } else {
            return bfprt(arr, L, part[0] - 1, index);
        }
    }

    public static int medisOfMedis(int[] arr, int L, int R) {
        int size = R - L + 1;
        int offset = size % 5 == 0 ? 0 : 1;
        int[] mArr = new int[size / 5 + offset];
        for (int team = 0; team < mArr.length; team++) {
            int teamFirst = L + team * 5;
            mArr[team] = getMedis(arr, teamFirst, Math.min(R, teamFirst + 4));
        }
        return bfprt(mArr, 0, mArr.length - 1, mArr.length / 2);
    }

    public static int getMedis(int[] arr, int L, int R) {
        insertSort(arr, L, R);
        return arr[(L + R) / 2];
    }

    public static void insertSort(int[] arr, int L, int R) {
        for (int i = L + 1; i <= R; i++) {
            for (int j = i - 1; j >= L; j--) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
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
            int ans2 = minKth2(ar,K);
            if (ans1 != ans2) {
                System.out.println("error");
            }
        }
        System.out.println("finished");
//        int[] arr = new int[]{5, 8, 2, 6, 1, 3, 7};
//        int k = 4;
//        System.out.println(minKth(arr, k));
//        System.out.println(minKth1(arr, k));
//        System.out.println(minKth2(arr, k));
    }

}
