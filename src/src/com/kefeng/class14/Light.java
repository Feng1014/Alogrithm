package com.kefeng.class14;


/**
 * 给定字符数组【x...x....x.】
 * “x”表示墙，“.”表示街道，在每个街道上放灯，街道的前后+1位置都会亮，要求在街道上放置灯，让每个街道和墙都亮
 */
public class Light {

    public static int minLight(String road) {
        char[] str = road.toCharArray();
        int light = 0;
        int i = 0;
        while (i < str.length) {
            if (str[i] == 'x') {
                i++;
            } else {
                light++;
                if (i + 1 == str.length) {
                    break;
                } else {
                    if (str[i + 1] == 'x') {
                        i += 2;
                    } else {
                        i += 3;
                    }
                }
            }
        }
        return light;
    }

    public static String generateRandomString(int len) {
        char[] str = new char[(int) ((Math.random() * len) + 1)];
        for (int i = 0; i < str.length; i++) {
            str[i] = Math.random() < 0.5 ? 'x' : '.';
        }
        return String.valueOf(str);
    }

    public static void main(String[] args) {
        int len = 20;
        int testtimes = 1000;
        for (int i = 0; i < testtimes; i++) {
            String str = generateRandomString(len);
            System.out.println(minLight(str));
        }
    }

}
