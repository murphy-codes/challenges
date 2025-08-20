// Source: https://leetcode.com/problems/count-square-submatrices-with-all-ones/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-19
// At the time of submission:
//   Runtime 2 ms Beats 100.00%
//   Memory 56.13 MB Beats 24.32%

/****************************************
* 
* Given a `m * n` matrix of ones and zeros,
* _ return how many square submatrices have all ones.
*
* Example 1:
* Input: matrix =
* [
*   [0,1,1,1],
*   [1,1,1,1],
*   [0,1,1,1]
* ]
* Output: 15
* Explanation:
* There are 10 squares of side 1.
* There are 4 squares of side 2.
* There is  1 square of side 3.
* Total number of squares = 10 + 4 + 1 = 15.
*
* Example 2:
* Input: matrix =
* [
*   [1,0,1],
*   [1,1,0],
*   [1,1,0]
* ]
* Output: 7
* Explanation:
* There are 6 squares of side 1.
* There is 1 square of side 2.
* Total number of squares = 6 + 1 = 7.
*
* Constraints:
* • 1 <= arr.length <= 300
* • 1 <= arr[0].length <= 300
* • 0 <= arr[i][j] <= 1
* 
****************************************/

class Solution {
    // This solution uses dynamic programming in-place to count all square
    // submatrices filled with ones. Each cell stores the size of the largest
    // square ending at that position by extending from its top, left, and
    // top-left neighbors. The result accumulates all such values. This runs
    // in O(m*n) time with O(1) extra space, modifying the input matrix.

    // JIT warm-up (optional optimization trick, not needed for correctness)
    static {
        int[][] sampleMatrix = new int[][]{{0,1,0,1,1,0},{1,0,0,1,1,1},{1,1,0,0,0,1},{1,0,0,1,1,1}};
        for (int i = 0; i < 500; i++) countSquares(sampleMatrix);
    }

    public static int countSquares(int[][] matrix) {
        int rows = matrix.length, cols = matrix[0].length;
        int totalSquares = 0;

        // Process from (1,1) because top row & left col are handled later
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                if (matrix[i][j] == 1) {
                    // Update with size of largest square ending at (i,j)
                    matrix[i][j] += Math.min(
                        matrix[i - 1][j],
                        Math.min(matrix[i][j - 1], matrix[i - 1][j - 1])
                    );
                    totalSquares += matrix[i][j];
                }
            }
        }

        // Add squares from the first column
        for (int i = 0; i < rows; i++) {
            totalSquares += matrix[i][0];
        }

        // Add squares from the first row
        for (int j = 1; j < cols; j++) {
            totalSquares += matrix[0][j];
        }

        return totalSquares;
    }
}
