package com.kefeng.class15;


import java.util.ArrayList;
import java.util.List;

/**
 * 求岛屿数量
 * 二维数组中的0，1是依次给出的， 计算每一步可用的岛屿数量
 */
public class NumberOfIslands2 {

    public static List<Integer> numIslands(int m, int n, int[][] positions) {
        UnionFind unionFind = new UnionFind(m, n);
        List<Integer> ans = new ArrayList<>();
        for (int[] position : positions
        ) {
            ans.add(unionFind.connect(position[0], position[1]));
        }
        return ans;
    }

    public static class UnionFind {

        private int[] parent;
        private int[] size;
        private int[] help;
        private final int col;
        private final int row;
        private int sets;

        public UnionFind(int m, int n) {
            row = m;
            col = n;
            sets = 0;
            int len = row * col;
            parent = new int[len];
            size = new int[len];
            help = new int[len];
        }

        public int index(int r, int c) {
            return r * col + c;
        }

        public int find(int i) {
            int hi = 0;
            while (i != parent[i]) {
                help[hi++] = i;
                i = parent[i];
            }
            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = i;
            }
            return i;
        }

        public void union(int r1, int c1, int r2, int c2) {
            if (r1 < 0 || r1 == row || c1 < 0 || c1 == col || r2 < 0 || r2 == row || c2 < 0 || c2 == col) {
                return;
            }
            int index1 = index(r1, c1);
            int index2 = index(r2, c2);
            if (size[index1] == 0 || size[index2] == 0) {
                return;
            }
            int f1 = find(index1);
            int f2 = find(index2);
            if (f1 != f2) {
                if (size[f1] >= size[f2]) {
                    size[f1] += size[f2];
                    parent[f2] = f1;
                } else {
                    size[f2] += size[f1];
                    parent[f1] = f2;
                }
                sets--;
            }
        }

        public int connect(int r, int c) {
            int index1 = index(r, c);
            if (size[index1] == 0) {
                parent[index1] = index1;
                size[index1] = 1;
                sets++;
                union(r - 1, c, r, c);
                union(r, c - 1, r, c);
                union(r, c, r, c + 1);
                union(r, c, r + 1, c);
            }
            return sets;
        }
    }
}
