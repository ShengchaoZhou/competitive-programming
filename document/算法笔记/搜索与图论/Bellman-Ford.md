# Bellman-Ford

Bellman-Ford 算法用于在含负权边的图中求单源最短路，并可通过限制松弛轮数约束路径经过的边数。

## [AcWing 853. 有边数限制的最短路](https://www.acwing.com/problem/content/855/)

### 题目描述

给定一个包含 $n$ 个点和 $m$ 条边的有向图，图中可能存在重边、自环和负权回路。求从 $1$ 号点到 $n$ 号点、最多经过 $k$ 条边的最短距离。

**输入格式**

- 第一行包含三个整数 $n,m,k$。
- 接下来 $m$ 行，每行包含三个整数 $x,y,z$，表示存在一条从 $x$ 指向 $y$、长度为 $z$ 的边。

**输出格式**

输出满足边数限制的最短距离；如果不存在这样的路径，输出 `impossible`。

**数据范围**

- $1\le n,k\le 500$
- $1\le m\le 10^4$
- $|z|\le 10^4$

**输入样例**

```text
3 3 1
1 2 1
2 3 1
1 3 3
```

**输出样例**

```text
3
```

### 参考 Java 解法

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    private static final long INF = Long.MAX_VALUE / 4;

    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int maximumEdges = scanner.nextInt();

        int[] from = new int[m];
        int[] to = new int[m];
        int[] weight = new int[m];
        for (int edge = 0; edge < m; edge++) {
            from[edge] = scanner.nextInt();
            to[edge] = scanner.nextInt();
            weight[edge] = scanner.nextInt();
        }

        long[] distance = new long[n + 1];
        Arrays.fill(distance, INF);
        distance[1] = 0;

        for (int round = 0; round < maximumEdges; round++) {
            long[] previous = distance.clone();
            for (int edge = 0; edge < m; edge++) {
                if (previous[from[edge]] == INF) {
                    continue;
                }
                distance[to[edge]] = Math.min(
                        distance[to[edge]], previous[from[edge]] + weight[edge]);
            }
        }

        System.out.println(distance[n] == INF ? "impossible" : distance[n]);
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
                if (length == -1) {
                    return -1;
                }
            }
            return buffer[pointer++];
        }

        private int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }

            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value * sign;
        }
    }
}
```

### 时空复杂度

- 时间复杂度：$O(km)$，每轮都遍历全部 $m$ 条边。
- 空间复杂度：$O(n+m)$，用于存储边、距离数组及上一轮距离的备份。
