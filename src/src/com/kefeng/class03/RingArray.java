package com.kefeng.class03;

/**
 * 使用数组实现循环队列
 */
public class RingArray {

    public static class MyQueue {
        private int[] arr;
        private int pushi;
        private int popi;
        private int size;
        private final int limit;

        public MyQueue(int limit) {
            arr = new int[limit];
            pushi = 0;
            popi = 0;
            size = 0;
            this.limit = limit;
        }

        public void push(int value) {
            if (size == limit) {
                System.out.println("队列已满，无法添加新元素");
            }
            size++;
            arr[pushi] = value;
            pushi = nextIndex(pushi);
        }

        public int pop() {
            if (size == 0) {
                System.out.println("队列已空，无法从队列拿出元素");
            }
            size--;
            int ans = arr[popi];
            popi = nextIndex(popi);
            return ans;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int nextIndex(int index) {
            return index < limit - 1 ? index + 1 : 0;
        }
    }
}
