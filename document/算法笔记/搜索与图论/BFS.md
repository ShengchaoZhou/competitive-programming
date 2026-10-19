# BFS

广度优先搜索按与起点的距离逐层扩展，适用于每次操作代价相同的最短步数问题。状态在入队时标记，保证第一次发现就是最短距离，并避免同一状态被多次加入队列。

| 算法类别 | 具体题型 | 题目与题集标记 |
| --- | --- | --- |
| [最短步数](#最短步数) | 网格中的最短路径 | [AcWing 844. 走迷宫](https://www.acwing.com/problem/content/846/) （AcWing基础课） |
|  | 局面编码与隐式图 | [AcWing 845. 八数码](https://www.acwing.com/problem/content/847/) （AcWing基础课）<br>[LeetCode 909. 蛇梯棋](https://leetcode.cn/problems/snakes-and-ladders/) （面试150） |
|  | 单字符变化的最短序列 | [LeetCode 433. 最小基因变化](https://leetcode.cn/problems/minimum-genetic-mutation/) （面试150）<br>[LeetCode 127. 单词接龙](https://leetcode.cn/problems/word-ladder/) （面试150） |
| [多源扩散](#多源扩散) | 同时开始的传播时间 | [LeetCode 994. 腐烂的橘子](https://leetcode.cn/problems/rotting-oranges/) （Hot100） |

## 最短步数

把一个局面作为图中的节点，把一次合法操作作为单位权边。使用队列从起点向外扩展，记录距离和已访问状态；当边权不全相同时，需要改用适合权值条件的最短路算法。

### 网格中的最短路径

将可通行格子看作节点，上下左右相邻格子之间连单位权边。用二维距离数组兼任访问标记，越界和障碍不能入队；若共有 mn 个格子，时间与空间复杂度均为 O(mn)。

- [AcWing 844. 走迷宫](https://www.acwing.com/problem/content/846/) （AcWing基础课）：从左上角开始 BFS，第一次到达右下角时得到最少移动次数。

### 局面编码与隐式图

状态不必是一张显式给出的图。先给每个局面确定唯一表示，再按操作规则生成下一层；访问集合必须按完整局面判重，而不能只记录其中一部分。

- [AcWing 845. 八数码](https://www.acwing.com/problem/content/847/) （AcWing基础课）：将九个位置编码成字符串，移动空格生成相邻局面；最多有 9! 种排列，队列耗尽仍未到目标则无解。
- [LeetCode 909. 蛇梯棋](https://leetcode.cn/problems/snakes-and-ladders/) （面试150）：将棋盘转换为从下向上交替方向的编号。一次掷骰后至多触发一次蛇或梯子跳转，再按最终落点判重；n × n 棋盘共有 n² 个状态。

### 单字符变化的最短序列

把合法字符串作为状态，每次修改一个字符生成邻居，只有属于允许集合且尚未访问的状态才能入队。可从两端同时搜索并优先扩展较小的一侧；状态较多时要避免反复扫描整个词表寻找邻居。

- [LeetCode 433. 最小基因变化](https://leetcode.cn/problems/minimum-genetic-mutation/) （面试150）：每个位置只尝试 A、C、G、T，合法下一状态必须在基因库中，结果统计变换次数。
- [LeetCode 127. 单词接龙](https://leetcode.cn/problems/word-ladder/) （面试150）：每次替换一个小写字母，词典中的单词是允许状态；结果统计序列中的单词数，因此起点层数为 1。

## 多源扩散

多个起点同时以距离 0 入队，相当于添加一个到所有起点距离为 0 的虚拟源点。之后按 BFS 顺序扩展，每个状态的距离就是它到最近起点的最小步数。

### 同时开始的传播时间

将所有初始传播源加入队列，首次传播到一个新节点时立即更新它的状态。答案取所有可达目标的最大到达时间；仍存在未到达目标时返回无解。

- [LeetCode 994. 腐烂的橘子](https://leetcode.cn/problems/rotting-oranges/) （Hot100）：所有腐烂橘子同时入队，记录新鲜橘子数量并在感染时递减；没有新鲜橘子时答案为 0，最终仍有新鲜橘子则返回 -1。时间和空间均为 O(mn)。
