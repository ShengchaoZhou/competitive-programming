package templates.AlgorithmBasics;
import java.util.*;

public class basic003001BinarySearch {
    static final int N = (int)1e5 + 10;
    static int[] a = new int[N];
    static int n, q;

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt(); q = sc.nextInt();
        for (int i = 0; i < n; i++) a[i] = sc.nextInt();

        while (q-- != 0) {
            int x = sc.nextInt();
            int l = 0, r = n - 1;
            // 找左边界
            while (l < r) {
                int mid = l + r >> 1;
                if (a[mid] >= x) r = mid;
                else l = mid + 1;
            }
            if (a[l] != x) System.out.println("-1 -1");
            else {
                System.out.print(l + " ");
                l = 0; r = n - 1;
                // 找右边界
                while (l < r) {
                    int mid = l + r + 1 >> 1;
                    if (a[mid] <= x) l = mid;
                    else r = mid - 1;
                }
                System.out.println(r);
            }
        }
    }
}