// Source: https://leetcode.com/problems/maximum-number-of-points-from-grid-queries/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-03-28
// At the time of submission:
//   Runtime 99 ms Beats 56.39%
//   Memory 58.65 MB Beats 46.62%

/****************************************
* 
* You are given an `m x n` integer matrix `grid` and an array `queries` of size `k`.
* Find an array `answer` of size `k` such that for each integer `queries[i]` you start
* in the top left cell of the matrix and repeat the following process:
* • If `queries[i]` is strictly greater than the value of the current cell that you are in,
* then you get one point if it is your first time visiting this cell, and you can move
* to any adjacent cell in all `4` directions: up, down, left, and right.
* • Otherwise, you do not get any points, and you end this process.
* After the process, `answer[i]` is the maximum number of points you can get.
* Note that for each query you are allowed to visit the same cell multiple times.
* Return the resulting array `answer`.
*
* Example 1:
* Input: grid = [[1,2,3],[2,5,7],[3,5,1]], queries = [5,6,2]
* Output: [5,8,1]
* Explanation: The diagrams above show which cells we visit to get points for each query.
*
* Example 2:
* Input: grid = [[5,2,1],[1,1,2]], queries = [3]
* Output: [0]
* Explanation: We can not get any points because the value of the top left cell is already greater than or equal to 3.
*
* Constraints:
* • m == grid.length
* • n == grid[i].length
* • 2 <= m, n <= 1000
* • 4 <= m * n <= 10^5
* • k == queries.length
* • 1 <= k <= 10^4
* • 1 <= grid[i][j], queries[i] <= 10^6
* 
****************************************/

import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.HashMap;

class Solution {
    // We use a priority queue (min-heap) to process grid cells in increasing order.
    // For each query, we expand from the smallest unprocessed cell, marking valid
    // cells and counting points. Sorting queries and using a greedy approach ensures
    // each query processes only necessary cells, optimizing efficiency.
    // Time complexity: O(m * n log(m * n) + k log k), Space complexity: O(m * n + k).
    public int[] maxPoints(int[][] grid, int[] queries) {
        int m = grid.length, n = grid[0].length;
        int[] result = new int[queries.length];
        
        // Sort queries and store original indices
        int[][] sortedQueries = new int[queries.length][2];
        for (int i = 0; i < queries.length; i++) {
            sortedQueries[i][0] = queries[i];
            sortedQueries[i][1] = i;
        }
        Arrays.sort(sortedQueries, (a, b) -> Integer.compare(a[0], b[0]));

        // Min-Heap for BFS (stores [cell value, row, col])
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        pq.offer(new int[]{grid[0][0], 0, 0});

        // Visited matrix
        boolean[][] visited = new boolean[m][n];
        visited[0][0] = true;

        int points = 0, lastQueryVal = 0;
        int[] directions = {-1, 0, 1, 0, -1}; // For 4-directional movement

        // Process sorted queries
        for (int[] query : sortedQueries) {
            int queryValue = query[0];
            int queryIndex = query[1];

            // Expand BFS while the top of heap is within the query value
            while (!pq.isEmpty() && pq.peek()[0] < queryValue) {
                int[] cell = pq.poll();
                int value = cell[0], r = cell[1], c = cell[2];

                // Increment points for this valid cell
                points++;

                // Explore 4 possible directions
                for (int d = 0; d < 4; d++) {
                    int nr = r + directions[d], nc = c + directions[d + 1];

                    if (nr >= 0 && nr < m && nc >= 0 && nc < n && !visited[nr][nc]) {
                        visited[nr][nc] = true;
                        pq.offer(new int[]{grid[nr][nc], nr, nc});
                    }
                }
            }
            result[queryIndex] = points;
        }
        return result;
    }
}
