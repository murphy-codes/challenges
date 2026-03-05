// Source: https://leetcode.com/problems/special-positions-in-a-binary-matrix/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-03
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 47.05 MB Beats 83.22%

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
    // For each row, check whether it contains exactly one '1'. If so,
    // record the column where it appears. Then verify that this column
    // contains no other '1' in any other row. If both conditions hold,
    // the position is special. Time complexity is O(m * n) and space
    // complexity is O(1) since no extra arrays are used.
    public int numSpecial(int[][] mat) {

        int specialCount = 0;

        for (int row = 0; row < mat.length; row++) {

            // Check if this row contains exactly one '1'
            int columnIndex = findSingleOneColumn(mat, row);

            // If exactly one '1' exists, verify the column condition
            if (columnIndex >= 0 && isColumnValid(mat, row, columnIndex)) {
                specialCount++;
            }
        }

        return specialCount;
    }

    // Returns the column index if the row contains exactly one '1'.
    // If the row contains zero or more than one '1', returns -1.
    private int findSingleOneColumn(int[][] mat, int row) {

        int columnIndex = -1;

        for (int col = 0; col < mat[0].length; col++) {

            if (mat[row][col] == 1) {

                // If we already saw a '1', row is invalid
                if (columnIndex >= 0)
                    return -1;

                columnIndex = col;
            }
        }

        return columnIndex;
    }

    // Verifies that no other row contains a '1' in this column.
    private boolean isColumnValid(int[][] mat, int row, int columnIndex) {

        for (int r = 0; r < mat.length; r++) {

            if (mat[r][columnIndex] == 1 && r != row)
                return false;
        }

        return true;
    }
}