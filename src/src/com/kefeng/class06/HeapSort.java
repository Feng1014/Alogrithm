package com.kefeng.class06;

/**
 * 堆排序
 */
public class HeapSort {

    /**
     * 用户新增元素，需要向上和父节点比较，调整成堆
     *
     * @param arr
     * @param index
     */
    public static void heapInsert(int[] arr, int index) {
        while (arr[index] > arr[(index - 1) / 2]) {
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    /**
     * 堆中index位置数值改变，向下调整成堆
     *
     * @param arr
     * @param index
     * @param heapSize
     */
    public static void heapify(int[] arr, int index, int heapSize) {
        int left = 2 * index + 1;
        while (left < heapSize) {
            int largest = (left + 1 < heapSize && arr[left] < arr[left + 1]) ? left + 1 : left;
            int maxIndex = arr[index] > arr[largest] ? arr[index] : arr[largest];
            if (maxIndex == index) {
                break;
            }
            swap(arr, largest, index);
            index = largest;
            left = 2 * index + 1;
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 堆排序空间复杂度o(1)
     */
    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        /**o(N*logN)*/
        for (int i = 0; i < arr.length; i++) {/**o(N)*/
            heapInsert(arr, i);/**o(logN)*/
        }
        /**o(N)*/
        for (int i = arr.length - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }
        int heapSize = arr.length;
        swap(arr, 0, --heapSize);
        while (heapSize > 0) {
            heapify(arr, 0, heapSize);
            swap(arr, 0, --heapSize);
        }
    }

    public static void main(String[] args) {
        int[] arr = {5, 4, 8, 3, 9, 7, 1, 2};
        heapSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }

    }

}
