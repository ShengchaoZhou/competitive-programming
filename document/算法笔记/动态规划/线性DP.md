# 线性 DP

线性 DP 用于处理状态依照序列、字符串或网格的线性顺序逐步转移的最优值问题。

## [AcWing 898. 数字三角形](https://www.acwing.com/problem/content/900/)

### 题目描述

给定一个共有 `n` 层的数字三角形。从顶端出发，每次只能走到下一层中与当前位置相邻的左下或右下节点，直到到达最底层。求所有可行路径中，途经数字之和的最大值。

**输入格式**

- 第一行包含整数 `n`，表示三角形的层数。
- 接下来 `n` 行，第 `i` 行包含 `i` 个整数，表示第 `i` 层的数字。

**输出格式**

输出一个整数，表示从顶端走到底层能够获得的最大路径和。

**数据范围**

- `1 ≤ n ≤ 500`
- 三角形中每个整数均在 `[-10000, 10000]` 内。

**输入样例**

```text
5
7
3 8
8 1 0
2 7 4 4
4 5 2 6 5
```

**输出样例**

```text
30
```

### 参考 Java 解法

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    private static final int NEGATIVE_INFINITY = -1_000_000_000;

    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        int n = scanner.nextInt();
        int[] best = new int[n + 1];
        int[] rowValues = new int[n + 1];
        Arrays.fill(best, NEGATIVE_INFINITY);
        best[0] = 0;

        for (int row = 1; row <= n; row++) {
            for (int column = 1; column <= row; column++) {
                rowValues[column] = scanner.nextInt();
            }
            for (int column = row; column >= 1; column--) {
                best[column] = Math.max(best[column - 1], best[column]) + rowValues[column];
            }
            best[0] = NEGATIVE_INFINITY;
        }

        int answer = NEGATIVE_INFINITY;
        for (int column = 1; column <= n; column++) {
            answer = Math.max(answer, best[column]);
        }
        System.out.println(answer);
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

- 时间复杂度：$O(n^2)$，每个三角形节点只处理一次。
- 空间复杂度：$O(n)$，使用一维数组保存上一层的最优结果。

## [AcWing 895. 最长上升子序列](https://www.acwing.com/problem/content/897/)

### 题目描述

给定一个长度为 `N` 的整数序列，可以在保持原有相对次序的前提下选取若干元素组成子序列。求数值严格递增的子序列所能达到的最大长度。

**输入格式**

- 第一行包含整数 `N`，表示序列长度。
- 第二行包含 `N` 个整数，表示完整序列。

**输出格式**

输出一个整数，表示最长严格上升子序列的长度。

**数据范围**

- `1 ≤ N ≤ 1000`
- 序列中每个整数均在 `[-10^9, 10^9]` 内。

**输入样例**

```text
7
3 1 2 1 8 5 6
```

**输出样例**

```text
4
```

### 参考 Java 解法

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        int n = scanner.nextInt();
        int[] numbers = new int[n];
        int[] bestEndingAt = new int[n];

        for (int i = 0; i < n; i++) {
            numbers[i] = scanner.nextInt();
        }

        int answer = 0;
        for (int i = 0; i < n; i++) {
            bestEndingAt[i] = 1;
            for (int j = 0; j < i; j++) {
                if (numbers[j] < numbers[i]) {
                    bestEndingAt[i] = Math.max(bestEndingAt[i], bestEndingAt[j] + 1);
                }
            }
            answer = Math.max(answer, bestEndingAt[i]);
        }

        System.out.println(answer);
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

- 时间复杂度：$O(N^2)$，每个元素都会检查它之前的所有元素。
- 空间复杂度：$O(N)$，用于保存原序列和以各位置结尾的最优长度。

## [AcWing 896. 最长上升子序列 II](https://www.acwing.com/problem/content/898/)

### 题目描述

给定一个长度为 `N` 的整数序列，从中按原有相对次序选取若干元素，求数值严格递增的子序列的最大长度。

**输入格式**

- 第一行包含整数 `N`，表示序列长度。
- 第二行包含 `N` 个整数，表示完整序列。

**输出格式**

输出一个整数，表示最长严格上升子序列的长度。

**数据范围**

- `1 ≤ N ≤ 100000`
- 序列中每个整数均在 `[-10^9, 10^9]` 内。

**输入样例**

```text
7
3 1 2 1 8 5 6
```

**输出样例**

```text
4
```

### 参考 Java 解法

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        int n = scanner.nextInt();
        int[] minimumTail = new int[n];
        int length = 0;

        for (int i = 0; i < n; i++) {
            int value = scanner.nextInt();
            int left = 0;
            int right = length;

            while (left < right) {
                int middle = left + (right - left) / 2;
                if (minimumTail[middle] >= value) {
                    right = middle;
                } else {
                    left = middle + 1;
                }
            }

            minimumTail[left] = value;
            if (left == length) {
                length++;
            }
        }

        System.out.println(length);
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

- 时间复杂度：$O(N \log N)$，每个元素都通过一次二分查找确定更新位置。
- 空间复杂度：$O(N)$，用于保存各长度上升子序列的最小结尾值。

## [AcWing 897. 最长公共子序列](https://www.acwing.com/problem/content/899/)

### 题目描述

给定长度分别为 `N` 和 `M` 的小写字母字符串 `A` 与 `B`。子序列可以通过删除若干字符得到，但剩余字符的相对次序不能改变。求同时为 `A` 和 `B` 子序列的字符串的最大长度。

**输入格式**

- 第一行包含两个整数 `N` 和 `M`。
- 第二行包含一个长度为 `N` 的字符串 `A`。
- 第三行包含一个长度为 `M` 的字符串 `B`。
- 两个字符串均只含小写英文字母。

**输出格式**

输出一个整数，表示 `A` 和 `B` 的最长公共子序列长度。

**数据范围**

- `1 ≤ N, M ≤ 1000`

**输入样例**

```text
4 5
acbd
abedc
```

**输出样例**

```text
3
```

### 参考 Java 解法

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        char[] first = scanner.next().toCharArray();
        char[] second = scanner.next().toCharArray();
        int[] best = new int[m + 1];

        for (int i = 1; i <= n; i++) {
            int diagonal = 0;
            for (int j = 1; j <= m; j++) {
                int oldAbove = best[j];
                best[j] = Math.max(best[j], best[j - 1]);
                if (first[i - 1] == second[j - 1]) {
                    best[j] = Math.max(best[j], diagonal + 1);
                }
                diagonal = oldAbove;
            }
        }

        System.out.println(best[m]);
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

        private String next() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            StringBuilder token = new StringBuilder();
            while (c > ' ') {
                token.append((char) c);
                c = read();
            }
            return token.toString();
        }

        private int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
```

### 时空复杂度

- 时间复杂度：$O(NM)$，需要枚举两个字符串的前缀组合。
- 空间复杂度：$O(M)$，使用一维数组滚动保存动态规划状态。

## [AcWing 902. 最短编辑距离](https://www.acwing.com/problem/content/904/)

### 题目描述

给定两个字符串 `A` 和 `B`，允许对 `A` 执行删除一个字符、插入一个字符或将一个字符替换为另一个字符这三种操作，每次操作的代价都为 `1`。求将 `A` 变成 `B` 所需的最少操作次数。

**输入格式**

- 第一行包含整数 `n`，表示字符串 `A` 的长度。
- 第二行包含长度为 `n` 的字符串 `A`。
- 第三行包含整数 `m`，表示字符串 `B` 的长度。
- 第四行包含长度为 `m` 的字符串 `B`。
- 两个字符串均只含大写英文字母。

**输出格式**

输出一个整数，表示将 `A` 转换为 `B` 的最少操作次数。

**数据范围**

- `1 ≤ n, m ≤ 1000`

**输入样例**

```text
10
AGTCTGACGC
11
AGTAAGTAGGC
```

**输出样例**

```text
4
```

### 参考 Java 解法

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        int n = scanner.nextInt();
        char[] source = scanner.next().toCharArray();
        int m = scanner.nextInt();
        char[] target = scanner.next().toCharArray();
        int[] distance = new int[m + 1];

        for (int j = 0; j <= m; j++) {
            distance[j] = j;
        }

        for (int i = 1; i <= n; i++) {
            int diagonal = distance[0];
            distance[0] = i;
            for (int j = 1; j <= m; j++) {
                int oldAbove = distance[j];
                int replaceCost = source[i - 1] == target[j - 1] ? 0 : 1;
                distance[j] = Math.min(
                        Math.min(oldAbove + 1, distance[j - 1] + 1),
                        diagonal + replaceCost
                );
                diagonal = oldAbove;
            }
        }

        System.out.println(distance[m]);
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

        private String next() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            StringBuilder token = new StringBuilder();
            while (c > ' ') {
                token.append((char) c);
                c = read();
            }
            return token.toString();
        }

        private int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
```

### 时空复杂度

- 时间复杂度：$O(nm)$，每对字符前缀都计算一次最小编辑距离。
- 空间复杂度：$O(m)$，使用一维滚动数组保存状态。

## [AcWing 899. 编辑距离](https://www.acwing.com/problem/content/901/)

### 题目描述

先给定 `n` 个字符串，再进行 `m` 次询问。每次询问包含一个目标字符串和操作次数上限，需要统计给定的 `n` 个字符串中，有多少个可以通过不超过该上限次的单字符插入、删除或替换操作变成目标字符串。

**输入格式**

- 第一行包含两个整数 `n` 和 `m`，分别表示已知字符串数量和询问数量。
- 接下来 `n` 行，每行包含一个已知字符串。
- 再接下来 `m` 行，每行包含一个目标字符串和一个整数，该整数表示允许的最大操作次数。
- 所有字符串均只含小写英文字母，且长度不超过 `10`。

**输出格式**

对每次询问输出一行一个整数，表示可在限定操作次数内变成目标字符串的已知字符串数量。

**数据范围**

- `1 ≤ n, m ≤ 1000`
- 每个字符串的长度不超过 `10`。

**输入样例**

```text
3 2
abc
acd
bcd
ab 1
acbd 2
```

**输出样例**

```text
1
3
```

### 参考 Java 解法

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        int wordCount = scanner.nextInt();
        int queryCount = scanner.nextInt();
        String[] words = new String[wordCount];

        for (int i = 0; i < wordCount; i++) {
            words[i] = scanner.next();
        }

        StringBuilder answer = new StringBuilder();
        for (int query = 0; query < queryCount; query++) {
            String target = scanner.next();
            int limit = scanner.nextInt();
            int count = 0;

            for (String word : words) {
                if (Math.abs(word.length() - target.length()) <= limit
                        && editDistance(word, target) <= limit) {
                    count++;
                }
            }
            answer.append(count).append('\n');
        }

        System.out.print(answer);
    }

    private static int editDistance(String source, String target) {
        int[] distance = new int[target.length() + 1];
        for (int j = 0; j <= target.length(); j++) {
            distance[j] = j;
        }

        for (int i = 1; i <= source.length(); i++) {
            int diagonal = distance[0];
            distance[0] = i;
            for (int j = 1; j <= target.length(); j++) {
                int oldAbove = distance[j];
                int replaceCost = source.charAt(i - 1) == target.charAt(j - 1) ? 0 : 1;
                distance[j] = Math.min(
                        Math.min(oldAbove + 1, distance[j - 1] + 1),
                        diagonal + replaceCost
                );
                diagonal = oldAbove;
            }
        }
        return distance[target.length()];
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

        private String next() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            StringBuilder token = new StringBuilder();
            while (c > ' ') {
                token.append((char) c);
                c = read();
            }
            return token.toString();
        }

        private int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
```

### 时空复杂度

设字符串最大长度为 $L$，题目中 $L \le 10$。

- 时间复杂度：每次询问为 $O(nL^2)$，全部 `m` 次询问为 $O(mnL^2)$。
- 空间复杂度：$O(nL + L)$，其中 $O(nL)$ 用于保存已知字符串，计算单次编辑距离使用 $O(L)$ 额外空间。
