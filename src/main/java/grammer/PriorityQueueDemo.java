package grammer;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueDemo {
    public static void main(String[] args) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // 默认小根堆
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a); // 使用自定义比较器创建大根堆
        PriorityQueue<int[]> arrayHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]); // int[]需要传入比较器

        // ListNode已经实现了Comparable接口，重写了compareTo方法
        // 不同于C++的结构体，会自动对第一个元素排序，Java必须对ListNode指定比较器，或者在ListNode中重写compareTo函数
        PriorityQueue<ListNode> nodeHeap = new PriorityQueue<>();

        // Segment没有实现Comparable接口，那么可以传入Comparator接口作为比较，一般简化成lambda表达式
        PriorityQueue<Segment> segMinHeap = new PriorityQueue<>(new Comparator<Segment>() {
            @Override
            public int compare(Segment o1, Segment o2) {
                return o1.l - o2.l;
            }
        });
        // Comparator.comparingInt本身就是一个工厂方法，把提供的 “取整型关键字” 的函数包装成真正的Comparator并返回
        PriorityQueue<Segment> segMinHeap1 = new PriorityQueue<>(Comparator.comparingInt(a -> a.l));
        PriorityQueue<Segment> segMinHeap2 = new PriorityQueue<>((a, b) -> a.l - b.l); // 推荐，注意整数相减溢出问题
        PriorityQueue<Segment> segMinHeap3 = new PriorityQueue<>((a, b) -> Integer.compare(a.l, b.l)); // 可以防止溢出
        PriorityQueue<Segment> segMinHeap4 = new PriorityQueue<>((a, b) -> a.l < b.l ? -1 : 1); // 可以防止溢出
        // reversed() 需要明确 T 的类型，而如果 a 没有显式标注 Segment，编译器无法正确推导 T 的类型。
        PriorityQueue<Segment> segMaxHeap1 = new PriorityQueue<>(Comparator.comparingInt((Segment a) -> a.l).reversed());
        PriorityQueue<Segment> segMaxHeap2 = new PriorityQueue<>((a, b) -> b.l - a.l); // 推荐
        PriorityQueue<Segment> segMaxHeap3 = new PriorityQueue<>((a, b) -> Integer.compare(b.l, a.l));

        // 添加元素
        minHeap.offer(10); // 队列满不会抛出异常，返回false
        // 若minHeap.add(null);
        // PriorityQueue 使用它们的 compareTo 方法来排序。
        // compareTo 无法处理 null，因此会引发异常。会抛出 NullPointerException
        minHeap.add(20);

        minHeap.peek(); // 查看堆顶元素（不移除）
        minHeap.remove(); // // 删除堆顶元素并返回
        minHeap.poll(); // 删除堆顶元素并返回
        minHeap.isEmpty(); // 判断队列是否为空

        // 遍历
        for (int it : minHeap) System.out.println(it);
        for (ListNode it : nodeHeap) System.out.println(it.val);
    }
}
