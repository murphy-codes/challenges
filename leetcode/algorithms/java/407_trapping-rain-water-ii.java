// Source: https://leetcode.com/problems/trapping-rain-water-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-20

/****************************************
* 
* Given an `m x n` integer matrix `heightMap` representing the height of each unit cell in a 2D elevation map, return the volume of water it can trap after raining.
* 
* Example 1:
*   [Image depicting the height map as grid of cubes stacked on top of each other]
* Input: heightMap = [[1,4,3,1,3,2],[3,2,1,3,2,4],[2,3,3,2,3,1]]
* Output: 4
* Explanation: After the rain, water is trapped between the blocks.
* We have two small ponds 1 and 3 units trapped.
* The total volume of water trapped is 4.
* 
* Example 2:
*   [Image depicting the height map as grid of cubes stacked on top of each other]
* Input: heightMap = [[3,3,3,3,3],[3,2,2,2,3],[3,2,1,2,3],[3,2,2,2,3],[3,3,3,3,3]]
* Output: 10
* 
* Constraints:
* • m == heightMap.length
* • n == heightMap[i].length
* • 1 <= m, n <= 200
* • 0 <= heightMap[i][j] <= 2 * 10^4
* 
****************************************/

import java.util.PriorityQueue;

class Solution {
    // Solve using a min-heap (priority queue) to simulate water flow from the outer boundary inward
    // Push all boundary cells into the heap, keeping track of the min-height boundary at each step
    // Process cells by popping the lowest height, updating trapped water, and adding neighbors
    // Time: O(m * n * log(m * n)), Space: O(m * n)
    public int trapRainWater(int[][] heightMap) {
        if (heightMap == null || heightMap.length == 0 || heightMap[0].length == 0) {
            return 0;
        }

        int m = heightMap.length;
        int n = heightMap[0].length;
        boolean[][] visited = new boolean[m][n];
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        // Add all boundary cells to the priority queue
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || i == m - 1 || j == 0 || j == n - 1) {
                    pq.offer(new int[]{heightMap[i][j], i, j});
                    visited[i][j] = true;
                }
            }
        }

        int totalWater = 0;
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        // Process cells in the priority queue
        while (!pq.isEmpty()) {
            int[] cell = pq.poll();
            int height = cell[0], x = cell[1], y = cell[2];

            // Visit all unvisited neighbors
            for (int[] dir : directions) {
                int nx = x + dir[0];
                int ny = y + dir[1];

                if (nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny]) {
                    visited[nx][ny] = true;

                    // If the neighbor is lower, trap water
                    totalWater += Math.max(0, height - heightMap[nx][ny]);

                    // Push the neighbor into the queue with updated height
                    pq.offer(new int[]{Math.max(height, heightMap[nx][ny]), nx, ny});
                }
            }
        }

        return totalWater;
    }
}
