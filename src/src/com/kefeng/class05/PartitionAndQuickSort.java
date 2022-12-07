package com.kefeng.class05;

/**
 * 快速排序
 */
public class PartitionAndQuickSort {

    /**
     * 把数组分成 <最后元素 ==最后元素 >最后元素，三个部分。返回“==最后元素”的索引
     *
     * @param arr
     * @param L
     * @param R
     * @return
     */
    public static int[] netherlandsFlag(int[] arr, int L, int R) {
        /**L>R，表示不是有效范围*/
        if (L > R) {
            return new int[]{-1, -1};
        }
        /**L==R表示拿最后元素做比较值，划分数组后只有[L,R]*/
        if (L == R) {
            return new int[]{L, R};
        }
        /**less表示“<最后元素”的右边界*/
        int less = L - 1;
        /**more表示“>最后元素”的左边界*/
        int more = R;
        /**表示当前索引*/
        int index = L;
        while (index < more) {
            /**当前索引和最后元素比较，如果二者相等，当前索引++*/
            if (arr[index] == arr[R]) {
                index++;
            } else if (arr[index] < arr[R]) {
                /**当前元素和最后元素比较，小于最后元素。交换“<最后元素”区间右侧元素和当前元素，当前索引++*/
                swap(arr, index++, ++less);
            } else {
                /**当前元素和最后元素比较，大于最后元素。交换“>最后元素”区间左侧元素和当前元素，当前索引不更新*/
                swap(arr, index, --more);
            }
        }
        swap(arr, more, R);
        return new int[]{less + 1, more};
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    public static void process(int[] arr, int L, int R) {
        if (L > R) {
            return;
        }
        swap(arr, L + (int) (Math.random() * (R - L + 1)), R);
        int[] equals = netherlandsFlag(arr, L, R);
        process(arr, L, equals[0] - 1);
        process(arr, equals[1] + 1, R);
    }

    public static void main(String[] args) {
        int[] arr = {4, 2, 1, 8, 6, 7, 3, 7, 5};
        int[] ans = netherlandsFlag(arr, 0, arr.length - 1);
        for (int res : ans
        ) {
            System.out.print(res + " ");
        }
        System.out.println();
        quickSort(arr);
        for (int an : arr
        ) {
            System.out.print(an + " ");
        }
    }

}
