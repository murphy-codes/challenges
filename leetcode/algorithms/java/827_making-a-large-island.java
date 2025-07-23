// Source: https://leetcode.com/problems/making-a-large-island/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-31
// At the time of submission:
//   Runtime 22 ms Beats 100.00%
//   Memory 80.46 MB Beats 41.77%

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

class Solution {
    // This solution labels each island uniquely using DFS, storing their sizes in a static
    // array to optimize lookups. Then, for each zero cell, it calculates the potential
    // island size by merging adjacent islands (without double-counting duplicates). It
    // returns the max such size or the largest existing island if no zeros exist.
    // Time complexity: O(n^2), since DFS visits all cells once and each zero checks up to 4 neighbors.
    // Space complexity: O(n^2), due to grid modifications and the static island area array.

    // Large enough array for island areas to avoid hashmap overhead.
    static int[] islandAreas = new int[500 * 500 / 2 + 2];
    int islandId;
    int maxRow;
    int maxCol;

    public int largestIsland(int[][] grid) {
        islandId = 2; // Start labeling islands from 2 (to avoid confusion with 0 and 1)
        islandAreas[2] = 0; // Default area for first island
        maxRow = grid.length - 1;
        maxCol = grid[0].length - 1;

        // Label islands with unique ids and record their sizes
        for (int r = 0; r <= maxRow; r++) {
            int[] row = grid[r];
            for (int c = 0; c <= maxCol; c++) {
                if (row[c] == 1) {
                    islandAreas[islandId] = dfsMarkIsland(grid, r, c);
                    islandId++;
                }
            }
        }

        int maxArea = islandAreas[2]; // If no zeros, largest island is the max area found so far

        // Check every zero cell: calculate potential island size if flipped
        for (int r = 0; r <= maxRow; r++) {
            int[] row = grid[r];
            for (int c = 0; c <= maxCol; c++) {
                if (row[c] == 0) {
                    int area = 1; // Flipping this cell to 1
                    int idxUp = 0, idxLeft = 0, idxDown = 0;

                    // Check up neighbor
                    if (r > 0) {
                        int id = grid[r - 1][c];
                        if (id > 0) {
                            area += islandAreas[id];
                            idxUp = id;
                        }
                    }
                    // Check left neighbor (avoid double-count)
                    if (c > 0) {
                        int id = row[c - 1];
                        if (id > 0 && id != idxUp) {
                            area += islandAreas[id];
                            idxLeft = id;
                        }
                    }
                    // Check down neighbor (avoid double-count)
                    if (r < maxRow) {
                        int id = grid[r + 1][c];
                        if (id > 0 && id != idxUp && id != idxLeft) {
                            area += islandAreas[id];
                            idxDown = id;
                        }
                    }
                    // Check right neighbor (avoid double-count)
                    if (c < maxCol) {
                        int id = row[c + 1];
                        if (id > 0 && id != idxUp && id != idxLeft && id != idxDown) {
                            area += islandAreas[id];
                        }
                    }

                    if (area > maxArea) maxArea = area;
                }
            }
        }

        return maxArea;
    }

    // DFS to mark island cells with islandId and return island size
    private int dfsMarkIsland(int[][] grid, int r, int c) {
        int area = 1;
        grid[r][c] = islandId;

        if (r > 0 && grid[r - 1][c] == 1) area += dfsMarkIsland(grid, r - 1, c);
        if (c > 0 && grid[r][c - 1] == 1) area += dfsMarkIsland(grid, r, c - 1);
        if (r < maxRow && grid[r + 1][c] == 1) area += dfsMarkIsland(grid, r + 1, c);
        if (c < maxCol && grid[r][c + 1] == 1) area += dfsMarkIsland(grid, r, c + 1);

        return area;
    }
}
