// Source: https://leetcode.com/problems/making-a-large-island/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-31
// At the time of submission:
//   Runtime 70 ms Beats 88.42%
//   Memory 72.53 MB Beats 87.81%

/****************************************
* 
* You are given an `n x n` binary matrix grid. You are allowed to change at most
* _ one `0` to be `1`.
* Return the size of the largest island in `grid` after applying this operation.
* An island is a 4-directionally connected group of `1`s.
*
* Example 1:
* Input: grid = [[1,0],[0,1]]
* Output: 3
* Explanation: Change one 0 to 1 and connect two 1s, then we get an island with area = 3.
*
* Example 2:
* Input: grid = [[1,1],[1,0]]
* Output: 4
* Explanation: Change the 0 to 1 and make the island bigger, only one island with area = 4.
*
* Example 3:
* Input: grid = [[1,1],[1,1]]
* Output: 4
* Explanation: Can't change any 0 to 1, only one island with area = 4.
*
* Constraints:
* • n == grid.length
* • n == grid[i].length
* • 1 <= n <= 500
* • grid[i][j] is either `0` or `1`.
* 
****************************************/

import java.util.HashMap;
import java.util.HashSet;

class Solution {
    // We use DFS to find and label all islands, storing their sizes in a hashmap.  
    // Then, we iterate over all 0s, checking adjacent unique islands to find the  
    // largest possible island size by flipping a single 0. If no 0s exist, we  
    // return the largest existing island. This runs in O(n²) time and space.  
    public int largestIsland(int[][] grid) {
        int n = grid.length;
        HashMap<Integer, Integer> islandSize = new HashMap<>();
        int islandId = 2, maxIsland = 0;

        // Step 1: Identify islands and store their sizes
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 1) {
                    int size = dfs(grid, r, c, islandId);
                    islandSize.put(islandId++, size);
                    maxIsland = Math.max(maxIsland, size);
                }
            }
        }

        // Step 2: Try flipping each 0 and calculate the largest possible island
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 0) {
                    HashSet<Integer> seen = new HashSet<>();
                    int possibleSize = 1; // Count the flipped cell

                    // Check all 4 directions
                    for (int[] d : directions) {
                        int nr = r + d[0], nc = c + d[1];
                        if (nr >= 0 && nr < n && nc >= 0 && nc < n && grid[nr][nc] > 1) {
                            int id = grid[nr][nc];
                            if (seen.add(id)) { // Prevent double-counting
                                possibleSize += islandSize.get(id);
                            }
                        }
                    }
                    maxIsland = Math.max(maxIsland, possibleSize);
                }
            }
        }

        return maxIsland;
    }

    // Directions for up, down, left, right
    private static final int[][] directions = {{0,1},{1,0},{0,-1},{-1,0}};

    // DFS to mark an island and count its size
    private int dfs(int[][] grid, int r, int c, int id) {
        int n = grid.length, size = 1;
        grid[r][c] = id; // Mark with island ID

        for (int[] d : directions) {
            int nr = r + d[0], nc = c + d[1];
            if (nr >= 0 && nr < n && nc >= 0 && nc < n && grid[nr][nc] == 1) {
                size += dfs(grid, nr, nc, id);
            }
        }
        return size;
    }
}
