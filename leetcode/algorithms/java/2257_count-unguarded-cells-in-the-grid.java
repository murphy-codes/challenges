// Source: https://leetcode.com/problems/count-unguarded-cells-in-the-grid/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-02
// At the time of submission:
//   Runtime 17 ms Beats 100.00%
//   Memory 147.00 MB Beats 5.52%

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
    // Sweep in 4 directions from each guard until blocked by wall/guard.
    // Mark visible cells as guarded and count them directly.
    // Result = total cells - (guards + walls + guarded).
    // Time: O(m*n), Space: O(m*n).
    public int countUnguarded(int m, int n, int[][] guards, int[][] walls) {
        int[][] visit = new int[m][n]; // 0 = empty, 1 = guarded, 2 = blocked
        int count = 0;
        int gr = guards.length;
        int wr = walls.length;

        for (int[] w : walls) visit[w[0]][w[1]] = 2;
        for (int[] g : guards) visit[g[0]][g[1]] = 2;

        for (int i = 0; i < gr; i++) {
            int dx = guards[i][0], dy = guards[i][1];

            // ↓ DOWN
            for (int r = dx + 1; r < m; r++) {
                if (visit[r][dy] == 2) break;
                if (visit[r][dy] == 1) continue;
                visit[r][dy] = 1;
                count++;
            }
            // → RIGHT
            for (int c = dy + 1; c < n; c++) {
                if (visit[dx][c] == 2) break;
                if (visit[dx][c] == 1) continue;
                visit[dx][c] = 1;
                count++;
            }
            // ← LEFT
            for (int c = dy - 1; c >= 0; c--) {
                if (visit[dx][c] == 2) break;
                if (visit[dx][c] == 1) continue;
                visit[dx][c] = 1;
                count++;
            }
            // ↑ UP
            for (int r = dx - 1; r >= 0; r--) {
                if (visit[r][dy] == 2) break;
                if (visit[r][dy] == 1) continue;
                visit[r][dy] = 1;
                count++;
            }
        }

        return (m * n) - (gr + wr + count);
    }
}
