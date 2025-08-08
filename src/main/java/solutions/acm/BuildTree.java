package solutions.acm;
import java.util.*;

public class BuildTree {
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        if (n == 0) return;
        // 读取层序数组
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();
        // 特判空树
        if (arr[0] == -1) return;

        // ---------- 建树 ----------
        TreeNode root = new TreeNode(arr[0]);
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        int idx = 1;                 // 指向下一待处理的数组位置
        while (!q.isEmpty() && idx < n) {
            TreeNode cur = q.remove();
            // left
            if (arr[idx] != -1) {
                cur.left = new TreeNode(arr[idx]);
                q.add(cur.left);
            }
            idx++;
            if (idx >= n) break;
            // right
            if (arr[idx] != -1) {
                cur.right = new TreeNode(arr[idx]);
                q.add(cur.right);
            }
            idx++;
        }

        // ---------- 题目逻辑（先序遍历） ----------
        StringBuilder sb = new StringBuilder();
        preorder(root, sb);
        // 去掉最后一个空格
        System.out.println(sb.toString().trim());
    }

    static void preorder(TreeNode node, StringBuilder sb) {
        if (node == null) return;
        sb.append(node.val).append(' ');
        preorder(node.left, sb);
        preorder(node.right, sb);
    }
}
