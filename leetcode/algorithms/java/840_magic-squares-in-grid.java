// Source: https://leetcode.com/problems/magic-squares-in-grid/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-29
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 43.47 MB Beats 22.16%

/****************************************
* 
* A `3 x 3` magic square is a `3 x 3` grid filled with distinct numbers from 1
* _ to 9 such that each row, column, and both diagonals all have the same sum.
* Given a `row x col` `grid` of integers, how many `3 x 3` magic square
* _ subgrids are there?
* Note: while a magic square can only contain numbers from 1 to 9, `grid`
* _ may contain numbers up to 15.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2020/09/11/magic_main.jpg]
* Input: grid = [[4,3,8,4],[9,5,1,9],[2,7,6,2]]
* Output: 1
* Explanation:
* The following subgrid is a 3 x 3 magic square:
* [Image: https://assets.leetcode.com/uploads/2020/09/11/magic_valid.jpg]
* while this one is not:
* [Image: https://assets.leetcode.com/uploads/2020/09/11/magic_invalid.jpg]
* In total, there is only one magic square inside the given grid.
*
* Example 2:
* Input: grid = [[8]]
* Output: 0
*
* Constraints:
* • `row == grid.length`
* • `col == grid[i].length`
* • `1 <= row, col <= 10`
* • `0 <= grid[i][j] <= 15`
* 
****************************************/

class Solution {
    // Slides a 3x3 window over the grid and checks each subgrid for magic-square
    // properties. Valid magic squares must contain distinct values 1..9 and have
    // all row, column, and diagonal sums equal to 15. The 3x3 magic square pattern
    // always places 5 in the center, allowing a quick early rejection. Time is
    // O(rows * cols) and space is O(1), since each window is checked in constant time.
    public int numMagicSquaresInside(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        int count = 0;

        for (int r = 0; r <= rows - 3; r++) {
            for (int c = 0; c <= cols - 3; c++) {
                if (isMagic(grid, r, c)) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isMagic(int[][] g, int r, int c) {
        // must contain numbers 1..9 uniquely
        boolean[] seen = new boolean[10];
        for (int i = r; i < r + 3; i++) {
            for (int j = c; j < c + 3; j++) {
                int v = g[i][j];
                if (v < 1 || v > 9 || seen[v]) return false;
                seen[v] = true;
            }
        }

        // center must be 5 for 3x3 magic square
        if (g[r + 1][c + 1] != 5) return false;

        // check sums == 15
        return
            g[r][c] + g[r][c+1] + g[r][c+2] == 15 &&  // row 1
            g[r+1][c] + g[r+1][c+1] + g[r+1][c+2] == 15 && // row 2
            g[r+2][c] + g[r+2][c+1] + g[r+2][c+2] == 15 && // row 3

            g[r][c] + g[r+1][c] + g[r+2][c] == 15 && // col 1
            g[r][c+1] + g[r+1][c+1] + g[r+2][c+1] == 15 && // col 2
            g[r][c+2] + g[r+1][c+2] + g[r+2][c+2] == 15 && // col 3

            g[r][c] + g[r+1][c+1] + g[r+2][c+2] == 15 && // diag
            g[r][c+2] + g[r+1][c+1] + g[r+2][c] == 15;   // anti-diag
    }
}
