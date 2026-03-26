// Source: https://leetcode.com/problems/equal-sum-grid-partition-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-25
// At the time of submission:
//   Runtime 270 ms Beats 76.47%
//   Memory 273.88 MB Beats 82.35%

/****************************************
* 
* You are given an `m x n` matrix `grid` of positive integers. Your task is to
* _ determine if it is possible to make either one horizontal or one vertical
* _ cut on the grid such that:
* • Each of the two resulting sections formed by the cut is non-empty.
* • The sum of elements in both sections is equal, or can be made equal by
* __ discounting at most one single cell in total (from either section).
* • If a cell is discounted, the rest of the section must remain connected.
* Return `true` if such a partition exists; otherwise return `false`.
* Note: A section is connected if every cell in it can be reached from any other
* _ cell by moving up, down, left, or right through other cells in the section.
*
* Example 1:
* Input: grid = [[1,4],[2,3]]
* Output: true
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2025/03/30/lc.jpeg]
* A horizontal cut after the first row gives sums 1 + 4 = 5 and 2 + 3 = 5, which are equal. Thus, the answer is true.
*
* Example 2:
* Input: grid = [[1,2],[3,4]]
* Output: true
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2025/04/01/chatgpt-image-apr-1-2025-at-05_28_12-pm.png]
* A vertical cut after the first column gives sums 1 + 3 = 4 and 2 + 4 = 6.
* By discounting 2 from the right section (6 - 2 = 4), both sections have equal sums and remain connected. Thus, the answer is true.
*
* Example 3:
* Input: grid = [[1,2,4],[2,3,5]]
* Output: false
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2025/04/01/chatgpt-image-apr-2-2025-at-02_50_29-am.png]
* A horizontal cut after the first row gives 1 + 2 + 4 = 7 and 2 + 3 + 5 = 10.
* By discounting 3 from the bottom section (10 - 3 = 7), both sections have equal sums, but they do not remain connected as it splits the bottom section into two parts ([2] and [5]). Thus, the answer is false.
*
* Example 4:
* Input: grid = [[4,1,8],[3,2,6]]
* Output: false
* Explanation:
* No valid cut exists, so the answer is false.
*
* Constraints:
* • `1 <= m == grid.length <= 10^5`
* • `1 <= n == grid[i].length <= 10^5`
* • `2 <= m * n <= 10^5`
* • `1 <= grid[i][j] <= 10^5`
* 
****************************************/

class Solution {
    // Try all horizontal cuts and rotate grid to cover all orientations.
    // Maintain prefix sum and a set of seen values in the top section.
    // Let tag = 2 * prefixSum - total; removing tag balances the sums.
    // Handle thin grids (1 row/column) with endpoint-only removal rules.
    // Time: O(m * n); Space: O(m * n) due to hash set usage.

    public boolean canPartitionGrid(int[][] grid) {
        long total = 0;
        int m = grid.length;
        int n = grid[0].length;

        // Compute total sum
        for (int[] row : grid) {
            for (int val : row) {
                total += val;
            }
        }

        // Try all 4 rotations (handles both horizontal + vertical cuts)
        for (int rot = 0; rot < 4; rot++) {

            m = grid.length;
            n = grid[0].length;

            // If only 1 row → no valid horizontal cut
            if (m < 2) {
                grid = rotate(grid);
                continue;
            }

            long prefixSum = 0;
            java.util.Set<Long> seen = new java.util.HashSet<>();
            seen.add(0L);

            // Special case: single column (1D vertical)
            if (n == 1) {
                for (int i = 0; i < m - 1; i++) {
                    prefixSum += grid[i][0];

                    long tag = prefixSum * 2 - total;

                    // Only endpoints allowed
                    if (tag == 0 ||
                        tag == grid[0][0] ||
                        tag == grid[i][0]) {
                        return true;
                    }
                }
                grid = rotate(grid);
                continue;
            }

            // General case
            for (int i = 0; i < m - 1; i++) {

                for (int j = 0; j < n; j++) {
                    prefixSum += grid[i][j];
                    seen.add((long) grid[i][j]);
                }

                long tag = prefixSum * 2 - total;

                // First row → top partition is 1 row → endpoint restriction
                if (i == 0) {
                    if (tag == 0 ||
                        tag == grid[0][0] ||
                        tag == grid[0][n - 1]) {
                        return true;
                    }
                    continue;
                }

                // General 2D case
                if (seen.contains(tag)) {
                    return true;
                }
            }

            grid = rotate(grid);
        }

        return false;
    }

    private int[][] rotate(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][] rotated = new int[n][m];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                rotated[j][m - 1 - i] = grid[i][j];
            }
        }

        return rotated;
    }
}