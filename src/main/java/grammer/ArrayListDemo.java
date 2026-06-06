package grammer;

import java.util.*;

public class ArrayListDemo {

    public static void main(String[] args) {
        // ArrayList添加和删除
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(5);
        integers.add(1, 5);
        integers.set(0, 2); // 修改某个位置的元素
        integers.remove(integers.size() - 1); // 对应vector中的pop_back()，时间O(1)

        // 创建4个双端队列写法
        List<ArrayDeque<Integer>> dq = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) dq.add(new ArrayDeque<>());

        // 创建List数组的写法
        ArrayList<Integer>[] arr = new ArrayList[4];
        for (int i = 0; i < 4; i++) arr[i] = new ArrayList<>();

        // 使用多态时，增强for循环写的类型(List)要与定义的类型(List)一样
        List<Integer>[] arr2 = new ArrayList[4];
        for (int i = 0; i < 4; i++) arr[i] = new ArrayList<>();
        for (List<Integer> a : arr2) {
            System.out.println(a);
        }

        /**
         * Collections使用
         */
        Collections.swap(integers, 0, 1);
        Collections.reverse(integers);
        /**
         * toArray() toArray(T[]) 应用
         */
        List<int[]> people = new ArrayList<>();
        people.add(new int[]{1, 1});
        people.add(new int[]{2, 2});
        int[][] peopleArray = people.toArray(new int[people.size()][]);
        System.out.println(Arrays.deepToString(peopleArray));
    }
}
