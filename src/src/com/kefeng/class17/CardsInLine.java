package com.kefeng.class17;


/**
 * 给定数组，玩家A和玩家B依次拿出元素，求最后胜出者拿到的最高分数
 * A先拿，则B后拿；B先拿，则A后拿
 */
public class CardsInLine {

    /**
     * 暴力递归求解获得最大分数
     *
     * @param arr
     * @return
     */
    public static int win(int[] arr) {
        int first = f(arr, 0, arr.length - 1);
        int second = g(arr, 0, arr.length - 1);
        return Math.max(first, second);
    }

    public static int f(int[] arr, int L, int R) {
        if (L == R) {
            return arr[L];
        }
        int p1 = arr[L] + g(arr, L + 1, R);
        int p2 = arr[R] + g(arr, L, R - 1);
        return Math.max(p1, p2);
    }

    public static int g(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        int p1 = f(arr, L + 1, R);
        int p2 = f(arr, L, R - 1);
        return Math.min(p1, p2);
    }

    /**
     * 记忆化搜索求得最大分数
     *
     * @param arr
     * @return
     */
    public static int win1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int N = arr.length;
        int[][] fmap = new int[N][N];
        int[][] gmap = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                fmap[i][j] = -1;
                gmap[i][j] = -1;
            }
        }
        int first = f1(arr, 0, arr.length - 1, fmap, gmap);
        int second = g1(arr, 0, arr.length - 1, fmap, gmap);
        return Math.max(first, second);
    }

    public static int f1(int[] arr, int L, int R, int[][] fmap, int[][] gmap) {
        if (fmap[L][R] != -1) {
            return fmap[L][R];
        }
        int ans = 0;
        if (L == R) {
            ans = arr[L];
        } else {
            int p1 = arr[L] + g1(arr, L + 1, R, fmap, gmap);
            int p2 = arr[R] + g1(arr, L, R - 1, fmap, gmap);
            ans = Math.max(p1, p2);
        }
        fmap[L][R] = ans;
        return ans;
    }

    public static int g1(int[] arr, int L, int R, int[][] fmap, int[][] gmap) {
        if (gmap[L][R] != -1) {
            return gmap[L][R];
        }
        int ans = 0;
        if (L != R) {
            int p1 = f1(arr, L + 1, R, fmap, gmap);
            int p2 = f1(arr, L, R - 1, fmap, gmap);
            ans = Math.min(p1, p2);
        }
        gmap[L][R] = ans;
        return ans;
    }

    /**
     * 动态规划求最大分数
     *
     * @param arr
     * @return
     */
    public static int win2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int N = arr.length;
        int[][] fmap = new int[N][N];
        int[][] gmap = new int[N][N];
        for (int i = 0; i < N; i++) {
            fmap[i][i] = arr[i];
        }
        for (int startCol = 1; startCol < N; startCol++) {
            int L = 0;
            int R = startCol;
            while (R < N) {
                fmap[L][R] = Math.max(arr[L] + gmap[L + 1][R], arr[R] + gmap[L][R - 1]);
                gmap[L][R] = Math.min(fmap[L + 1][R], fmap[L][R - 1]);
                L++;
                R++;
            }
        }
        return Math.max(fmap[0][N - 1], gmap[0][N - 1]);
    }

    public static void main(String[] args) {
        int[] arr = {5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7};
        System.out.println(win(arr));
        System.out.println(win1(arr));
        System.out.println(win2(arr));
    }
}
