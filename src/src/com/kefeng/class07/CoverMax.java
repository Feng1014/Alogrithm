package com.kefeng.class07;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 给定线段数组，求包含最多线段的区间，给出包含最多的线段个数
 */
public class CoverMax {

    public static int maxCover(int[][] lines) {
        int minStart = Integer.MAX_VALUE;
        int maxEnd = Integer.MIN_VALUE;
        for (int i = 0; i < lines.length; i++) {
            minStart = Math.min(minStart, lines[i][0]);
            maxEnd = Math.max(maxEnd, lines[i][1]);
        }
        int cover = 0;
        for (double p = minStart + 0.5; p < maxEnd; p += 1) {
            int cur = 0;
            for (int j = 0; j < lines.length; j++) {
                if (lines[j][0] < p && lines[j][1] > p) {
                    cur++;
                }
            }
            cover = Math.max(cover, cur);
        }
        return cover;
    }

    public static class Line {
        public int start;
        public int end;

        public Line(int S, int E) {
            start = S;
            end = E;
        }
    }

    public static int maxCover1(int[][] M) {
        Line[] lines = new Line[M.length];
        for (int i = 0; i < M.length; i++) {
            lines[i] = new Line(M[i][0], M[i][1]);
        }
        Arrays.sort(lines, new StartComparator());
        int cover = 0;
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int i = 0; i < lines.length; i++) {
            while (!heap.isEmpty() && heap.peek() <= lines[i].start) {
                heap.poll();
            }
            heap.add(lines[i].end);
            cover = Math.max(cover, heap.size());
        }
        return cover;
    }

    /**
     * 比较器的规范
     * 返回负数，第一个参数排在前面
     * 返回正数，第二个参数排在前面
     * 返回0，任意参数排前面均可
     */
    public static class StartComparator implements Comparator<Line> {

        @Override
        public int compare(Line o1, Line o2) {
            return o1.start - o2.start;
        }
    }

    public static int[][] generateLines(int L, int S, int E) {
        int size = (int) (Math.random() * L) + 1;
        int[][] lines = new int[size][2];
        for (int i = 0; i < size; i++) {
            int a = (int) (Math.random() * (E - S + 1));
            int b = (int) (Math.random() * (E - S + 1));
            if (a == b) {
                b = a + 1;
            }
            lines[i][0] = Math.min(a, b);
            lines[i][1] = Math.max(a, b);
        }
        return lines;
    }

    public static void main(String[] args) {
        int L = 100;
        int S = 0;
        int E = 200;
        int testTimes = 1000;
        for (int i = 0; i < testTimes; i++) {
            int[][] lines = generateLines(L, S, E);
            int ans1 = maxCover(lines);
            int ans2 = maxCover1(lines);
            if (ans1 != ans2) {
                System.out.println("error");
            }
        }
        System.out.println("finished");
    }

}
