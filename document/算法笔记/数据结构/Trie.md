# Trie

Trie 用于按公共前缀组织字符串或定长位序列，从而高效完成精确查询和逐位最优匹配。

## [AcWing 835. Trie字符串统计](https://www.acwing.com/problem/content/837/)

### 题目描述

维护一个允许重复字符串的集合，需要处理以下两种操作：

- `I x`：向集合中插入一次字符串 $x$。
- `Q x`：查询字符串 $x$ 当前在集合中出现了多少次。

所有字符串都只包含小写英文字母。

**输入格式**

第一行包含一个整数 $N$，表示操作数量。

接下来 $N$ 行，每行包含一个操作符和一个字符串，格式为 `I x` 或 `Q x`。

**输出格式**

对于每个 `Q x` 操作输出一行一个整数，表示 $x$ 在集合中的出现次数。

**数据范围**

- $1 \le N \le 2 \times 10^4$
- 所有操作中输入的字符串总长度不超过 $10^5$
- 字符串只包含小写英文字母

**输入样例**

```text
5
I abc
Q abc
Q ab
I ab
Q ab
```

**输出样例**

```text
1
0
1
```

### 参考 Java 解法

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final int ALPHABET_SIZE = 26;
    private static final int MAX_TOTAL_LENGTH = 100000;

    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        int operationCount = scanner.nextInt();
        int[] children = new int[(MAX_TOTAL_LENGTH + 1) * ALPHABET_SIZE];
        int[] terminalCount = new int[MAX_TOTAL_LENGTH + 1];
        int nodeCount = 0;
        StringBuilder answer = new StringBuilder();

        for (int i = 0; i < operationCount; i++) {
            String operation = scanner.next();
            String word = scanner.next();

            if (operation.charAt(0) == 'I') {
                int node = 0;
                for (int j = 0; j < word.length(); j++) {
                    int letter = word.charAt(j) - 'a';
                    int edge = node * ALPHABET_SIZE + letter;
                    if (children[edge] == 0) {
                        children[edge] = ++nodeCount;
                    }
                    node = children[edge];
                }
                terminalCount[node]++;
            } else {
                int node = 0;
                boolean exists = true;
                for (int j = 0; j < word.length(); j++) {
                    int letter = word.charAt(j) - 'a';
                    int nextNode = children[node * ALPHABET_SIZE + letter];
                    if (nextNode == 0) {
                        exists = false;
                        break;
                    }
                    node = nextNode;
                }
                answer.append(exists ? terminalCount[node] : 0).append('\n');
            }
        }

        System.out.print(answer);
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

- 时间复杂度：$O(L)$，其中 $L$ 为所有操作中字符串长度之和，每个字符只沿 Trie 处理一次。
- 空间复杂度：$O(L \cdot |\Sigma|)$，这里字符集大小 $|\Sigma|=26$；使用定长转移数组保存至多 $L+1$ 个节点。

## [AcWing 143. 最大异或对](https://www.acwing.com/problem/content/145/)

### 题目描述

给定 $N$ 个非负整数 $A_1,A_2,\ldots,A_N$，从中选出两个数进行按位异或运算，求可能得到的最大结果。

**输入格式**

第一行包含一个整数 $N$。

第二行包含 $N$ 个整数 $A_1,A_2,\ldots,A_N$。

**输出格式**

输出一个整数，表示任意两个给定整数异或后能够得到的最大值。

**数据范围**

- $1 \le N \le 10^5$
- $0 \le A_i < 2^{31}$

**输入样例**

```text
3
1 2 3
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
    private static final int BIT_COUNT = 31;

    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        int n = scanner.nextInt();
        int maxNodeCount = n * BIT_COUNT + 1;
        int[] children = new int[maxNodeCount * 2];
        int nodeCount = 0;
        int answer = 0;

        for (int i = 0; i < n; i++) {
            int value = scanner.nextInt();

            int node = 0;
            for (int bit = 30; bit >= 0; bit--) {
                int currentBit = value >>> bit & 1;
                int edge = node * 2 + currentBit;
                if (children[edge] == 0) {
                    children[edge] = ++nodeCount;
                }
                node = children[edge];
            }

            node = 0;
            int xorValue = 0;
            for (int bit = 30; bit >= 0; bit--) {
                int currentBit = value >>> bit & 1;
                int oppositeBit = currentBit ^ 1;
                int oppositeNode = children[node * 2 + oppositeBit];

                if (oppositeNode != 0) {
                    xorValue |= 1 << bit;
                    node = oppositeNode;
                } else {
                    node = children[node * 2 + currentBit];
                }
            }
            answer = Math.max(answer, xorValue);
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

- 时间复杂度：$O(31N)$，每个整数都按 $31$ 个有效二进制位插入和查询，可视为 $O(N)$。
- 空间复杂度：$O(31N)$，二进制 Trie 最多为每个整数新建 $31$ 个节点。
