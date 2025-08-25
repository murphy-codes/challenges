// Source: https://leetcode.com/problems/diagonal-traverse/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-24
// At the time of submission:
//   Runtime 2 ms Beats 99.41%
//   Memory 46.94 MB Beats 89.54%

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
    // Traverse the matrix diagonally, alternating up-right and down-left.
    // Use a boolean flag `ascending` to control direction of movement.
    // Adjust row/col indices when hitting edges, flipping direction as needed.
    // Time: O(m*n) since each element is visited once. Space: O(m*n) for result.
    public int[] findDiagonalOrder(int[][] mat) {
        int row = 0, col = 0, m = mat.length, n = mat[0].length;
        int[] traversed = new int[m*n];
        boolean ascending = true;
        for (int i = 0; i < m*n; i++) {
            traversed[i] = mat[row][col];
            if (ascending) {
                row--;
                col++;
                if (col >= n) {
                    col--;
                    row+=2;
                    ascending ^= true;
                } else if (row < 0) {
                    row = 0;
                    ascending ^= true;
                }
            } else {
                row++;
                col--;
                if (row >= m) {
                    row--;
                    col+=2;
                    ascending ^= true;
                } else if (col < 0) {
                    col = 0;
                    ascending ^= true;
                }
            }
        }
        return traversed;
    }
}