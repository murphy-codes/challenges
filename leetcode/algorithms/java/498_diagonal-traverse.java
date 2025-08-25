// Source: https://leetcode.com/problems/diagonal-traverse/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-24
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 47.50 MB Beats 33.91%

/****************************************
* 
* Given an `m x n` matrix `mat`, return an array of all the elements of the
* _ array in a diagonal order.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/04/10/diag1-grid.jpg]
* Input: mat = [[1,2,3],[4,5,6],[7,8,9]]
* Output: [1,2,4,7,5,3,6,8,9]
*
* Example 2:
* Input: mat = [[1,2],[3,4]]
* Output: [1,2,3,4]
*
* Constraints:
* • m == mat.length
* • n == mat[i].length
* • 1 <= m, n <= 10^4
* • 1 <= m * n <= 10^4
* • -10^5 <= mat[i][j] <= 10^5
* 
****************************************/

class Solution {
    // This solution traverses the matrix in zig-zag diagonals.
    // It alternates between moving down-left and up-right, writing
    // directly into the result array without extra storage.
    // Edge cases (single row/col) are handled upfront.
    // Time complexity: O(m*n), where m=rows and n=cols.
    // Space complexity: O(1) extra space, aside from output array.
    public int[] findDiagonalOrder(int[][] mat) {
        // Handle single row directly
        if (mat.length == 1) {
            return mat[0];
        }

        int total = mat.length * mat[0].length; // total number of elements
        int[] result = new int[total];

        result[0] = mat[0][0]; // first element is always top-left

        // Handle single column directly
        if (mat[0].length == 1) {
            for (int r = 1; r < mat.length; r++) {
                result[r] = mat[r][0];
            }
            return result;
        }

        int pos = 1; // current index in result
        int row = 0, col = 1;
        boolean movingDownLeft = true;

        while (pos < total) {
            if (movingDownLeft) {
                // Move down-left until hitting bottom or left boundary
                while (col > 0 && row < mat.length - 1) {
                    result[pos++] = mat[row][col];
                    row++;
                    col--;
                }
                result[pos++] = mat[row][col];

                // Try moving down if possible, else move right
                if (row < mat.length - 1) row++;
                else col++;

                movingDownLeft = false;
            } else {
                // Move up-right until hitting top or right boundary
                while (row > 0 && col < mat[0].length - 1) {
                    result[pos++] = mat[row][col];
                    row--;
                    col++;
                }
                result[pos++] = mat[row][col];

                // Try moving right if possible, else move down
                if (col < mat[0].length - 1) col++;
                else row++;

                movingDownLeft = true;
            }
        }

        return result;
    }
}
