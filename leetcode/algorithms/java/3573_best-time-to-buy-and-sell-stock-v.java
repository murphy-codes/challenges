// Source: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-v/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-16
// At the time of submission:
//   Runtime 11 ms Beats 97.52%
//   Memory 46.16 MB Beats 92.20%

/****************************************
* 
* You are given an integer array `prices` where `prices[i]` is the price of a
* _ stock in dollars on the `i^th` day, and an integer `k`.
* You are allowed to make at most `k` transactions, where each transaction can
* _ be either of the following:
* • Normal transaction: Buy on day `i`, then sell on a later day `j` where `i < j`.
* __ You profit `prices[j] - prices[i]`.
* • Short selling transaction: Sell on day `i`, then buy back on a later day `j`
* __ where `i < j`. You profit `prices[i] - prices[j]`.
* Note that you must complete each transaction before starting another.
* _ Additionally, you can't buy or sell on the same day you are selling or
* _ buying back as part of a previous transaction.
* Return the maximum total profit you can earn by making at most `k` transactions.
*
* Example 1:
* Input: prices = [1,7,9,8,2], k = 2
* Output: 14
* Explanation:
* We can make $14 of profit through 2 transactions:
* • A normal transaction: buy the stock on day 0 for $1 then sell it on day 2 for $9.
* • A short selling transaction: sell the stock on day 3 for $8 then buy back on day 4 for $2.
*
* Example 2:
* Input: prices = [12,16,19,19,8,1,19,13,9], k = 3
* Output: 36
* Explanation:
* We can make $36 of profit through 3 transactions:
* • A normal transaction: buy the stock on day 0 for $12 then sell it on day 2 for $19.
* • A short selling transaction: sell the stock on day 3 for $19 then buy back on day 4 for $8.
* • A normal transaction: buy the stock on day 5 for $1 then sell it on day 6 for $19.
*
* Constraints:
* • `2 <= prices.length <= 10^3`
* • `1 <= prices[i] <= 10^9`
* • `1 <= k <= prices.length / 2`
* 
****************************************/

class Solution {
    // We use DP where dp[t][i] is the max profit up to day i with t transactions.
    // Open long and short states are tracked implicitly via bestBuy and bestShort.
    // Each day considers doing nothing or closing a long or short transaction.
    // Time complexity: O(n * k), Space complexity: O(n)
    public long maximumProfit(int[] prices, int k) {
        int n = prices.length;
        if (n < 2 || k == 0) return 0;

        // prev[t-1][i]: max profit up to day i with t-1 transactions
        // curr[t][i]:   max profit up to day i with t transactions
        long[] prevProfit = new long[n];
        long[] currProfit = new long[n];

        for (int transaction = 1; transaction <= k; transaction++) {

            // Best states for opening long or short transactions
            long bestLongBuy  = -prices[0]; // dp_prev[0] - prices[0]
            long bestShortSell = prices[0]; // dp_prev[0] + prices[0]

            currProfit[0] = 0;

            for (int day = 1; day < n; day++) {
                long noAction = currProfit[day - 1];
                long closeLong = bestLongBuy + prices[day];
                long closeShort = bestShortSell - prices[day];

                currProfit[day] = Math.max(
                    noAction,
                    Math.max(closeLong, closeShort)
                );

                // Update best states using previous transaction layer
                bestLongBuy = Math.max(
                    bestLongBuy,
                    prevProfit[day - 1] - prices[day]
                );
                bestShortSell = Math.max(
                    bestShortSell,
                    prevProfit[day - 1] + prices[day]
                );
            }

            // Swap DP layers
            long[] tmp = prevProfit;
            prevProfit = currProfit;
            currProfit = tmp;
        }

        return prevProfit[n - 1];
    }
}
