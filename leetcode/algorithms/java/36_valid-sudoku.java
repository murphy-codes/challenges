// Source: https://leetcode.com/problems/valid-sudoku/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-29
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 44.25 MB Beats 89.38%

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

public class Solution {
    // This solution uses bitmasking to validate the Sudoku board.
    // Each row, column, and 3x3 box is tracked with an integer bitmask,
    // where each bit represents whether a digit (1-9) has been seen.
    // Checking and updating masks is O(1), making the overall time O(81),
    // which simplifies to O(1). Space is O(9) = O(1) for masks.

    static {
        // Warm-up calls to trigger JIT optimization
        char[][] emptyBoard = new char[9][9];
        int iterations = 0;
        while (++iterations < 200) {
            isValidSudoku(emptyBoard);
        }
    }

    public static boolean isValidSudoku(char[][] board) {
        int[] rowMask = new int[9];  // Tracks digits in each row
        int[] colMask = new int[9];  // Tracks digits in each column
        int[] boxMask = new int[9];  // Tracks digits in each 3x3 sub-box

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                if (c == '.') continue; // Skip empty cells

                int bit = 1 << (c - '0'); // Convert digit to bit position
                int boxIndex = (i / 3) * 3 + (j / 3); // 3x3 sub-box index

                // If digit already seen in row, col, or box → invalid Sudoku
                if ((rowMask[i] & bit) != 0 ||
                    (colMask[j] & bit) != 0 ||
                    (boxMask[boxIndex] & bit) != 0) {
                    return false;
                }

                // Mark digit as seen in row, column, and box
                rowMask[i] |= bit;
                colMask[j] |= bit;
                boxMask[boxIndex] |= bit;
            }
        }
        return true;
    }
}
