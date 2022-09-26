package com.kefeng.class14;



import java.util.Arrays;
import java.util.Comparator;

/**
 * 贪心算法求解会议室的最优安排
 * 会议室使用数组表示【1，3】表示1点开始，3点结束
 * 会议室使用二维数组表示
 */
public class BestArrange {

    public static class program {

        public int start;
        public int end;

        public program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    /**
     * 使用纯暴力挨个试验，怎么安排会议最优
     * @param programs
     * @return
     */
    public static int bestArrange(program[] programs) {
        if (programs == null || programs.length == 0) {
            return 0;
        }
        return process(programs, 0, 0);
    }

    /**
     *
     * @param programs 是待安排的会议数组
     * @param done 已经安排的会议个数
     * @param timeline 目前来到的时间点是何处
     * @return
     */
    public static int process(program[] programs, int done, int timeline) {
        if (programs.length == 0) {
            return done;
        }
        int ans = done;
        for (int i = 0; i < programs.length; i++) {
            if (timeline <= programs[i].start) {
                program[] next = copyButExcept(programs, i);
                ans = Math.max(ans, process(next, done + 1, programs[i].end));
            }
        }
        return ans;
    }

    public static program[] copyButExcept(program[] programs, int index) {
        program[] ans = new program[programs.length - 1];
        int k = 0;
        for (int i = 0; i < programs.length; i++) {
            if (i != index) {
                ans[k++] = programs[i];
            }
        }
        return ans;
    }

    /**
     * 使用贪心解决会议室的安排
     * @param programs
     * @return
     */
    public static int bestArrange1(program[] programs){
        Arrays.sort(programs, new myComparator());
        int results = 0;
        int timeline = 0;
        for (int i = 0; i < programs.length; i++) {
            if (timeline <= programs[i].start){
                results++;
                timeline = programs[i].end;
            }
        }
        return results;
    }

    public static class myComparator implements Comparator<program>{

        @Override
        public int compare(program o1, program o2) {
            return o1.end - o2.end;
        }
    }

    public static program[] generateProgram(int programPoolSize, int timeSize) {
        program[] programs = new program[(int) (Math.random() * (programPoolSize + 1))];
        for (int i = 0; i < programs.length; i++) {
            int r1 = (int) (Math.random() * (timeSize + 1));
            int r2 = (int) (Math.random() * (timeSize + 1));
            if (r1 == r2) {
                programs[i] = new program(r1, r1 + 1);
            } else {
                programs[i] = new program(Math.min(r1, r2), Math.max(r1, r2));
            }
        }
        return programs;
    }

    public static void main(String[] args) {
        int programSize = 12;
        int timeSize = 20;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            program[] programs = generateProgram(programSize, timeSize);
            if (bestArrange(programs) != bestArrange1(programs)){
                System.out.println("Oops========");
            }
//            int ba = bestArrange(programs);
//            System.out.println(ba);
//            int ba = bestArrange1(programs);
//            System.out.println(ba);
        }
        System.out.println("finish");
    }
}
