# 状态压缩 DP

状态压缩 DP 用于将规模较小的集合或轮廓状态编码为二进制数，再在可枚举的状态之间进行计数或最优值转移。

## [AcWing 291. 蒙德里安的梦想](https://www.acwing.com/problem/content/293/)

### 题目描述

给定一个 $N \times M$ 的棋盘，需要用若干个 $1 \times 2$ 的长方形恰好覆盖所有格子。长方形可以横放或竖放，不能重叠，也不能超出棋盘。请计算一共有多少种不同的覆盖方案。

**输入格式**

- 输入包含多组测试数据，每组占一行，包含两个整数 `N` 和 `M`。
- 输入 `0 0` 时结束，该组数据不需处理。

**输出格式**

对每组测试数据输出一行，包含棋盘的覆盖方案数。

**数据范围**

- $1 \le N,M \le 11$

**输入样例**

```text
1 2
1 3
1 4
2 2
2 3
2 4
2 11
4 11
0 0
```

**输出样例**

```text
1
0
1
2
3
5
144
51205
```

### 参考 Java 解法

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    private static final int[][][] TRANSITION_CACHE = new int[12][][];

    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        StringBuilder answer = new StringBuilder();

        while (true) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            if (n == 0 && m == 0) break;

            if (n > m) {
                int temporary = n;
                n = m;
                m = temporary;
            }

            int[][] predecessors = getPredecessors(n);
            int stateCount = 1 << n;
            long[] previous = new long[stateCount];
            previous[0] = 1;

            for (int column = 0; column < m; column++) {
                long[] current = new long[stateCount];
                for (int state = 0; state < stateCount; state++) {
                    long ways = 0;
                    for (int predecessor : predecessors[state]) {
                        ways += previous[predecessor];
                    }
                    current[state] = ways;
                }
                previous = current;
            }

            answer.append(previous[0]).append('\n');
        }

        System.out.print(answer);
    }

    private static int[][] getPredecessors(int rowCount) {
        if (TRANSITION_CACHE[rowCount] != null) {
            return TRANSITION_CACHE[rowCount];
        }

        int stateCount = 1 << rowCount;
        boolean[] canBeFilledVertically = new boolean[stateCount];
        for (int state = 0; state < stateCount; state++) {
            canBeFilledVertically[state] = hasOnlyEvenEmptyRuns(state, rowCount);
        }

        int[][] predecessors = new int[stateCount][];
        for (int current = 0; current < stateCount; current++) {
            int[] candidates = new int[stateCount];
            int size = 0;
            for (int previous = 0; previous < stateCount; previous++) {
                if ((current & previous) == 0
                        && canBeFilledVertically[current | previous]) {
                    candidates[size++] = previous;
                }
            }
            predecessors[current] = Arrays.copyOf(candidates, size);
        }

        TRANSITION_CACHE[rowCount] = predecessors;
        return predecessors;
    }

    private static boolean hasOnlyEvenEmptyRuns(int state, int rowCount) {
        int emptyRunLength = 0;
        for (int row = 0; row < rowCount; row++) {
            if ((state & (1 << row)) == 0) {
                emptyRunLength++;
            } else {
                if ((emptyRunLength & 1) == 1) return false;
                emptyRunLength = 0;
            }
        }
        return (emptyRunLength & 1) == 0;
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

- 时间复杂度：记 $n=\min(N,M)$、$m=\max(N,M)$，预处理最坏为 $O(4^n)$，每列枚举所有合法状态转移，总体最坏为 $O(m \cdot 4^n)$。
- 空间复杂度：滚动 DP 数组为 $O(2^n)$，合法转移表最坏为 $O(4^n)$。

## [AcWing 91. 最短Hamilton路径](https://www.acwing.com/problem/content/93/)

### 题目描述

给定一张包含 $n$ 个顶点的带权无向图，顶点编号为 $0$ 到 $n-1$。请找出一条从顶点 $0$ 出发、到顶点 $n-1$ 结束，并且不重不漏地经过每个顶点恰好一次的路径，求该路径的最小总权值。

**输入格式**

- 第一行包含整数 `n`。
- 接下来 `n` 行，每行包含 `n` 个整数；第 `i` 行第 `j` 个数 `a[i][j]` 表示顶点 `i` 与顶点 `j` 之间的距离。
- 数据保证 `a[x][x] = 0`、`a[x][y] = a[y][x]`，且对任意 `x, y, z` 均有 `a[x][y] + a[y][z] ≥ a[x][z]`。

**输出格式**

输出一个整数，表示最短 Hamilton 路径的长度。

**数据范围**

- $1 \le n \le 20$
- $0 \le a[i][j] \le 10^7$

**输入样例**

```text
5
0 2 4 5 1
2 0 6 5 3
4 6 0 8 3
5 5 8 0 5
1 3 3 5 0
```

**输出样例**

```text
18
```

### 参考 Java 解法

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    private static final int INF = 0x3f3f3f3f;

    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        int n = scanner.nextInt();
        int[][] distance = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                distance[i][j] = scanner.nextInt();
            }
        }

        int stateCount = 1 << Math.max(0, n - 1);
        int[] dp = new int[stateCount * n];
        Arrays.fill(dp, INF);
        dp[0] = 0;

        for (int state = 0; state < stateCount; state++) {
            if (state == 0) {
                for (int next = 1; next < n; next++) {
                    int nextState = 1 << (next - 1);
                    dp[nextState * n + next] = distance[0][next];
                }
                continue;
            }

            int unvisited = (stateCount - 1) ^ state;
            for (int last = 1; last < n; last++) {
                if ((state & (1 << (last - 1))) == 0) continue;

                int currentDistance = dp[state * n + last];
                if (currentDistance == INF) continue;

                for (int candidates = unvisited; candidates != 0; candidates &= candidates - 1) {
                    int bit = candidates & -candidates;
                    int next = Integer.numberOfTrailingZeros(bit) + 1;
                    int nextState = state | bit;
                    int index = nextState * n + next;
                    dp[index] = Math.min(
                            dp[index],
                            currentDistance + distance[last][next]
                    );
                }
            }
        }

        int fullState = stateCount - 1;
        System.out.println(dp[fullState * n + n - 1]);
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

- 时间复杂度：$O(n^2 \cdot 2^{n-1})$。
- 空间复杂度：$O(n \cdot 2^{n-1} + n^2)$，用于保存状态 DP 和距离矩阵。
