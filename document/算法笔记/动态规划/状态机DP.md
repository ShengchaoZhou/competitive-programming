# 状态机 DP

状态机 DP 用少量明确状态描述决策过程，例如是否持有股票、已经完成几次交易，再按天进行合法转移。

| 算法类型 | 题型 | 题目与题集标记 |
| --- | --- | --- |
| [股票交易状态](#股票交易状态) | 最多一次交易 | [LeetCode 121. 买卖股票的最佳时机](https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/) （Hot100/面试150/CodeTop200） |
|  | 不限交易次数 | [LeetCode 122. 买卖股票的最佳时机 II](https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-ii/) （面试150/CodeTop200） |
|  | 交易次数受限 | [LeetCode 123. 买卖股票的最佳时机 III](https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iii/) （面试150/CodeTop200）<br>[LeetCode 188. 买卖股票的最佳时机 IV](https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iv/) （面试150） |

## 股票交易状态

买入使现金减少且进入持股状态，卖出使现金增加且进入空仓状态。收益转移应基于前一天的状态；最终答案通常只能从空仓状态取得。

### 最多一次交易

可以只维护此前最低买入价格与最大利润。若使用 DP，持股状态只能从尚未交易的初始现金买入，不能从卖出后的收益再次买入。

- [LeetCode 121. 买卖股票的最佳时机](https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/) （Hot100/面试150/CodeTop200）：按天更新最低历史价格与当前卖出利润，不盈利时允许不交易。

### 不限交易次数

保留持股与空仓两个状态，分别比较保持现状与执行一次买卖。题目没有手续费与冷冻期时，也等价于累加每段正的相邻涨幅。

- [LeetCode 122. 买卖股票的最佳时机 II](https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-ii/) （面试150/CodeTop200）：允许多次交易但同一时刻只能持有一股，使用两状态递推。

### 交易次数受限

用 cash[t] 表示恰好完成 t 次卖出且空仓的收益，hold[t] 表示同样卖出次数下仍持股的收益。买入来自上一天 cash[t]，第 t 次卖出来自上一天 hold[t-1]；初始只有 cash[0]=0，其余不可达。交易上限为 k 时，答案为 max(cash[0..k])，而非强制完成 k 次交易。复杂度 O(nk)，空间 O(k)。

- [LeetCode 123. 买卖股票的最佳时机 III](https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iii/) （面试150/CodeTop200）：将交易上限设为 2，分别维护每个阶段的最优收益。
- [LeetCode 188. 买卖股票的最佳时机 IV](https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iv/) （面试150）：将交易次数纳入状态；当 k>=n/2 时可退化为不限次数版本。
