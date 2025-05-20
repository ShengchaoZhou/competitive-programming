package templates.AlgorithmBasics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class basic001002QuickSort {
    static class Pair implements Comparable<Pair> {
        int value, index;

        Pair(int value, int index) {
            this.value = value;
            this.index = index;
        }

        @Override
        public int compareTo(Pair other) {
            if (this.value == other.value) {
                return Integer.compare(this.index, other.index); // 按原始索引排序，保证稳定性
            }
            return Integer.compare(this.value, other.value);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        Pair[] q = new Pair[n];
        String[] strs = br.readLine().split(" ");
        for (int i = 0; i < n; i++) q[i] = new Pair(Integer.parseInt(strs[i]), i);

        quickSort(q, 0, n - 1);

        for (int i = 0; i < n; i++) {
            System.out.print(q[i].value + " ");
        }
    }

    private static void quickSort(Pair[] q, int l, int r) {
        if (l >= r) return;
        Pair x = q[l];
        int i = l - 1, j = r + 1;
        while (i < j) {
            do i++; while (q[i].compareTo(x) < 0);
            do j--; while (q[j].compareTo(x) > 0);
            if (i < j) {
                Pair t = q[i];
                q[i] = q[j];
                q[j] = t;
            }
        }
        quickSort(q, l, j);
        quickSort(q, j + 1, r);
    }
}
