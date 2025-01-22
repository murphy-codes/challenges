// Source: https://leetcode.com/problems/map-of-highest-peak/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-21

/****************************************
* 
* You are given an integer matrix `isWater` of size `m x n` that represents a map of land and water cells.
* 
* If `isWater[i][j] == 0`, cell `(i, j)` is a land cell.
* If `isWater[i][j]` == 1, cell `(i, j)` is a water cell.
* You must assign each cell a height in a way that follows these rules:
* 
* • The height of each cell must be non-negative.
* • If the cell is a water cell, its height must be `0`.
* • Any two adjacent cells must have an absolute height difference of at most `1`. A cell is adjacent to another cell if the former is directly north, east, south, or west of the latter (i.e., their sides are touching).
* 
* Find an assignment of heights such that the maximum height in the matrix is maximized.
* 
* Return an integer matrix `height` of size `m x n` where `height[i][j]` is cell `(i, j)`'s height. If there are multiple solutions, return any of them.
* 
* Example 1:
*   [Image depicting a 2x2 matrix, with the top right being a water cell.]
* Input: isWater = [[0,1],[0,0]]
* Output: [[1,0],[2,1]]
* Explanation: The image shows the assigned heights of each cell.
* The blue cell is the water cell, and the green cells are the land cells.
* 
* Example 2:
*   [Image depicting a 3x3 matrix, with the top right and middle left being water cells.]
* Input: isWater = [[0,0,1],[1,0,0],[0,0,0]]
* Output: [[1,1,0],[0,1,1],[1,2,2]]
* Explanation: A height of 2 is the maximum possible height of any assignment.
* Any height assignment that has a maximum height of 2 while still meeting the rules will also be accepted.
* 
* Constraints:
* • m == isWater.length
* • n == isWater[i].length
* • 1 <= m, n <= 1000
* • `isWater[i][j]` is `0` or `1`.
* • There is at least one water cell.
* 
* Note: This question is the same as 542: https://leetcode.com/problems/01-matrix/
* 
****************************************/

import java.util.Queue;
import java.util.LinkedList;

class Solution {
    // Use multi-source BFS to assign heights starting from water cells (height = 0)
    // Heights propagate outward from water cells, incrementing by 1 for each step
    // Ensure each cell's height differs by at most 1 from its neighbors
    // BFS guarantees the shortest path to the nearest water cell for every land cell
    // Time: O(m * n), Space: O(m * n)
    public int[][] highestPeak(int[][] isWater) {
        int m = isWater.length;
        int n = isWater[0].length;
        int[][] height = new int[m][n];
        for (int[] row : height) {
            Arrays.fill(row, -1); // Mark all cells as unvisited initially
        }
        
        Queue<int[]> queue = new LinkedList<>();
        
        // Initialize BFS with all water cells
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (isWater[i][j] == 1) {
                    queue.offer(new int[] {i, j});
                    height[i][j] = 0; // Water cells have height 0
                }
            }
        }
        
        // Directions for north, south, east, west
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        // Perform BFS
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int currX = cell[0], currY = cell[1];
            for (int[] dir : directions) {
                int newX = currX + dir[0];
                int newY = currY + dir[1];
                // Check bounds and if the cell is unvisited
                if (newX >= 0 && newX < m && newY >= 0 && newY < n && height[newX][newY] == -1) {
                    height[newX][newY] = height[currX][currY] + 1;
                    queue.offer(new int[] {newX, newY});
                }
            }
        }
        
        return height;
    }
}
