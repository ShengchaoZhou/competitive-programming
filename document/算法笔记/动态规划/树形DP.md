# 树形 DP

树形 DP 用于把树上问题拆成若干子树状态，再按父子依赖关系合并子树的最优结果。

## [AcWing 285. 没有上司的舞会](https://www.acwing.com/problem/content/287/)

### 题目描述

一所大学有 $N$ 名职员，编号为 $1$ 到 $N$，他们的直属关系构成一棵以校长为根的树。第 $i$ 名职员有一个可能为负数的快乐指数 $H_i$。现在要邀请一部分职员参加周年庆舞会，但任何职员都不愿和自己的直接上司同时参会。求在满足这一条件时，所有参会职员的快乐指数之和最大是多少。

**输入格式**

- 第一行包含整数 `N`。
- 接下来 `N` 行，第 `i` 行包含整数 `H[i]`，表示 `i` 号职员的快乐指数。
- 接下来 `N - 1` 行，每行包含两个整数 `L` 和 `K`，表示 `K` 是 `L` 的直接上司。

**输出格式**

输出一个整数，表示可获得的最大快乐指数之和。

**数据范围**

- $1 \le N \le 6000$
- $-128 \le H_i \le 127$

**输入样例**

```text
7
1
1
1
1
1
1
1
1 3
2 3
6 4
7 4
4 5
3 5
```

**输出样例**

```text
5
```

### 参考 Java 解法

```java
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] happiness = new int[n + 1];
        for (int employee = 1; employee <= n; employee++) {
            happiness[employee] = scanner.nextInt();
        }

        int[] head = new int[n + 1];
        Arrays.fill(head, -1);
        int[] child = new int[Math.max(0, n - 1)];
        int[] nextEdge = new int[Math.max(0, n - 1)];
        boolean[] hasSuperior = new boolean[n + 1];

        for (int edge = 0; edge < n - 1; edge++) {
            int subordinate = scanner.nextInt();
            int superior = scanner.nextInt();
            child[edge] = subordinate;
            nextEdge[edge] = head[superior];
            head[superior] = edge;
            hasSuperior[subordinate] = true;
        }

        int root = 1;
        while (hasSuperior[root]) root++;

        int[] stack = new int[n];
        int[] order = new int[n];
        int stackSize = 0;
        int orderSize = 0;
        stack[stackSize++] = root;

        while (stackSize > 0) {
            int employee = stack[--stackSize];
            order[orderSize++] = employee;
            for (int edge = head[employee]; edge != -1; edge = nextEdge[edge]) {
                stack[stackSize++] = child[edge];
            }
        }

        int[] absent = new int[n + 1];
        int[] present = new int[n + 1];
        for (int index = orderSize - 1; index >= 0; index--) {
            int employee = order[index];
            present[employee] = happiness[employee];

            for (int edge = head[employee]; edge != -1; edge = nextEdge[edge]) {
                int subordinate = child[edge];
                absent[employee] += Math.max(absent[subordinate], present[subordinate]);
                present[employee] += absent[subordinate];
            }
        }

        System.out.println(Math.max(absent[root], present[root]));
    }

}
```

### 时空复杂度

- 时间复杂度：$O(N)$，每名职员和每条上下级关系都只处理常数次。
- 空间复杂度：$O(N)$，用于存储树、非递归遍历顺序和 DP 状态。
