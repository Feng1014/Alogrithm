package com.kefeng.class31;

/**
 * indexTree
 */
public class IndexTree {

    public static class indexTree {
        private int N;
        private int[] tree;

        public indexTree(int size) {
            N = size;
            tree = new int[N + 1];
        }

        /**
         * 求1-index上所有元素的值
         *
         * @param index
         * @return
         */
        public int sum(int index) {
            int ret = 0;
            while (index > 0) {
                ret += tree[index];
                index -= index & (-index);
            }
            return ret;
        }

        /**
         * 将index位置的元素加d，后续相关元素都加d
         *
         * @param index
         * @param d
         */
        public void add(int index, int d) {
            while (index <= N) {
                tree[index] += d;
                index += index & (-index);
            }
        }
    }

    public static class Right {
        private int N;
        private int[] tree;

        public Right(int size) {
            N = size + 1;
            tree = new int[N];
        }

        public int sum(int index) {
            int ret = 0;
            for (int i = 1; i <= index; i++) {
                ret += tree[i];
            }
            return ret;
        }

        public void add(int index, int d) {
            tree[index] += d;
        }
    }

    public static void main(String[] args) {
        int N = 100;
        int V = 100;
        int testTimes = 200;
        indexTree tree = new indexTree(N);
        Right right = new Right(N);
        for (int i = 0; i < testTimes; i++) {
            int index = (int) ((Math.random() * N) + 1);
            if (Math.random() < 0.5) {
                int d = (int) ((Math.random() * V) + 1);
                tree.add(index, d);
                right.add(index, d);
            } else {
                if (tree.sum(index) != right.sum(index)) {
                    System.out.println("error");
                }
            }
        }
        System.out.println("finished");
    }
}
