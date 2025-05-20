package grammer;

import java.util.*;

public class TreeMapDemo {
    public static void main(String[] args) {
        TreeMap<String, Integer> map = new TreeMap<>();
        /*----------------------常用--------------------*/
        map.put("apple", 3); // 插入键值对
        map.put("banana", 2);
        map.put("cherry", 5);

        map.get("banana"); // 获取指定键的值
        map.containsKey("apple"); // 判断是否包含某个键

        map.remove("cherry"); // 删除指定键

        map.size(); // 获取键值对数量
        map.isEmpty(); // 判断是否为空
        map.clear(); // 清空所有键值对

        map.ceilingKey("b"); // 获取大于等于给定键的最小键
        map.higherKey("banana"); // 获取严格大于给定键的最小键
        map.floorKey("b"); // 获取小于等于给定键的最大键
        map.lowerKey("banana"); // 获取严格小于给定键的最大键

        /*----------------------不常用--------------------*/
        map.firstKey(); // 获取第一个键（按自然顺序）
        map.lastKey(); // 获取最后一个键

        map.headMap("cherry"); // 获取小于指定键的子Map（不包含指定键）
        map.tailMap("banana"); // 获取大于等于指定键的子Map
        map.subMap("apple", "cherry"); // 获取从apple（含）到cherry（不含）的子Map

        map.keySet(); // 获取所有键的集合（有序）
        map.values(); // 获取所有值的集合
        map.entrySet(); // 获取所有键值对的集合


    }
}
