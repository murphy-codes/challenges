// Source: https://leetcode.com/problems/minimum-penalty-for-a-shop/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-26
// At the time of submission:
//   Runtime 12 ms Beats 62.70%
//   Memory 46.74 MB Beats 76.36%

/****************************************
* 
* You are given the customer visit log of a shop represented by a 0-indexed
* _ string `customers` consisting only of characters `'N'` and `'Y'`:
* • if the `i^th` character is `'Y'`, it means that customers come at the `i^th` hour
* • whereas `'N'` indicates that no customers come at the `i^th` hour.
* If the shop closes at the `j^th` hour (`0 <= j <= n`), the penalty is calculated as follows:
* • For every hour when the shop is open and no customers come, the penalty increases by `1`.
* • For every hour when the shop is closed and customers come, the penalty increases by `1`.
* Return the earliest hour at which the shop must be closed to incur a minimum penalty.
* Note that if a shop closes at the `j^th` hour, it means the shop is closed at the hour `j`.
*
* Example 1:
* Input: customers = "YYNY"
* Output: 2
* Explanation:
* - Closing the shop at the 0th hour incurs in 1+1+0+1 = 3 penalty.
* - Closing the shop at the 1st hour incurs in 0+1+0+1 = 2 penalty.
* - Closing the shop at the 2nd hour incurs in 0+0+0+1 = 1 penalty.
* - Closing the shop at the 3rd hour incurs in 0+0+1+1 = 2 penalty.
* - Closing the shop at the 4th hour incurs in 0+0+1+0 = 1 penalty.
* Closing the shop at 2nd or 4th hour gives a minimum penalty. Since 2 is earlier, the optimal closing time is 2.
*
* Example 2:
* Input: customers = "NNNNN"
* Output: 0
* Explanation: It is best to close the shop at the 0th hour as no customers arrive.
*
* Example 3:
* Input: customers = "YYYY"
* Output: 4
* Explanation: It is best to close the shop at the 4th hour as customers arrive at each hour.
*
* Constraints:
* • `1 <= customers.length <= 10^5`
* • `customers consists only of characters `'Y'` and `'N'`.
* 
****************************************/

class Solution {
    // Build a suffix array counting future 'Y' visits for each closing hour,
    // then sweep from left to right tracking past 'N' hours. For every hour i,
    // the penalty of closing then is: open-without-customers (prefix 'N') +
    // closed-with-customers (suffix 'Y'). Track the smallest penalty and the
    // earliest hour achieving it. Time complexity: O(n). Space complexity: O(n).
    public int bestClosingTime(String customers) {
        int bCT = 0, penalty = Integer.MAX_VALUE, count = 0;
        int n = customers.length();
        int[] suffix = new int[n+1];
        for (int i = n-1; i >= 0; i--) suffix[i] = (customers.charAt(i)=='Y') ? ++count : count;
        count = 0;
        for (int i = 0; i < n; i++) {
            int cur = count + suffix[i];
            if (cur < penalty) {
                penalty = cur;
                bCT = i;
            }
            if (customers.charAt(i)=='N') count++;
        }
        if (count < penalty) return n; // stay open the last hour?
        return bCT;
    }
}