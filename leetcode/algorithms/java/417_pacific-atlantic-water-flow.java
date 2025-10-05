// Source: https://leetcode.com/problems/pacific-atlantic-water-flow/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-04
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 46.32 MB Beats 8.41%

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
import java.util.AbstractList;

class Solution {
    // Perform reverse DFS from Pacific and Atlantic borders to find cells that
    // can flow to both oceans. A shared visited matrix tracks ocean reachability.
    // When a cell is visited by both Pacific ('P') and Atlantic ('A'), it is added
    // to the result. Lazy evaluation via AbstractList defers computation until use.
    // Time complexity: O(m*n) since each cell is visited at most twice.
    // Space complexity: O(m*n) for recursion stack and visited tracking.

    List<List<Integer>> pacificAtlantic(int[][] heights) {
        // Use lazy evaluation: the solution is computed only when needed
        return new AbstractList<List<Integer>>() {
            private List<List<Integer>> result;

            public List<Integer> get(int i) {
                init();
                return result.get(i);
            }

            public int size() {
                init();
                return result.size();
            }

            private void init() {
                if (result == null) {
                    result = solve(heights);
                }
            }
        };
    }

    // Directions for 4-way movement (up, down, left, right)
    private static final int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    
    public List<List<Integer>> solve(int[][] heights) {
        List<List<Integer>> result = new ArrayList<>();
        if (heights == null || heights.length == 0) return result;

        int m = heights.length, n = heights[0].length;
        char[][] visited = new char[m][n];

        // DFS from Pacific edges (top and left)
        for (int col = 0; col < n; col++) dfs(heights, 0, col, visited, 'P', result);
        for (int row = 0; row < m; row++) dfs(heights, row, 0, visited, 'P', result);

        // DFS from Atlantic edges (bottom and right)
        for (int col = 0; col < n; col++) dfs(heights, m - 1, col, visited, 'A', result);
        for (int row = 0; row < m; row++) dfs(heights, row, n - 1, visited, 'A', result);

        return result;
    }

    private void dfs(int[][] heights, int row, int col,
                     char[][] visited, char ocean, List<List<Integer>> result) {

        // If this cell already reached Pacific and now reaches Atlantic, add it
        if (visited[row][col] == 'P' && ocean == 'A') {
            result.add(new ArrayList<Integer>() {{
                add(row);
                add(col);
            }});
        }

        // Mark cell as visited for the current ocean
        visited[row][col] = ocean;

        // Explore all 4 directions
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            // Continue if within bounds, can flow upward (non-decreasing),
            // and hasn't already been visited by this ocean
            if (newRow >= 0 && newRow < heights.length &&
                newCol >= 0 && newCol < heights[0].length &&
                heights[row][col] <= heights[newRow][newCol] &&
                visited[newRow][newCol] != ocean) {

                dfs(heights, newRow, newCol, visited, ocean, result);
            }
        }
    }
}
