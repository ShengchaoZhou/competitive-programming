package templates.AlgorithmBasics;

import java.util.*;

class  basic004003HighPrecisionMultiply {
    public static List<Integer> mul(List<Integer> A, int B) {
        int t = 0;
        List<Integer> C = new ArrayList<>();
        for (int i = 0; i < A.size(); i++) {
            t += A.get(i) * B;
            C.add(t % 10);
            t /= 10;
        }
        while (t > 0) {
            C.add(t % 10);
            t /= 10;
        }
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

        List<Integer> C = mul(A, B);

        for (int i = C.size() - 1; i >= 0; i--) {
            System.out.print(C.get(i));
        }
    }
}