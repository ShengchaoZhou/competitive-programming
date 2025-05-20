package templates.AlgorithmBasics;

import java.util.Scanner;

public class basic003002BinarySearch {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        double x = sc.nextDouble();
        double l = -100, r = 100;
        while (r - l > 1e-8) {
            double mid = (l + r) / 2;
            if (mid * mid * mid <= x) l = mid;
            else r = mid;
        }
        System.out.printf("%.6f", l);
    }
}
