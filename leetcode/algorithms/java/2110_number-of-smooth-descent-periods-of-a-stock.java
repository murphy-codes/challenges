// Source: https://leetcode.com/problems/number-of-smooth-descent-periods-of-a-stock/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-15
// At the time of submission:
//   Runtime 2 ms Beats 100.00%
//   Memory 84.36 MB Beats 22.22%

/****************************************
* 
* You are given an integer array `prices` representing the daily price history
* _ of a stock, where `prices[i]` is the stock price on the `i^th` day.
* A smooth descent period of a stock consists of one or more contiguous days
* _ such that the price on each day is lower than the price on the preceding day
* _ by exactly `1`. The first day of the period is exempted from this rule.
* Return the number of smooth descent periods.
*
* Example 1:
* Input: prices = [3,2,1,4]
* Output: 7
* Explanation: There are 7 smooth descent periods:
* [3], [2], [1], [4], [3,2], [2,1], and [3,2,1]
* Note that a period with one day is a smooth descent period by the definition.
*
* Example 2:
* Input: prices = [8,6,7,7]
* Output: 4
* Explanation: There are 4 smooth descent periods: [8], [6], [7], and [7]
* Note that [8,6] is not a smooth descent period as 8 - 6 ≠ 1.
*
* Example 3:
* Input: prices = [1]
* Output: 1
* Explanation: There is 1 smooth descent period: [1]
*
* Constraints:
* • `1 <= prices.length <= 10^5`
* • `1 <= prices[i] <= 10^5`
* 
****************************************/

class Solution {
    // Iterate through prices while tracking the length of each smooth descent run.
    // A run of length k contributes k * (k + 1) / 2 valid subarrays. When the descent
    // breaks, add the run’s contribution and reset the counter. Single-day periods
    // are naturally included since runs start at length 1. Time O(n), space O(1).
    public long getDescentPeriods(int[] prices) {
        int run = 1;   // length of current smooth descent run
        long total = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i - 1] - prices[i] == 1) run++;
            else { // add all subarrays from the completed run
                total += (long) run * (run + 1) / 2;
                run = 1;
            }
        }
        total += (long) run * (run + 1) / 2; // add final run
        return total;
    }
}
