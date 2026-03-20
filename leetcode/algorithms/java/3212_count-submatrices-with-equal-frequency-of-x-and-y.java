// Source: https://leetcode.com/problems/count-submatrices-with-equal-frequency-of-x-and-y/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-19
// At the time of submission:
//   Runtime 16 ms Beats 100.00%
//   Memory 260.23 MB Beats 5.66%

/****************************************
* 
* Given a 2D character matrix `grid`, where `grid[i][j]` is either
* _ `'X'`, `'Y'`, or `'.'`, return the number of submatrices that contain:
* • `grid[0][0]`
* • an equal frequency of `'X'` and `'Y'`.
* • at least one `'X'`.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2024/06/07/examplems.png]
* Input: grid = [["X","Y","."],["Y",".","."]]
* Output: 3
* Explanation:
* • 1. [["X","Y"]]
* • 2. [["X"],["Y"]]
* • 3. [["X","Y","."]]
*
* Example 2:
* Input: grid = [["X","X"],["X","Y"]]
* Output: 0
* Explanation:
* No submatrix has an equal frequency of 'X' and 'Y'.
*
* Example 3:
* Input: grid = [[".","."],[".","."]]
* Output: 0
* Explanation:
* No submatrix has at least one 'X'.
*
* Constraints:
* • `1 <= grid.length, grid[i].length <= 1000`
* • `grid[i][j]` is either `'X'`, `'Y'`, or `'.'`.
* 
****************************************/

class Solution {
    // Maintain column-wise counts of X and Y while iterating rows.
    // For each row, build prefix sums across columns representing
    // submatrix (0,0) to (r,c). A valid submatrix has equal X and Y
    // counts and at least one X. Count all such prefixes.
    // Time: O(m * n), Space: O(n).
    public int numberOfSubmatrices(char[][] grid) {

        int rows = grid.length;
        int cols = grid[0].length;

        // Column-wise cumulative counts of X and Y
        int[] colXCount = new int[cols];
        int[] colYCount = new int[cols];

        int result = 0;

        for (int r = 0; r < rows; r++) {

            // Update column accumulators for current row
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 'X') colXCount[c]++;
                if (grid[r][c] == 'Y') colYCount[c]++;
            }

            int totalX = 0;
            int totalY = 0;

            // Build prefix over columns for submatrix (0,0) → (r,c)
            for (int c = 0; c < cols; c++) {

                totalX += colXCount[c];
                totalY += colYCount[c];

                // Valid if equal X and Y AND at least one X
                if (totalX == totalY && totalX > 0) {
                    result++;
                }
            }
        }

        return result;
    }
}

 