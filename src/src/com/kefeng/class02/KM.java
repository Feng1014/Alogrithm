package com.kefeng.class02;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 一个序列有一种数出现K次，其余数全部出现M次，求出现K次的这种数
 */
public class KM {

    public static int test(int[] arr, int K, int M) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : arr
        ) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }
        int ans = 0;
        for (int nu : map.keySet()
        ) {
            if (map.get(nu) == K) {
                ans = nu;
                break;
            }
        }
        return ans;
    }

    public static int onlyKTimes(int[] arr, int K, int M) {
        /**每个整数是32位，所以准备32长度的数组。存储每个数的每个二进制位上是否是1*/
        int[] t = new int[32];
        for (int num : arr
        ) {
            /**每个数的每个二进制位向右移动i位后和1做与运算，即可得到该数的第i位二进制是否为1，如果是1则累加到数组t中*/
            for (int i = 0; i < 32; i++) {
                t[i] += (num >> i) & 1;
            }
        }
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            /**对于数组t,如果每个位置1的个数对M取余后不等于0，则说明该位一定有出现次数为K的数进行累加*/
            if ((t[i] % M) != 0) {
                /**1向左移动i位后，和ans做或运算，可以得到出现K次的数*/
                ans |= (1 << i);
            }
        }
        return ans;
    }

    public static int[] randomArray(int kinds, int range, int K, int M) {
        int kNumTimes = randomValue(range);
        int numTimes = (int) (Math.random() * kinds) + 2;
        int[] arr = new int[K + (numTimes - 1) * M];
        int index = 0;
        for (; index < K; index++) {
            arr[index] = kNumTimes;
        }
        HashSet<Integer> set = new HashSet<>();
        set.add(kNumTimes);
        numTimes--;
        while (numTimes != 0) {
            int curNum = 0;
            do {
                curNum = randomValue(range);
            } while (set.contains(curNum));
            set.add(curNum);
            for (int i = 0; i < M; i++) {
                arr[index++] = curNum;
            }
            numTimes--;
        }
        for (int i = 0; i < arr.length; i++) {
            int j = (int) (Math.random() * arr.length);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        return arr;
    }

    public static int randomValue(int range) {
        return (int) (Math.random() * range) - (int) (Math.random() * range);
    }

    public static void main(String[] args) {
        int kinds = 4;
        int range = 20;
        int testTimes = 100;
        int max = 9;
        for (int i = 0; i < testTimes; i++) {
            int a = (int) (Math.random() * max) + 1;
            int b = (int) (Math.random() * max) + 1;
            int K = Math.min(a, b);
            int M = Math.max(a, b);
            if (K == M) {
                M++;
            }
            int[] arr = randomArray(kinds, range, K, M);
            int ans1 = test(arr, K, M);
            int ans2 = onlyKTimes(arr, K, M);
            if (ans1 != ans2) {
                System.out.println("error");
            }
        }
        System.out.println("finished");
    }
}
