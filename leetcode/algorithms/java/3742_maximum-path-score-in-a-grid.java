// Source: https://leetcode.com/problems/maximum-path-score-in-a-grid/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-30
// At the time of submission:
//   Runtime 331 ms Beats 52.69%
//   Memory 85.96 MB Beats 74.19%

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

class Solution {
    // 3D DP: dp[i][j][c] = max score at (i,j) with cost c.
    // Transition from top/left if cost constraint allows.
    // Track valid states (-1 = unreachable).
    // Time: O(m*n*k), Space: O(m*n*k)
    public int maxPathScore(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;

        // dp[i][j][c] = max score at (i,j) with cost c
        int[][][] dp = new int[m][n][k + 1];

        // Initialize all states as unreachable
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int c = 0; c <= k; c++) {
                    dp[i][j][c] = -1;
                }
            }
        }

        // Starting point
        dp[0][0][0] = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                int cellCost = (grid[i][j] == 0) ? 0 : 1;
                int cellScore = grid[i][j];

                for (int c = 0; c <= k; c++) {

                    if (i == 0 && j == 0) continue;

                    // From top
                    if (i > 0 && c >= cellCost && dp[i - 1][j][c - cellCost] != -1) {
                        dp[i][j][c] = Math.max(
                            dp[i][j][c],
                            dp[i - 1][j][c - cellCost] + cellScore
                        );
                    }

                    // From left
                    if (j > 0 && c >= cellCost && dp[i][j - 1][c - cellCost] != -1) {
                        dp[i][j][c] = Math.max(
                            dp[i][j][c],
                            dp[i][j - 1][c - cellCost] + cellScore
                        );
                    }
                }
            }
        }

        // Find best result at destination
        int result = -1;
        for (int c = 0; c <= k; c++) {
            result = Math.max(result, dp[m - 1][n - 1][c]);
        }

        return result;
    }
}