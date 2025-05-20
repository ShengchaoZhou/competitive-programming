package templates.AlgorithmBasics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class basic001001QuickSort {
    static final int N = 100010;
    static int[] q = new int[N];
    static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] q = new int[n];
        String[] strs = br.readLine().split(" ");
        for (int i = 0; i < q.length; i++) q[i] = Integer.parseInt(strs[i]);

        quickSort(q, 0, n - 1);

        for (int i = 0; i < n; i++) {
            System.out.print(q[i] + " ");
        }
    }

    private static void quickSort(int[] q, int l, int r) {
        if (l >= r) return;
        // i，j提前往外移一次
        int x = q[l], i = l - 1, j = r + 1;
        while (i < j) {
            // 不能写成q[i] <= x
            do i++; while (q[i] < x);
            do j--; while (q[j] > x);
            if (i < j) {
                int t = q[i];
                q[i] = q[j];
                q[j] = t;
            }
        }
        // x = q[l]，取左边界所以必须是j，j + 1
        // i = j 或者 i = j + 1，所以不能用i
        quickSort(q, l, j);
        quickSort(q, j + 1, r);
    }
}
