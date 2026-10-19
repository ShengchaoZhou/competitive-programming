# 区间 DP

区间 DP 按区间长度从小到大推进，让大区间答案依赖更短的子区间。

| 算法类型 | 题型 | 题目与题集标记 |
| --- | --- | --- |
| [区间拆分](#区间拆分) | 相邻合并的最小代价 | [AcWing 282. 石子合并](https://www.acwing.com/problem/content/284/) （AcWing基础课） |
|  | 最长回文子序列 | [LeetCode 516. 最长回文子序列](https://leetcode.cn/problems/longest-palindromic-subsequence/) （CodeTop200） |

## 区间拆分

状态 f[l][r] 描述原序列的一段，通常枚举分割点或处理两端元素。单点与空区间的初值依题意确定。

### 相邻合并的最小代价

枚举最后一次合并的分界 k，转移为 f[l][r]=min(f[l][k]+f[k+1][r]+sum(l,r))。用前缀和 O(1) 求区间和，整体 O(n³) 时间、O(n²) 空间。

- [AcWing 282. 石子合并](https://www.acwing.com/problem/content/284/) （AcWing基础课）：最后一次合并将当前区间拆成左右两段，只有相邻石堆能合并。

### 最长回文子序列

两端字符相等时在内部最优长度上加二，不等时尝试舍弃任意一端。单点长度为 1，空区间为 0；这里允许跳过字符，和回文子串不同。

- [LeetCode 516. 最长回文子序列](https://leetcode.cn/problems/longest-palindromic-subsequence/) （CodeTop200）：按区间长度递增计算，时间与空间均为 O(n²)。
