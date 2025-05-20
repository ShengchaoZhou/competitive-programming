package templates.AlgorithmBasics;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class basic004002HighPrecisionSubtract {
    public static boolean cmp(List<Integer> A, List<Integer> B) {
        if (A.size() != B.size()) return A.size() > B.size();
        for (int i = A.size() - 1; i >= 0; i--) {
            if (A.get(i) != B.get(i)) return A.get(i) > B.get(i);
        }
        return true;
    }

    public static List<Integer> sub(List<Integer> A, List<Integer> B) {
        int t = 0;
        List<Integer> C = new ArrayList<>();
        for (int i = 0; i < A.size(); i++) {
            t += A.get(i);
            if (i < B.size()) t -= B.get(i);
            C.add((t + 10) % 10);
            if (t < 0) t = -1; // 有借位
            else t = 0;
        }
        // 去除前导0，并保证至少留一位
        while (C.size() > 1 && C.get(C.size() - 1) == 0) C.remove(C.size() - 1);

        return C;
    }

    public static void main(String args[]) {
        List<Integer> A = new ArrayList<>(), B = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        for (int i = s.length() - 1; i >= 0; i--) {
            A.add(s.charAt(i) - '0');
        }
        s = sc.next();
        for (int i = s.length() - 1; i >= 0; i--) {
            B.add(s.charAt(i) - '0');
        }

        List<Integer> C;
        if (cmp(A, B)) C = sub(A, B);
        else {
            C = sub(B, A);
            System.out.print('-');
        }

        for (int i = C.size() - 1; i >= 0; i--) {
            System.out.print(C.get(i));
        }
    }
}
