// Source: https://leetcode.com/problems/maximum-score-from-grid-operations/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-28
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 47.80 MB Beats 82.14%

/****************************************
* 
* You are given a 2D matrix `grid` of size `n x n`. Initially, all cells of
* _ the grid are colored white. In one operation, you can select any cell of
* _ indices `(i, j)`, and color black all the cells of the `j^th` column
* _ starting from the top row down to the `i^th` row.
* The grid score is the sum of all `grid[i][j]` such that cell `(i, j)` is
* _ white and it has a horizontally adjacent black cell.
* Return the maximum score that can be achieved after some number of operations.
*
* Example 1:
* Input: grid = [[0,0,0,0,0],[0,0,3,0,0],[0,1,0,0,0],[5,0,0,3,0],[0,0,0,0,2]]
* Output: 11
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2024/05/11/one.png]
* In the first operation, we color all cells in column 1 down to row 3, and in the second operation, we color all cells in column 4 down to the last row. The score of the resulting grid is `grid[3][0] + grid[1][2] + grid[3][3]` which is equal to 11.
*
* Example 2:
* Input: grid = [[10,9,0,0,15],[7,1,0,8,0],[5,20,0,11,0],[0,0,0,1,2],[8,12,1,10,3]]
* Output: 94
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2024/05/11/two-1.png]
* We perform operations on 1, 2, and 3 down to rows 1, 4, and 0, respectively. The score of the resulting grid is `grid[0][0] + grid[1][0] + grid[2][1] + grid[4][1] + grid[1][3] + grid[2][3] + grid[3][3] + grid[4][3] + grid[0][4]` which is equal to 94.
*
* Constraints:
* • `1 <= n == grid.length <= 100`
* • `n == grid[i].length`
* • `0 <= grid[i][j] <= 10^9`
* 
****************************************/

class Solution {
    // This solution avoids heavy 3D DP by using two rolling 1D DP arrays.
    // We sweep columns in both directions and maintain the best score that
    // can be carried forward based on adjacent black/white relationships.
    // Instead of tracking every height state explicitly, we update optimal
    // values incrementally, reducing the solution from O(n^3) to O(n^2)
    // time with only O(n) extra space.
    public long maximumScore(int[][] grid) {
        int n = grid.length;

        // DP arrays for forward and backward sweeps
        long[] leftToRightDp = new long[n];
        long[] rightToLeftDp = new long[n];

        long bestResult = 0;

        // Previous best values carried between iterations
        long previousForward = 0;
        long previousBackward = 0;

        int col = 0;

        while (col < n - 1) {
            // Process current column from top -> bottom
            long currentForward = processColumn(
                grid,
                leftToRightDp,
                col,
                previousForward,
                0,
                1,
                n
            );

            // Update carry value for next iteration
            previousForward = Math.max(bestResult, previousBackward);

            // Process next column from bottom -> top
            previousBackward = processColumn(
                grid,
                rightToLeftDp,
                col + 1,
                bestResult,
                n - 1,
                -1,
                -1
            );

            // Update best answer so far
            bestResult = Math.max(previousForward, currentForward);

            col++;
        }

        return Math.max(bestResult, previousBackward);
    }

    /**
     * Sweeps one column in a specific direction and updates DP.
     *
     * row = starting row
     * dir = traversal direction (+1 or -1)
     * stop = stopping boundary
     */
    private long processColumn(
        int[][] grid,
        long[] dp,
        int col,
        long previousBest,
        int row,
        int dir,
        int stop
    ) {
        long best = 0;

        while (row != stop) {
            best = Math.max(best, previousBest);

            previousBest = dp[row];

            best += grid[row][col];

            dp[row] = best;

            row += dir;
        }

        return best;
    }
}