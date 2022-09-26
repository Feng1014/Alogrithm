package com.kefeng.class15;

/**
 * letcode第547题
 * 求解矩阵中的相互关联的集合
 */
public class FriendCircles {

    public static int findCircleNum(int[][] M){
        int N = M.length;
        UnionFind unionFind = new UnionFind(N);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (M[i][j] == 1){
                    unionFind.union(i, j);
                }
            }
        }
        return unionFind.sets();
    }

    public static class UnionFind{

        private int[] parent;
        private int[] size;
        private int[] help;
        private int sets;

        public UnionFind(int N){
            parent = new int[N];
            size = new int[N];
            help = new int[N];
            sets = N;
            for (int i = 0; i < N; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public int find(int i){
            int hi = 0;
            while (i != parent[i]){
                help[hi++] = i;
                i = parent[i];
            }
            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = i;
            }
            return i;
        }

        public void union(int i, int j){
            int ihead = find(i);
            int jhead = find(j);
            if (ihead != jhead){
                int isize = size[ihead];
                int jsize = size[jhead];
                if (isize >= jsize){
                    size[ihead] += size[jhead];
                    parent[jhead] = ihead;
                }else {
                    size[jhead] += size[ihead];
                    parent[ihead] = jhead;
                }
                sets--;
            }
        }

        public int sets(){
            return sets;
        }
    }
}
