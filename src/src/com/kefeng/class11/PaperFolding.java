package com.kefeng.class11;

/**
 * 一张纸条经过多次对折，打印出每个折痕的方向（向下为凹，向上为凸）
 */
public class PaperFolding {

    public static void printAllFolds(int N) {
        process(1, N, true);
    }

    public static void process(int i, int N, boolean down) {
        if (i > N) {
            return;
        }
        process(i + 1, N, true);
        System.out.print(down ? "凹 " : "凸 ");
        process(i + 1, N, false);
    }

    public static void main(String[] args) {
        int N = 4;
        printAllFolds(N);
    }
}
