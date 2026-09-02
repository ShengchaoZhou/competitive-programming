# Floyd

Floyd 算法用于一次求出图中任意两点之间的最短路，适合点数较小且查询较多的场景。

## [AcWing 854. Floyd 求最短路](https://www.acwing.com/problem/content/856/)

### 题目描述

给定一个包含 $n$ 个点和 $m$ 条边的有向图，图中可能存在重边和自环，边权可能为负数，但保证不存在负权回路。回答 $k$ 次两点最短距离查询。

**输入格式**

- 第一行包含三个整数 $n,m,k$。
- 接下来 $m$ 行，每行包含三个整数 $x,y,z$，表示存在一条从 $x$ 指向 $y$、长度为 $z$ 的边。
- 接下来 $k$ 行，每行包含两个整数 $x,y$，询问从 $x$ 到 $y$ 的最短距离。

**输出格式**

对每个询问输出一行：如果可达，输出最短距离；否则输出 `impossible`。

**数据范围**

- $1\le n\le 200$
- $1\le m\le 2\times 10^4$
- $1\le k\le n^2$
- $|z|\le 10^4$

**输入样例**

```text
3 3 2
1 2 1
2 3 2
1 3 1
2 1
1 3
```

**输出样例**

```text
impossible
1
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
        int queryCount = scanner.nextInt();

        long[][] distance = new long[n + 1][n + 1];
        for (int from = 1; from <= n; from++) {
            Arrays.fill(distance[from], INF);
            distance[from][from] = 0;
        }

        for (int edge = 0; edge < m; edge++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            int weight = scanner.nextInt();
            distance[from][to] = Math.min(distance[from][to], weight);
        }

        for (int middle = 1; middle <= n; middle++) {
            for (int from = 1; from <= n; from++) {
                if (distance[from][middle] == INF) {
                    continue;
                }
                for (int to = 1; to <= n; to++) {
                    if (distance[middle][to] == INF) {
                        continue;
                    }
                    distance[from][to] = Math.min(
                            distance[from][to],
                            distance[from][middle] + distance[middle][to]);
                }
            }
        }

        StringBuilder answer = new StringBuilder();
        for (int query = 0; query < queryCount; query++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            if (distance[from][to] == INF) {
                answer.append("impossible\n");
            } else {
                answer.append(distance[from][to]).append('\n');
            }
        }
        System.out.print(answer);
    }
}
```

### 时空复杂度

- 时间复杂度：$O(n^3+m+k)$，预处理后每次查询为 $O(1)$。
- 空间复杂度：$O(n^2)$，用于存储任意两点之间的最短距离。
