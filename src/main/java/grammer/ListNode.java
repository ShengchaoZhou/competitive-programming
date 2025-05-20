package grammer;

public class ListNode implements Comparable<ListNode> {
    int val;
    ListNode next;

    ListNode() {}
    ListNode(int val) {this.val = val;}
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    // @Override可以省略，但是public不能省略，省略后只有包内可见
    @Override
    public int compareTo(ListNode other) {
        return Integer.compare(this.val, other.val);
    }
}
