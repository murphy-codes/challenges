// Source: https://leetcode.com/problems/special-positions-in-a-binary-matrix/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-03
// At the time of submission:
//   Runtime 2 ms Beats 94.84%
//   Memory 47.15 MB Beats 65.84%

/****************************************
* 
* Given an m` x n` binary matrix `mat`, return the number of special positions in `mat`.
* A position `(i, j)` is called special if `mat[i][j] == 1` and all other
* _ elements in row `i` and column `j` are `0` (rows and columns are 0-indexed).
*
* Example 1:
* Input: mat = [[1,0,0],[0,0,1],[1,0,0]]
* Output: 1
* Explanation: (1, 2) is a special position because mat[1][2] == 1 and all other elements in row 1 and column 2 are 0.
*
* Example 2:
* Input: mat = [[1,0,0],[0,1,0],[0,0,1]]
* Output: 3
* Explanation: (0, 0), (1, 1) and (2, 2) are special positions.
*
* Constraints:
* • `m == mat.length`
* • `n == mat[i].length`
* • `1 <= m, n <= 100`
* • `mat[i][j]` is either `0` or `1`.
* 
****************************************/

class Solution {
    // Count how many 1s appear in each row and each column.
    // A cell is special if it is 1 and its row and column both have exactly one 1.
    // Use two passes: one for counting, one for validation.
    // Time: O(m * n), Space: O(m + n).
    public int numSpecial(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;

        int[] rowCount = new int[m];
        int[] colCount = new int[n];

        // Count number of 1s in each row and column
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 1) {
                    rowCount[i]++;
                    colCount[j]++;
                }
            }
        }

        int specialCount = 0;

        // Identify special positions
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 1 && rowCount[i] == 1 && colCount[j] == 1) {
                    specialCount++;
                }
            }
        }

        return specialCount;
    }
}