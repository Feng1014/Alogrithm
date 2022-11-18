package com.kefeng.class03;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 使用队列表示栈
 */
public class TwoQueueImplementStack {

    public static class queueToStack<T> {

        private Queue<T> queue;
        private Queue<T> help;

        public queueToStack() {
            queue = new LinkedList<>();
            help = new LinkedList<>();
        }

        /**
         * 进入栈的操作，只需要向queue中新增元素即可
         * 无需判断栈是否已满
         */
        public void push(T pushValue) {
            queue.offer(pushValue);
        }

        /**
         * 两个队列表示栈。一个队列存放元素，另一个队列作为辅助队列
         * queue中是先进先出的存元素。满足栈的先进后出特性。
         * 只需要将queue中除队尾元素外，其余的元素全部以先进先出的方式添加到help队列中
         * 接着将queue中的元素出队列，即可满足栈后进先出的特性
         */
        public T pop() {
            while (queue.size() > 1) {
                help.offer(queue.poll());
            }
            T ans = queue.poll();
            Queue<T> tmp = queue;
            queue = help;
            help = tmp;
            return ans;
        }

        public T peek() {
            while (queue.size() > 1) {
                help.offer(queue.poll());
            }
            T ans = queue.poll();
            help.offer(ans);
            Queue<T> tmp = queue;
            queue = help;
            help = tmp;
            return ans;
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

}
