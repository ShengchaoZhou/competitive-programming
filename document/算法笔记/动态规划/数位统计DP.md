# 数位统计 DP

数位统计 DP 用于按十进制位拆分整数范围，统计某些数字或数位特征在区间内出现的次数。

## [AcWing 338. 计数问题](https://www.acwing.com/problem/content/340/)

### 题目描述

给定两个正整数 `a` 和 `b`，统计闭区间 `[min(a,b), max(a,b)]` 内所有整数的十进制表示中，数字 `0` 到 `9` 分别出现了多少次。例如统计数字 `11` 时，其中的数字 `1` 应计数两次。

输入包含多组数据，以 `0 0` 作为结束标志，该行不参与统计。

**输入格式**

- 每组数据占一行，包含两个整数 `a` 和 `b`。
- 当输入行为 `0 0` 时结束。

**输出格式**

每组数据输出一行十个整数，依次表示数字 `0`、`1`、……、`9` 的出现次数，相邻数字之间用空格分隔。

**数据范围**

- `0 < a,b < 100000000`

**输入样例**

```text
1 10
44 497
346 542
1199 1748
1496 1403
1004 503
1714 190
1317 854
1976 494
1001 1960
0 0
```

**输出样例**

```text
1 2 1 1 1 1 1 1 1 1
85 185 185 185 190 96 96 96 95 93
40 40 40 93 136 82 40 40 40 40
115 666 215 215 214 205 205 154 105 106
16 113 19 20 114 20 20 19 19 16
107 105 100 101 101 197 200 200 200 200
413 1133 503 503 503 502 502 417 402 412
196 512 186 104 87 93 97 97 142 196
398 1375 398 398 405 499 499 495 488 471
294 1256 296 296 296 296 287 286 286 247
```

### 参考 Java 解法

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        StringBuilder answer = new StringBuilder();

        while (true) {
            long a = scanner.nextLong();
            long b = scanner.nextLong();
            if (a == 0 && b == 0) break;

            long left = Math.min(a, b);
            long right = Math.max(a, b);
            for (int digit = 0; digit <= 9; digit++) {
                if (digit > 0) answer.append(' ');
                answer.append(countUpTo(right, digit) - countUpTo(left - 1, digit));
            }
            answer.append('\n');
        }

        System.out.print(answer);
    }

    private static long countUpTo(long number, int digit) {
        if (number <= 0) return 0;

        long count = 0;
        for (long factor = 1; factor <= number; factor *= 10) {
            long lower = number % factor;
            long current = number / factor % 10;
            long higher = number / factor / 10;

            if (digit != 0) {
                count += higher * factor;
                if (current > digit) {
                    count += factor;
                } else if (current == digit) {
                    count += lower + 1;
                }
            } else if (higher != 0) {
                count += (higher - 1) * factor;
                count += current == 0 ? lower + 1 : factor;
            }

            if (factor > number / 10) break;
        }
        return count;
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

        private long nextLong() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            long value = 0;
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

- 时间复杂度：每组数据为 $O(10\log M)$，其中 $M=\max(a,b)$，可简写为 $O(\log M)$。
- 空间复杂度：$O(1)$；不计输出缓冲区。
