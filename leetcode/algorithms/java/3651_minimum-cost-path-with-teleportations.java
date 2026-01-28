// Source: https://leetcode.com/problems/minimum-cost-path-with-teleportations/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-27
// At the time of submission:
//   Runtime 31 ms Beats 98.55%
//   Memory 48.22 MB Beats 28.99%

/****************************************
* 
* You are given a `m x n` 2D integer array `grid` and an integer `k`. You start
* _ at the top-left cell `(0, 0)` and your goal is to reach the bottom‐right
* _ cell `(m - 1, n - 1)`.
* There are two types of moves available:
* • Normal move: You can move right or down from your current cell `(i, j)`,
* __ i.e. you can move to `(i, j + 1)` (right) or `(i + 1, j)` (down). The
* __ cost is the value of the destination cell.
* • Teleportation: You can teleport from any cell `(i, j)`, to any cell
* __ `(x, y)` such that `grid[x][y] <= grid[i][j]`; the cost of this move
* __ is 0. You may teleport at most `k` times.
* Return the minimum total cost to reach cell `(m - 1, n - 1)` from `(0, 0)`.
*
* Example 1:
* Input: grid = [[1,3,3],[2,5,4],[4,3,5]], k = 2
* Output: 7
* Explanation:
* Initially we are at (0, 0) and cost is 0.
* Current Position	Move	New Position	Total Cost
* `(0, 0)`	Move Down	`(1, 0)`	0 + 2 = 2
* `(1, 0)`	Move Right	`(1, 1)`	2 + 5 = 7
* `(1, 1)`	Teleport to `(2, 2)`	`(2, 2)`	7 + 0 = 7
* The minimum cost to reach bottom-right cell is 7.
*
* Example 2:
* Input: grid = [[1,2],[2,3],[3,4]], k = 1
* Output: 9
* Explanation:
* Initially we are at (0, 0) and cost is 0.
* Current Position	Move	New Position	Total Cost
* `(0, 0)`	Move Down	`(1, 0)`	`0 + 2 = 2`
* `(1, 0)`	Move Right	`(1, 1)`	`2 + 3 = 5`
* `(1, 1)`	Move Down	`(2, 1)`	`5 + 4 = 9`
* The minimum cost to reach bottom-right cell is 9.
*
* Constraints:
* • `2 <= m, n <= 80`
* • `m == grid.length`
* • `n == grid[i].length`
* • `0 <= grid[i][j] <= 10^4`
* • `0 <= k <= 10`
* 
****************************************/

import java.util.Arrays;

class Solution {
    // We compute the minimum path cost using DP while allowing up to k
    // teleportations. Normal moves are handled via rolling 1D grid DP.
    // Teleportation is optimized by tracking minimum cost per grid value
    // and propagating suffix minimums to allow O(1) teleport transitions.
    // Early stopping avoids unnecessary teleport layers.
    // Time Complexity: O(k * m * n + k * maxValue)
    // Space Complexity: O(maxValue + n)

    public int minCost(int[][] grid, int k) {
        int rows = grid.length;
        int cols = grid[0].length;

        // If we can teleport immediately to the end for free
        if (k > 0 && grid[0][0] >= grid[rows - 1][cols - 1]) {
            return 0;
        }

        // Find maximum grid value
        int maxValue = 0;
        for (int[] row : grid) {
            for (int val : row) {
                maxValue = Math.max(maxValue, val);
            }
        }

        // sufMinCost[v] = min cost reachable with value >= v
        int[] sufMinCost = new int[maxValue + 2];
        Arrays.fill(sufMinCost, Integer.MAX_VALUE);

        // minCostAtValue[v] = min cost to reach any cell with value v
        int[] minCostAtValue = new int[maxValue + 1];

        // dp[col] = min cost to reach this column in current row
        int[] dp = new int[cols + 1];

        for (int used = 0; used <= k; used++) {
            Arrays.fill(minCostAtValue, Integer.MAX_VALUE);
            Arrays.fill(dp, Integer.MAX_VALUE / 2);

            // Offset trick to avoid special-casing (0,0)
            dp[1] = -grid[0][0];

            for (int[] row : grid) {
                for (int j = 0; j < cols; j++) {
                    int cellValue = row[j];

                    dp[j + 1] = Math.min(
                        Math.min(dp[j], dp[j + 1]) + cellValue,
                        sufMinCost[cellValue]
                    );

                    minCostAtValue[cellValue] =
                        Math.min(minCostAtValue[cellValue], dp[j + 1]);
                }
            }

            boolean noChange = true;

            // Update suffix minimums
            for (int v = maxValue; v >= 0; v--) {
                int best = Math.min(sufMinCost[v + 1], minCostAtValue[v]);
                if (best < sufMinCost[v]) {
                    sufMinCost[v] = best;
                    noChange = false;
                }
            }

            // Early exit if teleport adds no improvement
            if (noChange) break;
        }

        return dp[cols];
    }
}
