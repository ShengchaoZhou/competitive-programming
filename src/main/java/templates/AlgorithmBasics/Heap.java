package templates.AlgorithmBasics;

import java.util.*;

class Heap {
    static final int N = 1000010;
    static int[] heap = new int[N];
    static int idx = 1;
    static void swap(int[] arr, int x, int y) {
        int t = arr[x];
        arr[x] = arr[y];
        arr[y] = t;
    }
    // 选出最大值
    static void down(int u) {
        int t = u;
        if (u << 1 < idx && heap[u << 1] < heap[u]) t = u << 1;
        if ((u << 1 | 1) < idx && heap[u << 1 | 1] < heap[t]) t = u << 1 | 1;
        if (t != u) {
            swap(heap, u, t);
            down(t);
        }
    }
    // 添加元素时使用
    static void up(int u) {
        if (u > 1 && heap[u] < heap[u >> 1]) {
            swap(heap, u, u >> 1);
            up(u >> 1);
        }
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        while (n-- > 0) {
            int op = sc.nextInt();
            if (op == 1) {
                heap[idx] = sc.nextInt();
                up(idx++);
            } else if (op == 2) {
                System.out.println(heap[1]);
            } else {
                heap[1] = heap[--idx];
                down(1);
            }
        }
    }
}