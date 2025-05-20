package templates.AlgorithmBasics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class basic001003QuickSort {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[][] q = new int[n][2]; // 存值和下标
        String[] strs = br.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            q[i][0] = Integer.parseInt(strs[i]); // 数值
            q[i][1] = i; // 原始下标
        }

        quickSort(q, 0, n - 1);

        for (int i = 0; i < n; i++) {
            System.out.print(q[i][0] + " ");
        }
    }

    private static void quickSort(int[][] q, int l, int r) {
        if (l >= r) return;
        int[] x = q[l]; // 基准元素
        int i = l - 1, j = r + 1;
        while (i < j) {
            do i++; while (compare(q[i], x) < 0);
            do j--; while (compare(q[j], x) > 0);
            if (i < j) {
                int[] t = q[i];
                q[i] = q[j];
                q[j] = t;
            }
        }
        quickSort(q, l, j);
        quickSort(q, j + 1, r);
    }

    private static int compare(int[] a, int[] b) {
        if (a[0] != b[0]) return Integer.compare(a[0], b[0]); // 先按值排序
        return Integer.compare(a[1], b[1]); // 值相同时按原始下标排序
    }
}
