// Source: https://leetcode.com/problems/detect-cycles-in-2d-grid/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-26
// At the time of submission:
//   Runtime 24 ms Beats 22.84%
//   Memory 146.74 MB Beats 14.41%

/****************************************
* 
* Given a 2D array of characters `grid` of size `m x n`, you need to find if
* _ there exists any cycle consisting of the same value in `grid`.
* A cycle is a path of length 4 or more in the grid that starts and ends at
* _ the same cell. From a given cell, you can move to one of the cells adjacent
* _ to it - in one of the four directions (up, down, left, or right), if it
* _ has the same value of the current cell.
* Also, you cannot move to the cell that you visited in your last move. For
* _ example, the cycle `(1, 1) -> (1, 2) -> (1, 1)` is invalid because from
* _ `(1, 2)` we visited `(1, 1)` which was the last visited cell.
* Return `true` if any cycle of the same value exists in grid, otherwise,
* _ return `false`.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2020/07/15/1.png]
* Input: grid = [["a","a","a","a"],["a","b","b","a"],["a","b","b","a"],["a","a","a","a"]]
* Output: true
* Explanation: There are two valid cycles shown in different colors in the image below:
* [Image: https://assets.leetcode.com/uploads/2020/07/15/11.png]
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2020/07/15/22.png]
* Input: grid = [["c","c","c","a"],["c","d","c","c"],["c","c","e","c"],["f","c","c","c"]]
* Output: true
* Explanation: There is only one valid cycle highlighted in the image below:
* [Image: https://assets.leetcode.com/uploads/2020/07/15/2.png]
*
* Example 3:
* [Image: https://assets.leetcode.com/uploads/2020/07/15/3.png]
* Input: grid = [["a","b","b"],["b","z","b"],["b","b","a"]]
* Output: false
*
* Constraints:
* • `m == grid.length`
* • `n == grid[i].length`
* • `1 <= m, n <= 500`
* • `grid` consists only of lowercase English letters.
* 
****************************************/

class Solution {
    // Use DFS to explore each connected group of same-character cells.
    // Track the previous (parent) cell so we do not count immediately
    // stepping back as a cycle. If we reach an already visited cell
    // that is not the parent, then a valid cycle exists.
    // Time: O(m * n), Space: O(m * n)

    private int rows;
    private int cols;
    private boolean[][] visited;

    public boolean containsCycle(char[][] grid) {
        rows = grid.length;
        cols = grid[0].length;
        visited = new boolean[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!visited[r][c]) {
                    if (dfs(grid, r, c, -1, -1, grid[r][c])) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean dfs(char[][] grid,
                        int row,
                        int col,
                        int parentRow,
                        int parentCol,
                        char target) {

        visited[row][col] = true;

        int[][] directions = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}
        };

        for (int[] dir : directions) {
            int nextRow = row + dir[0];
            int nextCol = col + dir[1];

            // Skip out-of-bounds cells
            if (nextRow < 0 || nextRow >= rows ||
                nextCol < 0 || nextCol >= cols) {
                continue;
            }

            // Skip different characters
            if (grid[nextRow][nextCol] != target) {
                continue;
            }

            // Skip the immediate parent cell
            if (nextRow == parentRow && nextCol == parentCol) {
                continue;
            }

            // If already visited and not parent, cycle found
            if (visited[nextRow][nextCol]) {
                return true;
            }

            if (dfs(grid, nextRow, nextCol, row, col, target)) {
                return true;
            }
        }

        return false;
    }
}