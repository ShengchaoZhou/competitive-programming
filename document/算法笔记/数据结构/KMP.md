# KMP

KMP 用于在线性时间内找出一个模式串在文本串中的全部匹配位置。

## [AcWing 831. KMP字符串](https://www.acwing.com/problem/content/833/)

### 题目描述

给定文本串 $S$ 和模式串 $P$，两个字符串都只包含大小写英文字母和阿拉伯数字。模式串可能在文本串中出现多次，请输出它每次出现时的起始下标，文本串下标从 $0$ 开始计算，重叠的匹配也需要统计。

**输入格式**

第一行包含整数 $N$，表示模式串 $P$ 的长度。

第二行包含模式串 $P$。

第三行包含整数 $M$，表示文本串 $S$ 的长度。

第四行包含文本串 $S$。

**输出格式**

输出一行，按从小到大的顺序输出 $P$ 在 $S$ 中所有出现位置的起始下标，相邻下标之间用空格分隔。

**数据范围**

- $1 \le N \le 10^5$
- $1 \le M \le 10^6$
- $P$ 和 $S$ 只包含大小写英文字母及阿拉伯数字

**输入样例**

```text
3
aba
5
ababa
```

**输出样例**

```text
0 2
```

### 参考 Java 解法

```java
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        FastOutput output = new FastOutput();
        int n = scanner.nextInt();
        String pattern = scanner.next();
        int m = scanner.nextInt();
        String text = scanner.next();

        int[] prefix = new int[n];
        for (int i = 1, matched = 0; i < n; i++) {
            while (matched > 0 && pattern.charAt(i) != pattern.charAt(matched)) {
                matched = prefix[matched - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(matched)) {
                matched++;
            }
            prefix[i] = matched;
        }

        boolean first = true;
        for (int i = 0, matched = 0; i < m; i++) {
            while (matched > 0 && text.charAt(i) != pattern.charAt(matched)) {
                matched = prefix[matched - 1];
            }
            if (text.charAt(i) == pattern.charAt(matched)) {
                matched++;
            }

            if (matched == n) {
                if (!first) output.writeSpace();
                output.writeInt(i - n + 1);
                first = false;
                matched = prefix[matched - 1];
            }
        }

        output.writeNewLine();
        output.flush();
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

    private static class FastOutput {
        private final BufferedOutputStream output = new BufferedOutputStream(System.out);
        private final byte[] digits = new byte[12];

        private void writeInt(int value) throws IOException {
            if (value == 0) {
                output.write('0');
                return;
            }

            int size = 0;
            while (value > 0) {
                digits[size++] = (byte) ('0' + value % 10);
                value /= 10;
            }
            while (size > 0) {
                output.write(digits[--size]);
            }
        }

        private void writeSpace() throws IOException {
            output.write(' ');
        }

        private void writeNewLine() throws IOException {
            output.write('\n');
        }

        private void flush() throws IOException {
            output.flush();
        }
    }
}
```

### 时空复杂度

- 时间复杂度：$O(N+M)$，模式串预处理和文本串匹配都只进行线性扫描。
- 空间复杂度：$O(N+M)$，用于保存两个输入字符串和模式串的前缀数组。
