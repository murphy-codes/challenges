// Source: https://leetcode.com/problems/largest-magic-square/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-17
// At the time of submission:
//   Runtime 7 ms Beats 72.35%
//   Memory 46.42 MB Beats 44.71%

/****************************************
* 
* A `k x k` magic square is a `k x k` grid filled with integers such that every
* row sum, every column sum, and both diagonal sums are all equal. The integers
* in the magic square do not have to be distinct. Every `1 x 1` grid is
* trivially a magic square.
* Given an `m x n` integer grid, return the size (i.e., the side length `k`)
* of the largest magic square that can be found within this grid.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/05/29/magicsquare-grid.jpg]
* Input: grid = [[7,1,4,5,6],[2,5,1,6,4],[1,5,4,3,2],[1,2,7,3,4]]
* Output: 3
* Explanation: The largest magic square has a size of 3.
* Every row sum, column sum, and diagonal sum of this magic square is equal to 12.
* - Row sums: 5+1+6 = 5+4+3 = 2+7+3 = 12
* - Column sums: 5+5+2 = 1+4+7 = 6+3+3 = 12
* - Diagonal sums: 5+4+3 = 6+4+2 = 12
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2021/05/29/magicsquare2-grid.jpg]
* Input: grid = [[5,1,3,1],[9,3,3,1],[1,3,3,8]]
* Output: 2
*
* Constraints:
* • `m == grid.length`
* • `n == grid[i].length`
* • `1 <= m, n <= 50`
* • `1 <= grid[i][j] <= 10^6`
* 
****************************************/

class Solution {
    // Precompute prefix sums for rows, columns, and both diagonals.
    // Try square sizes from largest to smallest and stop early on success.
    // Each candidate square is checked in O(k) using O(1) sum queries.
    // Time: O(m * n * min(m, n)), Space: O(m * n).

    public int largestMagicSquare(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // Prefix sums
        int[][] rowSum = new int[m][n + 1];
        int[][] colSum = new int[n][m + 1];
        int[][] diag1  = new int[m + 1][n + 1]; // top-left -> bottom-right
        int[][] diag2  = new int[m + 1][n + 1]; // top-right -> bottom-left

        // Build prefix sums
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                rowSum[r][c + 1] = rowSum[r][c] + grid[r][c];
                colSum[c][r + 1] = colSum[c][r] + grid[r][c];
                diag1[r + 1][c + 1] = diag1[r][c] + grid[r][c];
                diag2[r + 1][c] = diag2[r][c + 1] + grid[r][c];
            }
        }

        // Try larger squares first
        for (int k = Math.min(m, n); k >= 2; k--) {
            for (int r = 0; r + k <= m; r++) {
                for (int c = 0; c + k <= n; c++) {
                    if (isMagic(grid, rowSum, colSum, diag1, diag2, r, c, k)) {
                        return k;
                    }
                }
            }
        }

        return 1; // 1x1 is always magic
    }

    private boolean isMagic(
        int[][] grid,
        int[][] rowSum,
        int[][] colSum,
        int[][] diag1,
        int[][] diag2,
        int r,
        int c,
        int k
    ) {
        int target = rowSum[r][c + k] - rowSum[r][c];

        // Check rows
        for (int i = 0; i < k; i++) {
            if (rowSum[r + i][c + k] - rowSum[r + i][c] != target) {
                return false;
            }
        }

        // Check columns
        for (int j = 0; j < k; j++) {
            if (colSum[c + j][r + k] - colSum[c + j][r] != target) {
                return false;
            }
        }

        // Check diagonals
        int d1 = diag1[r + k][c + k] - diag1[r][c];
        int d2 = diag2[r + k][c] - diag2[r][c + k];

        return d1 == target && d2 == target;
    }
}
