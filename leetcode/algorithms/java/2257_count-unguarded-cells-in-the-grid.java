// Source: https://leetcode.com/problems/count-unguarded-cells-in-the-grid/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-01
// At the time of submission:
//   Runtime 25 ms Beats 56.93%
//   Memory 147.24 MB Beats 5.11%

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

import java.util.List;

class Solution {
    // This solution marks visibility for each guard in four directions until blocked.
    // Each guard "sweeps" its row/column, marking visible cells as guarded.
    // The grid is scanned once more to count unguarded empty cells.
    // Time Complexity: O(m * n) — each cell processed at most once per direction.
    // Space Complexity: O(m * n) — to store the grid state.
    public int countUnguarded(int m, int n, int[][] guards, int[][] walls) {
        int[][] grid = new int[m][n];

        // 1 = wall, 2 = guard, 3 = guarded
        for (int[] wall : walls)
            grid[wall[0]][wall[1]] = 1;
        for (int[] guard : guards)
            grid[guard[0]][guard[1]] = 2;

        // Directions: up, down, left, right
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] g : guards) {
            int r = g[0], c = g[1];
            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                while (nr >= 0 && nr < m && nc >= 0 && nc < n && grid[nr][nc] != 1 && grid[nr][nc] != 2) {
                    if (grid[nr][nc] == 0)
                        grid[nr][nc] = 3;  // Mark as guarded
                    nr += d[0];
                    nc += d[1];
                }
            }
        }

        int unguarded = 0;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (grid[i][j] == 0)
                    unguarded++;

        return unguarded;
    }
}
