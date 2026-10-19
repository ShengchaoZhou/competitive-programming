# KMP

KMP 用模式串自身的前后缀关系跳过重复比较，适合单个模式串的线性匹配。设文本长度 n、模式长度 m，前缀函数预处理 O(m)，匹配 O(n)，额外空间 O(m)。

| 算法类型 | 题型 | 题目与标注 |
| --- | --- | --- |
| [前缀函数与字符串匹配](#前缀函数与字符串匹配) | 查找模式串的出现位置 | [AcWing 831. KMP字符串](https://www.acwing.com/problem/content/833/) （AcWing基础课）<br>[LeetCode 28. 找出字符串中第一个匹配项的下标](https://leetcode.cn/problems/find-the-index-of-the-first-occurrence-in-a-string/) （面试150） |

## 前缀函数与字符串匹配

令 pi[i] 表示模式串前缀 P[0..i] 的最长相等真前缀与真后缀长度。失配时令 j = pi[j-1]，不断回退已匹配长度；文本指针不回退。

### 查找模式串的出现位置

扫描文本并维护已匹配长度 j。字符相等时 j++，达到 m 时记录起点 i-m+1；若继续找所有匹配，则将 j 回退到 pi[m-1]，保留重叠出现的可能。采用 0 下标时不要直接套用 1 下标 next 数组公式。

- [AcWing 831. KMP字符串](https://www.acwing.com/problem/content/833/) （AcWing基础课）：输出全部匹配起点，包括重叠出现的位置。
- [LeetCode 28. 找出字符串中第一个匹配项的下标](https://leetcode.cn/problems/find-the-index-of-the-first-occurrence-in-a-string/) （面试150）：找到第一次完整匹配后返回起点，始终未匹配则返回 -1；空模式串的返回值按题目或接口约定处理。
