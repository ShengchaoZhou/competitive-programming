package solutions.others;
import java.util.Scanner;

/** 面试极简版 Hash 表 —— int→int，拉链法 */
public class Hash {

    /** —— 可按需调节 —— */
    private static final int SIZE = 1024;          // 桶数量：简单起见固定为 1024（2^10）

    /** 节点定义（单向链表） */
    private static class Node {
        int key, val;
        Node next;
        Node(int k, int v, Node n) { key = k; val = v; next = n; }
    }

    /** 桶数组，每个元素是链表头指针 */
    private static final Node[] buckets = new Node[SIZE];

    /** 朴素哈希函数：取模即可 */
    private static int hash(int k) {
        return (k & 0x7fffffff) % SIZE;           // 防负数
    }

    /** put：插入或更新 */
    private static void put(int k, int v) {
        int h = hash(k);
        for (Node cur = buckets[h]; cur != null; cur = cur.next) {
            if (cur.key == k) {                   // 已存在：更新
                cur.val = v;
                return;
            }
        }
        buckets[h] = new Node(k, v, buckets[h]);  // 头插
    }

    /** get：不存在返回 null-like 值（这里用 Integer.MIN_VALUE） */
    private static int get(int k) {
        int h = hash(k);
        for (Node cur = buckets[h]; cur != null; cur = cur.next)
            if (cur.key == k) return cur.val;
        return Integer.MIN_VALUE;
    }

    /** remove */
    private static void remove(int k) {
        int h = hash(k);
        Node cur = buckets[h], prev = null;
        while (cur != null) {
            if (cur.key == k) {
                if (prev == null) buckets[h] = cur.next;
                else prev.next = cur.next;
                return;
            }
            prev = cur; cur = cur.next;
        }
    }

    /* 演示用主程序：支持 3 个操作
         1 k v   put(k,v)
         2 k     get(k)   → 输出值或 "NF"
         3 k     remove(k)
       输入示例：
           6
           1 3 100
           1 5 200
           2 3
           3 3
           2 3
           2 5
    */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder out = new StringBuilder();
        int q = sc.nextInt();
        while (q-- > 0) {
            int op = sc.nextInt();
            int k  = sc.nextInt();
            if (op == 1) { int v = sc.nextInt(); put(k, v); }
            else if (op == 2) {
                int ans = get(k);
                out.append(ans == Integer.MIN_VALUE ? "NF" : ans).append('\n');
            } else if (op == 3) remove(k);
        }
        System.out.print(out);
    }
}
