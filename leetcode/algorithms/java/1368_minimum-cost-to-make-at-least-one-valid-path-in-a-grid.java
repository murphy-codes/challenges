// Source: https://leetcode.com/problems/minimum-cost-to-make-at-least-one-valid-path-in-a-grid/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-17

/****************************************
* 
* Given an `m x n` grid. Each cell of the grid has a sign pointing to the next cell you should visit if you are currently in this cell. The sign of `grid[i][j]` can be:
* 
* • `1` which means go to the cell to the right. (i.e go from `grid[i][j]` to `grid[i][j + 1]`)
* • `2` which means go to the cell to the left. (i.e go from `grid[i][j]` to `grid[i][j - 1]`)
* • `3` which means go to the lower cell. (i.e go from `grid[i][j]` to `grid[i + 1][j]`)
* • `4` which means go to the upper cell. (i.e go from `grid[i][j]` to `grid[i - 1][j]`)
* 
* Notice that there could be some signs on the cells of the grid that point outside the grid.
* 
* You will initially start at the upper left cell `(0, 0)`. A valid path in the grid is a path that starts from the upper left cell `(0, 0)` and ends at the bottom-right cell `(m - 1, n - 1)` following the signs on the grid. The valid path does not have to be the shortest.
* 
* You can modify the sign on a cell with `cost = 1`. You can modify the sign on a cell one time only.
* 
* Return the minimum cost to make the grid have at least one valid path.
* 
* Example 1:
*   [Image depicting the following 16 arrows w/i cells]
*   → → → →
*   ← ← ← ←
*   → → → →
*   ← ← ← ←
* Input: grid = [[1,1,1,1],[2,2,2,2],[1,1,1,1],[2,2,2,2]]
* Output: 3
* Explanation: You will start at point (0, 0).
* The path to (3, 3) is as follows. (0, 0) --> (0, 1) --> (0, 2) --> (0, 3) change the arrow to down with cost = 1 --> (1, 3) --> (1, 2) --> (1, 1) --> (1, 0) change the arrow to down with cost = 1 --> (2, 0) --> (2, 1) --> (2, 2) --> (2, 3) change the arrow to down with cost = 1 --> (3, 3)
* The total cost = 3.
* 
* Example 2:
*   [Image depicting the following 9 arrows w/i cells]
*   → → ↓
*   ↓ ← ←
*   → → ↑ 
* Input: grid = [[1,1,3],[3,2,2],[1,1,4]]
* Output: 0
* Explanation: You can follow the path from (0, 0) to (2, 2).
* 
* Example 3:
*   [Image depicting the following 4 arrows w/i cells]
*   → ←
*   ↑ ↓
* Input: grid = [[1,2],[4,3]]
* Output: 1
* 
* Constraints:
* • m == grid.length
* • n == grid[i].length
* • 1 <= m, n <= 100
* • 1 <= grid[i][j] <= 4
* 
****************************************/

import java.util.Deque;
import java.util.ArrayDeque;

class Solution {
    // We treat this as a graph problem using 0-1 BFS, where each cell is a node and edges represent moves
    // Matching directions have a cost of 0 (front of deque), mismatched directions cost 1 (back of deque)
    // Start from (0, 0) and find the minimum cost to reach (m-1, n-1) by processing each cell once
    // Time: O(m * n), Space: O(m * n)
    public int minCost(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // Directions: right, left, down, up
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        // Deque for 0-1 BFS
        Deque<int[]> deque = new ArrayDeque<>();
        boolean[][] visited = new boolean[m][n];

        // Start from (0, 0) with cost 0
        deque.offer(new int[]{0, 0, 0});

        while (!deque.isEmpty()) {
            int[] cell = deque.poll();
            int x = cell[0], y = cell[1], cost = cell[2];

            // If we reach the bottom-right cell, return the cost
            if (x == m - 1 && y == n - 1) {
                return cost;
            }

            // Mark the current cell as visited
            if (visited[x][y]) continue;
            visited[x][y] = true;

            // Explore all 4 directions
            for (int d = 0; d < 4; d++) {
                int nx = x + directions[d][0];
                int ny = y + directions[d][1];

                // Check if the next cell is within bounds
                if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                    // Cost of moving: 0 if the arrow matches the direction, else 1
                    int newCost = (grid[x][y] == d + 1) ? cost : cost + 1;

                    // Add the cell to the deque
                    if (newCost == cost) {
                        deque.offerFirst(new int[]{nx, ny, newCost});
                    } else {
                        deque.offerLast(new int[]{nx, ny, newCost});
                    }
                }
            }
        }

        return -1; // Shouldn't reach here under valid constraints
    }
}
