// Source: https://leetcode.com/problems/ones-and-zeroes/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-10
// At the time of submission:
//   Runtime 15 ms Beats 99.85%
//   Memory 43.60 MB Beats 50.15%

/****************************************
* 
* You are given an array of binary strings `strs` and two integers `m` and `n`.
* Return the size of the largest subset of `strs` such that there are at most
* _ `m` `0`'s and `n` `1`'s in the subset.
* A set `x` is a subset of a set `y` if all elements of `x` are also elements of `y`.
*
* Example 1:
* Input: strs = ["10","0001","111001","1","0"], m = 5, n = 3
* Output: 4
* Explanation: The largest subset with at most 5 0's and 3 1's is {"10", "0001", "1", "0"}, so the answer is 4.
* Other valid but smaller subsets include {"0001", "1"} and {"10", "1", "0"}.
* {"111001"} is an invalid subset because it contains 4 1's, greater than the maximum of 3.
*
* Example 2:
* Input: strs = ["10","0","1"], m = 1, n = 1
* Output: 2
* Explanation: The largest subset is {"0", "1"}, so the answer is 2.
*
* Constraints:
* • `1 <= strs.length <= 600`
* • `1 <= strs[i].length <= 100`
* • `strs[i] consists only of digits '0' and '1'.`
* • `1 <= m, n <= 100`
* 
****************************************/

class Solution {
    // This solution uses a 2D DP approach similar to 0/1 knapsack.
    // Each string consumes a certain count of zeros and ones, and we decide
    // whether to include it while keeping within m zeros and n ones limits.
    // The DP table is updated in reverse to ensure each string is only used once.
    // Time Complexity: O(l * m * n), Space Complexity: O(m * n)
    public int findMaxForm(String[] strs, int m, int n) {
        int len = strs.length;
        int[][] dp = new int[m + 1][n + 1];
        dp[0][0] = 1; // base: empty subset uses 0 zeros, 0 ones

        for (int i = 1; i <= len; i++) {
            String curr = strs[i - 1];
            int zeros = 0, ones = 0;

            // Count zeros and ones in current string
            for (char c : curr.toCharArray()) {
                if (c == '0') zeros++;
                else ones++;
            }

            // Iterate backwards to ensure each string is only counted once
            for (int j = m; j >= zeros; j--) {
                for (int k = n; k >= ones; k--) {
                    if (dp[j - zeros][k - ones] > 0) {
                        dp[j][k] = Math.max(dp[j][k], 1 + dp[j - zeros][k - ones]);
                    }
                }
            }
        }

        // Find maximum number of strings that can be formed
        int result = 1;
        for (int j = 1; j <= m; j++) {
            for (int k = 1; k <= n; k++) {
                result = Math.max(result, dp[j][k]);
            }
        }

        return result - 1;
    }
}
