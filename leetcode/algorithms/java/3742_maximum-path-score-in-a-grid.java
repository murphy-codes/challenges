// Source: https://leetcode.com/problems/maximum-path-score-in-a-grid/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-30
// At the time of submission:
//   Runtime 54 ms Beats 99.46%
//   Memory 50.88 MB Beats 98.92%

/****************************************
* 
* You are given an `m x n` grid where each cell contains one of the values
* _ 0, 1, or 2. You are also given an integer `k`.
* You start from the top-left corner (0, 0) and want to reach the bottom-right
* _ corner `(m - 1, n - 1)` by moving only right or down.
* Each cell contributes a specific score and incurs an associated cost,
* _ according to their cell values:
* • 0: adds 0 to your score and costs 0.
* • 1: adds 1 to your score and costs 1.
* • 2: adds 2 to your score and costs 1. ​​​​​​​
* Return the maximum score achievable without exceeding a total cost of `k`,
* _ or -1 if no valid path exists.
* Note: If you reach the last cell but the total cost exceeds `k`,
* _ the path is invalid.
*
* Example 1:
* Input: grid = [[0, 1],[2, 0]], k = 1
* Output: 2
* Explanation:​​​​​​​
* The optimal path is:
* Cell	grid[i][j]	Score	Total Score	Cost	Total Cost
* (0, 0)	0	0	0	0	0
* (1, 0)	2	2	2	1	1
* (1, 1)	0	0	2	0	1
* Thus, the maximum possible score is 2.
*
* Example 2:
* Input: grid = [[0, 1],[1, 2]], k = 1
* Output: -1
* Explanation:
* There is no path that reaches cell `(1, 1)`​​​​​​​ without exceeding cost k. Thus, the answer is -1.
*
* Constraints:
* • `1 <= m, n <= 200`
* • `0 <= k <= 10^3`
* • `​​​​​​​grid[0][0] == 0`
* • `0 <= grid[i][j] <= 2`
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Use DP with dp[j][c] storing max score at column j with cost c.
    // Transition from left and top while updating cost (0 or 1 per cell).
    // Reverse iterate cost to avoid overwriting needed states.
    // Precompute min path cost to prune impossible cases early.
    // Time: O(m * n * k), Space: O(n * k)
    public int maxPathScore(int[][] grid, int maxCost) {
        // Early pruning: if minimum required cost exceeds maxCost, impossible
        if (minPathCost(grid) > maxCost) {
            return -1;
        }

        int rows = grid.length;
        int cols = grid[0].length;

        // Max cost can't exceed number of steps in path
        maxCost = Math.min(maxCost, rows + cols - 2);

        // dp[col][cost] = max score reaching current row at column "col"
        int[][] dp = new int[cols + 1][maxCost + 2];

        // Initialize DP with unreachable states
        for (int[] row : dp) {
            Arrays.fill(row, Integer.MIN_VALUE);
        }

        dp[1][1] = 0; // starting position

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int value = grid[r][c];

                // max cost to reach (r,c) is r + c
                for (int cost = Math.min(maxCost, r + c); cost >= 0; cost--) {
                    int prevCost = value > 0 ? cost - 1 : cost;

                    dp[c + 1][cost + 1] =
                        Math.max(dp[c + 1][prevCost + 1], dp[c][prevCost + 1]) + value;
                }
            }
        }

        // Get best score at destination
        int result = 0;
        for (int score : dp[cols]) {
            result = Math.max(result, score);
        }

        return result;
    }

    // Minimum cost path (treat values > 0 as cost 1)
    private int minPathCost(int[][] grid) {
        int cols = grid[0].length;
        int[] dp = new int[cols + 1];

        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[1] = 0;

        for (int[] row : grid) {
            for (int c = 0; c < cols; c++) {
                dp[c + 1] = Math.min(dp[c], dp[c + 1])
                          + Math.min(row[c], 1);
            }
        }

        return dp[cols];
    }
}