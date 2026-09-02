# Kruskal

Kruskal 算法用于求带权无向图的最小生成树，按边权排序并用并查集排除会形成环的边，适合稀疏图。

## [AcWing 859. Kruskal 算法求最小生成树](https://www.acwing.com/problem/content/861/)

### 题目描述

给定一个包含 $n$ 个点和 $m$ 条边的带权无向图，图中可能存在重边和自环，边权可能为负数。求该图最小生成树的边权之和。

**输入格式**

- 第一行包含两个整数 $n$ 和 $m$。
- 接下来 $m$ 行，每行包含三个整数 $u,v,w$，表示 $u$ 和 $v$ 之间存在一条权值为 $w$ 的无向边。

**输出格式**

如果最小生成树存在，输出其边权之和；否则输出 `impossible`。

**数据范围**

- $1\le n\le 10^5$
- $1\le m\le 2\times 10^5$
- $|w|\le 10^3$

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
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        Edge[] edges = new Edge[m];
        for (int i = 0; i < m; i++) {
            edges[i] = new Edge(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
        }
        Arrays.sort(edges, (first, second) -> Integer.compare(first.weight, second.weight));

        DisjointSet disjointSet = new DisjointSet(n);
        long totalWeight = 0;
        int selectedEdges = 0;

        for (Edge edge : edges) {
            if (disjointSet.union(edge.first, edge.second)) {
                totalWeight += edge.weight;
                selectedEdges++;
                if (selectedEdges == n - 1) {
                    break;
                }
            }
        }

        System.out.println(selectedEdges == n - 1 ? totalWeight : "impossible");
    }

    private static class Edge {
        private final int first;
        private final int second;
        private final int weight;

        private Edge(int first, int second, int weight) {
            this.first = first;
            this.second = second;
            this.weight = weight;
        }
    }

    private static class DisjointSet {
        private final int[] parent;
        private final int[] size;

        private DisjointSet(int n) {
            parent = new int[n + 1];
            size = new int[n + 1];
            for (int vertex = 1; vertex <= n; vertex++) {
                parent[vertex] = vertex;
                size[vertex] = 1;
            }
        }

        private int find(int vertex) {
            int root = vertex;
            while (root != parent[root]) {
                root = parent[root];
            }
            while (vertex != root) {
                int next = parent[vertex];
                parent[vertex] = root;
                vertex = next;
            }
            return root;
        }

        private boolean union(int first, int second) {
            int firstRoot = find(first);
            int secondRoot = find(second);
            if (firstRoot == secondRoot) {
                return false;
            }
            if (size[firstRoot] < size[secondRoot]) {
                int temporary = firstRoot;
                firstRoot = secondRoot;
                secondRoot = temporary;
            }
            parent[secondRoot] = firstRoot;
            size[firstRoot] += size[secondRoot];
            return true;
        }
    }
}
```

### 时空复杂度

- 时间复杂度：$O(m\log m)$，主要开销是对所有边排序，并查集操作的均摊开销近似常数。
- 空间复杂度：$O(n+m)$，用于存储边集和并查集。
