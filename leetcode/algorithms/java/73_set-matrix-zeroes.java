// Source: https://leetcode.com/problems/set-matrix-zeroes/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-20
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 45.81 MB Beats 29.76%

/****************************************
* 
* Given an `m x n` integer matrix `matrix`, if an element is `0`,
* _ set its entire row and column to `0`'s.
* You must do it in place.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2020/08/17/mat1.jpg]
* Input: matrix = [[1,1,1],[1,0,1],[1,1,1]]
* Output: [[1,0,1],[0,0,0],[1,0,1]]
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2020/08/17/mat2.jpg]
* Input: matrix = [[0,1,2,0],[3,4,5,2],[1,3,1,5]]
* Output: [[0,0,0,0],[0,4,5,0],[0,3,1,0]]
*
* Constraints:
* • m == matrix.length
* • n == matrix[0].length
* • 1 <= m, n <= 200
* • -2^31 <= matrix[i][j] <= 2^31 - 1
*
*
* Follow up:
* • A straightforward solution using O(mn) space is probably a bad idea.
* • A simple improvement uses O(m + n) space, but still not the best solution.
* • Could you devise a constant space solution?
* 
****************************************/

class Solution {
    // Use first row and column as flags to mark zero rows/columns
    // Check first row/col separately to avoid overwriting flags
    // In-place updates allow constant space usage (O(1))
    // Time complexity is O(m * n) for scanning and updates
    // Space complexity is O(1), no extra data structures used

    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        
        boolean firstRowHasZero = false;
        boolean firstColHasZero = false;
        
        // Check if first row has any zero
        for (int j = 0; j < n; j++) {
            if (matrix[0][j] == 0) {
                firstRowHasZero = true;
                break;
            }
        }

        // Check if first column has any zero
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                firstColHasZero = true;
                break;
            }
        }

        // Use first row and column to mark zero rows/columns
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        // Zero cells based on marks
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        // Zero first row if needed
        if (firstRowHasZero) {
            for (int j = 0; j < n; j++) {
                matrix[0][j] = 0;
            }
        }

        // Zero first column if needed
        if (firstColHasZero) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
    }
}
