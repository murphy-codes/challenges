// Source: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-v/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-16
// At the time of submission:
//   Runtime 93 ms Beats 63.48%
//   Memory 47.15 MB Beats 73.40%

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
    // We use DP over days and completed transactions.
    // dp[t][0/1/2] tracks max profit with t completed transactions
    // while holding no position, a long position, or a short position.
    // Each day transitions allow opening or closing exactly one position.
    // Time: O(n * k), Space: O(k)

    public long maximumProfit(int[] prices, int k) {
        final long NEG_INF = Long.MIN_VALUE / 4;

        // dp[t][0] = no position
        // dp[t][1] = holding long
        // dp[t][2] = holding short
        long[][] dp = new long[k + 1][3];

        for (int t = 0; t <= k; t++) {
            dp[t][0] = 0;
            dp[t][1] = NEG_INF;
            dp[t][2] = NEG_INF;
        }

        for (int price : prices) {
            long[][] next = new long[k + 1][3];

            for (int t = 0; t <= k; t++) {
                next[t][0] = dp[t][0];
                next[t][1] = dp[t][1];
                next[t][2] = dp[t][2];
            }

            for (int t = 0; t <= k; t++) {
                // Open long or short
                next[t][1] = Math.max(next[t][1], dp[t][0] - price);
                next[t][2] = Math.max(next[t][2], dp[t][0] + price);

                // Close positions
                if (t + 1 <= k) {
                    if (dp[t][1] != NEG_INF) {
                        next[t + 1][0] = Math.max(
                            next[t + 1][0],
                            dp[t][1] + price
                        );
                    }
                    if (dp[t][2] != NEG_INF) {
                        next[t + 1][0] = Math.max(
                            next[t + 1][0],
                            dp[t][2] - price
                        );
                    }
                }
            }

            dp = next;
        }

        long ans = 0;
        for (int t = 0; t <= k; t++) {
            ans = Math.max(ans, dp[t][0]);
        }
        return ans;
    }
}
