package com.kefeng.class17;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 打印字符串的所有子序列，无重复的子序列
 */
public class PrintAllSubsquences {

    /**
     * 打印字符串所有的子序列，包含重复的子序列
     */
    public static List<String> subs(String s) {
        char[] str = s.toCharArray();
        List<String> ans = new ArrayList<>();
        String path = "";
        process(str, 0, ans, path);
        return ans;
    }

    public static void process(char[] str, int index, List<String> ans, String path) {
        if (index == str.length) {
            ans.add(path);
            return;
        }
        process(str, index + 1, ans, path);
        process(str, index + 1, ans, path + String.valueOf(str[index]));
    }

    /**求字符串的所有子序列，去除重复的子序列*/
    public static List<String> subNoRepeat(String s) {
        char[] str = s.toCharArray();
        HashSet<String> set = new HashSet<>();
        String path = "";
        process1(str, 0, set, path);
        List<String> ans = new ArrayList<>();
        for (String se : set
        ) {
            ans.add(se);
        }
        return ans;
    }

    public static void process1(char[] str, int index, HashSet<String> set, String path){
        if (index == str.length){
            set.add(path);
            return;
        }
        process1(str, index + 1, set, path);
        process1(str, index + 1, set, path + String.valueOf(str[index]));
    }

    public static void main(String[] args) {
        String ans = "abcd";
        List<String> result = new ArrayList<>();
        result = subs(ans);
        for (String str : result
        ) {
            System.out.println(str);
        }
        System.out.println("---------------");
        List<String> result1 = new ArrayList<>();
        result = subNoRepeat(ans);
        for (String str : result
        ) {
            System.out.println(str);
        }
    }
}
