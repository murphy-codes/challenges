// Source: https://leetcode.com/problems/count-square-submatrices-with-all-ones/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-19
// At the time of submission:
//   Runtime 6 ms Beats 75.00%
//   Memory 54.78 MB Beats 93.75%

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
    // This solution uses DP where dp[i][j] stores the size of the largest
    // square ending at (i,j). If matrix[i][j] == 1, then dp[i][j] is 1 +
    // the minimum of its top, left, and top-left neighbors. Each dp[i][j]
    // contributes to the total count since it represents all smaller squares
    // within it. Time complexity: O(m*n). Space complexity: O(m*n) (or O(n)).
    public int countSquares(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] dp = new int[rows][cols];
        int totalSquares = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == 1) {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1; // edges are just 1x1 squares
                    } else {
                        dp[i][j] = 1 + Math.min(dp[i - 1][j],
                                        Math.min(dp[i][j - 1],
                                                 dp[i - 1][j - 1]));
                    }
                    totalSquares += dp[i][j];
                }
            }
        }
        return totalSquares;
    }
}
