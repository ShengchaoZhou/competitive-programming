# Trie

Trie 按前缀共享路径，适合字符串集合查询和二进制位序列上的逐位选择。节点表示前缀，路径存在与完整单词存在不同，完整单词需要额外的结束标记或计数。

| 算法类型 | 题型 | 题目与标注 |
| --- | --- | --- |
| [字符串字典树](#字符串字典树) | 精确查询与前缀查询 | [AcWing 835. Trie字符串统计](https://www.acwing.com/problem/content/837/) （AcWing基础课）<br>[LeetCode 208. 实现 Trie (前缀树)](https://leetcode.cn/problems/implement-trie-prefix-tree/) （Hot100/面试150/CodeTop200） |
|  | 含通配符的匹配 | [LeetCode 211. 添加与搜索单词 - 数据结构设计](https://leetcode.cn/problems/design-add-and-search-words-data-structure/) （面试150） |
|  | 字典树与网格回溯 | [LeetCode 212. 单词搜索 II](https://leetcode.cn/problems/word-search-ii/) （面试150） |
| [二进制 Trie](#二进制-trie) | 最大异或配对 | [AcWing 143. 最大异或对](https://www.acwing.com/problem/content/145/) （AcWing基础课） |
| [隐式数字字典树](#隐式数字字典树) | 按前缀跳过整棵子树 | [LeetCode 440. 字典序的第K小数字](https://leetcode.cn/problems/k-th-smallest-in-lexicographical-order/) （CodeTop200） |

## 字符串字典树

每条边表示一个字符，根到节点的路径对应一个前缀。若总字符数为 L，最多新建 L 个节点；使用固定大小转移数组时空间为 O(L|Σ|)，字符集固定时可视为 O(L)。

### 精确查询与前缀查询

插入、查找长度为 m 的单词均为 O(m)。插入结束后更新末尾节点的计数，精确查询读取末尾计数；前缀查询只要求路径完整。空节点编号与根节点的表示应保持一致。

- [AcWing 835. Trie字符串统计](https://www.acwing.com/problem/content/837/) （AcWing基础课）：统计字符串出现次数，不能只判断对应路径是否存在。
- [LeetCode 208. 实现 Trie (前缀树)](https://leetcode.cn/problems/implement-trie-prefix-tree/) （Hot100/面试150/CodeTop200）：search 检查终止标记，startsWith 只检查前缀路径。

### 含通配符的匹配

普通字符只沿对应分支前进，点号枚举当前节点的所有子节点并递归匹配后缀。终点仍须检查完整单词标记；时间取决于实际访问的分支数，多个通配符时最坏呈指数增长。

- [LeetCode 211. 添加与搜索单词 - 数据结构设计](https://leetcode.cn/problems/design-add-and-search-words-data-structure/) （面试150）：点号只匹配一个任意字母，不能当作任意长度的字符串。

### 字典树与网格回溯

先把全部单词建立成 Trie，再从每个格子出发深度优先搜索；当前路径不是任何单词的前缀时立即剪枝。标记并回溯恢复格子使用状态，找到单词后去重。总字符数 S、最长单词 L、网格 m×n 时，保守时间上界 O(S+mn·4ᴸ)，Trie 空间 O(S)。

- [LeetCode 212. 单词搜索 II](https://leetcode.cn/problems/word-search-ii/) （面试150）：一个路径中每个格子只能使用一次，通过共享前缀同时搜索整个词表。

## 二进制 Trie

把非负整数按固定有效位宽从高到低插入，子节点只有 0 和 1。高位的收益严格大于所有低位之和，因此可以逐位贪心选择。

### 最大异或配对

查询 x 时优先选择与当前位相反的分支，没有该分支再走同位分支。若有效位数为 B，n 个数的插入与查询共 O(nB)，空间 O(nB)；AcWing 原题数值小于 2³¹，处理第 30 位到第 0 位。

- [AcWing 143. 最大异或对](https://www.acwing.com/problem/content/145/) （AcWing基础课）：求一组非负整数中两个数的最大异或值。

## 隐式数字字典树

把十进制整数当作字符串，字典序等价于从前缀 1 到 9 开始遍历数字 Trie。无需真正建树，可以直接计算某个前缀下面的节点数量。

### 按前缀跳过整棵子树

统计前缀 p 下数字数量时逐层扩展区间 [p,p+1)，累加它与 [1,n+1) 的交集长度。若该子树大小不超过剩余步数，移动到下一前缀；否则进入 p×10 并扣除当前根。时间 O(log² n)，额外空间 O(1)，边界计算使用 long。

- [LeetCode 440. 字典序的第K小数字](https://leetcode.cn/problems/k-th-smallest-in-lexicographical-order/) （CodeTop200）：先把第 k 个转换为从根前缀 1 出发的 k-1 步，再决定横向跳过或下探。
