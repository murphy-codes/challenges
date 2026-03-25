// Source: https://leetcode.com/problems/equal-sum-grid-partition-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-24
// At the time of submission:
//   Runtime 4 ms Beats 97.20%
//   Memory 161.04 MB Beats 53.15%

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
    // Compute row sums and total grid sum in one pass.
    // If total is odd, equal partition is impossible.
    // Check horizontal cuts using prefix sums of row totals.
    // Then check vertical cuts by accumulating column values.
    // Early exit when running sum exceeds target.
    // Time: O(m * n); Space: O(m) for row sums.
    public boolean canPartitionGrid(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        long[] rowSums = new long[rows];
        long totalSum = 0;

        // Compute row sums and total sum
        for (int i = 0; i < rows; i++) {
            for (int val : grid[i]) {
                rowSums[i] += val;
            }
            totalSum += rowSums[i];
        }

        // If total is odd, cannot split evenly
        if ((totalSum % 2) != 0) {
            return false;
        }

        long target = totalSum / 2;

        // Check horizontal cuts using row sums
        long running = 0;
        for (int i = 0; i < rows - 1 && running < target; i++) {
            running += rowSums[i];
        }
        if (running == target) {
            return true;
        }

        // Check vertical cuts
        running = 0;
        for (int j = 0; j < cols - 1 && running < target; j++) {
            for (int i = 0; i < rows; i++) {
                running += grid[i][j];
            }
        }

        return running == target;
    }
}