package com.kefeng.class15;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * letcode 200题
 * 求岛屿的数量
 */
public class NumberOfIslands {

    /**
     * 递归求解有多少个岛屿
     *
     * @param board
     * @return
     */
    public static int numIslands(char[][] board) {
        int islands = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '1') {
                    infect(board, i, j);
                    islands++;
                }
            }
        }
        return islands;
    }

    public static void infect(char[][] board, int i, int j) {
        if (i < 0 || i == board.length || j < 0 || j == board[0].length || board[i][j] != '1') {
            return;
        }
        board[i][j] = '2';
        infect(board, i - 1, j);
        infect(board, i + 1, j);
        infect(board, i, j - 1);
        infect(board, i, j + 1);
    }

    /**
     * 并查集求解有多少个岛屿
     * 使用数组构建并查集
     * @param board
     * @return
     */
    public static int numIslands1(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        UnionFind unionFind = new UnionFind(board);
        for (int i = 1; i < col; i++) {
            if (board[0][i - 1] == '1' && board[0][i] == '1') {
                unionFind.union(0, i - 1, 0, i);
            }
        }
        for (int i = 1; i < row; i++) {
            if (board[i - 1][0] == '1' && board[i][0] == '1') {
                unionFind.union(i - 1, 0, i, 0);
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (board[i][j] == '1') {
                    if (board[i - 1][j] == '1') {
                        unionFind.union(i - 1, j, i, j);
                    }
                    if (board[i][j - 1] == '1') {
                        unionFind.union(i, j - 1, i, j);
                    }
                }
            }
        }
        return unionFind.sets();
    }

    public static class UnionFind {

        private int[] patent;
        private int[] size;
        private int[] help;
        private int sets;
        private int col;

        public UnionFind(char[][] board) {
            int row = board.length;
            col = board[0].length;
            int num = row * col;
            sets = 0;
            patent = new int[num];
            size = new int[num];
            help = new int[num];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (board[i][j] == '1') {
                        int inde = index(i, j);
                        patent[inde] = inde;
                        size[inde] = 1;
                        sets++;
                    }
                }
            }
        }

        public int index(int r, int c) {
            return r * col + c;
        }

        public int find(int i) {
            int hi = 0;
            while (patent[i] != i) {
                help[hi++] = i;
                i = patent[i];
            }
            for (hi--; hi >= 0; hi--) {
                patent[help[hi]] = i;
            }
            return i;
        }

        public void union(int r1, int c1, int r2, int c2) {
            int n1 = index(r1, c1);
            int n2 = index(r2, c2);
            int head1 = find(n1);
            int head2 = find(n2);
            if (head1 != head2) {
                if (size[head1] >= size[head2]) {
                    size[head1] += size[head2];
                    patent[head2] = head1;
                } else {
                    size[head2] += size[head1];
                    patent[head1] = head2;
                }
                sets--;
            }
        }

        public int sets() {
            return sets;
        }
    }

    /**
     * 并查集求解岛屿数量
     * 使用哈希表构建并查集，使用封装和泛型的技术扩展程序
     * @param board
     * @return
     */
    public static int numIslands2(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        Dot[][] dots = new Dot[row][col];
        List<Dot> dotList = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == '1') {
                    dots[i][j] = new Dot();
                    dotList.add(dots[i][j]);
                }
            }
        }
        UnionFind1<Dot> unionFind1 = new UnionFind1<>(dotList);
        for (int i = 1; i < col; i++) {
            if (board[0][i - 1] == '1' && board[0][i] == '1') {
                unionFind1.union(dots[0][i - 1], dots[0][i]);
            }
        }
        for (int j = 1; j < row; j++) {
            if (board[j - 1][0] == '1' && board[j][0] == '1') {
                unionFind1.union(dots[j - 1][0], dots[j][0]);
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (board[i][j] == '1') {
                    if (board[i - 1][j] == '1') {
                        unionFind1.union(dots[i - 1][j], dots[i][j]);
                    }
                    if (board[i][j - 1] == '1') {
                        unionFind1.union(dots[i][j - 1], dots[i][j]);
                    }
                }
            }
        }
        return unionFind1.sets();
    }

    public static class Dot {

    }

    public static class Node<V> {

        V value;

        public Node(V v) {
            value = v;
        }
    }

    public static class UnionFind1<V> {

        public HashMap<V, Node<V>> nodes;
        public HashMap<Node<V>, Node<V>> parents;
        public HashMap<Node<V>, Integer> sizeMap;

        public UnionFind1(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V cur : values
            ) {
                Node<V> node = new Node<>(cur);
                nodes.put(cur, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public Node<V> findFather(Node<V> cur) {
            Stack<Node<V>> path = new Stack<>();
            while (cur != parents.get(cur)) {
                path.push(cur);
                cur = parents.get(cur);
            }
            while (!path.isEmpty()) {
                parents.put(path.pop(), cur);
            }
            return cur;
        }

        public void union(V a, V b) {
            Node<V> aHead = findFather(nodes.get(a));
            Node<V> bHead = findFather(nodes.get(b));
            if (aHead != bHead) {
                int aSize = sizeMap.get(aHead);
                int bSize = sizeMap.get(bHead);
                Node<V> big = aSize >= bSize ? aHead : bHead;
                Node<V> small = big == aHead ? bHead : aHead;
                sizeMap.put(big, aSize + bSize);
                parents.put(small, big);
                sizeMap.remove(small);
            }
        }

        public int sets() {
            return sizeMap.size();
        }
    }
}
