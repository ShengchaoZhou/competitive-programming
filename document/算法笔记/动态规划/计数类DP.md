# 计数类 DP

计数类 DP 用于统计满足指定组成或选择条件的不同方案数量。

## [AcWing 900. 整数划分](https://www.acwing.com/problem/content/902/)

### 题目描述

一个正整数 `n` 可以写成若干个正整数之和。若约定各加数按非递增顺序排列，则每一种不同的加数组合对应一种划分。给定 `n`，求它共有多少种不同的划分方式，答案对 `1000000007` 取模。

**输入格式**

输入一行，包含一个正整数 `n`。

**输出格式**

输出一个整数，表示不同划分方式的数量对 `1000000007` 取模后的结果。

**数据范围**

- `1 ≤ n ≤ 1000`

**输入样例**

```text
5
```

**输出样例**

```text
7
```

### 参考 Java 解法

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final int MOD = 1_000_000_007;

    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        int n = scanner.nextInt();
        long[] dp = new long[n + 1];
        dp[0] = 1;

        for (int part = 1; part <= n; part++) {
            for (int sum = part; sum <= n; sum++) {
                dp[sum] = (dp[sum] + dp[sum - part]) % MOD;
            }
        }

        System.out.println(dp[n]);
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 12];
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

- 时间复杂度：$O(n^2)$。
- 空间复杂度：$O(n)$。
