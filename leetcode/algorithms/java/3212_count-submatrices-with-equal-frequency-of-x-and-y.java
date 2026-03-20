// Source: https://leetcode.com/problems/count-submatrices-with-equal-frequency-of-x-and-y/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-19
// At the time of submission:
//   Runtime 17 ms Beats 98.11%
//   Memory 260.24 MB Beats 5.66%

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
    // Convert grid values: 'X'=+1, 'Y'=-1, '.'=0. Maintain column-wise
    // prefix sums and X counts. For each row, build a running prefix sum
    // across columns representing submatrix (0,0) to (i,j). Count cases
    // where sum == 0 (equal X and Y) and at least one X exists.
    // Time: O(m * n), Space: O(n).
    public int numberOfSubmatrices(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        int[] colSum = new int[cols]; // stores X=+1, Y=-1
        int[] colX = new int[cols];   // counts number of X

        int result = 0;

        for (int i = 0; i < rows; i++) {

            int prefixSum = 0;
            int prefixX = 0;

            for (int j = 0; j < cols; j++) {

                // Update column accumulators
                if (grid[i][j] == 'X') {
                    colSum[j] += 1;
                    colX[j] += 1;
                } else if (grid[i][j] == 'Y') {
                    colSum[j] -= 1;
                }

                // Build prefix for submatrix (0,0) → (i,j)
                prefixSum += colSum[j];
                prefixX += colX[j];

                // Valid if equal X and Y AND at least one X
                if (prefixSum == 0 && prefixX > 0) {
                    result++;
                }
            }
        }

        return result;
    }
}