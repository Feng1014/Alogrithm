package com.kefeng.class23;


import java.util.LinkedList;

/**
 * 加油站数组，给定加油站里有多少油，数组存储；每个加油站到下一站需要消耗多少油，数组存储。
 * 问，客车从那个加油站出发能跑完所有的加油站，返回可能跑完的加油站索引。
 */
public class GasStation {

    public static int canCompleteCircuit(int[] gas, int[] cost) {
        boolean[] good = goodArray(gas, cost);
        for (int i = 0; i < good.length; i++) {
            if (good[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 判断从任意加油站出发能够跑完所有加油站
     *
     * @param gas  加油站含油量
     * @param cost 加油站到下个加油站需要消耗多少油
     * @return 返回能否跑完的数组，true能跑完，false无法跑完
     */
    public static boolean[] goodArray(int[] gas, int[] cost) {
        int N = gas.length;
        int M = N << 1;
        int[] arr = new int[M];
        for (int i = 0; i < N; i++) {
            arr[i] = gas[i] - cost[i];
            arr[i + N] = gas[i] - cost[i];
        }
        for (int i = 1; i < M; i++) {
            arr[i] += arr[i - 1];
        }
        LinkedList<Integer> W = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            while (!W.isEmpty() && arr[W.peekLast()] >= arr[i]) {
                W.pollLast();
            }
            W.addLast(i);
        }
        boolean[] result = new boolean[N];
        for (int i = 0, offset = 0, j = N; j < M; offset = arr[i++], j++) {
            if (arr[W.peekFirst()] - offset >= 0) {
                result[i] = true;
            }
            if (W.peekFirst() == i) {
                W.pollFirst();
            }
            while (!W.isEmpty() && arr[W.peekLast()] >= arr[j]) {
                W.pollLast();
            }
            W.addLast(j);
        }
        return result;
    }

}
