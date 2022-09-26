package com.kefeng.class14;


import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;


/**
 * 对于一个字符串数组，随意排列，寻找字典序最小的字符组合  贪心算法
 */
public class LowestLexiCography {

    /**
     * 暴力求解 对所有的字符串遍历
     *
     * @param strs
     * @return
     */
    public static String lowestStrs(String[] strs) {
        if (strs == null || strs.length == 0) {
            return null;
        }
        TreeSet<String> ans = process(strs);
        return ans.size() == 0 ? "" : ans.first();
    }

    public static TreeSet<String> process(String[] strs) {
        TreeSet<String> ans = new TreeSet<>();
        if (strs.length == 0) {
            ans.add("");
            return ans;
        }
        for (int i = 0; i < strs.length; i++) {
            String first = strs[i];
            String[] nexts = removeIndex(strs, i);
            TreeSet<String> next = process(nexts);
            for (String cur : next
            ) {
                ans.add(first + cur);
            }
        }
        return ans;
    }

    /**
     * 删除字符串数组strs中坐标是i的字符串
     *
     * @param strs
     * @param index
     * @return
     */
    public static String[] removeIndex(String[] strs, int index) {
        int N = strs.length;
        int ansIndex = 0;
        String[] ans = new String[N - 1];
        for (int i = 0; i < N; i++) {
            if (i != index) {
                ans[ansIndex++] = strs[i];
            }
        }
        return ans;
    }

    public static class myComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return (o1 + o2).compareTo(o2 + o1);
        }
    }

    /**
     * 贪心算法计算最短字典序
     *
     * @param strs
     * @return
     */
    public static String lowestStr1(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        Arrays.sort(strs, new myComparator());
        String ans = "";
        for (int i = 0; i < strs.length; i++) {
            ans += strs[i];
        }
        return ans;
    }

    public static String generateRandomString(int strl) {
        char[] an = new char[(int) (Math.random() * strl )+ 1];
        for (int i = 0; i < an.length; i++) {
            int value = (int) (Math.random() * 5);
            an[i] = (char) ((Math.random() <= 0.5) ? (value + 65) : (value + 97));
        }
        return String.valueOf(an);
    }

    public static String[] generateRandomStringArray(int strl, int arrl) {
        String[] ans = new String[(int) (Math.random() * arrl) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strl);
        }
        return ans;
    }

    public static String[] copyStringArray(String[] arr) {
        String[] ans = new String[arr.length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = String.valueOf(arr[i]);
        }
        return ans;
    }
    public static void main(String[] args) {
        int arrl = 6;
        int strl = 5;
        int testTimes = 100;
        for (int i = 0; i < testTimes; i++) {
            String[] ans = generateRandomStringArray(strl, arrl);
            String[] arr2 = copyStringArray(ans);
            if (!lowestStrs(ans).equals(lowestStr1(arr2))) {
//                for (String str : ans) {
//                    System.out.println(str + ",");
//                }
                System.out.println("Oops======");
            }
        }
        System.out.println("finish");
    }

}

