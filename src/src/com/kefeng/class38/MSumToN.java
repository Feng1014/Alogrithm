package com.kefeng.class38;

/**
 * 对于一个数N，如果该数可以由连续数的和，则返回true，否则返回false。5=2+3，2与3连续，则返回true。
 */
public class MSumToN {

    public static boolean isMsum(int num) {
        for (int start = 1; start <= num; start++) {
            int sum = start;
            for (int j = start + 1; j <= num; j++) {
                if (sum + j == num) {
                    return true;
                }
                if (sum + j > num) {
                    break;
                }
                sum += j;
            }
        }
        return false;
    }

    public static boolean isMsum1(int num) {
        return num != (num & (-num));
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 200; i++) {
            System.out.println(i + ":" + isMsum1(i));
        }
    }
}
