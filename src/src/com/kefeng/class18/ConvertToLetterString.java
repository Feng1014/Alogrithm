package com.kefeng.class18;


/**
 * 1对应A，2对应B...,26对应Z
 * 一个数字字符串转为对应字母的字符串，问有多少种转换方法
 */
public class ConvertToLetterString {

    public static int number(String str){
        if (str == null || str.length() == 0){
            return 0;
        }
        return process(str.toCharArray(), 0);
    }

    /**
     * 暴力递归求转换方法数，
     * @param str
     * @param index 表示【0...index - 1】位置字符已经转换完毕，从index到字符串结尾转换
     * @return
     */
    public static int process(char[] str, int index){
        if (index == str.length){
            return 1;
        }
        if (str[index] == '0'){
            return 0;
        }
        int ways = process(str, index + 1);
        if (index + 1 < str.length && str[index] - '0' * 10 + str[index + 1] < 27){
            ways += process(str, index + 2);
        }
        return ways;
    }

    /**
     * 动态规划求解转换方法，只有一个变量发生转移，使用一维数组存储
     * @param s
     * @return
     */
    public static int dp(String s){
        if (s == null || s.length() == 0){
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[] dp = new int[N + 1];
        dp[N] = 1;
        for (int index = N - 1; index >= 0; index--) {
            if (str[index] != '0'){
                int ways = dp[index + 1];
                if (index + 1 < str.length && str[index] - '0' * 10 + str[index] < 27){
                    ways += dp[index + 2];
                }
                dp[index] = ways;
            }
        }
        return dp[0];
    }

    public static void main(String[] args) {
        String ans = "12352123321";
        System.out.println(number(ans));
        System.out.println(dp(ans));
    }
}
