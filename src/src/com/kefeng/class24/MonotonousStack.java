package com.kefeng.class24;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 单调栈
 */
public class MonotonousStack {

    /**
     * 无重复数组求每个元素左右两侧距离最近得到最小值
     * @param arr
     * @return
     */
    public static int[][] getNearLessNoRepeat(int[] arr) {
        int[][] ans = new int[arr.length][2];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                int temp = stack.pop();
                int leftMin = stack.isEmpty() ? -1 : stack.peek();
                ans[temp][0] = leftMin;
                ans[temp][1] = i;
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int temp = stack.pop();
            int leftMin = stack.isEmpty() ? -1 : stack.peek();
            ans[temp][0] = leftMin;
            ans[temp][1] = -1;
        }
        return ans;
    }

    /**
     * 有重复元素的数组求每个元素左右两侧距离最近的最小值
     * @param arr
     * @return
     */
    public static int[][] getNearLess(int[] arr) {
        int[][] ans = new int[arr.length][2];
        Stack<List<Integer>> stack = new Stack<>();
        for (int i = 0; i < ans.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
                List<Integer> popis = stack.pop();
                int leftMin = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                for (Integer popi : popis
                ) {
                    ans[popi][0] = leftMin;
                    ans[popi][1] = i;
                }
            }
            if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
                stack.peek().add(Integer.valueOf(i));
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                stack.add(list);
            }
        }
        while (!stack.isEmpty()) {
            List<Integer> popis = stack.pop();
            int leftMin = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
            for (Integer popi : popis
            ) {
                ans[popi][0] = leftMin;
                ans[popi][1] = -1;
            }
        }
        return ans;
    }

    public static int[][] rightWay(int[] arr) {
        int[][] res = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            int leftLessIndex = -1;
            int rightLessIndex = -1;
            int cur = i - 1;
            while (cur >= 0) {
                if (arr[cur] < arr[i]) {
                    leftLessIndex = cur;
                    break;
                }
                cur--;
            }
            cur = i + 1;
            while (cur < arr.length) {
                if (arr[cur] < arr[i]) {
                    rightLessIndex = cur;
                    break;
                }
                cur++;
            }
            res[i][0] = leftLessIndex;
            res[i][1] = rightLessIndex;
        }
        return res;
    }

    public static int[] generateArray(int size, int max) {
        int N = (int) (Math.random() * size) + 1;
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * max) + 1;
        }
        return arr;
    }

    public static int[] generateNoRepeatArray(int size) {
        int N = (int) (Math.random() * size) + 1;
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < N; i++) {
            int swapIndex = (int) (Math.random() * arr.length);
            int temp = arr[swapIndex];
            arr[swapIndex] = arr[i];
            arr[i] = temp;
        }
        return arr;
    }

    public static boolean isEqual(int[][] arr1, int[][] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i][0] != arr2[i][0] || arr1[i][1] != arr2[i][1]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int size = 10;
        int max = 20;
        int testTimes = 100;
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = generateArray(size, max);
            int[] arr2 = generateNoRepeatArray(size);
            if (!isEqual(getNearLess(arr1), rightWay(arr1))) {
                System.out.println("error");
            }
            if (!isEqual(getNearLessNoRepeat(arr2), rightWay(arr2))) {
                System.out.println("error");
            }
        }
        System.out.println("finished");
    }

}
