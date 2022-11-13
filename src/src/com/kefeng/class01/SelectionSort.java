package com.kefeng.class01;

/**
 * 选择排序
 */
public class SelectionSort {

    /**
     * 从小到大的冒泡排序。从头开始遍历，前者大于后者，交换位置。
     *
     * @param arr
     */
    public static void bubbleSort(int[] arr) {
        int N = arr.length;
        /**N个数一共需要N-1趟才能排完序*/
        for (int i = 0; i < N - 1; i++) {
            /**每趟排序，能够确定最后一个位置的元素。所以每趟排序参与排序的元素与排序趟数有关。每趟需要比较的次数是总长度减去趟数*/
            for (int j = 0; j < N - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    /**
     * 选择排序。每趟排序选出未排序列中的最小元素放在首位，重复此操作直至所有元素有序
     *
     * @param arr
     */
    public static void selectSort(int[] arr) {
        int N = arr.length;
        /**N个数需要N趟排序*/
        for (int i = 0; i < N; i++) {
            /**每趟排序首先选择首位元素为最小值*/
            int minIndex = i;
            /**从首位元素+1位置开始，向后选择最小的元素和首位元素比较，如果小于首位元素则交换*/
            for (int j = i + 1; j < N; j++) {
                /**minIndex表示最小元素，j表示除首位元素外所有的元素。minIndex和j比较取较小的元素*/
                minIndex = arr[j] < arr[minIndex] ? j : minIndex;
            }
            /**除首位元素外的最小元素和首位元素交换，使得每趟排序在首位确定有序元素*/
            swap(arr, minIndex, i);
        }
    }

    /**
     * 插入排序。分为已排序和未排序两个序列。从未排序中拿出一个元素和已排序的所有元素比较，放到已排序列的合适位置上。重复此过程直至未排序列所有元素放到合适位置上
     *
     * @param arr
     */
    public static void insertSort(int[] arr) {
        int N = arr.length;
        /**开始排序时，首位元素为有序序列，后续所有元素为无序序列。从无序序列中拿出一个元素放到有序序列上，组成新的有序序列*/
        for (int i = 1; i < N; i++) {
            /**j表示有序序列的最后一个元素，j+1表示无序序列的第一个元素。arr[j]和arr[j+1]相比，如果后者小于前者则交换，并要保持有序序列不能越界，即j>=0才可以*/
            for (int j = i - 1; j >= 0 && arr[j + 1] < arr[j]; j--) {
                swap(arr, j, j + 1);
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {1, 5, 6, 0, 4, -8, 78, 652, 2};
//        selectSort(arr);
//        bubbleSort(arr);
        insertSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
