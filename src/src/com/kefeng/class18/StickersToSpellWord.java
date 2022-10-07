package com.kefeng.class18;

import java.util.HashMap;

/**
 * 给定几张贴纸，从给定的贴纸中裁剪拼接成目标字符串，问需要最少的贴纸数
 * 本题测试链接：https://leetcode.com/problems/stickers-to-spell-word
 */
public class StickersToSpellWord {

    public static int minStickers(String[] stickers, String target) {
        int ans = process(stickers, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public static int process(String[] stickers, String target) {
        if (target.length() == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (String first : stickers
        ) {
            String rest = minus(target, first);
            if (rest.length() != target.length()) {
                min = Math.min(min, process(stickers, rest));
            }
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    public static String minus(String s1, String s2) {
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int[] count = new int[26];
        for (char ch : str1
        ) {
            count[ch - 'a']++;
        }
        for (char ch : str2
        ) {
            count[ch - 'a']--;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                for (int j = 0; j < count[i]; j++) {
                    builder.append((char) (i + 'a'));
                }
            }
        }
        return builder.toString();
    }

    /**
     * 二维数组代替贴纸字符串
     *
     * @param stickers
     * @param target
     * @return
     */
    public static int minStickers1(String[] stickers, String target) {
        int N = stickers.length;
        int[][] count = new int[N][26];
        for (int i = 0; i < N; i++) {
            char[] str = stickers[i].toCharArray();
            for (char ch : str
            ) {
                count[i][ch - 'a']++;
            }
        }
        int ans = process2(count, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    /**
     * 返回搞定target的最少张数
     *
     * @param stickers
     * @param t
     * @return
     */
    public static int process2(int[][] stickers, String t) {
        if (t.length() == 0) {
            return 0;
        }
        /**做词频统计*/
        char[] target = t.toCharArray();
        int[] counts = new int[26];
        for (char ch : target
        ) {
            counts[ch - 'a']++;
        }
        int N = stickers.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            /**第一张贴纸*/
            int[] sticker = stickers[i];
            /**选取第一个字母和target第一个字母相同的贴纸，进行尝试*/
            if (sticker[target[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (counts[j] > 0) {
                        int nums = counts[j] - sticker[j];
                        for (int k = 0; k < nums; k++) {
                            builder.append((char) (j + 'a'));
                        }
                    }
                }
                String rest = builder.toString();
                min = Math.min(min, process2(stickers, rest));
            }
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    /**使用缓存保存已求解的值*/
    public static int minStickers2(String[] stickers, String target) {
        int N = stickers.length;
        int[][] counts = new int[N][26];
        for (int i = 0; i < N; i++) {
            char[] sticker = stickers[i].toCharArray();
            for (char ch : sticker
            ) {
                counts[i][ch - 'a']++;
            }
        }
        HashMap<String, Integer> map = new HashMap<>();
        map.put("", 0);
        int ans = process3(counts, target, map);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public static int process3(int[][] stickers, String t, HashMap<String, Integer> map) {
        if (map.containsKey(t)) {
            return map.get(t);
        }
        char[] target = t.toCharArray();
        int[] tcount = new int[26];
        for (char ch : target
        ) {
            tcount[ch - 'a']++;
        }
        int N = stickers.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            int[] sticker = stickers[i];
            if (sticker[target[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (tcount[j] > 0) {
                        int nums = tcount[j] - sticker[j];
                        for (int k = 0; k < nums; k++) {
                            builder.append((char) (j + 'a'));
                        }
                    }
                }
                String rest = builder.toString();
                min = Math.min(min, process3(stickers, rest, map));
            }
        }
        int ans = min + (min == Integer.MAX_VALUE ? 0 : 1);
        map.put(t, ans);
        return ans;
    }

    /**
     * 动态规划四种模型。模型1，从左向右尝试模型；模型2，范围尝试模型（纸牌游戏，玩家1拿A，玩家2拿B）；模型3，样本对应模型（最长公共子序列）；模型4，业务限制模型；
     * @param args
     */
    public static void main(String[] args) {
        String[] stickers = new String[]{"acc", "bbc", "aaa"};
        String target = "abc";
        System.out.println(minStickers(stickers, target));
    }

}
