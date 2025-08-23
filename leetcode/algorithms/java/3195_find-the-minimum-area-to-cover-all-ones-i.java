// Source: https://leetcode.com/problems/find-the-minimum-area-to-cover-all-ones-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-22
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 196.96 MB Beats 48.03%

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
    // This solution finds the bounding rectangle of all 1's by scanning
    // from each direction (top, bottom, left, right) and stopping early
    // as soon as the first 1 is found. This reduces unnecessary work.
    // Time complexity: O(rows * cols) in worst case (must scan all cells).
    // Space complexity: O(1), since only a few integers/flags are used.
    public int minimumArea(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        int topRow = 0, bottomRow = 0, leftCol = 0, rightCol = 0;
        boolean found = false;

        // Find the first row that contains a 1
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    topRow = i;
                    found = true;
                    break;
                }
            }
            if (found) break;
        }

        found = false;
        // Find the last row that contains a 1
        for (int i = rows - 1; i >= 0; i--) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    bottomRow = i;
                    found = true;
                    break;
                }
            }
            if (found) break;
        }

        found = false;
        // Find the first column that contains a 1
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (grid[j][i] == 1) {
                    leftCol = i;
                    found = true;
                    break;
                }
            }
            if (found) break;
        }

        found = false;
        // Find the last column that contains a 1
        for (int i = cols - 1; i >= 0; i--) {
            for (int j = 0; j < rows; j++) {
                if (grid[j][i] == 1) {
                    rightCol = i;
                    found = true;
                    break;
                }
            }
            if (found) break;
        }

        // Compute area = width × height
        return (rightCol - leftCol + 1) * (bottomRow - topRow + 1);
    }
}
