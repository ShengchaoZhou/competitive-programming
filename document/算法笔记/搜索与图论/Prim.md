# Prim

Prim 算法用于求带权无向图的最小生成树，朴素实现适合点数较小的稠密图。

## [AcWing 858. Prim 算法求最小生成树](https://www.acwing.com/problem/content/860/)

### 题目描述

给定一个包含 $n$ 个点和 $m$ 条边的带权无向图，图中可能存在重边和自环，边权可能为负数。求该图最小生成树的边权之和。

**输入格式**

- 第一行包含两个整数 $n$ 和 $m$。
- 接下来 $m$ 行，每行包含三个整数 $u,v,w$，表示 $u$ 和 $v$ 之间存在一条权值为 $w$ 的无向边。

**输出格式**

如果最小生成树存在，输出其边权之和；否则输出 `impossible`。

**数据范围**

- $1\le n\le 500$
- $1\le m\le 10^5$
- $|w|\le 10^4$

**输入样例**

```text
4 5
1 2 1
1 3 2
1 4 3
2 3 2
3 4 4
```

**输出样例**

```text
6
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

        long[][] graph = new long[n + 1][n + 1];
        for (int vertex = 1; vertex <= n; vertex++) {
            Arrays.fill(graph[vertex], INF);
        }

        for (int edge = 0; edge < m; edge++) {
            int first = scanner.nextInt();
            int second = scanner.nextInt();
            int weight = scanner.nextInt();
            graph[first][second] = Math.min(graph[first][second], weight);
            graph[second][first] = Math.min(graph[second][first], weight);
        }

        long[] distanceToTree = new long[n + 1];
        boolean[] inTree = new boolean[n + 1];
        Arrays.fill(distanceToTree, INF);
        distanceToTree[1] = 0;

        long totalWeight = 0;
        boolean connected = true;
        for (int round = 0; round < n; round++) {
            int nearest = -1;
            for (int vertex = 1; vertex <= n; vertex++) {
                if (!inTree[vertex]
                        && (nearest == -1
                        || distanceToTree[vertex] < distanceToTree[nearest])) {
                    nearest = vertex;
                }
            }

            if (nearest == -1 || distanceToTree[nearest] == INF) {
                connected = false;
                break;
            }

            inTree[nearest] = true;
            totalWeight += distanceToTree[nearest];
            for (int vertex = 1; vertex <= n; vertex++) {
                if (!inTree[vertex] && graph[nearest][vertex] < distanceToTree[vertex]) {
                    distanceToTree[vertex] = graph[nearest][vertex];
                }
            }
        }

        System.out.println(connected ? totalWeight : "impossible");
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

- 时间复杂度：$O(n^2+m)$，主要开销是在邻接矩阵上执行 $n$ 轮选点与更新。
- 空间复杂度：$O(n^2)$，用于存储邻接矩阵。
