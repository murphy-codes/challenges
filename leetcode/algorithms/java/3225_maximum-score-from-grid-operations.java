// Source: https://leetcode.com/problems/maximum-score-from-grid-operations/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-28
// At the time of submission:
//   Runtime 99 ms Beats 71.43%
//   Memory 108.84 MB Beats 32.14%

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
    // Let each column choose a black height h, meaning the top h cells are black.
    // A white cell scores only if a left/right neighbor is black, so each column's
    // contribution depends only on adjacent column heights.
    // DP tracks (currentHeight, previousHeight), and prefix/suffix max arrays
    // optimize transitions from O(n^4) brute force into efficient O(n^3),
    // which passes comfortably for n <= 100.

    public long maximumScore(int[][] grid) {
        int n = grid.length;

        // Single column: no horizontal neighbor exists, so score is always 0.
        if (n == 1) {
            return 0;
        }

        // dp[col][currHeight][prevHeight]
        // currHeight = black height of current column
        // prevHeight = black height of previous column

        // Represents the best score up to this column configuration.
        long[][][] dp = new long[n][n + 1][n + 1];

        // Prefix sums for each column:
        // colPrefix[c][h] = sum of top h cells in column c
        long[][] colPrefix = new long[n][n + 1];
        for (int col = 0; col < n; col++) {
            for (int row = 1; row <= n; row++) {
                colPrefix[col][row] =
                    colPrefix[col][row - 1] + grid[row - 1][col];
            }
        }

        // Optimization helpers to reduce O(n^4) transition cost.
        long[][] prefixBest = new long[n + 1][n + 1];
        long[][] suffixBest = new long[n + 1][n + 1];

        for (int col = 1; col < n; col++) {
            for (int currHeight = 0; currHeight <= n; currHeight++) {
                for (int prevHeight = 0; prevHeight <= n; prevHeight++) {

                    if (currHeight <= prevHeight) {
                        // Current column is shorter or equal:
                        // contribution comes from current column.
                        long gain =
                            colPrefix[col][prevHeight] -
                            colPrefix[col][currHeight];

                        dp[col][currHeight][prevHeight] = Math.max(
                            dp[col][currHeight][prevHeight],
                            suffixBest[prevHeight][0] + gain
                        );

                    } else {
                        // Current column is taller:
                        // contribution comes from previous column.
                        long gain =
                            colPrefix[col - 1][currHeight] -
                            colPrefix[col - 1][prevHeight];

                        dp[col][currHeight][prevHeight] = Math.max(
                            dp[col][currHeight][prevHeight],
                            Math.max(
                                suffixBest[prevHeight][currHeight],
                                prefixBest[prevHeight][currHeight] + gain
                            )
                        );
                    }
                }
            }

            // Build prefixBest and suffixBest for next iteration.
            for (int currHeight = 0; currHeight <= n; currHeight++) {
                prefixBest[currHeight][0] = dp[col][currHeight][0];

                for (int prevHeight = 1; prevHeight <= n; prevHeight++) {
                    long penalty = (prevHeight > currHeight)
                        ? (colPrefix[col][prevHeight] -
                           colPrefix[col][currHeight])
                        : 0;

                    prefixBest[currHeight][prevHeight] = Math.max(
                        prefixBest[currHeight][prevHeight - 1],
                        dp[col][currHeight][prevHeight] - penalty
                    );
                }

                suffixBest[currHeight][n] = dp[col][currHeight][n];

                for (int prevHeight = n - 1; prevHeight >= 0; prevHeight--) {
                    suffixBest[currHeight][prevHeight] = Math.max(
                        suffixBest[currHeight][prevHeight + 1],
                        dp[col][currHeight][prevHeight]
                    );
                }
            }
        }
        long answer = 0;

        // Final column can be treated as fully black (n)
        // or fully white (0), depending on optimal ending.
        for (int prevHeight = 0; prevHeight <= n; prevHeight++) {
            answer = Math.max(
                answer,
                Math.max(
                    dp[n - 1][n][prevHeight],
                    dp[n - 1][0][prevHeight]
                )
            );
        }

        return answer;
    }
}