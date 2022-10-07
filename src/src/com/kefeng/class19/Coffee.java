package com.kefeng.class19;


import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 数组arr代表一个咖啡机冲一杯咖啡需要的时间，n个人需要喝咖啡，只能使用咖啡机串行冲咖啡。冲好的时间就是喝完的时间。
 * 只有一台洗咖啡的机器，咖啡喝完之后可以洗可以自然挥发。洗咖啡杯是串行执行，自然挥发是并行执行。
 * 四个参数，arr，n，a（洗杯子的时间），b（自然挥发的时间）
 * 假设从0点开始，返回所有人喝完咖啡且洗完咖啡杯到达的时间点。
 */
public class Coffee {

    public static class Machine {
        public int timePoint;
        public int workTime;

        public Machine(int t, int w){
            timePoint = t;
            workTime = w;
        }
    }

    public static class MachineComparator implements Comparator<Machine>{

        @Override
        public int compare(Machine o1, Machine o2) {
            return (o1.timePoint + o1.workTime) - (o2.timePoint + o2.workTime);
        }
    }

    public static int minTime(int[] arr, int n, int a, int b){
        PriorityQueue<Machine> heap = new PriorityQueue<>(new MachineComparator());
        for (int i = 0; i < arr.length; i++) {
            heap.add(new Machine(0, arr[i]));
        }
        int[] drinks = new int[n];
        for (int i = 0; i < n; i++) {
            Machine cur = heap.poll();
            cur.timePoint += cur.workTime;
            drinks[i] = cur.timePoint;
            heap.add(cur);
        }
        return bestTime(drinks, a, b, 0, 0);
    }

    /**
     * 咖啡喝完后，洗杯子的次序
     * @param drinks 数组表示n个人喝完咖啡的时间，即能洗杯子的时间
     * @param wash 洗杯子的时间
     * @param air 自然挥发的时间
     * @param index 从index索引开始洗杯子
     * @param free 洗机器的可用时间点
     * @return
     */
    public static int bestTime(int[] drinks, int wash, int air, int index, int free){
        if (index == drinks.length){
            return 0;
        }
        /**洗第index个杯子后到达的时间点，和洗剩余所有杯子后到达的时间点，二者求最大值（木桶原理）。洗杯子是串行的，洗完第index杯子的时间点即是剩余杯子开始洗的时间点*/
        int selfWash = Math.max(drinks[index], free) + wash;
        int restWash = bestTime(drinks, wash, air, index + 1, selfWash);
        int p1 = Math.max(selfWash, restWash);
        /**挥发第index个杯子后到达的时间点，和挥发剩余所有杯子后到达的时间点，二者求最大值（木桶原理）。挥发杯子是并行的，挥发完第index杯子和挥发剩余杯子同时执行*/
        int selfAir = drinks[index] + air;
        int restAir = bestTime(drinks, wash, air, index + 1, free);
        int p2 = Math.max(selfAir, restAir);
        return Math.min(p1, p2);
    }

    /**喝咖啡阶段和暴力递归阶段无差别，洗杯子使用动态规划求洗完所有杯子的最小时间点*/
    public static int minTimeDp(int[] arr, int n, int a, int b){
        PriorityQueue<Machine> heap = new PriorityQueue<>(new MachineComparator());
        for (int i = 0; i < arr.length; i++) {
            heap.add(new Machine(0, arr[i]));
        }
        int[] drinks = new int[n];
        for (int i = 0; i < n; i++) {
            Machine cur = heap.poll();
            cur.timePoint += cur.workTime;;
            drinks[i] = cur.timePoint;
            heap.add(cur);
        }
        return bestTimeDp(drinks, a, b);
    }

    public static int bestTimeDp(int[] drinks, int wash, int air){
        int N = drinks.length;
        int maxFree = 0;
        for (int i = 0; i < N; i++) {
            maxFree = Math.max(maxFree, drinks[i]) + wash;
        }
        int[][] dp = new int[N + 1][maxFree + 1];
        for (int index = N - 1; index >= 0; index--) {
            for (int free = 0; free <= maxFree; free++) {
                /**index号杯子决定洗*/
                int selfWash = Math.max(drinks[index], free) + wash;
                if (selfWash > maxFree){
                    /**index号杯子洗完的时间比所有杯子洗完的时间都大，则转移矩阵中使用不到该转移变量，所以不用填写矩阵，以index号开始洗杯子的时间点开始，之后所有的时间点都不用洗杯子*/
                    break;
                }
                int restWash = dp[index + 1][selfWash];
                int p1 = Math.max(selfWash, restWash);
                /**index号杯子决定挥发*/
                int selfAir = drinks[index] + air;
                int restAir = dp[index + 1][free];
                int p2 = Math.max(selfAir, restAir);
                /**在free时间点开始洗index号之后所有的杯子需要的最少时间*/
                dp[index][free] = Math.min(p1, p2);
            }
        }
        return dp[0][0];
    }

    public static int[] randomArray(int len, int max){
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) ((Math.random() * max) + 1);
        }
        return arr;
    }

    public static void main(String[] args) {
        int len = 10;
        int max = 10;
        int testTime = 10;
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(len, max);
            int n = (int) ((Math.random() * 7) + 1);
            int a = (int) ((Math.random() * 7) + 1);
            int b = (int) ((Math.random() * 7) + 1);
            int ans1 = minTime(arr, n, a, b);
            int ans2 = minTimeDp(arr, n, a, b);
            if (ans1 == ans2){
                System.out.println("finished");
            }
        }
    }
}
