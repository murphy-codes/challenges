// Source: https://leetcode.com/problems/minimum-penalty-for-a-shop/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-26
// At the time of submission:
//   Runtime 4 ms Beats 100.00%
//   Memory 46.97 MB Beats 50.44%

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
    // Single-pass scan computes when closing yields lowest penalty. We track a
    // running score that rises when customers arrive ('Y') and falls when open
    // with no customers ('N'). Whenever score turns positive, hour i becomes a
    // better closing time and score resets. Time: O(n) over string length. Space:
    // O(1) using only a few counters and byte access to characters.
    public int bestClosingTime(String customers) {

        // Convert string to raw bytes (fast character access)
        byte[] visits = customers.getBytes(java.nio.charset.Charset.forName("ISO-8859-1"));

        int bestTime = -1;          // latest closing hour index giving best score
        int score = 0;              // running penalty score (lower is better)

        for (int i = 0; i < visits.length; i++) {

            if (visits[i] == 89) {  // 'Y'
                score++;
                if (score > 0) {
                    bestTime = i;   // better closing time found at hour i
                    score = 0;      // reset running score
                }
            } else {                // 'N'
                score--;
            }
        }

        return bestTime + 1;        // convert index to closing hour
    }
}
