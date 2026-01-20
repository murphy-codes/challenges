// Source: https://leetcode.com/problems/maximum-side-length-of-a-square-with-sum-less-than-or-equal-to-threshold/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-19
// At the time of submission:
//   Runtime 4 ms Beats 100.00%
//   Memory 53.90 MB Beats 93.43%

/****************************************
* 
* Given a `m x n` matrix `mat` and an integer `threshold`, return the maximum
* _ side-length of a square with a sum less than or equal to `threshold` or
* _ return `0` if there is no such square.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2019/12/05/e1.png]
* Input: mat = [[1,1,3,2,4,3,2],[1,1,3,2,4,3,2],[1,1,3,2,4,3,2]], threshold = 4
* Output: 2
* Explanation: The maximum side length of square with sum less than 4 is 2 as shown.
*
* Example 2:
* Input: mat = [[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2]], threshold = 1
* Output: 0
*
* Constraints:
* • `m == mat.length`
* • `n == mat[i].length
* • `1 <= m, n <= 300`
* • `0 <= mat[i][j] <= 10^4`
* • `0 <= threshold <= 10^5`
* 
****************************************/

class Solution {
    // Converts the matrix into an in-place 2D prefix sum array.
    // For each cell as bottom-right, incrementally expands square size.
    // Uses prefix sums to compute square sums in O(1) time.
    // Prunes early when sums exceed threshold.
    // Time: O(m * n * min(m, n)), Space: O(1) extra.

    public int maxSideLength(int[][] mat, int threshold) {
        int rows = mat.length;
        int cols = mat[0].length;

        // Build prefix sums in-place (row-wise then column-wise)
        for (int r = 0; r < rows; r++) {
            for (int c = 1; c < cols; c++) {
                mat[r][c] += mat[r][c - 1];
            }
        }
        for (int r = 1; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                mat[r][c] += mat[r - 1][c];
            }
        }

        int maxSide = 0;

        // Try every bottom-right corner
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {

                // Grow square size starting from current best + 1
                for (int side = maxSide + 1;
                     r + 1 - side >= 0 && c + 1 - side >= 0;
                     side++) {

                    int prevRow = r - side;
                    int prevCol = c - side;

                    int topLeft = (prevRow >= 0 && prevCol >= 0)
                        ? mat[prevRow][prevCol] : 0;
                    int top = (prevRow >= 0) ? mat[prevRow][c] : 0;
                    int left = (prevCol >= 0) ? mat[r][prevCol] : 0;

                    int squareSum = mat[r][c] + topLeft - top - left;

                    if (squareSum <= threshold) {
                        maxSide = side;
                    } else {
                        break; // larger squares will also fail
                    }
                }
            }
        }

        return maxSide;
    }
}
