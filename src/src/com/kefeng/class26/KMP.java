package com.kefeng.class26;


/**
 * KMP算法
 */
public class KMP {

    /**
     * 求字符串s1中能够完全匹配字符串s2的起始位置
     * @param s1
     * @param s2
     * @return
     */
    public static int getIndexOf(String s1, String s2) {
        if (s1 == null || s2 == null || s2.length() < 1 || s1.length() < s2.length()) {
            return -1;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int x = 0;
        int y = 0;
        /**获取next数组*/
        int[] next = getNextOf(str2);
        while (x < str1.length && y < str2.length) {
            /**如果str1字符串的x位置和str2字符串y位置能够匹配，x和y都向后移动*/
            if (str1[x] == str2[y]) {
                x++;
                y++;
            } /**如果str2字符串无法和str1字符串中同等长度的子字符串匹配，则在str1字符串中整体向后移动。
             例如str1的子串"abcabd"和str2“abcabt”无法匹配，则str1向后移动到d字符后的一个字符再开始匹配*/
            else if (next[y] == -1) {
                x++;
            }/**如果str2字符串和str1子字符串无法匹配，先将str2向后推next[y]个位置。
             例如str1的子串"abcabd"和str2“abcabt”无法匹配，是d和t无法匹配，则abcabd不动，str2移动到字符c和str1的字符d比较
             str1中以b,c,a,b开头的子串不用再和str2中以a，b开头的子串匹配，这是由next数组的特性决定的*/
            else {
                y = next[y];
            }
        }
        return y == str2.length ? x - y : -1;
    }

    /**
     * 求字符串str中不包括当前字符，从头到当前位置减一处，最大相同长度的前缀和后缀的值。
     * @param str
     * @return 例如abcabc,返回的是[-1,0,0,0,1,2]
     */
    public static int[] getNextOf(char[] str) {
        if (str == null || str.length == 0) {
            return new int[-1];
        }
        int N = str.length;
        int[] res = new int[N];
        /**规定首字符的相同长度的最大前缀和最大后缀为-1*/
        res[0] = -1;
        /**规定第二个字符的相同长度的最大前缀和最大后缀为0*/
        res[1] = 0;
        /**从2位置开始计算next，cn表示与i-1相比较的索引位置
         * 例如计算第三个字符的next时，cn即是0，i-1即是1，如果首字符和第二个字符相同，则第三个字符的相同长度的最大前缀和后缀的值为1*/
        int i = 2, cn = 0;
        while (i < N) {
            /**如果字符串i-1位置和字符串cn位置的值相同，则i位置的next值是i-1位置的next值加1.
             * 例如字符串aaaa，第三个字符的最长前缀和最长后缀都是a，到第四个字符a时，只需要比较第4-1个位置和，第三个字符的最长前缀a的下一个索引元素即可，
             * 如果二者的值相同，那么第四个字符相同的最长前后缀就是第三个字符相同的最长前后缀加1*/
            if (str[i - 1] == str[cn]) {
                res[i++] = ++cn;
            }/**如果不相同，则需要再向左移动cn即可，只要cn没有小于0就可以一直移动*/
            else if (cn > 0) {
                cn = res[cn];
            }/**如果向左移动cn至小于0的位置，那么表示i位置的字符没有长度相同的最大前后缀，赋0即可*/
            else {
                res[i++] = 0;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        String s1 = "aaababcadf";
        String s2 = "abc";
        System.out.println(getIndexOf(s1, s2));
    }

}
