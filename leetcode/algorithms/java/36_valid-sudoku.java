// Source: https://leetcode.com/problems/valid-sudoku/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-29
// At the time of submission:
//   Runtime 1 ms Beats 99.87%
//   Memory 44.51 MB Beats 56.61%

/****************************************
* 
* Determine if a 9 x 9 Sudoku board is valid. Only the filled cells need to be
* _ validated according to the following rules:
* 1. Each row must contain the digits 1-9 without repetition.
* 2. Each column must contain the digits 1-9 without repetition.
* 3. Each of the nine 3 x 3 sub-boxes of the grid must contain the
* __ digits 1-9 without repetition.
* Note:
* • A Sudoku board (partially filled) could be valid
* __ but is not necessarily solvable.
* • Only the filled cells need to be validated according to the mentioned rules.
*
* Example 1:
* [Image: https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Sudoku-by-L2G-20050714.svg/250px-Sudoku-by-L2G-20050714.svg.png]
* Input: board =
* [["5","3",".",".","7",".",".",".","."]
* ,["6",".",".","1","9","5",".",".","."]
* ,[".","9","8",".",".",".",".","6","."]
* ,["8",".",".",".","6",".",".",".","3"]
* ,["4",".",".","8",".","3",".",".","1"]
* ,["7",".",".",".","2",".",".",".","6"]
* ,[".","6",".",".",".",".","2","8","."]
* ,[".",".",".","4","1","9",".",".","5"]
* ,[".",".",".",".","8",".",".","7","9"]]
* Output: true
*
* Example 2:
* Input: board =
* [["8","3",".",".","7",".",".",".","."]
* ,["6",".",".","1","9","5",".",".","."]
* ,[".","9","8",".",".",".",".","6","."]
* ,["8",".",".",".","6",".",".",".","3"]
* ,["4",".",".","8",".","3",".",".","1"]
* ,["7",".",".",".","2",".",".",".","6"]
* ,[".","6",".",".",".",".","2","8","."]
* ,[".",".",".","4","1","9",".",".","5"]
* ,[".",".",".",".","8",".",".","7","9"]]
* Output: false
* Explanation: Same as Example 1, except with the 5 in the top left corner being modified to 8. Since there are two 8's in the top left 3x3 sub-box, it is invalid.
*
* Constraints:
* • board.length == 9
* • board[i].length == 9
* • `board[i][j]` is a digit `1-9` or `'.'`.
* 
****************************************/

class Solution {
    // This solution tracks digits seen in each row, column, and 3x3 box.
    // For every filled cell, we check if that digit already exists in any
    // of the corresponding structures. If so, the board is invalid.
    // Time complexity: O(81) ≈ O(1), since we scan all cells once.
    // Space complexity: O(9*9) ≈ O(1), using fixed boolean arrays.
    public boolean isValidSudoku(char[][] board) {
        boolean[][] rows = new boolean[9][9];
        boolean[][] cols = new boolean[9][9];
        boolean[][] boxes = new boolean[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                if (c == '.') continue;
                int num = c - '1'; // map '1'..'9' → 0..8
                int boxIndex = (i / 3) * 3 + (j / 3);
                if (rows[i][num] || cols[j][num] || boxes[boxIndex][num]) {
                    return false;
                }
                rows[i][num] = cols[j][num] = boxes[boxIndex][num] = true;
            }
        }
        return true;
    }
}