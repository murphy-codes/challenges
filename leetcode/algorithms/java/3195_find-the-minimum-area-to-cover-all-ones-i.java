// Source: https://leetcode.com/problems/find-the-minimum-area-to-cover-all-ones-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-22
// At the time of submission:
//   Runtime 5 ms Beats 94.49%
//   Memory 197.00 MB Beats 48.03%

/****************************************
* 
* You are given a 2D binary array `grid`. Find a rectangle with horizontal and
* _ vertical sides with the smallest area, such that all the 1's in `grid` lie
* _ inside this rectangle.
* Return the minimum possible area of the rectangle.
*
* Example 1:
* Input: grid = [[0,1,0],[1,0,1]]
* Output: 6
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2024/05/08/examplerect0.png]
* The smallest rectangle has a height of 2 and a width of 3, so it has an area of `2 * 3 = 6`.
*
* Example 2:
* Input: grid = [[1,0],[0,0]]
* Output: 1
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2024/05/08/examplerect1.png]
* The smallest rectangle has both height and width 1, so its area is `1 * 1 = 1`.
*
* Constraints:
* • 1 <= grid.length, grid[i].length <= 1000
* • `grid[i][j]` is either `0` or `1`.
* • The input is generated such that there is at least one 1 in `grid`.
* 
****************************************/

class Solution {
    // Traverse the grid to find the min/max row and col containing a 1.  
    // These values define the smallest rectangle covering all 1's.  
    // The rectangle's height = (maxRow - minRow + 1), width = (maxCol - minCol + 1).  
    // Final area is height * width.  
    // Time Complexity: O(n*m), since we scan the entire grid once.  
    // Space Complexity: O(1), since only a few variables are stored.
    public int minimumArea(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Initialize bounds with extremes
        int minRow = rows, maxRow = -1;
        int minCol = cols, maxCol = -1;

        // Traverse the grid and track boundaries of 1's
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    minRow = Math.min(minRow, i);
                    maxRow = Math.max(maxRow, i);
                    minCol = Math.min(minCol, j);
                    maxCol = Math.max(maxCol, j);
                }
            }
        }

        // Compute area from bounding rectangle
        int height = maxRow - minRow + 1;
        int width = maxCol - minCol + 1;

        return height * width;
    }
}
