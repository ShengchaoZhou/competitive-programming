# 计数类 DP

计数类 DP 统计满足条件的构造方式。状态与枚举顺序共同决定哪些方案被认为相同，必须避免漏计和重复计数。

| 算法类型 | 题型 | 题目与题集标记 |
| --- | --- | --- |
| [组合结构计数](#组合结构计数) | 整数划分 | [AcWing 900. 整数划分](https://www.acwing.com/problem/content/902/) （AcWing基础课） |
|  | 二叉搜索树结构计数 | [LeetCode 96. 不同的二叉搜索树](https://leetcode.cn/problems/unique-binary-search-trees/) （CodeTop200） |
| [路径与最优解计数](#路径与最优解计数) | 环上的定步数路径 | [CodeTop 补充题2. 圆环回原点问题](https://mp.weixin.qq.com/s/NZPaFsFrTybO3K3s7p7EVg) （CodeTop200） |
|  | 最长递增子序列的条数 | [LeetCode 673. 最长递增子序列的个数](https://leetcode.cn/problems/number-of-longest-increasing-subsequence/) （CodeTop200） |

## 组合结构计数

把一个合法对象拆成更小的合法对象，确认拆分方式是否唯一，再对互斥情况求和、对独立组合求积。

### 整数划分

将可选正整数看作可重复使用的物品，按加数从小到大、目标和从小到大累加，f[0]=1。这样每种数量组合只计一次，复杂度 O(n²)，按题目模数取余。

- [AcWing 900. 整数划分](https://www.acwing.com/problem/content/902/) （AcWing基础课）：只区分各加数的数量，不把不同排列视为新划分。

### 二叉搜索树结构计数

枚举根节点后，左子树与右子树的节点数确定。设 f[0]=1，则 f[n]=sum(f[left]*f[n-1-left])；空子树必须提供一种组合方式。

- [LeetCode 96. 不同的二叉搜索树](https://leetcode.cn/problems/unique-binary-search-trees/) （CodeTop200）：对每个根的左右子树数量求乘积，再汇总所有根的情况。

## 路径与最优解计数

有些计数依赖位置，有些必须先判断最优值。除了记录方案数量，还可能需要记录长度、步数等状态。

### 环上的定步数路径

设环有 m 个位置，f[t][j] 表示走 t 步到位置 j 的方法数。若每步可向相邻两侧移动，则来自 (j-1+m)%m 与 (j+1)%m；初始原点为 1，其他为 0，答案取指定步数时的原点状态。

- [CodeTop 补充题2. 圆环回原点问题](https://mp.weixin.qq.com/s/NZPaFsFrTybO3K3s7p7EVg) （CodeTop200）：按步数与位置递推返回原点的路径数；环大小、步数和取模要求以原题为准。

### 最长递增子序列的条数

同时维护以当前项结尾的最长长度 len[i] 与对应数量 cnt[i]，初始均为 1，表示仅包含当前元素的子序列。只从 j<i 且 a[j]<a[i] 的位置转移；得到更长候选时替换长度和计数，长度相同时累加数量。最后汇总全局最长长度对应的结尾。

- [LeetCode 673. 最长递增子序列的个数](https://leetcode.cn/problems/number-of-longest-increasing-subsequence/) （CodeTop200）：只累加达到最优长度的转移，不能把所有较短递增子序列也算进去。
