// Source: https://leetcode.com/problems/minimum-cost-path-with-teleportations/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-27
// At the time of submission:
//   Runtime 67 ms Beats 97.17%
//   Memory 275.87 MB Beats 36.03%

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class Solution {
    // We use dynamic programming where costs[i][j] is the minimum cost to
    // reach the bottom-right cell from (i, j). Teleportation is handled by
    // sorting all cells by value and propagating the minimum reachable cost
    // across all cells with grid value less than or equal to the current one.
    // This avoids enumerating all teleport destinations explicitly.
    // Time Complexity: O((k + log(mn)) * m * n)
    // Space Complexity: O(m * n)

    public int minCost(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        // Collect all cells and sort them by grid value (ascending)
        List<int[]> cells = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                cells.add(new int[]{i, j});
            }
        }
        cells.sort(Comparator.comparingInt(c -> grid[c[0]][c[1]]));

        // costs[i][j] = minimum cost to reach bottom-right from (i, j)
        int[][] costs = new int[m][n];
        for (int[] row : costs) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        // Repeat DP for up to k teleportations
        for (int used = 0; used <= k; used++) {

            // Handle teleport transitions in sorted order
            int minCostSoFar = Integer.MAX_VALUE;
            int start = 0;

            for (int i = 0; i < cells.size(); i++) {
                int x = cells.get(i)[0];
                int y = cells.get(i)[1];
                minCostSoFar = Math.min(minCostSoFar, costs[x][y]);

                // If next cell has the same grid value, delay updates
                if (i + 1 < cells.size() &&
                    grid[x][y] == grid[cells.get(i + 1)[0]][cells.get(i + 1)[1]]) {
                    continue;
                }

                // Apply teleport relaxation to all cells with this value
                for (int r = start; r <= i; r++) {
                    int cx = cells.get(r)[0];
                    int cy = cells.get(r)[1];
                    costs[cx][cy] = minCostSoFar;
                }
                start = i + 1;
            }

            // Handle normal moves (right and down)
            for (int i = m - 1; i >= 0; i--) {
                for (int j = n - 1; j >= 0; j--) {
                    if (i == m - 1 && j == n - 1) {
                        costs[i][j] = 0;
                        continue;
                    }
                    if (i + 1 < m) {
                        costs[i][j] = Math.min(
                            costs[i][j],
                            costs[i + 1][j] + grid[i + 1][j]
                        );
                    }
                    if (j + 1 < n) {
                        costs[i][j] = Math.min(
                            costs[i][j],
                            costs[i][j + 1] + grid[i][j + 1]
                        );
                    }
                }
            }
        }

        return costs[0][0];
    }
}
