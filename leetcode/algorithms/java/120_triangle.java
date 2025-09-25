// Source: https://leetcode.com/problems/triangle/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-23
// At the time of submission:
//   Runtime 3 ms Beats 79.02%
//   Memory 45.34 MB Beats 69.84%

/****************************************
* 
* Given a `triangle` array, return the minimum path sum from top to bottom.
*
* For each step, you may move to an adjacent number of the row below. More
* _ formally, if you are on index `i` on the current row, you may move to either
* _ index `i` or index `i + 1` on the next row.
*
* Example 1:
* Input: triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
* Output: 11
* Explanation: The triangle looks like:
*    2
*   3 4
*  6 5 7
* 4 1 8 3
* The minimum path sum from top to bottom is 2 + 3 + 5 + 1 = 11 (underlined above).
*
* Example 2:
* Input: triangle = [[-10]]
* Output: -10
*
* Constraints:
* • `1 <= triangle.length <= 200`
* • `triangle[0].length == 1`
* • `triangle[i].length == triangle[i - 1].length + 1`
* • `-10^4 <= triangle[i][j] <= 10^4`
*
* Follow up: Could you do this using only `O(n)` extra space, where `n` is
* _ the total number of rows in the triangle?
* 
****************************************/

import java.util.List;

class Solution {
    // This solution uses bottom-up dynamic programming. Starting from the
    // last row, we compute the minimum path sum to each element by adding
    // the current value with the smaller of its two children. We reuse a
    // 1D array for space efficiency. The final answer is stored at dp[0].
    // Time: O(n^2), visiting each element once; Space: O(n), for dp array.
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[] dp = new int[n];
        // Initialize dp with the last row
        for (int j = 0; j < n; j++) {
            dp[j] = triangle.get(n - 1).get(j);
        }
        // Build upwards from the second to last row
        for (int i = n - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                dp[j] = triangle.get(i).get(j) + Math.min(dp[j], dp[j + 1]);
            }
        }
        return dp[0]; // The minimum path sum from top to bottom
    }
}
