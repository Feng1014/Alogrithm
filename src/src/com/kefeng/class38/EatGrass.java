package com.kefeng.class38;

/**
 * 牛和羊吃N份草，每次按照1，4，16...的规律吃草，谁先吃完草谁获胜。问最终谁会获胜。
 */
public class EatGrass {

    public static String whoWin(int n) {
        if (n < 5) {
            return (n == 0 || n == 2) ? "后手" : "先手";
        }
        int want = 1;
        while (n - want >= 0) {
            if (whoWin(n - want).equals("后手")) {
                return "先手";
            }
            if (want <= (n / 4)) {
                want *= 4;
            } else {
                break;
            }
        }
        return "后手";
    }

    public static String whoWin1(int n) {
        if (n % 5 == 0 || n % 5 == 2) {
            return "后手";
        } else {
            return "先手";
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 200000000; i++) {
            System.out.println(i + ":" + whoWin1(i));
        }
    }
}
