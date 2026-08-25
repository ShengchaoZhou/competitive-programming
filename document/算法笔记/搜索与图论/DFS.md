# DFS

DFS 用于沿搜索树的一条路径不断深入，并通过回溯枚举排列、棋盘布局等所有可行方案。

## [AcWing 842. 排列数字](https://www.acwing.com/problem/content/844/)

### 题目描述

给定整数 $n$，将 $1 \sim n$ 这 $n$ 个整数排成一列，并按照字典序输出所有可能的排列。

**输入格式**

输入一行，包含整数 $n$。

**输出格式**

按照字典序输出所有排列，每个排列占一行，相邻数字之间用一个空格分隔。

**数据范围**

- $1 \le n \le 7$

**输入样例**

```text
3
```

**输出样例**

```text
1 2 3
1 3 2
2 1 3
2 3 1
3 1 2
3 2 1
```

### 参考 Java 解法

```java
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Main {
    private static int n;
    private static int[] permutation;
    private static boolean[] used;
    private static BufferedWriter output;

    private static void dfs(int position) throws IOException {
        if (position == n) {
            for (int i = 0; i < n; i++) {
                if (i > 0) {
                    output.write(' ');
                }
                output.write(Integer.toString(permutation[i]));
            }
            output.newLine();
            return;
        }

        for (int number = 1; number <= n; number++) {
            if (!used[number]) {
                used[number] = true;
                permutation[position] = number;
                dfs(position + 1);
                used[number] = false;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        n = scanner.nextInt();
        permutation = new int[n];
        used = new boolean[n + 1];
        output = new BufferedWriter(new OutputStreamWriter(System.out));

        dfs(0);
        output.flush();
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);

        int nextInt() throws IOException {
            int c;
            do {
                c = input.read();
            } while (c <= ' ' && c != -1);

            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = input.read();
            }
            return value;
        }
    }
}
```

### 时空复杂度

- 时间复杂度：$O(n \cdot n!)$，共输出 $n!$ 个长度为 $n$ 的排列。
- 空间复杂度：$O(n)$，保存当前排列、使用标记和递归调用栈。

## [AcWing 843. n-皇后问题](https://www.acwing.com/problem/content/845/)

### 题目描述

在一个 $n \times n$ 的国际象棋棋盘上放置 $n$ 个皇后，使任意两个皇后都不在同一行、同一列或同一条斜线上。输出所有不重复且不遗漏的合法摆放方案。

**输入格式**

输入一行，包含整数 $n$。

**输出格式**

每个方案占 $n$ 行，每行是一个长度为 $n$ 的字符串，其中 `Q` 表示皇后，`.` 表示空格子。

每个方案输出完毕后再输出一个空行。方案的先后顺序任意，但行末不能有多余空格。

**数据范围**

- $1 \le n \le 9$

**输入样例**

```text
4
```

**输出样例**

```text
.Q..
...Q
Q...
..Q.

..Q.
Q...
...Q
.Q..

```

### 参考 Java 解法

```java
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {
    private static int n;
    private static char[][] board;
    private static boolean[] occupiedColumn;
    private static boolean[] occupiedMainDiagonal;
    private static boolean[] occupiedAntiDiagonal;
    private static BufferedWriter output;

    private static void dfs(int row) throws IOException {
        if (row == n) {
            for (char[] line : board) {
                output.write(line);
                output.newLine();
            }
            output.newLine();
            return;
        }

        for (int column = 0; column < n; column++) {
            int mainDiagonal = row - column + n;
            int antiDiagonal = row + column;
            if (!occupiedColumn[column]
                    && !occupiedMainDiagonal[mainDiagonal]
                    && !occupiedAntiDiagonal[antiDiagonal]) {
                board[row][column] = 'Q';
                occupiedColumn[column] = true;
                occupiedMainDiagonal[mainDiagonal] = true;
                occupiedAntiDiagonal[antiDiagonal] = true;

                dfs(row + 1);

                board[row][column] = '.';
                occupiedColumn[column] = false;
                occupiedMainDiagonal[mainDiagonal] = false;
                occupiedAntiDiagonal[antiDiagonal] = false;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        n = scanner.nextInt();
        board = new char[n][n];
        for (char[] row : board) {
            Arrays.fill(row, '.');
        }
        occupiedColumn = new boolean[n];
        occupiedMainDiagonal = new boolean[2 * n + 1];
        occupiedAntiDiagonal = new boolean[2 * n + 1];
        output = new BufferedWriter(new OutputStreamWriter(System.out));

        dfs(0);
        output.flush();
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);

        int nextInt() throws IOException {
            int c;
            do {
                c = input.read();
            } while (c <= ' ' && c != -1);

            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = input.read();
            }
            return value;
        }
    }
}
```

### 时空复杂度

- 时间复杂度：搜索过程至多为 $O(n \cdot n!)$；若共有 $S$ 个方案，输出还需要 $O(S \cdot n^2)$ 时间。
- 空间复杂度：$O(n^2)$，棋盘占用 $O(n^2)$，标记数组和递归调用栈占用 $O(n)$。
