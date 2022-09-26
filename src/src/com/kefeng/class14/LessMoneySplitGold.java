package com.kefeng.class14;



import java.util.PriorityQueue;

/**
 * 对于一个数组构成的黄金，求分割黄金最少的代价
 * 【10， 20， 30】组成和为60的黄金，求分割成【10， 20， 30】这样所需的最少代价
 * 60先分割成30和30，代价是60，30再分割成10，20，代价是30，所以总的代价是90.
 * 60可以先分割成50和10，代价是60；接着50分割成20，30，代价是50；总代价是110，比较两种分割方式，第一种代价少，所以选择第一种。
 */
public class LessMoneySplitGold {

    public static int lessMoney(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process(arr, 0);
    }

    /**
     * 暴力枚举，第一个元素和第二个求和，再和第三个，再和第四个；接着第二个再和第三个，第四个，第五个，依次下去即可得到一个最优的答案
     *
     * @param arr
     * @param pre
     * @return
     */
    public static int process(int[] arr, int pre) {
        if (arr.length == 1) {
            return pre;
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                ans = Math.min(ans, process(copyAndMerge(arr, i, j), pre + arr[i] + arr[j]));
            }
        }
        return ans;
    }

    public static int[] copyAndMerge(int[] arr, int i, int j) {
        int[] ans = new int[arr.length - 1];
        int k = 0;
        for (int l = 0; l < arr.length; l++) {
            if (l != i && l != j) {
                ans[k++] = arr[l];
            }
        }
        ans[k] = arr[i] + arr[j];
        return ans;
    }

    /**
     * 使用PriorityQueue优先队列存储数组，相当于把数组转换成小顶堆，从小顶堆里面拿出元素进行求和
     * 哈夫曼编码的过程
     *
     * @param arr
     * @return
     */
    public static int lessMoney1(int[] arr) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i < arr.length; i++) {
            queue.add(arr[i]);
        }
        int sum = 0;
        int cur = 0;
        while (queue.size() > 1) {
            cur = queue.poll() + queue.poll();
            sum += cur;
            queue.add(cur);
        }
        return sum;
    }

    public static int[] generateArr(int maxsize, int maxvalue) {
        int[] arr = new int[(int) (Math.random() * (maxsize + 1))];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxvalue + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
        int maxsize = 6;
        int maxvalue = 1000;
        int testtimes = 1000;
        for (int i = 0; i < testtimes; i++) {
            int[] arr = generateArr(maxsize, maxvalue);
            if (lessMoney1(arr) != lessMoney(arr)) {
                System.out.println("Oops=====");
            }
        }
        System.out.println("finish");
    }
}
