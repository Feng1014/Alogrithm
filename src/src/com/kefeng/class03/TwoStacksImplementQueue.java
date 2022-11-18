package com.kefeng.class03;

import java.util.Stack;

/**
 * 使用两个栈代表队列
 */
public class TwoStacksImplementQueue {

    public static class stackToQueue {

        private Stack<Integer> pushStack;
        private Stack<Integer> popStack;

        public stackToQueue() {
            pushStack = new Stack<Integer>();
            popStack = new Stack<Integer>();
        }

        /**
         * 从pushStack把数据转到popStack
         */
        private void pushToPop() {
            /**当popStack栈为空时，才可以从pushStack转向popStack*/
            if (popStack.isEmpty()) {
                while (!pushStack.isEmpty()) {
                    popStack.push(pushStack.pop());
                }
            }
        }

        /**
         * 向队列中插入元素，只需要向pushStack插入元素即可
         * 无需判断队列是否已满
         */
        public void offer(int pushInt) {
            pushStack.push(pushInt);
            pushToPop();
        }

        /**
         * 从队列中拿出元素，只需从popStack栈中拿出元素
         */
        public int poll() {
            if (pushStack.isEmpty() && popStack.isEmpty()) {
                throw new RuntimeException("Queue is empty");
            }
            /**先要从pushStack栈将数据转到popStack。然后才能拿到数据*/
            pushToPop();
            return popStack.pop();
        }

        /**
         * 拿队列顶部的元素，即是拿popStack栈顶的元素
         */
        public int peek() {
            if (pushStack.isEmpty() && popStack.isEmpty()) {
                throw new RuntimeException("Queue is empty");
            }
            /**先要从pushStack栈将数据转到popStack。然后才能拿到数据*/
            pushToPop();
            return popStack.peek();
        }

        public boolean isEmpty() {
            return pushStack.isEmpty() && popStack.isEmpty();
        }
    }


}
