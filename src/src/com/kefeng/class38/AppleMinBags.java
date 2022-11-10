package com.kefeng.class38;

/**
 * 小明买苹果，使用容量为6和容量为8的袋子装，如果购买的苹果数不能用容量为6和容量为8的袋子装完，则不购买这些苹果，返回-1.如果需要能够装完苹果则返回所需的袋子数。
 */
public class AppleMinBags {

    public static int minBags(int apples) {
        if (apples < 0) {
            return -1;
        }
        int bags8 = apples / 8;
        int rest = apples - 8 * bags8;
        while (bags8 >= 0) {
            if (rest % 6 == 0) {
                return bags8 + (rest / 6);
            } else {
                bags8--;
                rest += 8;
            }
        }
        return -1;
    }

    public static int minBags1(int apples) {
        if ((apples & 1) != 0) {
            return -1;
        }
        if (apples<18){
            return apples == 0 ? 0 : (apples == 6 || apples == 8) ? 1 : (apples == 12 || apples == 14 || apples == 16) ? 2 : -1;
        }
        return (apples - 18) / 8 + 3;
    }

    public static void main(String[] args) {
        for (int i = 1; i < 200; i++) {
            System.out.println(i + ":" + minBags1(i));
        }
    }
}
