// Source: https://leetcode.com/problems/maximum-number-of-fish-in-a-grid/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-28
// At the time of submission:
//   Runtime 6 ms Beats 33.66%
//   Memory 45.13 MB Beats 42.62%

/****************************************
* 
* You are given a 0-indexed 2D matrix `grid` of size `m x n`, where `(r, c)` represents:
* • A land cell if grid[r][c] = 0, or
* • A water cell containing `grid[r][c]` fish, if `grid[r][c]` > 0.
* 
* A fisher can start at any water cell `(r, c)` and can do the following operations any number of times:
* • Catch all the fish at cell (r, c), or
* • Move to any adjacent water cell.
* 
* Return the maximum number of fish the fisher can catch if he chooses his starting cell optimally, or `0` if no water cell exists.
* 
* An adjacent cell of the cell `(r, c)`, is one of the cells `(r, c + 1)`, `(r, c - 1)`, `(r + 1, c)` or `(r - 1, c)` if it exists.
* 
* Example 1:
*   [Image depicting a 4x4 grid: https://assets.leetcode.com/uploads/2023/03/29/example.png]
* Input: grid = [[0,2,1,0],[4,0,0,3],[1,0,0,4],[0,3,2,0]]
* Output: 7
* Explanation: The fisher can start at cell (1,3) and collect 3 fish, then move to cell (2,3) and collect 4 fish.
* 
* Example 2:
*   [Image depicting a 4x4 grid, with all 0s except for a 1 in the top left and the bottom right cells
*   https://assets.leetcode.com/uploads/2023/03/29/example2.png]
* Input: grid = [[1,0,0,0],[0,0,0,0],[0,0,0,0],[0,0,0,1]]
* Output: 1
* Explanation: The fisher can start at cells (0,0) or (3,3) and collect a single fish. 
* 
* Constraints:
* • m == grid.length
* • n == grid[i].length
* • 1 <= m, n <= 10
* • 0 <= grid[i][j] <= 10
* 
****************************************/

class Solution {
    // This solution uses Depth First Search (DFS) to traverse the grid and calculate the sum of fish in each connected component of water cells.
    // The grid is iterated cell by cell, and DFS is initiated from each unvisited water cell, marking cells as visited to avoid reprocessing.
    // Each DFS call explores all adjacent water cells, summing up the fish in the component (or "pond") and updating the maximum fish count.
    // Time complexity: O(m * n), as each cell is visited at most once during the DFS traversal.
    // Space complexity: O(m * n) due to the visited array and recursion stack for the DFS calls.
    public int findMaxFish(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        int maxFish = 0;

        // Directions for moving up, down, left, right
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        // Iterate through the grid
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] > 0 && !visited[i][j]) {
                    // Start DFS from each unvisited water cell
                    maxFish = Math.max(maxFish, dfs(i, j, grid, visited, directions));
                }
            }
        }

        return maxFish;
    }

    // Helper method for DFS traversal
    private int dfs(int r, int c, int[][] grid, boolean[][] visited, int[][] directions) {
        int m = grid.length;
        int n = grid[0].length;

        if (r < 0 || r >= m || c < 0 || c >= n || grid[r][c] == 0 || visited[r][c]) {
            return 0;
        }

        visited[r][c] = true; // Mark cell as visited
        int fishCount = grid[r][c];
        for (int[] dir : directions) {
            fishCount += dfs(r + dir[0], c + dir[1], grid, visited, directions);
        }
        return fishCount;
    }
}
