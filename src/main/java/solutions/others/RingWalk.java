package solutions.others;

import java.util.Arrays;

/**
 * 圆环10个点，走n步回到0的方案数，假设 n>=0
 */
public class RingWalk {
    public static int countWays(int n) {
        int[][] f = new int[n + 1][10]; // f[i][j]表示走i步，到达位置j的方案数
        f[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < 10; j++) {
                f[i][j] = f[i - 1][(j + 1) % 10] + f[i - 1][((j - 1) % 10 + 10) % 10];
            }
        }
        return f[n][0];
    }

    public static void main(String[] args) {
        for (int n = 0; n <= 12; n++) {
            System.out.println(n + " -> " + countWays(n));
        }
        // 期望：0->1, 2->2, 4->6, 6->20, 8->70, 10->254, 12->948 ...
    }
}
