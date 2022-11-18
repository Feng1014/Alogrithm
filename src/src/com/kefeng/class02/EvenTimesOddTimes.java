package com.kefeng.class02;


public class EvenTimesOddTimes {

    /**
     * 一个序列，有若干个数，求出现次数为奇数的数是多少
     */
    public static int oddTimes(int[] arr) {
        int N = arr.length;
        int ero = 0;
        for (int i = 0; i < N; i++) {
            ero ^= arr[i];
        }
        return ero;
    }

    /**
     * 在一个序列中，有两个数出现的次数为奇数，其余数出现的次数为偶数。求出现奇数次的两个数。
     *
     * @param arr
     */
    public static void twoOddTimes(int[] arr) {
        int N = arr.length;
        int ero = 0;
        /**假设出现次数为奇数的两个数分别为a,b;所有数求异或后得到a^b*/
        for (int i = 0; i < N; i++) {
            ero ^= arr[i];
        }
        /**找出ero的二进制中最右侧的1*/
        int rightOne = ero & (-ero);
        int onlyOne = 0;
        /**如果序列中的数和rightOne求余后不为0，表示该数二进制最右侧和rightOne有相同的1，对这部分数求异或后会得到a*/
        for (int j = 0; j < N; j++) {
            if ((arr[j] & rightOne) != 0) {
                onlyOne ^= arr[j];
            }
        }
        /**使用a^b^a后可以求得b*/
        int b = ero ^ onlyOne;
        System.out.println(onlyOne + "," + b);
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 2, 1, 3, 5, 5, 3, 4, 3, 4};
        int[] arrb = {1, 3, 2, 4, 1, 3, 5, 5, 3, 4, 3, 4};
        System.out.println(oddTimes(arr));
        twoOddTimes(arrb);
    }

}
