package com.kefeng;


import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 一个项目投入C，纯利润P，在初始资金W的情况下，最多做K个项目，求K个项目之后最大的利润（本金+纯利润）
 */
public class IPO {

    public static class program {

        public int cost;
        public int profit;

        public program(int cost, int profit) {
            this.cost = cost;
            this.profit = profit;
        }
    }

    public static int maxProfit(int W, int K, int[] costs, int[] profits) {

        PriorityQueue<program> minQueue = new PriorityQueue<>(new minComparator());
        PriorityQueue<program> maxQueue = new PriorityQueue<>(new maxComparator());

        for (int i = 0; i < costs.length; i++) {
            minQueue.add(new program(costs[i], profits[i]));
        }
        for (int i = 0; i < K; i++) {
            while (!minQueue.isEmpty() && minQueue.peek().cost <= W) {
                maxQueue.add(minQueue.poll());
            }
            if (maxQueue.isEmpty()) {
                return W;
            }
            W += maxQueue.poll().profit;
        }
        return W;
    }

    public static class minComparator implements Comparator<program> {

        @Override
        public int compare(program o1, program o2) {
            return o1.cost - o2.cost;
        }
    }

    public static class maxComparator implements Comparator<program> {

        @Override
        public int compare(program o1, program o2) {
            return o2.profit - o1.profit;
        }
    }

    public static program[] generateProgram(int maxSize, int maxValue) {
        program[] programs = new program[(int) (Math.random() * (maxSize + 1))];
        for (int i = 0; i < programs.length; i++) {
            int r1 = (int) (Math.random() * (maxValue + 1));
            int r2 = (int) (Math.random() * (maxValue + 1));
            if (r1 == r2) {
                programs[i] = new program(r1, r1 + 1);
            } else {
                programs[i] = new program(r1, r2);
            }
        }
        return programs;
    }

    public static int[] generateCost(program[] programs){
        int N = programs.length;
        int[] costs = new int[N];
        for (int i = 0; i < N; i++) {
            costs[i] = programs[i].cost;
        }
        return costs;
    }

    public static int[] generateProfit(program[] programs){
        int N = programs.length;
        int[] profits = new int[N];
        for (int i = 0; i < N; i++) {
            profits[i] = programs[i].profit;
        }
        return profits;
    }

    public static void main(String[] args) {
        int maxsize = 10;
        int maxvalue = 20;
        int testtimes = 1000;
        for (int i = 0; i < testtimes; i++) {
            program[] programs = generateProgram(maxsize, maxvalue);
            int[] costs = generateCost(programs);
            int[] profits = generateProfit(programs);
            int K = (int) (Math.random() * (maxsize + 1));
            int W = (int) (Math.random() * (maxvalue + 1));
            System.out.println(maxProfit(W, K, costs, profits));
        }
    }
}

