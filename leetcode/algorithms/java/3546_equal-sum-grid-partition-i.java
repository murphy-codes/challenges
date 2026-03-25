// Source: https://leetcode.com/problems/equal-sum-grid-partition-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-24
// At the time of submission:
//   Runtime 4 ms Beats 97.20%
//   Memory 161.87 MB Beats 26.57%

/****************************************
* 
* You are given an `m x n` matrix `grid` of positive integers. Your task is to
* _ determine if it is possible to make either one horizontal or one vertical
* _ cut on the grid such that:
* • Each of the two resulting sections formed by the cut is non-empty.
* • The sum of the elements in both sections is equal.
* Return `true` if such a partition exists; otherwise return `false`.
*
* Example 1:
* Input: grid = [[1,4],[2,3]]
* Output: true
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2025/03/30/lc.jpeg]
* A horizontal cut between row 0 and row 1 results in two non-empty sections, each with a sum of 5. Thus, the answer is true.
*
* Example 2:
* Input: grid = [[1,3],[2,4]]
* Output: false
* Explanation:
* No horizontal or vertical cut results in two non-empty sections with equal sums. Thus, the answer is false.
*
* Constraints:
* • `1 <= m == grid.length <= 10^5`
* • `1 <= n == grid[i].length <= 10^5`
* • `2 <= m * n <= 10^5`
* • `1 <= grid[i][j] <= 10^5`
* 
****************************************/

class Solution {
    // Compute total sum and check if it is even.
    // For horizontal cuts, accumulate row sums and check for target.
    // For vertical cuts, accumulate column sums similarly.
    // A valid cut exists if any prefix equals half the total sum.
    // Time: O(m * n) as all elements are visited a constant number of times.
    // Space: O(1) using only running sums.
    public boolean canPartitionGrid(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        long total = 0;

        // Compute total sum
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                total += grid[i][j];
            }
        }

        // If total is odd, cannot split evenly
        if (total % 2 != 0) {
            return false;
        }

        long target = total / 2;

        // Check horizontal cuts
        long running = 0;
        for (int i = 0; i < m - 1; i++) { // ensure bottom is non-empty
            for (int j = 0; j < n; j++) {
                running += grid[i][j];
            }
            if (running == target) {
                return true;
            }
        }

        // Check vertical cuts
        running = 0;
        for (int j = 0; j < n - 1; j++) { // ensure right is non-empty
            for (int i = 0; i < m; i++) {
                running += grid[i][j];
            }
            if (running == target) {
                return true;
            }
        }

        return false;
    }
}