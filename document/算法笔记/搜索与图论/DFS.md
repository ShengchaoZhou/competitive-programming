# DFS 与回溯

深度优先搜索沿一条选择路径展开，回溯在返回时恢复现场。先确定搜索状态、候选选择和结束条件，再利用合法性判断、排序去重或上下界剪枝减少无效分支。枚举类题目的开销通常由合法方案数量决定，不能把一次递归调用的开销当作整体复杂度。

| 算法类别 | 具体题型 | 题目与题集标记 |
| --- | --- | --- |
| [组合与排列](#组合与排列) | 排列与重复元素去重 | [AcWing 842. 排列数字](https://www.acwing.com/problem/content/844/) （AcWing基础课）<br>[LeetCode 46. 全排列](https://leetcode.cn/problems/permutations/) （Hot100/面试150/CodeTop200）<br>[LeetCode 47. 全排列 II](https://leetcode.cn/problems/permutations-ii/) （CodeTop200） |
|  | 子集与定长组合 | [LeetCode 78. 子集](https://leetcode.cn/problems/subsets/) （Hot100/CodeTop200）<br>[LeetCode 77. 组合](https://leetcode.cn/problems/combinations/) （面试150） |
|  | 允许重复选取的组合 | [LeetCode 39. 组合总和](https://leetcode.cn/problems/combination-sum/) （Hot100/面试150/CodeTop200） |
|  | 每个元素至多选一次的组合 | [LeetCode 40. 组合总和 II](https://leetcode.cn/problems/combination-sum-ii/) （CodeTop200） |
|  | 逐位置枚举候选 | [LeetCode 17. 电话号码的字母组合](https://leetcode.cn/problems/letter-combinations-of-a-phone-number/) （Hot100/面试150/CodeTop200） |
| [字符串划分与构造](#字符串划分与构造) | 字符串分段 | [LeetCode 131. 分割回文串](https://leetcode.cn/problems/palindrome-partitioning/) （Hot100）<br>[LeetCode 93. 复原 IP 地址](https://leetcode.cn/problems/restore-ip-addresses/) （CodeTop200） |
|  | 保持前缀合法的括号构造 | [LeetCode 22. 括号生成](https://leetcode.cn/problems/generate-parentheses/) （Hot100/面试150/CodeTop200） |
| [带约束的搜索](#带约束的搜索) | 棋盘放置 | [AcWing 843. n-皇后问题](https://www.acwing.com/problem/content/845/) （AcWing基础课）<br>[LeetCode 51. N 皇后](https://leetcode.cn/problems/n-queens/) （Hot100/CodeTop200）<br>[LeetCode 52. N 皇后 II](https://leetcode.cn/problems/n-queens-ii/) （面试150） |
|  | 网格中的路径匹配 | [LeetCode 79. 单词搜索](https://leetcode.cn/problems/word-search/) （Hot100/面试150/CodeTop200） |
|  | 枚举运算与括号结构 | [LeetCode 679. 24 点游戏](https://leetcode.cn/problems/24-game/) （CodeTop200） |

## 组合与排列

用路径保存已经选择的元素。排列关注先后顺序，通常记录哪些下标已用；组合与子集忽略选取顺序，通过递增的起始下标避免重复枚举。输出结果时复制当前路径，防止后续回溯修改已经保存的答案。

### 排列与重复元素去重

长度为 n 的排列有 n! 种，连同复制答案的时间通常为 O(n × n!)。有重复值时先排序；若相同值的前一个下标尚未在当前路径中使用，则跳过当前下标，使同一层只从一份相同值开始。每次递归返回后恢复路径和使用标记。

- [AcWing 842. 排列数字](https://www.acwing.com/problem/content/844/) （AcWing基础课）：按数值从小到大尝试尚未使用的数字，天然得到字典序排列。
- [LeetCode 46. 全排列](https://leetcode.cn/problems/permutations/) （Hot100/面试150/CodeTop200）：所有元素互异，用 used 标记保证每个位置选到不同下标。
- [LeetCode 47. 全排列 II](https://leetcode.cn/problems/permutations-ii/) （CodeTop200）：元素可以相同，排序后在同一层去重；不能简单禁止路径中出现相同值。

### 子集与定长组合

从 start 开始选择后续元素，下一层只考虑更大的下标。子集在每个搜索节点记录路径，定长组合在长度达到 k 时记录。若剩余元素数量不足以补齐 k 个，可以提前结束分支。

- [LeetCode 78. 子集](https://leetcode.cn/problems/subsets/) （Hot100/CodeTop200）：每个元素都有选或不选两种决策，共 2^n 个子集，复制结果需 O(n × 2^n)。
- [LeetCode 77. 组合](https://leetcode.cn/problems/combinations/) （面试150）：从 1 到 n 选择 k 个数，当前待补数量为 need 时，下一项最多选到 n - need + 1。

### 允许重复选取的组合

候选数为正时，剩余目标随选取单调下降。搜索状态为起始下标与剩余和；选中第 i 个候选后继续从 i 搜索，使该数可以多次使用。固定非降序选取顺序，避免把同一组合的不同排列重复计数。

- [LeetCode 39. 组合总和](https://leetcode.cn/problems/combination-sum/) （Hot100/面试150/CodeTop200）：将 target 减到 0 时记录答案；排序后遇到候选值大于剩余和即可停止当前层。

### 每个元素至多选一次的组合

选择下标 i 后从 i + 1 继续，保证同一元素至多使用一次。排序后仅跳过当前层中相同的候选值，下一层仍可选择另一个相同值；否则会遗漏由多个重复数构成的合法组合。

- [LeetCode 40. 组合总和 II](https://leetcode.cn/problems/combination-sum-ii/) （CodeTop200）：用 i > start 且 candidates[i] 等于前一个数判断同层重复，剩余和为 0 时收集当前组合。

### 逐位置枚举候选

当每个位置有自己的候选集合时，递归深度就是位置编号，每次从当前位置的集合选择一个元素。若各位置候选数量为 c_i，总组合数为它们的乘积；保存每个长度为 n 的结果还需要 O(n) 时间。

- [LeetCode 17. 电话号码的字母组合](https://leetcode.cn/problems/letter-combinations-of-a-phone-number/) （Hot100/面试150/CodeTop200）：先建立数字到字母集合的映射，每层处理一个数字；空输入按题意返回空结果，不输出空字符串。

## 字符串划分与构造

状态既可以表示已经处理到哪个位置，也可以表示已经构造的合法前缀。只把满足局部约束的候选扩展为下一层，避免生成完整字符串后再全部检查。

### 字符串分段

枚举下一段的结束位置，检查该段是否满足约束，再从下一位置继续。记录方案时复制所有已选片段；必须同时满足字符串已用完与段数要求，不能只看其中一个条件。

- [LeetCode 131. 分割回文串](https://leetcode.cn/problems/palindrome-partitioning/) （Hot100）：每一段都必须回文。可先用 O(n²) 的区间状态预处理回文性，再枚举切分点；输出数量在最坏情况下仍为指数级。
- [LeetCode 93. 复原 IP 地址](https://leetcode.cn/problems/restore-ip-addresses/) （CodeTop200）：只切成 4 段，每段长 1～3、数值在 0～255，且多位段不能以 0 开头；剩余字符数应介于剩余段数与其 3 倍之间。

### 保持前缀合法的括号构造

设已使用左括号数为 left、右括号数为 right，始终保持 right ≤ left ≤ n。只有 left < n 时才能添加左括号，只有 right < left 时才能添加右括号，长度到 2n 即得到合法结果。

- [LeetCode 22. 括号生成](https://leetcode.cn/problems/generate-parentheses/) （Hot100/面试150/CodeTop200）：把括号匹配约束直接融入搜索；方案数为第 n 个 Catalan 数，保存答案的时间为 O(n × C_n)。

## 带约束的搜索

将棋盘、路径或表达式中的选择视为搜索树的分支，在产生冲突时立即停止。撤销操作只恢复当前分支改变的状态，不能清除其他层仍在使用的信息。

### 棋盘放置

逐行放置一个皇后，用列、主对角线和副对角线标记冲突。位置 (r, c) 对应的两条对角线可分别用 r - c + n - 1 与 r + c 编号。利用列约束后，搜索节点数上界为 O(n!)；每个节点扫描列以及输出棋盘还需要额外时间。

- [AcWing 843. n-皇后问题](https://www.acwing.com/problem/content/845/) （AcWing基础课）：每行选择合法列，递归返回后同时恢复列与两类对角线标记。
- [LeetCode 51. N 皇后](https://leetcode.cn/problems/n-queens/) （Hot100/CodeTop200）：完整摆放后将每行皇后位置转换为棋盘字符串。
- [LeetCode 52. N 皇后 II](https://leetcode.cn/problems/n-queens-ii/) （面试150）：只统计合法摆放数量，不必保存每一张棋盘。

### 网格中的路径匹配

搜索状态为网格位置与待匹配字符下标，当前位置必须与目标字符相同。沿上下左右扩展，在当前路径中临时标记已访问格子，回溯后恢复，使其他起点和路径仍能使用该格子。

- [LeetCode 79. 单词搜索](https://leetcode.cn/problems/word-search/) （Hot100/面试150/CodeTop200）：同一条路径不能重复使用格子，可从每个匹配首字符的格子启动搜索；长度为 L 时，宽松时间上界为 O(mn × 4^L)，递归空间 O(L)。

### 枚举运算与括号结构

每次从当前数集中取两个数，用一种运算结果替代它们，再递归处理缩小后的数集。这种合并顺序覆盖不同括号结构；减法与除法必须考虑两个方向，除数接近 0 时跳过。

- [LeetCode 679. 24 点游戏](https://leetcode.cn/problems/24-game/) （CodeTop200）：剩下一个数时用误差阈值判断是否接近 24；加法与乘法可只枚举一种顺序，减少对称分支。
