package com.kefeng.class17;

import java.util.ArrayList;
import java.util.List;

/**
 * 求一个字符串的全排列，递归方法求解
 */
public class PrintAllPermutations {

    public static List<String> permutation(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] str = s.toCharArray();
        List<Character> rest = new ArrayList<>();
        for (char ch : str
        ) {
            rest.add(ch);
        }
        String path = "";
        f(rest, path, ans);
        return ans;
    }

    /**
     * 暴力递归求解字符串的全排列
     *
     * @param rest 表示剩余要选择的字符
     * @param path 表示已经选择好的字符
     * @param ans  存储全排列的某个子项
     */
    public static void f(List<Character> rest, String path, List<String> ans) {
        if (rest.isEmpty()) {
            ans.add(path);
        } else {
            int N = rest.size();
            for (int i = 0; i < N; i++) {
                char cur = rest.get(i);
                rest.remove(i);
                f(rest, path + cur, ans);
                rest.add(i, cur);
            }
        }
    }

    public static List<String> permutation1(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] str = s.toCharArray();
        g(str, 0, ans);
        return ans;
    }

    /**
     * 另一种递归方式
     *
     * @param str   表示已经选中的全排列子项
     * @param index 从index位置到子项结束前，都可以进行交换
     * @param ans
     */
    public static void g(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
        } else {
            for (int i = index; i < str.length; i++) {
                swap(str, index, i);
                g(str, index + 1, ans);
                swap(str, index, i);
            }
        }
    }

    public static List<String> permutation2(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] str = s.toCharArray();
        g2(str, 0, ans);
        return ans;
    }

    public static void g2(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
        } else {
            boolean[] visited = new boolean[256];
            for (int i = index; i < str.length; i++) {
                if (!visited[str[i]]) {
                    visited[str[i]] = true;
                    swap(str, index, i);
                    g2(str, index + 1, ans);
                    swap(str, index, i);
                }
            }
        }
    }

    public static void swap(char[] str, int i, int j) {
        char tmp = str[i];
        str[i] = str[j];
        str[j] = tmp;
    }

    public static void main(String[] args) {
        String test = "acc";
        List<String> result = new ArrayList<>();
        result = permutation(test);
        for (String str : result
        ) {
            System.out.println(str);
        }
        System.out.println("-------------");
        List<String> result1 = new ArrayList<>();
        result1 = permutation1(test);
        for (String str : result1
        ) {
            System.out.println(str);
        }
        System.out.println("-------------");
        List<String> result2 = new ArrayList<>();
        result2 = permutation2(test);
        for (String str : result2
        ) {
            System.out.println(str);
        }
        System.out.println("-------------");
    }
}
