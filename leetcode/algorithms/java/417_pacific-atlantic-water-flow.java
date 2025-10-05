// Source: https://leetcode.com/problems/pacific-atlantic-water-flow/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-04
// At the time of submission:
//   Runtime 4 ms Beats 97.67%
//   Memory 45.89 MB Beats 36.97%

/****************************************
* 
* There is an `m x n` rectangular island that borders both the Pacific Ocean and
* _ Atlantic Ocean. The Pacific Ocean touches the island's left and top edges,
* _ and the Atlantic Ocean touches the island's right and bottom edges.
* The island is partitioned into a grid of square cells. You are given an
* _ `m x n` integer matrix `heights` where `heights[r][c]` represents the
* _ height above sea level of the cell at coordinate `(r, c)`.
* The island receives a lot of rain, and the rain water can flow to neighboring
* _ cells directly north, south, east, and west if the neighboring cell's height
* _ is less than or equal to the current cell's height. Water can flow from any
* _ cell adjacent to an ocean into the ocean.
* Return a 2D list of grid coordinates `result` where `result[i] = [r_i, c_i]`
* _ denotes that rain water can flow from cell `(r_i, c_i)` to both the Pacific
* _ and Atlantic oceans.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/06/08/waterflow-grid.jpg]
* Input: heights = [[1,2,2,3,5],[3,2,3,4,4],[2,4,5,3,1],[6,7,1,4,5],[5,1,1,2,4]]
* Output: [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]
* Explanation: The following cells can flow to the Pacific and Atlantic oceans, as shown below:
* [0,4]: [0,4] -> Pacific Ocean
* [0,4] -> Atlantic Ocean
* [1,3]: [1,3] -> [0,3] -> Pacific Ocean
* [1,3] -> [1,4] -> Atlantic Ocean
* [1,4]: [1,4] -> [1,3] -> [0,3] -> Pacific Ocean
* [1,4] -> Atlantic Ocean
* [2,2]: [2,2] -> [1,2] -> [0,2] -> Pacific Ocean
* [2,2] -> [2,3] -> [2,4] -> Atlantic Ocean
* [3,0]: [3,0] -> Pacific Ocean
* [3,0] -> [4,0] -> Atlantic Ocean
* [3,1]: [3,1] -> [3,0] -> Pacific Ocean
* [3,1] -> [4,1] -> Atlantic Ocean
* [4,0]: [4,0] -> Pacific Ocean
* [4,0] -> Atlantic Ocean
* Note that there are other possible paths for these cells to flow to the Pacific and Atlantic oceans.
*
* Example 2:
* Input: heights = [[1]]
* Output: [[0,0]]
* Explanation: The water can flow from the only cell to the Pacific and Atlantic oceans.
*
* Constraints:
* • `m == heights.length`
* • `n == heights[r].length`
* • `1 <= m, n <= 200`
* • `0 <= heights[r][c] <= 10^5`
* 
****************************************/

import java.util.ArrayList;
import java.util.List;

class Solution {
    // Perform DFS outward from both oceans' borders to mark all cells that can
    // reach each ocean by non-decreasing height paths. Then, collect cells that
    // can reach both oceans. Each cell is visited at most twice (once per ocean).
    // Time complexity: O(m * n), Space complexity: O(m * n) for visited grids.
    
    private int m, n;
    private int[][] heights;
    private final int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        this.heights = heights;
        m = heights.length;
        n = heights[0].length;

        boolean[][] pacific = new boolean[m][n];
        boolean[][] atlantic = new boolean[m][n];

        // Run DFS from Pacific (top + left borders)
        for (int i = 0; i < m; i++) dfs(i, 0, pacific, heights[i][0]);
        for (int j = 0; j < n; j++) dfs(0, j, pacific, heights[0][j]);

        // Run DFS from Atlantic (bottom + right borders)
        for (int i = 0; i < m; i++) dfs(i, n - 1, atlantic, heights[i][n - 1]);
        for (int j = 0; j < n; j++) dfs(m - 1, j, atlantic, heights[m - 1][j]);

        // Collect cells reachable by both oceans
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (pacific[i][j] && atlantic[i][j]) {
                    List<Integer> cell = new ArrayList<>();
                    cell.add(i);
                    cell.add(j);
                    result.add(cell);
                }
            }
        }
        return result;
    }

    private void dfs(int r, int c, boolean[][] visited, int prevHeight) {
        // Out of bounds, already visited, or invalid flow (downhill)
        if (r < 0 || c < 0 || r >= m || c >= n || visited[r][c] || heights[r][c] < prevHeight)
            return;

        visited[r][c] = true;
        for (int[] d : dirs) {
            dfs(r + d[0], c + d[1], visited, heights[r][c]);
        }
    }
}
