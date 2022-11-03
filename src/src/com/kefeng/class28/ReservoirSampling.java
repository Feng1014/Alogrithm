package com.kefeng.class28;

/**
 * 蓄水池算法
 */
public class ReservoirSampling {

    public static int randomNum(int i) {
        return (int) (Math.random() * i) + 1;
    }

    public static void main(String[] args) {
        int testTimes = 100;
        int[] counts = new int[201];
        for (int i = 0; i < testTimes; i++) {
            int[] bag = new int[10];
            int bagi = 0;
            for (int j = 1; j <= 200; j++) {
                if (j <= 10) {
                    bag[bagi++] = j;
                } else {
                    if (randomNum(j) <= 10) {
                        bagi = (int) (Math.random() * 10);
                        bag[bagi] = j;
                    }
                }
            }
            for (int num : bag
            ) {
                counts[num]++;
            }
        }
        for (int i = 0; i <= counts.length; i++) {
            System.out.println(counts[i]);
        }
    }
}
