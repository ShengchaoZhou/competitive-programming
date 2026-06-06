package solutions.others;
import java.util.*;

/**
 * 奇数位升序偶数位降序的链表要求时间O(n)空间O(1)的排序
 */
public class OddEvenSort {
    static class Node {
        int val;
        Node next;
        Node() {}
        Node(int val, Node next) { this.val = val; this.next = next; }
    }

    static void partition(Node root, Node odd, Node even) {
        if (root == null) return;
        Node p = odd, q = even, cur = root;
        int cnt = 1;
        while (cur != null) {
            if (cnt % 2 == 1) { p.next = cur; p = p.next; }
            else { q.next = cur; q = q.next; }
            cur = cur.next;
            cnt++;
        }
        p.next = null;
        q.next = null;
    }

    static Node reverseList(Node root) {
        Node p = null, q = root, r = null;
        while (q != null) {
            r = q.next;

            q.next = p;
            p = q;
            q = r;
        }
        return p;
    }

    static Node mergeList(Node list1, Node list2) {
        Node dummy = new Node();
        Node p = list1, q = list2;
        Node cur = dummy;
        while (p != null && q != null) {
            if (p.val <= q.val) { cur.next = p; p = p.next; }
            else { cur.next = q; q = q.next; }
            cur = cur.next;
        }
        while (p != null) { cur.next = p; p = p.next; cur = cur.next; }
        while (q != null) { cur.next = q; q = q.next; cur = cur.next; }
        return dummy.next;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Node dummy = new Node();
        Node cur = dummy;
        int n = sc.nextInt(); // 节点个数
        while (n-- > 0) {
            cur.next = new Node(sc.nextInt(), null);
            cur = cur.next;
        }

        Node odd = new Node(), even = new Node();
        partition(dummy.next, odd, even);
        even.next = reverseList(even.next);
        dummy.next = mergeList(odd.next, even.next);

        cur = dummy.next;
        while (cur != null) { System.out.print(cur.val + " "); cur = cur.next; }
    }
}
