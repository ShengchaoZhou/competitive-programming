package grammer;

import java.util.HashMap;
import java.util.Map;

public class HashMapDemo {
    public static void main(String[] args) {
        /**
         * HashMap的使用
         */
        HashMap<Integer, Integer> hash = new HashMap<>(); // 创建
        hash.put(1, 10); hash.put(2, 20); // 添加
        hash.put(1, hash.getOrDefault(1, 0) + 1); // 用于统计的写法
        hash.putIfAbsent(2, 30);
        hash.get(1); // 查找键对应的值
        hash.containsKey(1); // 查找是否有键，O(n)
        hash.remove(1);

        /**
         * 遍历键值
         */
        for (int k : hash.keySet()) System.out.println(k + " " + hash.get(k)); // 推荐
        for (int v : hash.values()) System.out.println(v); // 遍历值
        // 遍历键值对，8之后可以写var entry
        for (Map.Entry<Integer, Integer> entry : hash.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        // 使用forEach遍历键值对
        hash.forEach((key, value) -> {
            System.out.println(key + " " + value);
        });

    }
}
