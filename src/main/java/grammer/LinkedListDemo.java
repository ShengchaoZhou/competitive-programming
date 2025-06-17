package grammer;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 当队列有添加空指针的需求时，使用LinkedList作为队列
 */
public class LinkedListDemo {
    public static void main(String[] args) {
        Deque<String> queue = new LinkedList<>();

        queue.offer("A");  // 添加元素，推荐用 offer()
        queue.offer(null); // LinkedList 支持添加 null
        queue.peek();
        queue.poll();
        queue.isEmpty();

        for (String item : queue) System.out.println(item);
    }
}