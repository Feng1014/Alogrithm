package com.kefeng.class30;

/**
 * 线段树的增加，更新，查询
 */
public class SegmentTree {

    public static class segmentTree {
        private int MAXN;
        private int[] arr;
        private int[] sum;
        private int[] lazy;
        private int[] change;
        private boolean[] update;

        public segmentTree(int[] origin) {
            int N = origin.length;
            MAXN = N + 1;
            arr = new int[MAXN << 2];
            for (int i = 1; i < MAXN; i++) {
                arr[i] = origin[i - 1];
            }
            sum = new int[MAXN << 2];
            lazy = new int[MAXN << 2];
            change = new int[MAXN << 2];
            update = new boolean[MAXN << 2];
        }

        private void pushUp(int rt) {
            sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
        }

        /**
         * 初始化sum数组，某个区间范围的和
         *
         * @param l  区间左边界
         * @param r  区间右边界
         * @param rt sum数组的下标
         */
        public void build(int l, int r, int rt) {
            if (l == r) {
                sum[rt] = arr[l];
                return;
            }
            int mid = (l + r) >> 1;
            build(l, mid, rt << 1);
            build(mid + 1, r, rt << 1 | 1);
            pushUp(rt);
        }

        /**
         * 懒更新，如果父节点分配更新任务后，当前节点没有更新，下一个任务来的时候一起更新，称为懒更新。
         *
         * @param rt 父节点下表
         * @param ln 父节点的左子树元素个数
         * @param rn 父节点的右子树元素个数
         */
        private void pushDown(int rt, int ln, int rn) {
            if (update[rt]) {
                update[rt << 1] = true;
                update[rt << 1 | 1] = true;
                change[rt << 1] = change[rt];
                change[rt << 1 | 1] = change[rt];
                sum[rt << 1] = change[rt] * ln;
                sum[rt << 1 | 1] = change[rt] * rn;
                lazy[rt << 1] = 0;
                lazy[rt << 1 | 1] = 0;
                update[rt] = false;
            }
            if (lazy[rt] != 0) {
                lazy[rt << 1] += lazy[rt];
                sum[rt << 1] += lazy[rt] * ln;
                lazy[rt << 1 | 1] += lazy[rt];
                sum[rt << 1 | 1] += lazy[rt] * rn;
                lazy[rt] = 0;
            }
        }

        /**
         * 在区间[L,R]上增加C。
         *
         * @param L  表示要增加C值的区间左边界
         * @param R  表示要增加C值的区间的右边界
         * @param C  增加C值
         * @param l  [L,R]是任务的区间。具体的增加C值操作是在[l,r]范围区间。l表示左边界
         * @param r  表示右边界
         * @param rt 表示sum数组中的位置
         */
        public void add(int L, int R, int C, int l, int r, int rt) {
            /**任务区间完全覆盖范围区间，则范围区间上的所有元素都需要增加C*/
            if (L <= l && R >= r) {
                sum[rt] += C * (r - l + 1);
                lazy[rt] += C;
                return;
            }
            /**任务区间没有覆盖范围区间*/
            int mid = (l + r) >> 1;
            /**首先需要处理懒任务*/
            pushDown(rt, mid - l + 1, r - mid);
            if (L <= mid) {
                add(L, R, C, l, mid, rt << 1);
            }
            if (R > mid) {
                add(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            pushUp(rt);
        }

        /**
         * 更新区间[L,R]上所有的值为C
         *
         * @param L  任务左区间
         * @param R  任务右区间
         * @param C
         * @param l  范围左区间
         * @param r  范围右区间
         * @param rt change数组上要修改的坐标
         */
        public void update(int L, int R, int C, int l, int r, int rt) {
            /**任务区间完全覆盖范围区间，则在范围区间上更新*/
            if (L <= l && R >= r) {
                change[rt] = C;
                update[rt] = true;
                sum[rt] = C * (r - l + 1);
                lazy[rt] = 0;
                return;
            }
            /**任务区间没覆盖范围区间，则需要讨论范围区间*/
            int mid = (l + r) >> 1;
            /**先判断之前是否有没分发的任务，如果有则分发下去*/
            pushDown(rt, mid - l + 1, r - mid);
            /**判断任务是否需要分发后。如果需要，则在范围的左子范围更新*/
            if (L <= mid) {
                update(L, R, C, l, mid, rt << 1);
            }
            /**如果需要则在范围的右子范围上更新*/
            if (R > mid) {
                update(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            /**最后处理更新后的结果*/
            pushUp(rt);
        }

        /**
         * 查找区间[L,R]上的累加和
         *
         * @param L
         * @param R
         * @param l
         * @param r
         * @param rt
         * @return
         */
        public long query(int L, int R, int l, int r, int rt) {
            if (L <= l && R >= r) {
                return sum[rt];
            }
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            long ans = 0;
            if (L <= mid) {
                ans += query(L, R, l, mid, rt << 1);
            }
            if (R > mid) {
                ans += query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return ans;
        }
    }

    public static class Right {
        public int[] arr;

        public Right(int[] origin) {
            arr = new int[origin.length + 1];
            for (int i = 0; i < origin.length; i++) {
                arr[i + 1] = origin[i];
            }
        }

        public void update(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] = C;
            }
        }

        public void add(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] += C;
            }
        }

        public long query(int L, int R) {
            long ans = 0;
            for (int i = L; i <= R; i++) {
                ans += arr[i];
            }
            return ans;
        }
    }

    public static int[] genarateRandomArray(int len, int max) {
        int size = (int) (Math.random() * len) + 1;
        int[] origin = new int[size];
        for (int i = 0; i < size; i++) {
            origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return origin;
    }

    public static boolean test() {
        int len = 100;
        int max = 1000;
        int testTimes = 5000;
        int addOrUpdateTimes = 1000;
        int queryTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = genarateRandomArray(len, max);
            segmentTree seg = new segmentTree(origin);
            int S = 1;
            int N = origin.length;
            int root = 1;
            seg.build(S, N, root);
            Right rig = new Right(origin);
            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                int C = (int) (Math.random() * max) - (int) (Math.random() * max);
                if (Math.random() < 0.5) {
                    seg.add(L, R, C, S, N, root);
                    rig.add(L, R, C);
                } else {
                    seg.update(L, R, C, S, N, root);
                    rig.update(L, R, C);
                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                long ans1 = seg.query(L, R, S, N, root);
                long ans2 = rig.query(L, R);
                if (ans1 != ans2) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] origin = { 2, 1, 1, 2, 3, 4, 5 };
        segmentTree seg = new segmentTree(origin);
        int S = 1; // 整个区间的开始位置，规定从1开始，不从0开始 -> 固定
        int N = origin.length; // 整个区间的结束位置，规定能到N，不是N-1 -> 固定
        int root = 1; // 整棵树的头节点位置，规定是1，不是0 -> 固定
        int L = 2; // 操作区间的开始位置 -> 可变
        int R = 5; // 操作区间的结束位置 -> 可变
        int C = 4; // 要加的数字或者要更新的数字 -> 可变
        // 区间生成，必须在[S,N]整个范围上build
        seg.build(S, N, root);
        // 区间修改，可以改变L、R和C的值，其他值不可改变
        seg.add(L, R, C, S, N, root);
        // 区间更新，可以改变L、R和C的值，其他值不可改变
        seg.update(L, R, C, S, N, root);
        // 区间查询，可以改变L和R的值，其他值不可改变
        long sum = seg.query(L, R, S, N, root);
        System.out.println(sum);

        System.out.println("对数器测试开始...");
        System.out.println("测试结果 : " + (test() ? "通过" : "未通过"));

    }

}
