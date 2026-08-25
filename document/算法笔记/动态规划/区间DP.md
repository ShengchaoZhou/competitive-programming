# 区间 DP

区间 DP 用于把一个大区间拆成若干较小区间，并利用子区间的最优结果求出整个区间的最优结果。

## [AcWing 282. 石子合并](https://www.acwing.com/problem/content/284/)

### 题目描述

有 `N` 堆石子从左到右排成一行，第 `i` 堆的质量为 `a[i]`。每次只能选择相邻的两堆进行合并，合并代价等于这两堆石子的质量之和，合并后的新石堆质量也等于二者之和。求把全部石子合并成一堆所需的最小总代价。

**输入格式**

- 第一行包含整数 `N`，表示石子堆数。
- 第二行包含 `N` 个整数，表示每堆石子的质量。

**输出格式**

输出一个整数，表示最小合并代价。

**数据范围**

- `1 ≤ N ≤ 300`
- `1 ≤ a[i] ≤ 1000`

**输入样例**

```text
4
1 3 5 2
```

**输出样例**

```text
22
```

### 参考 Java 解法

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        int n = scanner.nextInt();
        long[] prefixSum = new long[n + 1];

        for (int i = 1; i <= n; i++) {
            prefixSum[i] = prefixSum[i - 1] + scanner.nextInt();
        }

        long[][] dp = new long[n + 1][n + 1];
        for (int length = 2; length <= n; length++) {
            for (int left = 1; left + length - 1 <= n; left++) {
                int right = left + length - 1;
                dp[left][right] = Long.MAX_VALUE / 4;
                long intervalSum = prefixSum[right] - prefixSum[left - 1];

                for (int split = left; split < right; split++) {
                    dp[left][right] = Math.min(
                            dp[left][right],
                            dp[left][split] + dp[split + 1][right] + intervalSum
                    );
                }
            }
        }

        System.out.println(dp[1][n]);
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int pointer;
        private int length;

        private int read() throws IOException {
            if (pointer >= length) {
                length = input.read(buffer);
                pointer = 0;
                if (length == -1) return -1;
            }
            return buffer[pointer++];
        }

        private int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value;
        }
    }
}
```

### 时空复杂度

- 时间复杂度：$O(N^3)$。
- 空间复杂度：$O(N^2)$。
