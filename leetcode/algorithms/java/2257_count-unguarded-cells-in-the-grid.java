// Source: https://leetcode.com/problems/count-unguarded-cells-in-the-grid/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-02
// At the time of submission:
//   Runtime 20 ms Beats 83.45%
//   Memory 146.65 MB Beats 5.52%

/****************************************
* 
* You are given two integers `m` and `n` representing a 0-indexed `m x n` grid.
* _ You are also given two 2D integer arrays `guards` and `walls` where
* _ `guards[i] = [row_i, col_i]` and `walls[j] = [row_j, col_j]` represent the
* _ positions of the `i^th` guard and `j^th` wall respectively.
* A guard can see every cell in the four cardinal directions
* _ (north, east, south, or west) starting from their position unless
* _ obstructed by a wall or another guard. A cell is guarded if there is at
* _ least one guard that can see it.
* Return the number of unoccupied cells that are not guarded.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2022/03/10/example1drawio2.png]
* Input: m = 4, n = 6, guards = [[0,0],[1,1],[2,3]], walls = [[0,1],[2,2],[1,4]]
* Output: 7
* Explanation: The guarded and unguarded cells are shown in red and green respectively in the above diagram.
* There are a total of 7 unguarded cells, so we return 7.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2022/03/10/example2drawio.png]
* Input: m = 3, n = 3, guards = [[1,1]], walls = [[0,1],[1,0],[2,1],[1,2]]
* Output: 4
* Explanation: The unguarded cells are shown in green in the above diagram.
* There are a total of 4 unguarded cells, so we return 4.
*
* Constraints:
* • `1 <= m, n <= 10^5`
* • `2 <= m * n <= 10^5`
* • `1 <= guards.length, walls.length <= 5 * 10^4`
* • `2 <= guards.length + walls.length <= m * n`
* • `guards[i].length == walls[j].length == 2`
* • `0 <= row_i, row_j < m`
* • `0 <= col_i, col_j < n`
* • All the positions in `guards` and `walls` are unique.
* 
****************************************/

class Solution {
    // For each guard, sweep in 4 directions until blocked by wall/guard.
    // Mark all visible cells as guarded and count them on the fly.
    // The total unguarded cells = total - (guards + walls + guarded).
    // Time Complexity: O(m * n) — each cell visited at most once per direction.
    // Space Complexity: O(m * n) — for the grid storage.
    public int countUnguarded(int m, int n, int[][] guards, int[][] walls) {
        int[][] grid = new int[m][n];  // 0 = empty, 1 = guarded, 2 = blocked
        int guardedCount = 0;
        int guardCount = guards.length;
        int wallCount = walls.length;

        // Mark all wall positions as blocked
        for (int[] wall : walls)
            grid[wall[0]][wall[1]] = 2;

        // Mark all guard positions as blocked
        for (int[] guard : guards)
            grid[guard[0]][guard[1]] = 2;

        // Sweep in 4 directions from each guard
        for (int g = 0; g < guardCount; g++) {
            int row = guards[g][0];
            int col = guards[g][1];

            // Downward
            for (int r = row + 1; r < m; r++) {
                if (grid[r][col] == 2) break;          // stop at wall/guard
                if (grid[r][col] == 1) continue;       // already guarded
                grid[r][col] = 1;
                guardedCount++;
            }

            // Rightward
            for (int c = col + 1; c < n; c++) {
                if (grid[row][c] == 2) break;
                if (grid[row][c] == 1) continue;
                grid[row][c] = 1;
                guardedCount++;
            }

            // Leftward
            for (int c = col - 1; c >= 0; c--) {
                if (grid[row][c] == 2) break;
                if (grid[row][c] == 1) continue;
                grid[row][c] = 1;
                guardedCount++;
            }

            // Upward
            for (int r = row - 1; r >= 0; r--) {
                if (grid[r][col] == 2) break;
                if (grid[r][col] == 1) continue;
                grid[r][col] = 1;
                guardedCount++;
            }
        }

        // Total unguarded = total cells - (guards + walls + guarded)
        return (m * n) - (guardCount + wallCount + guardedCount);
    }
}
