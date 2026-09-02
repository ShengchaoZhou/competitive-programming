# Huffman 树

Huffman 树用于在反复合并带权对象且每次代价为两者权值之和时，求出最小总合并代价。

## [AcWing 148. 合并果子](https://www.acwing.com/problem/content/150/)

### 题目描述

果园中的果子按种类分成了 `n` 堆，第 `i` 堆有 `a[i]` 个果子，每个果子的重量均为 `1`。每次可以选择任意两堆合并，消耗的体力等于这两堆果子的总重量；新堆的重量也等于二者之和。

经过 `n - 1` 次合并后，所有果子会变成一堆。请安排合并顺序，求整个过程中消耗的最小体力之和。

**输入格式**

- 第一行包含整数 `n`，表示果子的种类数，也就是初始果子堆数。
- 第二行包含 `n` 个整数，第 `i` 个整数 `a[i]` 表示第 `i` 堆的果子数量。

**输出格式**

输出一个整数，表示将所有果子合并成一堆所需的最小体力。

**数据范围**

- `1 ≤ n ≤ 10000`
- `1 ≤ a[i] ≤ 20000`
- 输入保证最小体力值小于 `2^31`。

**输入样例**

```text
3
1 2 9
```

**输出样例**

```text
15
```

### 参考 Java 解法

```java
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        PriorityQueue<Long> minHeap = new PriorityQueue<>();

        for (int i = 0; i < n; i++) {
            minHeap.offer((long) scanner.nextInt());
        }

        long answer = 0;
        while (minHeap.size() > 1) {
            long merged = minHeap.poll() + minHeap.poll();
            answer += merged;
            minHeap.offer(merged);
        }

        System.out.println(answer);
    }
}
```

### 时空复杂度

- 时间复杂度：$O(n\log n)$。
- 空间复杂度：$O(n)$。
