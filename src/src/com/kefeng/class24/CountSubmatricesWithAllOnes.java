package com.kefeng.class24;


/**
 * 给定一个二维矩阵，返回元素全为1的子矩阵的个数
 * https://leetcode.com/problems/count-submatrices-with-all-ones
 */
public class CountSubmatricesWithAllOnes {

    public static int numSubmat(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0].length == 0) {
            return 0;
        }
        int nums = 0;
        int[] heights = new int[mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                heights[j] = mat[i][j] == 0 ? 0 : heights[j] + 1;
            }
            nums += largest(heights);
        }
        return nums;
    }

    public static int largest(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        int nums = 0;
        int N = heights.length;
        int[] stack = new int[N];
        int si = -1;
        for (int i = 0; i < N; i++) {
            while (si != -1 && heights[i] <= heights[stack[si]]) {
                int cur = stack[si--];
                /**遇到相等的值跳过，等到最后一起算*/
                if (heights[cur] > heights[i]) {
                    int left = si == -1 ? -1 : stack[si];
                    int L = i - left - 1;
                    int down = Math.max(left == -1 ? 0 : heights[left], heights[i]);
                    nums += (heights[cur] - down) * num(L);
                }
            }
            stack[++si] = i;
        }
        while (si != -1) {
            int cur = stack[si--];
            int left = si == -1 ? -1 : stack[si];
            int L = heights.length - left - 1;
            int down = left == -1 ? 0 : heights[left];
            nums += (heights[cur] - down) * num(L);
        }
        return nums;
    }

    public static int num(int n) {
        return (n * (n + 1) >> 1);
    }

}
