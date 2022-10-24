package com.kefeng.class27;

/**
 * o(n)时间复杂度求某字符串的最长回文子串的起始位置
 */
public class Manacher {

    public static int manacher(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        /**字符串“12343”变为“#1#2#3#4#3#”*/
        char[] str = manacherString(s);
        int max = Integer.MIN_VALUE;
        /**当前最长回文子串的中心点*/
        int C = -1;
        /**当前回文子串的右边界*/
        int R = -1;
        /**字符串每个位置的回文半径。例如12321中3的回文班级是3.*/
        int[] pArr = new int[str.length];
        for (int i = 0; i < str.length; i++) {
            /**在已经求得的最长回文子串【L，R】范围内，在求i位置的回文半径时可以使用对称的性质，即与i关于C对称的-i处回文半径可以辅助i位置的回文半径
             * 如果-i位置的回文半径没有越过L，则i位置的回文半径也不可能越过R
             * 如果-i位置的回文半径越过L，则i位置的回文半径只能止步于R，即R-i
             * 如果-i位置的回文半径压线L，则i位置的回文半径也压线R
             * 比较R-i和-i的回文半径pArr[2 * C - i]即可。
             * 假设C=5，i=7，-i=3，则2*5-7=3。*/
            pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
            while (i + pArr[i] < str.length && i - pArr[i] > -1) {
                /**从头开始，当前位置加回文半径与当前位置减回文半径的值相比较，如果二者相等则回文半径加1。
                 * 例如字符串123，1左边为空，右边为2，二者不相同则回文半径不增加，依旧是1。*/
                if (str[i + pArr[i]] == str[i - pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }
            /**如果当前位置加回文半径已经跳出目前最长回文子串的右边界，则更新R和C*/
            if (i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }
            max = Math.max(max, pArr[i]);
        }
        return max - 1;
    }

    public static char[] manacherString(String s) {
        int N = s.length();
        char[] str = s.toCharArray();
        char[] res = new char[2 * N + 1];
        int index = 0;
        for (int i = 0; i != res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : str[index++];
        }
        return res;
    }

    public static void main(String[] args) {
        String s = "abcdefgfekk";
        System.out.println(manacher(s));
    }

}
