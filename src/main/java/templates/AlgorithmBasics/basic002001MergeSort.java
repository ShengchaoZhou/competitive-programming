package templates.AlgorithmBasics;

import java.util.Scanner;

public class basic002001MergeSort {
    static final int N = 100010;
    static int[] a = new int[N];
    static int[] tmp = new int[N];
    static int n;

    static void mergeSort(int[] q, int l, int r) {
        if (l >= r) return;

        int mid = l + r >> 1;
        mergeSort(q, l, mid); mergeSort(q, mid + 1, r);

        int i = l, j = mid + 1, k = 0;
        while (i <= mid && j <= r) {
            // <= 才是稳定排序
            if (q[i] <= q[j]) tmp[k++] = q[i++];
            else tmp[k++] = q[j++];
        }

        while (i <= mid) tmp[k++] = q[i++];
        while (j <= r) tmp[k++] = q[j++];

        k = 0;
        while (k + l <= r) q[l + k] = tmp[k++];
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        for (int i = 1; i <= n; i++) a[i] = sc.nextInt();

        mergeSort(a, 1, n);

        for (int i = 1; i <= n; i++) System.out.printf("%d ", a[i]);
    }
}
