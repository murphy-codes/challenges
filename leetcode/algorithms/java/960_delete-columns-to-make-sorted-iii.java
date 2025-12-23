// Source: https://leetcode.com/problems/delete-columns-to-make-sorted-iii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-22
// At the time of submission:
//   Runtime 6 ms Beats 100.00%
//   Memory 44.21 MB Beats 54.55%

/****************************************
* 
* You are given an array of `n` strings `strs`, all of the same length.
* We may choose any deletion indices, and we delete all the characters in those
* _ indices for each string.
* For example, if we have `strs = ["abcdef","uvwxyz"]` and deletion indices
* _ `{0, 2, 3}`, then the final array after deletions is `["bef", "vyz"]`.
* Suppose we chose a set of deletion indices `answer` such that after deletions,
* _ the final array has every string (row) in lexicographic order.
* _ (i.e., `(strs[0][0] <= strs[0][1] <= ... <= strs[0][strs[0].length - 1])`,
* _ and `(strs[1][0] <= strs[1][1] <= ... <= strs[1][strs[1].length - 1])`, and
* _ so on). Return the minimum possible value of `answer.length`.
*
* Example 1:
* Input: strs = ["babca","bbazb"]
* Output: 3
* Explanation: After deleting columns 0, 1, and 4, the final array is strs = ["bc", "az"].
* Both these rows are individually in lexicographic order (ie. strs[0][0] <= strs[0][1] and strs[1][0] <= strs[1][1]).
* Note that strs[0] > strs[1] - the array strs is not necessarily in lexicographic order.
*
* Example 2:
* Input: strs = ["edcba"]
* Output: 4
* Explanation: If we delete less than 4 columns, the only row will not be lexicographically sorted.
*
* Example 3:
* Input: strs = ["ghi","def","abc"]
* Output: 0
* Explanation: All rows are already lexicographically sorted.
*
* Constraints:
* • `n == strs.length`
* • `1 <= n <= 100`
* • `1 <= strs[i].length <= 100`
* • `strs[i]` consists of lowercase English letters.
* 
****************************************/

class Solution {
    // Treat each column as a vector across rows and find the longest
    // subsequence of columns that is non-decreasing for every row.
    // Use DP where dp[j] is the max kept columns ending at column j.
    // Transition by checking if column i can precede j across all rows.
    // Time: O(cols^2 * rows), Space: O(cols)
    public int minDeletionSize(String[] strs) {
        int rows = strs.length;
        int cols = strs[0].length();

        int[] dp = new int[cols];
        int maxKept = 0;

        for (int j = 0; j < cols; j++) {
            dp[j] = 1; // At minimum, keep this column alone

            for (int i = 0; i < j; i++) {
                if (canFollow(strs, i, j, rows)) {
                    dp[j] = Math.max(dp[j], dp[i] + 1);
                }
            }

            maxKept = Math.max(maxKept, dp[j]);
        }

        return cols - maxKept;
    }

    private boolean canFollow(String[] strs, int prev, int curr, int rows) {
        for (int r = 0; r < rows; r++) {
            if (strs[r].charAt(prev) > strs[r].charAt(curr)) {
                return false;
            }
        }
        return true;
    }
}
