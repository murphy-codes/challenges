// Source: https://leetcode.com/problems/swim-in-rising-water/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-05
// At the time of submission:
//   Runtime 9 ms Beats 87.79%
//   Memory 44.34 MB Beats 73.74%

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

import java.util.PriorityQueue;

class Solution {
    // Directions for 4-way movement
    private static final int[][] DIRS = {{1,0},{-1,0},{0,1},{0,-1}};
    
    public int swimInWater(int[][] grid) {
        int n = grid.length;
        boolean[][] visited = new boolean[n][n];
        
        // Min-heap: [time, row, col], ordered by smallest time
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{grid[0][0], 0, 0});
        visited[0][0] = true;
        
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int time = cur[0], r = cur[1], c = cur[2];
            
            // If reached destination, this is the minimum possible time
            if (r == n - 1 && c == n - 1) return time;
            
            // Explore 4-directional neighbors
            for (int[] d : DIRS) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    pq.offer(new int[]{Math.max(time, grid[nr][nc]), nr, nc});
                }
            }
        }
        
        return -1; // unreachable, should never happen per constraints
    }
}