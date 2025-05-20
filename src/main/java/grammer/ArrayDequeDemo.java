package grammer;

import java.util.Deque;
import java.util.ArrayDeque;

public class ArrayDequeDemo {
    public static void main(String[] args) {
        /**
         * ArrayDeque 作为队列使用
         */
        Deque<int[]> queue = new ArrayDeque<>();
        // add和remove提示异常，offer和poll不会提示异常（推荐）
        queue.offer(new int[]{0, 0}); queue.offer(new int[]{1, 1}); queue.offer(new int[]{2, 2});
        queue.peek();
        queue.poll();
        queue.isEmpty();
        queue.clear();

        /**
         * ArrayDeque 作为栈使用
         */
        Deque<String> stack = new ArrayDeque<>();
        stack.push("X"); stack.push("Y"); stack.push("Z");
        stack.pop();
        stack.peek();
        stack.isEmpty();
        stack.clear();

        /**
         * 作为双端队列使用（Deque）
         */
        // 不能写成Queue<String> deque = new ArrayDeque<>();
        Deque<String> deque = new ArrayDeque<>();
        deque.offerFirst("1");
        deque.offerLast("5");
        deque.pollFirst();
        deque.pollLast();
        deque.peekFirst();
        deque.peekLast();
        deque.isEmpty();
        deque.clear();
    }
}
