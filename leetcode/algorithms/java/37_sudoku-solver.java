// Source: https://leetcode.com/problems/sudoku-solver/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-31
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 41.07 MB Beats 71.90%

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
    // This solution uses backtracking with bitmasks and the MRV heuristic.
    // Each row, column, and 3x3 box is tracked by a 9-bit mask for O(1) checks.
    // At each step we pick the empty cell with the fewest valid options (MRV),
    // then try each candidate digit using efficient bitwise operations.
    // Time complexity is exponential in the worst case, but MRV + pruning makes
    // it extremely fast in practice. Space is O(1), fixed for 9x9 Sudoku.

    // Bitmasks for digits 1-9 used in each row, column, and 3x3 box.
    // Bit (d) = 1 means digit (d+1) is already placed.
    private final int[] rowMask = new int[9];
    private final int[] colMask = new int[9];
    private final int[] boxMask = new int[9];

    // Stores positions of empty cells as single integers (r*9 + c).
    private final int[] emptyCells = new int[81];
    private int totalEmpty = 0;

    public void solveSudoku(char[][] board) {
        // Initialize masks and record empty cell positions
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                char ch = board[r][c];
                if (ch == '.') {
                    emptyCells[totalEmpty++] = r * 9 + c;
                } else {
                    int digit = ch - '1';        // 0..8
                    int bit = 1 << digit;
                    rowMask[r] |= bit;
                    colMask[c] |= bit;
                    boxMask[getBoxIndex(r, c)] |= bit;
                }
            }
        }
        backtrack(board, 0);
    }

    // Backtracking with MRV: always pick the cell with fewest choices.
    private boolean backtrack(char[][] board, int k) {
        if (k == totalEmpty) return true; // Solved

        // Step 1: Find best candidate cell (MRV heuristic)
        int bestIndex = k;
        int bestChoicesMask = 0;
        int minChoices = 10; // more than max possible (9)

        for (int i = k; i < totalEmpty; i++) {
            int pos = emptyCells[i];
            int r = pos / 9, c = pos % 9, b = getBoxIndex(r, c);
            int usedMask = rowMask[r] | colMask[c] | boxMask[b];
            int availableMask = (~usedMask) & 0x1FF; // mask of possible digits
            int count = Integer.bitCount(availableMask);

            if (count < minChoices) {
                minChoices = count;
                bestChoicesMask = availableMask;
                bestIndex = i;
                if (count == 1) break; // best possible
            }
            if (count == 0) return false; // dead end
        }

        // Step 2: Swap chosen cell into slot k
        swap(emptyCells, k, bestIndex);

        int pos = emptyCells[k];
        int r = pos / 9, c = pos % 9, b = getBoxIndex(r, c);
        int choices = bestChoicesMask == 0
                ? ((~(rowMask[r] | colMask[c] | boxMask[b])) & 0x1FF)
                : bestChoicesMask;

        // Step 3: Try each digit by iterating set bits
        while (choices != 0) {
            int bit = choices & -choices; // lowest set bit
            int digit = Integer.numberOfTrailingZeros(bit); // 0..8

            place(board, r, c, b, digit, bit);
            if (backtrack(board, k + 1)) return true;
            unplace(board, r, c, b, digit, bit);

            choices -= bit; // remove tried digit
        }

        return false; // backtrack
    }

    private void place(char[][] board, int r, int c, int b, int d, int bit) {
        board[r][c] = (char) ('1' + d);
        rowMask[r] |= bit;
        colMask[c] |= bit;
        boxMask[b] |= bit;
    }

    private void unplace(char[][] board, int r, int c, int b, int d, int bit) {
        board[r][c] = '.';
        rowMask[r] ^= bit;
        colMask[c] ^= bit;
        boxMask[b] ^= bit;
    }

    private static int getBoxIndex(int r, int c) {
        return (r / 3) * 3 + (c / 3);
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
    }
}
