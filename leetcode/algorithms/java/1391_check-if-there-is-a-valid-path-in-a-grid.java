// Source: https://leetcode.com/problems/check-if-there-is-a-valid-path-in-a-grid/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-26
// At the time of submission:
//   Runtime 17 ms Beats 80.09%
//   Memory 102.40 MB Beats 33.70%

/****************************************
* 
* You are given an `m x n` `grid`. Each cell of `grid` represents a street.
* _ The street of `grid[i][j]` can be:
* • `1` which means a street connecting the left cell and the right cell.
* • `2` which means a street connecting the upper cell and the lower cell.
* • `3` which means a street connecting the left cell and the lower cell.
* • `4` which means a street connecting the right cell and the lower cell.
* • `5` which means a street connecting the left cell and the upper cell.
* • `6` which means a street connecting the right cell and the upper cell.
* [Image: https://assets.leetcode.com/uploads/2020/03/05/main.png]
* You will initially start at the street of the upper-left cell `(0, 0)`.
* _ A valid path in the grid is a path that starts from the upper left cell
* _ `(0, 0)` and ends at the bottom-right cell `(m - 1, n - 1)`. The path
* _ should only follow the streets.
* Notice that you are not allowed to change any street.
* Return `true` if there is a valid path in the grid or `false` otherwise.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2020/03/05/e1.png]
* Input: grid = [[2,4,3],[6,5,2]]
* Output: true
* Explanation: As shown you can start at cell (0, 0) and visit all the cells of the grid to reach (m - 1, n - 1).
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2020/03/05/e2.png]
* Input: grid = [[1,2,1],[1,2,1]]
* Output: false
* Explanation: As shown you the street at cell (0, 0) is not connected with any street of any other cell and you will get stuck at cell (0, 0)
*
* Example 3:
* Input: grid = [[1,1,2]]
* Output: false
* Explanation: You will get stuck at cell (0, 1) and you cannot reach cell (0, 2).
*
* Constraints:
* • `m == grid.length`
* • `n == grid[i].length`
* • `1 <= m, n <= 300`
* • `1 <= grid[i][j] <= 6`
* 
****************************************/

class Solution {
    // Use DFS starting from (0,0), following only the directions
    // allowed by the current street type. A move is valid only if
    // the neighboring cell also connects back in the opposite direction.
    // If we can reach the bottom-right cell, a valid path exists.
    // Time: O(m * n), Space: O(m * n)
    
    // Direction order:
    // 0 = up, 1 = right, 2 = down, 3 = left
    private final int[][] directions = {
        {-1, 0}, // up
        {0, 1},  // right
        {1, 0},  // down
        {0, -1}  // left
    };

    // For each street type, define which directions are allowed
    private final int[][] streetDirs = {
        {},         // dummy for 0-index alignment
        {1, 3},     // type 1: left <-> right
        {0, 2},     // type 2: up <-> down
        {2, 3},     // type 3: left <-> down
        {1, 2},     // type 4: right <-> down
        {0, 3},     // type 5: left <-> up
        {0, 1}      // type 6: right <-> up
    };

    public boolean hasValidPath(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        boolean[][] visited = new boolean[rows][cols];

        return dfs(grid, 0, 0, visited, rows, cols);
    }

    private boolean dfs(int[][] grid,
                        int row,
                        int col,
                        boolean[][] visited,
                        int rows,
                        int cols) {

        // Reached destination
        if (row == rows - 1 && col == cols - 1) {
            return true;
        }

        visited[row][col] = true;

        int streetType = grid[row][col];

        // Try both valid directions for current street
        for (int dir : streetDirs[streetType]) {
            int nextRow = row + directions[dir][0];
            int nextCol = col + directions[dir][1];

            // Out of bounds
            if (nextRow < 0 || nextRow >= rows ||
                nextCol < 0 || nextCol >= cols) {
                continue;
            }

            // Already visited
            if (visited[nextRow][nextCol]) {
                continue;
            }

            // Check if neighbor connects back
            int oppositeDir = (dir + 2) % 4;
            if (!canConnect(grid[nextRow][nextCol], oppositeDir)) {
                continue;
            }

            if (dfs(grid, nextRow, nextCol, visited, rows, cols)) {
                return true;
            }
        }

        return false;
    }

    private boolean canConnect(int streetType, int neededDir) {
        for (int dir : streetDirs[streetType]) {
            if (dir == neededDir) {
                return true;
            }
        }
        return false;
    }
}