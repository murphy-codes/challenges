// Source: https://leetcode.com/problems/largest-magic-square/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-17
// At the time of submission:
//   Runtime 4 ms Beats 100.00%
//   Memory 46.45 MB Beats 44.71%

/****************************************
* 
* A `k x k` magic square is a `k x k` grid filled with integers such that every
* row sum, every column sum, and both diagonal sums are all equal. The integers
* in the magic square do not have to be distinct. Every `1 x 1` grid is
* trivially a magic square.
* Given an `m x n` integer grid, return the size (i.e., the side length `k`)
* of the largest magic square that can be found within this grid.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/05/29/magicsquare-grid.jpg]
* Input: grid = [[7,1,4,5,6],[2,5,1,6,4],[1,5,4,3,2],[1,2,7,3,4]]
* Output: 3
* Explanation: The largest magic square has a size of 3.
* Every row sum, column sum, and diagonal sum of this magic square is equal to 12.
* - Row sums: 5+1+6 = 5+4+3 = 2+7+3 = 12
* - Column sums: 5+5+2 = 1+4+7 = 6+3+3 = 12
* - Diagonal sums: 5+4+3 = 6+4+2 = 12
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2021/05/29/magicsquare2-grid.jpg]
* Input: grid = [[5,1,3,1],[9,3,3,1],[1,3,3,8]]
* Output: 2
*
* Constraints:
* • `m == grid.length`
* • `n == grid[i].length`
* • `1 <= m, n <= 50`
* • `1 <= grid[i][j] <= 10^6`
* 
****************************************/

class Solution {
    // Uses row and column prefix sums to validate square sums in O(1) time.
    // Iterates square sizes from largest to smallest, exiting early on success.
    // Rows and columns are validated first, followed by both diagonals.
    // Time Complexity: O(m * n * min(m, n))
    // Space Complexity: O(m * n) for prefix sum arrays

    // Checks whether a magic square of given size exists anywhere in the grid
    boolean existsMagicSquare(
        int[][] grid,
        int[][] rowPrefix,
        int[][] colPrefix,
        int size
    ) {
        int rows = grid.length;
        int cols = grid[0].length;

        int maxRowStart = rows - size;
        int maxColStart = cols - size;

        for (int startRow = 0; startRow <= maxRowStart; startRow++) {
            for (int startCol = 0; startCol <= maxColStart; startCol++) {

                // Target sum: first row of the square
                int targetSum =
                    rowPrefix[startRow][startCol + size] -
                    rowPrefix[startRow][startCol];

                boolean valid = true;

                // Check all rows and columns
                for (int offset = 0; valid && offset < size; offset++) {
                    int rowSum =
                        rowPrefix[startRow + offset][startCol + size] -
                        rowPrefix[startRow + offset][startCol];

                    int colSum =
                        colPrefix[startRow + size][startCol + offset] -
                        colPrefix[startRow][startCol + offset];

                    valid = (rowSum == targetSum) && (colSum == targetSum);
                }

                // Check both diagonals only if rows/columns matched
                if (valid) {
                    int diag1 = 0, diag2 = 0;

                    for (int i = 0; i < size; i++) {
                        diag1 += grid[startRow + i][startCol + i];
                        diag2 += grid[startRow + i][startCol + size - 1 - i];
                    }

                    if (diag1 == targetSum && diag2 == targetSum) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int largestMagicSquare(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Prefix sums for rows and columns
        int[][] rowPrefix = new int[rows][cols + 1];
        int[][] colPrefix = new int[rows + 1][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                rowPrefix[r][c + 1] = rowPrefix[r][c] + grid[r][c];
                colPrefix[r + 1][c] = colPrefix[r][c] + grid[r][c];
            }
        }

        // Try largest possible size first
        for (int size = Math.min(rows, cols); size > 1; size--) {
            if (existsMagicSquare(grid, rowPrefix, colPrefix, size)) {
                return size;
            }
        }

        return 1; // Every 1x1 square is magic
    }
}
