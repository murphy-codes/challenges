// Source: https://leetcode.com/problems/trapping-rain-water-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-20
// At the time of submission:
//   Runtime 8 ms Beats 99.76%
//   Memory 48.62 MB Beats 5.16%

/****************************************
* 
* Given an `m x n` integer matrix `heightMap` representing the height of each unit 
* _ cell in a 2D elevation map, return the volume of water it can trap after raining.
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

class Solution {
    // This solution uses iterative constraint propagation to find trapped water.
    // It initializes water levels to terrain heights, then repeatedly updates 
    // each cell's water level based on neighboring constraints until stable.
    // Time complexity is O(m * n * iterations), where iterations depends on 
    // terrain shape but generally converges quickly. Space complexity is O(m * n).
    public int trapRainWater(int[][] heightMap) {
        int rows = heightMap.length;
        int cols = heightMap[0].length;

        // vols[i][j] represents the current water height at cell (i, j),
        // initialized to the terrain height.
        int[][] waterLevel = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                waterLevel[i][j] = heightMap[i][j];
            }
        }

        boolean updated = true;
        boolean isFirstIteration = true;

        // Repeat spreading constraints until no updates happen
        while (updated) {
            updated = false;

            // Spread constraints top-left to bottom-right
            for (int i = 1; i < rows - 1; i++) {
                for (int j = 1; j < cols - 1; j++) {
                    int minNeighborLevel = Math.min(waterLevel[i - 1][j], waterLevel[i][j - 1]);
                    int newLevel = Math.max(heightMap[i][j], minNeighborLevel);

                    if (isFirstIteration || waterLevel[i][j] > newLevel) {
                        waterLevel[i][j] = newLevel;
                        updated = true;
                    }
                }
            }
            isFirstIteration = false;

            // Spread constraints bottom-right to top-left
            for (int i = rows - 2; i >= 1; i--) {
                for (int j = cols - 2; j >= 1; j--) {
                    int minNeighborLevel = Math.min(waterLevel[i + 1][j], waterLevel[i][j + 1]);
                    int newLevel = Math.max(heightMap[i][j], minNeighborLevel);

                    if (waterLevel[i][j] > newLevel) {
                        waterLevel[i][j] = newLevel;
                        updated = true;
                    }
                }
            }
        }

        // Calculate total trapped water volume by summing differences
        int totalWater = 0;
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                if (waterLevel[i][j] > heightMap[i][j]) {
                    totalWater += waterLevel[i][j] - heightMap[i][j];
                }
            }
        }

        return totalWater;
    }
}
