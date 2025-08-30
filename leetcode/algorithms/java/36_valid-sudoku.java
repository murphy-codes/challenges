// Source: https://leetcode.com/problems/valid-sudoku/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-29
// At the time of submission:
//   Runtime 6 ms Beats 28.73%
//   Memory 45.24 MB Beats 7.50%

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

import java.util.HashSet;
import java.util.Set;

class Solution {
    // This solution checks all rows, columns, and 3x3 sub-boxes separately.
    // For each, we use a fresh HashSet of digits 1–9 and ensure no duplicate
    // appears in that unit. If a duplicate is found, we return false early.
    // Time complexity: O(243) ≈ O(1), since board is fixed at 9x9.
    // Space complexity: O(243) ≈ O(1), for HashSets reused per unit.
    private static final int[][] squareOffsets = {
        {0, 0}, {0, 3}, {0, 6},{3, 0}, {3, 3}, {3, 6},{6, 0}, {6, 3}, {6, 6}
    };
    private static final Set<Character> digits = new HashSet<>();

    static {
        digits.add('1');
        digits.add('2');
        digits.add('3');
        digits.add('4');
        digits.add('5');
        digits.add('6');
        digits.add('7');
        digits.add('8');
        digits.add('9');
    }

    public boolean isValidSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            Set<Character> row = new HashSet<>(digits);
            for (int j = 0; j < 9; j++) {
                if(board[i][j]!='.'){
                    if(row.contains(board[i][j])) {row.remove(board[i][j]);}
                    else {return false;}
                }
            }
        }

        for (int j = 0; j < 9; j++) {
            Set<Character> column = new HashSet<>(digits);
            for (int i = 0; i < 9; i++) {
                if(board[i][j]!='.'){
                    if(column.contains(board[i][j])) {column.remove(board[i][j]);}
                    else {return false;}
                }
            }
        }
        
        for (int k = 0; k < 9; k++) {
            Set<Character> square = new HashSet<>(digits);
            int rowOff = squareOffsets[k][0];
            int colOff = squareOffsets[k][1];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if(board[i+rowOff][j+colOff]!='.'){
                        if(square.contains(board[i+rowOff][j+colOff])) {
                            square.remove(board[i+rowOff][j+colOff]);
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}