package templates.AlgorithmBasics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class basic004004HighPrecisionDivide {
    public static List<Integer> div(List<Integer> A, int B, int[] r) {
        int t = 0;
        List<Integer> C = new ArrayList<>();
        for (int i = A.size() - 1; i >= 0; i--) {
            t = A.get(i) + t * 10;
            C.add(t / B);
            t %= B;
        }
        r[0] = t;
        Collections.reverse(C);
        while (C.size() > 1 && C.get(C.size() - 1) == 0) C.remove(C.size() - 1);
        return C;
    }

    public static void main(String args[]) {
        List<Integer> A = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        for (int i = s.length() - 1; i >= 0; i--) {
            A.add(s.charAt(i) - '0');
        }
        int B = sc.nextInt();
        // 使用数组来模拟引用传值
        int[] r = new int[1];
        List<Integer> C = div(A, B, r);

        for (int i = C.size() - 1; i >= 0; i--) {
            System.out.print(C.get(i));
        }
        System.out.println();
        System.out.println(r[0]);
    }
}
