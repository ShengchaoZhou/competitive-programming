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
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder answer = new StringBuilder();
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
                if (!first) answer.append(' ');
                answer.append(i - n + 1);
                first = false;
                matched = prefix[matched - 1];
            }
        }

        System.out.println(answer);
    }
}
```

### 时空复杂度

- 时间复杂度：$O(N+M)$，模式串预处理和文本串匹配都只进行线性扫描。
- 空间复杂度：$O(N+M)$，用于保存两个输入字符串和模式串的前缀数组。
