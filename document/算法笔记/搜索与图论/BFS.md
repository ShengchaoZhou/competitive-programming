# BFS

BFS 用于按距离逐层扩展状态，在所有移动代价相同的迷宫或状态图中求最少步数。

## [AcWing 844. 走迷宫](https://www.acwing.com/problem/content/846/)

### 题目描述

给定一个 $n \times m$ 的迷宫，其中 `0` 表示可以通行，`1` 表示墙壁。一个人最初位于左上角 $(1,1)$，每次可以向上、下、左、右移动一格，求到达右下角 $(n,m)$ 至少需要移动多少次。

题目保证起点和终点均可通行，并且二者之间至少存在一条路径。

**输入格式**

第一行包含两个整数 $n$ 和 $m$。

接下来 $n$ 行，每行包含 $m$ 个整数，表示迷宫。

**输出格式**

输出一个整数，表示从左上角移动到右下角的最少移动次数。

**数据范围**

- $1 \le n,m \le 100$

**输入样例**

```text
5 5
0 1 0 0 0
0 1 0 1 0
0 0 0 0 0
0 1 1 1 0
0 0 0 1 0
```

**输出样例**

```text
8
```

### 参考 Java 解法

```java
import java.util.*;

public class Main {
    private static final int[] DX = {-1, 0, 1, 0};
    private static final int[] DY = {0, 1, 0, -1};

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[][] maze = new int[n][m];
        int[][] distance = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                maze[i][j] = scanner.nextInt();
            }
            Arrays.fill(distance[i], -1);
        }

        int[] queueX = new int[n * m];
        int[] queueY = new int[n * m];
        int head = 0;
        int tail = 0;
        queueX[tail] = 0;
        queueY[tail++] = 0;
        distance[0][0] = 0;

        while (head < tail) {
            int x = queueX[head];
            int y = queueY[head++];

            for (int direction = 0; direction < 4; direction++) {
                int nextX = x + DX[direction];
                int nextY = y + DY[direction];
                if (nextX >= 0 && nextX < n
                        && nextY >= 0 && nextY < m
                        && maze[nextX][nextY] == 0
                        && distance[nextX][nextY] == -1) {
                    distance[nextX][nextY] = distance[x][y] + 1;
                    queueX[tail] = nextX;
                    queueY[tail++] = nextY;
                }
            }
        }

        System.out.println(distance[n - 1][m - 1]);
    }
}
```

### 时空复杂度

- 时间复杂度：$O(nm)$，每个格子至多入队一次。
- 空间复杂度：$O(nm)$，保存迷宫、距离和队列。

## [AcWing 845. 八数码](https://www.acwing.com/problem/content/847/)

### 题目描述

在一个 $3 \times 3$ 的网格中放置数字 `1` 到 `8` 以及一个空格 `x`。一次操作可以把 `x` 与它上、下、左、右相邻位置的数字交换。

给定一个初始状态，求将其变为目标状态 `1 2 3 4 5 6 7 8 x` 所需的最少交换次数；如果无法到达目标状态，输出 `-1`。

**输入格式**

输入一行，包含初始状态的九个字符，相邻字符之间用空格分隔。

**输出格式**

输出一个整数，表示最少交换次数；无解时输出 `-1`。

**数据范围**

- 棋盘固定为 $3 \times 3$。
- 数字 `1` 到 `8` 与空格 `x` 各出现一次。

**输入样例**

```text
2 3 4 1 5 x 7 6 8
```

**输出样例**

```text
19
```

### 参考 Java 解法

```java
import java.util.*;

public class Main {
    private static final String TARGET = "12345678x";
    private static final int[] DX = {-1, 0, 1, 0};
    private static final int[] DY = {0, 1, 0, -1};

    private static int bfs(String start) {
        Queue<String> queue = new ArrayDeque<>();
        Map<String, Integer> distance = new HashMap<>();
        queue.offer(start);
        distance.put(start, 0);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            int currentDistance = distance.get(current);
            if (current.equals(TARGET)) {
                return currentDistance;
            }

            int emptyPosition = current.indexOf('x');
            int x = emptyPosition / 3;
            int y = emptyPosition % 3;

            for (int direction = 0; direction < 4; direction++) {
                int nextX = x + DX[direction];
                int nextY = y + DY[direction];
                if (nextX < 0 || nextX >= 3 || nextY < 0 || nextY >= 3) {
                    continue;
                }

                int nextPosition = nextX * 3 + nextY;
                char[] state = current.toCharArray();
                state[emptyPosition] = state[nextPosition];
                state[nextPosition] = 'x';
                String next = new String(state);

                if (!distance.containsKey(next)) {
                    distance.put(next, currentDistance + 1);
                    queue.offer(next);
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        StringBuilder start = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            start.append(scanner.next());
        }
        System.out.println(bfs(start.toString()));
    }
}
```

### 时空复杂度

- 时间复杂度：$O(9! \cdot 9)$，最坏情况下枚举所有排列状态，并复制长度为 $9$ 的状态字符串。
- 空间复杂度：$O(9! \cdot 9)$，队列和哈希表最多保存所有排列状态。
