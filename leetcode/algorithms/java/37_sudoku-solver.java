// Source: https://leetcode.com/problems/sudoku-solver/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-31
// At the time of submission:
//   Runtime 61 ms Beats 95.20%
//   Memory 41.10 MB Beats 56.80%

/****************************************
* 
* Write a program to solve a Sudoku puzzle by filling the empty cells.
* A sudoku solution must satisfy all of the following rules:
* 1. Each of the digits `1-9` must occur exactly once in each row.
* 2. Each of the digits `1-9` must occur exactly once in each column.
* 3. Each of the digits `1-9` must occur exactly once in each of the 9 `3x3`
* _ sub-boxes of the grid.
* The `'.'` character indicates empty cells.
*
* Example 1:
* [Image: https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Sudoku-by-L2G-20050714.svg/250px-Sudoku-by-L2G-20050714.svg.png]
* Input: board = [["5","3",".",".","7",".",".",".","."],["6",".",".","1","9","5",".",".","."],[".","9","8",".",".",".",".","6","."],["8",".",".",".","6",".",".",".","3"],["4",".",".","8",".","3",".",".","1"],["7",".",".",".","2",".",".",".","6"],[".","6",".",".",".",".","2","8","."],[".",".",".","4","1","9",".",".","5"],[".",".",".",".","8",".",".","7","9"]]
* Output: [["5","3","4","6","7","8","9","1","2"],["6","7","2","1","9","5","3","4","8"],["1","9","8","3","4","2","5","6","7"],["8","5","9","7","6","1","4","2","3"],["4","2","6","8","5","3","7","9","1"],["7","1","3","9","2","4","8","5","6"],["9","6","1","5","3","7","2","8","4"],["2","8","7","4","1","9","6","3","5"],["3","4","5","2","8","6","1","7","9"]]
* Explanation: The input board is shown above and the only valid solution is shown below:
* [Image: https://upload.wikimedia.org/wikipedia/commons/thumb/3/31/Sudoku-by-L2G-20050714_solution.svg/250px-Sudoku-by-L2G-20050714_solution.svg.png]
*
* Constraints:
* • board.length == 9
* • board[i].length == 9
* • `board[i][j]` is a digit or `'.'`.
* • It is guaranteed that the input board has only one solution.
* 
****************************************/

class Solution {
    // This solution uses backtracking with constraint tracking to solve Sudoku.
    // Each row, column, and 3x3 box is tracked using boolean arrays for O(1) checks.
    // We recursively fill cells with digits 1–9, backtracking on invalid choices.
    // Time complexity is roughly O(9^(n)) in the worst case, but pruned heavily
    // by constraints, making it efficient in practice. Space is O(1) (fixed size).

    // helper arrays to track used digits
    private boolean[][] rows = new boolean[9][10];
    private boolean[][] cols = new boolean[9][10];
    private boolean[][] boxes = new boolean[9][10];

    public void solveSudoku(char[][] board) {
        // initialize helper arrays with given board
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '0';
                    rows[i][num] = true;
                    cols[j][num] = true;
                    boxes[(i / 3) * 3 + j / 3][num] = true;
                }
            }
        }
        backtrack(board, 0, 0);
    }

    private boolean backtrack(char[][] board, int i, int j) {
        // move to next row
        if (i == 9) return true;
        // move to next column
        if (j == 9) return backtrack(board, i + 1, 0);

        if (board[i][j] != '.') {
            return backtrack(board, i, j + 1); // skip filled cell
        }

        for (int num = 1; num <= 9; num++) {
            int boxIndex = (i / 3) * 3 + j / 3;
            if (!rows[i][num] && !cols[j][num] && !boxes[boxIndex][num]) {
                // place num
                board[i][j] = (char)(num + '0');
                rows[i][num] = cols[j][num] = boxes[boxIndex][num] = true;

                if (backtrack(board, i, j + 1)) return true;

                // undo (backtrack)
                board[i][j] = '.';
                rows[i][num] = cols[j][num] = boxes[boxIndex][num] = false;
            }
        }

        return false; // no valid number found
    }
}
