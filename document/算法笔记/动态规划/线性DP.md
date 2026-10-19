# 线性 DP

线性 DP 沿序列、字符串或网格推进状态，用已经求出的前缀结果计算后续答案。先区分连续子数组与可跳过元素的子序列。

| 算法类型 | 题型 | 题目与题集标记 |
| --- | --- | --- |
| [一维序列状态](#一维序列状态) | 递推与相邻状态 | [LeetCode 70. 爬楼梯](https://leetcode.cn/problems/climbing-stairs/) （Hot100/面试150/CodeTop200）<br>[LeetCode 剑指 Offer 10- I. 斐波那契数列](https://leetcode.cn/problems/fei-bo-na-qi-shu-lie-lcof/) （CodeTop200）<br>[LeetCode 剑指 Offer 10- II. 青蛙跳台阶问题](https://leetcode.cn/problems/qing-wa-tiao-tai-jie-wen-ti-lcof/) （CodeTop200）<br>[LeetCode 118. 杨辉三角](https://leetcode.cn/problems/pascals-triangle/) （Hot100）<br>[LeetCode 264. 丑数 II](https://leetcode.cn/problems/ugly-number-ii/) （CodeTop200） |
|  | 非相邻选择 | [LeetCode 198. 打家劫舍](https://leetcode.cn/problems/house-robber/) （Hot100/面试150/CodeTop200）<br>[LeetCode 213. 打家劫舍 II](https://leetcode.cn/problems/house-robber-ii/) （CodeTop200） |
|  | 最大连续子数组 | [LeetCode 53. 最大子数组和](https://leetcode.cn/problems/maximum-subarray/) （Hot100/面试150/CodeTop200）<br>[LeetCode 剑指 Offer 42. 连续子数组的最大和](https://leetcode.cn/problems/lian-xu-zi-shu-zu-de-zui-da-he-lcof/) （CodeTop200）<br>[LeetCode 918. 环形子数组的最大和](https://leetcode.cn/problems/maximum-sum-circular-subarray/) （面试150） |
|  | 最大连续乘积 | [LeetCode 152. 乘积最大子数组](https://leetcode.cn/problems/maximum-product-subarray/) （Hot100/CodeTop200） |
| [子序列与字符串匹配](#子序列与字符串匹配) | 最长递增子序列 | [AcWing 895. 最长上升子序列](https://www.acwing.com/problem/content/897/) （AcWing基础课）<br>[AcWing 896. 最长上升子序列 II](https://www.acwing.com/problem/content/898/) （AcWing基础课）<br>[LeetCode 300. 最长递增子序列](https://leetcode.cn/problems/longest-increasing-subsequence/) （Hot100/面试150/CodeTop200） |
|  | 最长公共子序列 | [AcWing 897. 最长公共子序列](https://www.acwing.com/problem/content/899/) （AcWing基础课）<br>[LeetCode 1143. 最长公共子序列](https://leetcode.cn/problems/longest-common-subsequence/) （Hot100/CodeTop200） |
|  | 最长公共连续片段 | [LeetCode 718. 最长重复子数组](https://leetcode.cn/problems/maximum-length-of-repeated-subarray/) （CodeTop200） |
|  | 编辑距离 | [AcWing 902. 最短编辑距离](https://www.acwing.com/problem/content/904/) （AcWing基础课）<br>[AcWing 899. 编辑距离](https://www.acwing.com/problem/content/901/) （AcWing基础课）<br>[LeetCode 72. 编辑距离](https://leetcode.cn/problems/edit-distance/) （Hot100/面试150/CodeTop200） |
|  | 字符串交错与解码 | [LeetCode 97. 交错字符串](https://leetcode.cn/problems/interleaving-string/) （面试150/CodeTop200）<br>[LeetCode 91. 解码方法](https://leetcode.cn/problems/decode-ways/) （CodeTop200） |
|  | 带通配符的匹配 | [LeetCode 10. 正则表达式匹配](https://leetcode.cn/problems/regular-expression-matching/) （CodeTop200）<br>[LeetCode 44. 通配符匹配](https://leetcode.cn/problems/wildcard-matching/) （CodeTop200） |
| [网格状态](#网格状态) | 网格与三角形路径 | [AcWing 898. 数字三角形](https://www.acwing.com/problem/content/900/) （AcWing基础课）<br>[LeetCode 120. 三角形最小路径和](https://leetcode.cn/problems/triangle/) （面试150/CodeTop200）<br>[LeetCode 62. 不同路径](https://leetcode.cn/problems/unique-paths/) （Hot100/CodeTop200）<br>[LeetCode 63. 不同路径 II](https://leetcode.cn/problems/unique-paths-ii/) （面试150/CodeTop200）<br>[LeetCode 64. 最小路径和](https://leetcode.cn/problems/minimum-path-sum/) （Hot100/面试150/CodeTop200） |
|  | 以当前位置为角的最大正方形 | [LeetCode 221. 最大正方形](https://leetcode.cn/problems/maximal-square/) （面试150/CodeTop200） |

## 一维序列状态

状态通常表示一个前缀的答案，或以当前位置结尾的答案。这两种语义不能混用；初始化必须与是否允许空选择一致。

### 递推与相邻状态

若下一状态仅依赖前面固定数量的状态，可以滚动保存。计数题要按题目要求取模；看似相同的递推也可能有不同的空状态初值。

- [LeetCode 70. 爬楼梯](https://leetcode.cn/problems/climbing-stairs/) （Hot100/面试150/CodeTop200）：到第 n 阶只能从第 n-1 阶或第 n-2 阶到达，定义空台阶为一种起点。
- [LeetCode 剑指 Offer 10- I. 斐波那契数列](https://leetcode.cn/problems/fei-bo-na-qi-shu-lie-lcof/) （CodeTop200）：用两个滚动变量递推斐波那契数，按原题模数处理。
- [LeetCode 剑指 Offer 10- II. 青蛙跳台阶问题](https://leetcode.cn/problems/qing-wa-tiao-tai-jie-wen-ti-lcof/) （CodeTop200）：将跳法数写成相邻两项之和，空台阶对应一种选择。
- [LeetCode 118. 杨辉三角](https://leetcode.cn/problems/pascals-triangle/) （Hot100）：每个内部位置由上一行相邻两项相加得到，左右边界始终为 1。
- [LeetCode 264. 丑数 II](https://leetcode.cn/problems/ugly-number-ii/) （CodeTop200）：分别维护乘 2、3、5 的候选指针；当多个候选相同时，所有对应指针都要推进，避免重复。

### 非相邻选择

前 i 项的最优值来自“不选当前项”或“选当前项并接上前 i-2 项”。环形序列需拆成排除首项与排除末项的两个线性子问题。

- [LeetCode 198. 打家劫舍](https://leetcode.cn/problems/house-robber/) （Hot100/面试150/CodeTop200）：比较不抢当前房屋与抢当前房屋两种转移，可用 O(1) 额外空间。
- [LeetCode 213. 打家劫舍 II](https://leetcode.cn/problems/house-robber-ii/) （CodeTop200）：首尾不能同时选；只有一个房屋时单独处理。

### 最大连续子数组

以当前位置结尾的最大和，只能从当前元素重新开始，或接在上一位置的最大和后面。答案是所有结尾状态的最大值，必须保留全负数组中的最大元素。

- [LeetCode 53. 最大子数组和](https://leetcode.cn/problems/maximum-subarray/) （Hot100/面试150/CodeTop200）：使用 Kadane 转移 f=max(x,f+x)，同步维护全局最大值。
- [LeetCode 剑指 Offer 42. 连续子数组的最大和](https://leetcode.cn/problems/lian-xu-zi-shu-zu-de-zui-da-he-lcof/) （CodeTop200）：明确子数组非空，避免用 0 作为全负数组的错误答案。
- [LeetCode 918. 环形子数组的最大和](https://leetcode.cn/problems/maximum-sum-circular-subarray/) （面试150）：答案来自普通最大子数组，或总和减最小子数组；全负数组时不能采用空的环形补集。

### 最大连续乘积

负数会交换最大与最小的作用，因此同时保存以当前位置结尾的最大乘积和最小乘积。两者必须基于同一轮旧值更新，零会自然切断此前的乘积链。

- [LeetCode 152. 乘积最大子数组](https://leetcode.cn/problems/maximum-product-subarray/) （Hot100/CodeTop200）：同时比较 x、旧最大值*x、旧最小值*x，再更新全局最大值。

## 子序列与字符串匹配

子序列允许跳过元素，连续子数组或子串不允许。双序列问题通常用 f[i][j] 表示两个前缀的关系，空前缀构成边界。

### 最长递增子序列

朴素状态 f[i] 表示以第 i 项结尾的最长长度，枚举较小的前项转移，复杂度 O(n²)。优化时 tails[len] 保存该长度的最小末尾值，用第一个 >=x 的位置替换，得到严格递增子序列长度，复杂度 O(n log n)。

- [AcWing 895. 最长上升子序列](https://www.acwing.com/problem/content/897/) （AcWing基础课）：枚举所有更早且更小的元素，求以当前项结尾的最优长度。
- [AcWing 896. 最长上升子序列 II](https://www.acwing.com/problem/content/898/) （AcWing基础课）：使用 tails 与二分；tails 是最小末尾值表，不一定就是一条实际子序列。
- [LeetCode 300. 最长递增子序列](https://leetcode.cn/problems/longest-increasing-subsequence/) （Hot100/面试150/CodeTop200）：区分严格递增与非递减，二分边界随定义改变。

### 最长公共子序列

末尾字符相等时，接在 f[i-1][j-1] 后面；不等时，舍弃一个末尾，取 f[i-1][j] 与 f[i][j-1] 的最大值。时间 O(nm)，可滚动压缩一维，但需保存左上角旧值。

- [AcWing 897. 最长公共子序列](https://www.acwing.com/problem/content/899/) （AcWing基础课）：用两个字符串前缀作为状态，允许从任一字符串跳过字符。
- [LeetCode 1143. 最长公共子序列](https://leetcode.cn/problems/longest-common-subsequence/) （Hot100/CodeTop200）：相等时加一，不等时选择去掉某一侧末尾的最优值。

### 最长公共连续片段

f[i][j] 表示以两个当前位置同时结尾的相同连续片段长度。相等时接上左上角，不等时直接清零，答案取所有状态最大值。

- [LeetCode 718. 最长重复子数组](https://leetcode.cn/problems/maximum-length-of-repeated-subarray/) （CodeTop200）：这里要求连续，不能使用最长公共子序列的跳过字符转移。

### 编辑距离

前缀间的最小编辑代价来自插入、删除与替换；末尾相等时可直接继承左上角状态。空串到长度为 j 的串需 j 次插入，时间 O(nm)。

- [AcWing 902. 最短编辑距离](https://www.acwing.com/problem/content/904/) （AcWing基础课）：对三个编辑操作分别计算候选，取最小值。
- [AcWing 899. 编辑距离](https://www.acwing.com/problem/content/901/) （AcWing基础课）：将多次查询转成与字典字符串的编辑距离比较；长度差已超过上限时可提前排除。
- [LeetCode 72. 编辑距离](https://leetcode.cn/problems/edit-distance/) （Hot100/面试150/CodeTop200）：先初始化空前缀行列，再处理每对末尾字符。

### 字符串交错与解码

交错问题以已使用两个源串的长度为状态，目标位置由二者之和决定。解码问题以已处理前缀长度为状态，分别判断最后一个字符和最后两个字符是否合法。

- [LeetCode 97. 交错字符串](https://leetcode.cn/problems/interleaving-string/) （面试150/CodeTop200）：先检查总长度，再尝试由 s1 或 s2 的末尾接到目标串对应位置。
- [LeetCode 91. 解码方法](https://leetcode.cn/problems/decode-ways/) （CodeTop200）：单字符只允许 1～9，双字符只允许 10～26，不能把 0 单独解码。

### 带通配符的匹配

状态记录文本前缀与模式前缀能否完全匹配。正则中的 * 重复前一个元素；通配符中的 * 匹配任意长度字符串，两者的状态含义与转移必须分开。

- [LeetCode 10. 正则表达式匹配](https://leetcode.cn/problems/regular-expression-matching/) （CodeTop200）：x* 可匹配零个 x 或消费一个匹配字符后继续保留该模式；点号仅匹配一个字符。
- [LeetCode 44. 通配符匹配](https://leetcode.cn/problems/wildcard-matching/) （CodeTop200）：* 可跳过自己或消费一个文本字符，? 仅消费一个字符；空文本仅能匹配全为 * 的模式前缀。

## 网格状态

只允许沿固定方向移动时，网格天然形成无环的依赖顺序。滚动数组更新后，一部分值是当前行，另一部分仍是上一行，需要分清。

### 网格与三角形路径

计数时累加来自合法前驱的方案数，最值时在前驱中取最优值再加入当前格子。障碍格必须设为不可达，负数权值则不能把缺失边界当作 0 候选。

- [AcWing 898. 数字三角形](https://www.acwing.com/problem/content/900/) （AcWing基础课）：数字三角形求最大路径和，可自底向上合并相邻两个子状态。
- [LeetCode 120. 三角形最小路径和](https://leetcode.cn/problems/triangle/) （面试150/CodeTop200）：与最大和版本相同的依赖关系，转移改为取最小值。
- [LeetCode 62. 不同路径](https://leetcode.cn/problems/unique-paths/) （Hot100/CodeTop200）：到达每格的路径来自上方与左方，边界只有一个合法前驱。
- [LeetCode 63. 不同路径 II](https://leetcode.cn/problems/unique-paths-ii/) （面试150/CodeTop200）：遇到障碍立即清零当前路径数，起点被堵住时答案为 0。
- [LeetCode 64. 最小路径和](https://leetcode.cn/problems/minimum-path-sum/) （Hot100/面试150/CodeTop200）：从左方和上方的可达最小代价中选择，再加上当前格子。

### 以当前位置为角的最大正方形

令 f[i][j] 为以当前位置作为右下角的全 1 正方形边长。当前格为 1 时，取上、左、左上三者最小值再加一；为 0 时清零，最终输出最大边长的平方。

- [LeetCode 221. 最大正方形](https://leetcode.cn/problems/maximal-square/) （面试150/CodeTop200）：三个方向共同限制正方形扩展，不能只看上方和左方。
