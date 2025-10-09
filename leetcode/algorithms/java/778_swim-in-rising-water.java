// Source: https://leetcode.com/problems/swim-in-rising-water/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-05
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 44.12 MB Beats 92.63%

/****************************************
* 
* You are given an `n x n` integer matrix `grid` where each value `grid[i][j]`
* _ represents the elevation at that point `(i, j)`.
* It starts raining, and water gradually rises over time. At time `t`, the water
* _ level is `t`, meaning any cell with elevation less than equal to `t` is
* _ submerged or reachable.
* You can swim from a square to another 4-directionally adjacent square if and
* _ only if the elevation of both squares individually are at most `t`. You can
* _ swim infinite distances in zero time. Of course, you must stay within the
* _ boundaries of the grid during your swim.
* Return the minimum time until you can reach the bottom right square
* _ `(n - 1, n - 1)` if you start at the top left square `(0, 0)`.
*
* Example 1:
* Input: grid = [[0,2],[1,3]]
* Output: 3
* Explanation:
* At time 0, you are in grid location (0, 0).
* You cannot go anywhere else because 4-directionally adjacent neighbors have a
* _ higher elevation than t = 0.
* You cannot reach point (1, 1) until time 3.
* When the depth of water is 3, we can swim anywhere inside the grid.
*
* Example 2:
* Input: grid = [[0,1,2,3,4],[24,23,22,21,5],[12,13,14,15,16],[11,17,18,19,20],[10,9,8,7,6]]
* Output: 16
* Explanation: The final route is shown.
* We need to wait until time 16 so that (0, 0) and (4, 4) are connected.
*
* Constraints:
* • `n == grid.length`
* • `n == grid[i].length`
* • `1 <= n <= 50`
* • `0 <= grid[i][j] < n^2`
* • Each value `grid[i][j]` is unique.
* 
****************************************/

class Solution {
    // Binary search the minimum time 't' such that (n-1,n-1) is reachable
    // via DFS, only moving through cells ≤ t. Each DFS checks connectivity
    // under the current water level. Once reachable, search for smaller t.
    // Time: O(n² log n), Space: O(n²) due to recursion and visited tracking.

    private int n;
    private static final int[][] DIRS = {{1,0},{0,1},{-1,0},{0,-1}};
    
    public int swimInWater(int[][] grid) {
        n = grid.length;
        
        // Binary search over possible time values (water levels)
        int left = Math.max(grid[0][0], grid[n - 1][n - 1]);
        int right = n * n - 1;
        int result = 0;
        
        while (left <= right) {
            int mid = (left + right) / 2;
            boolean[] visited = new boolean[n * n];
            
            // If destination is reachable at this time, try smaller time
            if (dfs(0, 0, grid, mid, visited)) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        return result;
    }
    
    private boolean dfs(int x, int y, int[][] grid, int waterLevel, boolean[] visited) {
        int index = x * n + y;
        visited[index] = true;
        
        // If reached destination, path is valid under current water level
        if (x == n - 1 && y == n - 1) return true;
        
        // Explore 4-directionally adjacent cells
        for (int[] dir : DIRS) {
            int nx = x + dir[0], ny = y + dir[1];
            if (nx >= 0 && nx < n && ny >= 0 && ny < n &&
                !visited[nx * n + ny] && grid[nx][ny] <= waterLevel) {
                
                if (dfs(nx, ny, grid, waterLevel, visited)) return true;
            }
        }
        return false;
    }
}