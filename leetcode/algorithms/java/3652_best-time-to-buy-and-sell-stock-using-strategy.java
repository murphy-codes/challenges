// Source: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-using-strategy/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-18
// At the time of submission:
//   Runtime 3 ms Beats 100.00%
//   Memory 107.37 MB Beats 5.37%

/****************************************
* 
* You are given two integer arrays `prices` and `strategy`, where:
* • `prices[i]` is the price of a given stock on the `i^th` day.
* • `strategy[i]` represents a trading action on the `i^th` day, where:
* _ • `-1` indicates buying one unit of the stock.
* _ • `0` indicates holding the stock.
* _ • `1` indicates selling one unit of the stock.
* You are also given an even integer `k`, and may perform at most one
* _ modification to `strategy`. A modification consists of:
* • Selecting exactly `k` consecutive elements in strategy.
* • Set the first `k / 2` elements to `0` (hold).
* • Set the last `k / 2` elements to `1` (sell).
* The profit is defined as the sum of `strategy[i] * prices[i]` across all days.
* Return the maximum possible profit you can achieve.
* Note: There are no constraints on budget or stock ownership, so all buy and
* _ sell operations are feasible regardless of past actions.
*
* Example 1:
* Input: prices = [4,2,8], strategy = [-1,0,1], k = 2
* Output: 10
* Explanation:
* Modification	Strategy	Profit Calculation	Profit
* Original	[-1, 0, 1]	(-1 × 4) + (0 × 2) + (1 × 8) = -4 + 0 + 8	4
* Modify [0, 1]	[0, 1, 1]	(0 × 4) + (1 × 2) + (1 × 8) = 0 + 2 + 8	10
* Modify [1, 2]	[-1, 0, 1]	(-1 × 4) + (0 × 2) + (1 × 8) = -4 + 0 + 8	4
* Thus, the maximum possible profit is 10, which is achieved by modifying the subarray [0, 1]​​​​​​​.
*
* Example 2:
* Input: prices = [5,4,3], strategy = [1,1,0], k = 2
* Output: 9
* Explanation:
* Modification	Strategy	Profit Calculation	Profit
* Original	[1, 1, 0]	(1 × 5) + (1 × 4) + (0 × 3) = 5 + 4 + 0	9
* Modify [0, 1]	[0, 1, 0]	(0 × 5) + (1 × 4) + (0 × 3) = 0 + 4 + 0	4
* Modify [1, 2]	[1, 0, 1]	(1 × 5) + (0 × 4) + (1 × 3) = 5 + 0 + 3	8
* Thus, the maximum possible profit is 9, which is achieved without any modification.
*
* Constraints:
* • `2 <= prices.length == strategy.length <= 10^5`
* • `1 <= prices[i] <= 10^5`
* • `-1 <= strategy[i] <= 1`
* • `2 <= k <= prices.length`
* • `k` is even
* 
****************************************/

class Solution {
    // We compute base profit while tracking the profit delta of a sliding window.
    // The window removes profit from the first k/2 days and forces sells on the last.
    // The delta is updated incrementally in O(1) as the window slides forward.
    // This avoids prefix arrays and achieves optimal linear time and constant space.
    // Time: O(n), Space: O(1)
    public long maxProfit(int[] prices, int[] strategy, int k) {
        long baseProfit = 0;
        int half = k / 2;
        int n = prices.length;

        long windowDelta = 0;
        long bestDelta = 0;

        // Build base profit and first half of window
        for (int i = 0; i < half; i++) {
            int original = prices[i] * strategy[i];
            baseProfit += original;
            windowDelta += prices[i] - original; // removing original contribution
        }

        // Build second half of window (forced sells)
        for (int i = half; i < k; i++) {
            int original = prices[i] * strategy[i];
            baseProfit += original;
            windowDelta += prices[i] - original - prices[i - half];
        }

        bestDelta = Math.max(bestDelta, windowDelta);

        // Slide the window forward
        for (int i = k; i < n; i++) {
            int original = prices[i] * strategy[i];
            baseProfit += original;

            windowDelta += prices[i] - original
                         - prices[i - half]
                         + prices[i - k] * strategy[i - k];

            bestDelta = Math.max(bestDelta, windowDelta);
        }

        return baseProfit + bestDelta;
    }
}
