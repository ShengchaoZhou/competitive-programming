package templates.AlgorithmBasics;

import java.util.*;

class basic004001HighPrecisionAddition {
    public static List<Integer> add(List<Integer> A, List<Integer> B) {
        int t = 0;
        List<Integer> C = new ArrayList<>();
        for (int i = 0; i < A.size() || i < B.size(); i++) {
            if (i < A.size()) t += A.get(i);
            if (i < B.size()) t += B.get(i);
            C.add(t % 10);
            t /= 10;
        }
        if (t > 0) C.add(t);

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

        List<Integer> C = add(A, B);

        for (int i = C.size() - 1; i >= 0; i--) {
            System.out.print(C.get(i));
        }
    }
}