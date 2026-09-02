# SPFA

SPFA 用队列缩小 Bellman-Ford 中需要松弛的点集，可用于含负权边图的单源最短路和负权回路判定。

## [AcWing 851. SPFA 求最短路](https://www.acwing.com/problem/content/853/)

### 题目描述

给定一个包含 $n$ 个点和 $m$ 条边的有向图，图中可能存在重边和自环，边权可能为负数，但保证不存在负权回路。求从 $1$ 号点到 $n$ 号点的最短距离。

**输入格式**

- 第一行包含两个整数 $n$ 和 $m$。
- 接下来 $m$ 行，每行包含三个整数 $x,y,z$，表示存在一条从 $x$ 指向 $y$、长度为 $z$ 的边。

**输出格式**

输出 $1$ 号点到 $n$ 号点的最短距离；如果无法到达，输出 `impossible`。

**数据范围**

- $1\le n,m\le 10^5$
- $|z|\le 10^4$

**输入样例**

```text
3 3
1 2 5
2 3 -3
1 3 4
```

**输出样例**

```text
2
```

### 参考 Java 解法

```java
import java.util.*;

public class Main {
    private static final long INF = Long.MAX_VALUE / 4;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
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
        boolean[] inQueue = new boolean[n + 1];
        Arrays.fill(distance, INF);
        distance[1] = 0;

        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.offer(1);
        inQueue[1] = true;

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            inQueue[vertex] = false;

            for (int edge = head[vertex]; edge != -1; edge = next[edge]) {
                int neighbor = to[edge];
                long candidate = distance[vertex] + weight[edge];
                if (candidate < distance[neighbor]) {
                    distance[neighbor] = candidate;
                    if (!inQueue[neighbor]) {
                        queue.offer(neighbor);
                        inQueue[neighbor] = true;
                    }
                }
            }
        }

        System.out.println(distance[n] == INF ? "impossible" : distance[n]);
    }
}
```

### 时空复杂度

- 时间复杂度：常见数据下通常较快，最坏为 $O(nm)$。
- 空间复杂度：$O(n+m)$，用于存储邻接表、距离数组和队列。

## [AcWing 852. SPFA 判断负环](https://www.acwing.com/problem/content/854/)

### 题目描述

给定一个包含 $n$ 个点和 $m$ 条边的有向图，图中可能存在重边和自环，边权可能为负数。判断图中是否存在负权回路。

**输入格式**

- 第一行包含两个整数 $n$ 和 $m$。
- 接下来 $m$ 行，每行包含三个整数 $x,y,z$，表示存在一条从 $x$ 指向 $y$、长度为 $z$ 的边。

**输出格式**

如果图中存在负权回路，输出 `Yes`；否则输出 `No`。

**数据范围**

- $1\le n\le 2000$
- $1\le m\le 10^4$
- $|z|\le 10^4$

**输入样例**

```text
3 3
1 2 -1
2 3 4
3 1 -4
```

**输出样例**

```text
Yes
```

### 参考 Java 解法

```java
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
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
        int[] edgeCount = new int[n + 1];
        boolean[] inQueue = new boolean[n + 1];
        ArrayDeque<Integer> queue = new ArrayDeque<>();

        for (int vertex = 1; vertex <= n; vertex++) {
            queue.offer(vertex);
            inQueue[vertex] = true;
        }

        boolean hasNegativeCycle = false;
        while (!queue.isEmpty() && !hasNegativeCycle) {
            int vertex = queue.poll();
            inQueue[vertex] = false;

            for (int edge = head[vertex]; edge != -1; edge = next[edge]) {
                int neighbor = to[edge];
                long candidate = distance[vertex] + weight[edge];
                if (candidate < distance[neighbor]) {
                    distance[neighbor] = candidate;
                    edgeCount[neighbor] = edgeCount[vertex] + 1;
                    if (edgeCount[neighbor] >= n) {
                        hasNegativeCycle = true;
                        break;
                    }
                    if (!inQueue[neighbor]) {
                        queue.offer(neighbor);
                        inQueue[neighbor] = true;
                    }
                }
            }
        }

        System.out.println(hasNegativeCycle ? "Yes" : "No");
    }
}
```

### 时空复杂度

- 时间复杂度：最坏为 $O(nm)$。
- 空间复杂度：$O(n+m)$，用于存储邻接表、距离、路径边数和队列。
