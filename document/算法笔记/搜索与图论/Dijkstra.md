# Dijkstra

Dijkstra 算法用于求边权非负的图中从单个源点出发的最短路，稠密图可用朴素实现，稀疏图可用堆优化实现。

## [AcWing 849. Dijkstra 求最短路 I](https://www.acwing.com/problem/content/851/)

### 题目描述

给定一个包含 $n$ 个点和 $m$ 条边的有向图，图中可能存在重边和自环，所有边权均为正数。求从 $1$ 号点到 $n$ 号点的最短距离。

**输入格式**

- 第一行包含两个整数 $n$ 和 $m$。
- 接下来 $m$ 行，每行包含三个整数 $x,y,z$，表示存在一条从 $x$ 指向 $y$、长度为 $z$ 的边。

**输出格式**

输出 $1$ 号点到 $n$ 号点的最短距离；如果无法到达，输出 `-1`。

**数据范围**

- $1\le n\le 500$
- $1\le m\le 10^5$
- $1\le z\le 10^4$

**输入样例**

```text
3 3
1 2 2
2 3 1
1 3 4
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

        long[][] graph = new long[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            Arrays.fill(graph[i], INF);
            graph[i][i] = 0;
        }

        for (int i = 0; i < m; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            int weight = scanner.nextInt();
            graph[from][to] = Math.min(graph[from][to], weight);
        }

        long[] distance = new long[n + 1];
        boolean[] visited = new boolean[n + 1];
        Arrays.fill(distance, INF);
        distance[1] = 0;

        for (int round = 0; round < n; round++) {
            int nearest = -1;
            for (int vertex = 1; vertex <= n; vertex++) {
                if (!visited[vertex]
                        && (nearest == -1 || distance[vertex] < distance[nearest])) {
                    nearest = vertex;
                }
            }

            if (nearest == -1 || distance[nearest] == INF) {
                break;
            }
            visited[nearest] = true;

            for (int vertex = 1; vertex <= n; vertex++) {
                if (graph[nearest][vertex] != INF) {
                    distance[vertex] = Math.min(
                            distance[vertex], distance[nearest] + graph[nearest][vertex]);
                }
            }
        }

        System.out.println(distance[n] == INF ? -1 : distance[n]);
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

- 时间复杂度：$O(n^2+m)$，主要开销是在邻接矩阵上执行 $n$ 轮选点与更新。
- 空间复杂度：$O(n^2)$，用于存储邻接矩阵。

## [AcWing 850. Dijkstra 求最短路 II](https://www.acwing.com/problem/content/852/)

### 题目描述

给定一个包含 $n$ 个点和 $m$ 条边的有向图，图中可能存在重边和自环，所有边权均为非负数。求从 $1$ 号点到 $n$ 号点的最短距离。

**输入格式**

- 第一行包含两个整数 $n$ 和 $m$。
- 接下来 $m$ 行，每行包含三个整数 $x,y,z$，表示存在一条从 $x$ 指向 $y$、长度为 $z$ 的边。

**输出格式**

输出 $1$ 号点到 $n$ 号点的最短距离；如果无法到达，输出 `-1`。

**数据范围**

- $1\le n,m\le 1.5\times 10^5$
- $0\le z\le 10^4$
- 如果最短路存在，则其长度不超过 $10^9$。

**输入样例**

```text
3 3
1 2 2
2 3 1
1 3 4
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
import java.util.PriorityQueue;

public class Main {
    private static final long INF = Long.MAX_VALUE / 4;

    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        int[] head = new int[n + 1];
        int[] to = new int[m];
        int[] weight = new int[m];
        int[] next = new int[m];
        Arrays.fill(head, -1);

        for (int edge = 0; edge < m; edge++) {
            int from = scanner.nextInt();
            to[edge] = scanner.nextInt();
            weight[edge] = scanner.nextInt();
            next[edge] = head[from];
            head[from] = edge;
        }

        long[] distance = new long[n + 1];
        Arrays.fill(distance, INF);
        distance[1] = 0;

        PriorityQueue<State> heap = new PriorityQueue<>();
        heap.offer(new State(1, 0));

        while (!heap.isEmpty()) {
            State current = heap.poll();
            int vertex = current.vertex;
            if (current.distance != distance[vertex]) {
                continue;
            }

            for (int edge = head[vertex]; edge != -1; edge = next[edge]) {
                int neighbor = to[edge];
                long candidate = distance[vertex] + weight[edge];
                if (candidate < distance[neighbor]) {
                    distance[neighbor] = candidate;
                    heap.offer(new State(neighbor, candidate));
                }
            }
        }

        System.out.println(distance[n] == INF ? -1 : distance[n]);
    }

    private static class State implements Comparable<State> {
        private final int vertex;
        private final long distance;

        private State(int vertex, long distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(State other) {
            return Long.compare(distance, other.distance);
        }
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

- 时间复杂度：$O((n+m)\log n)$，使用优先队列取出当前距离最小的点。
- 空间复杂度：$O(n+m)$，用于存储邻接表、距离数组和优先队列。
