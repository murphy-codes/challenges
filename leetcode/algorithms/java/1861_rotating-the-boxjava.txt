// Source: https://leetcode.com/problems/rotating-the-box/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-05
// At the time of submission:
//   Runtime 4 ms Beats 100.00%
//   Memory 124.68 MB Beats 76.68%

/****************************************
* 
* You are given an `m x n` matrix of characters `boxGrid` representing a
* _ side-view of a box. Each cell of the box is one of the following:
* • A stone `'#'`
* • A stationary obstacle `'*'`
* • Empty `'.'`
* The box is rotated 90 degrees clockwise, causing some of the stones to fall
* _ due to gravity. Each stone falls down until it lands on an obstacle, another
* _ stone, or the bottom of the box. Gravity does not affect the obstacles'
* _ positions, and the inertia from the box's rotation does not affect the
* _ stones' horizontal positions.
* It is guaranteed that each stone in `boxGrid` rests on an obstacle, another
* _ stone, or the bottom of the box.
* Return an `n x m` matrix representing the box after the rotation described above.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/04/08/rotatingtheboxleetcodewithstones.png]
* Input: boxGrid = [["#",".","#"]]
* Output: [["."],
*          ["#"],
*          ["#"]]
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2021/04/08/rotatingtheboxleetcode2withstones.png]
* Input: boxGrid = [["#",".","*","."],
*                   ["#","#","*","."]]
* Output: [["#","."],
*          ["#","#"],
*          ["*","*"],
*          [".","."]]
* 
*
* Example 3:
* [Image: https://assets.leetcode.com/uploads/2021/04/08/rotatingtheboxleetcode3withstone.png]
* Input: boxGrid = [["#","#","*",".","*","."],
*                   ["#","#","#","*",".","."],
*                   ["#","#","#",".","#","."]]
* Output: [[".","#","#"],
*          [".","#","#"],
*          ["#","#","*"],
*          ["#","*","."],
*          ["#",".","*"],
*          ["#",".","."]]
*
* Constraints:
* • `m == boxGrid.length`
* • `n == boxGrid[i].length`
* • `1 <= m, n <= 500`
* • `boxGrid[i][j]` is either `'#'`, `'*'`, or `'.'`.
* 
****************************************/

class Solution {
    // Process each row right-to-left, simulating gravity with a write index.
    // Stones fall to lowest available position unless blocked by '*'.
    // Write results directly into rotated grid to avoid extra pass.
    // Time: O(m*n), Space: O(m*n)
    public char[][] rotateTheBox(char[][] boxGrid) {

        int rows = boxGrid.length;
        int cols = boxGrid[0].length;

        // Rotated grid will be cols x rows
        char[][] rotated = new char[cols][rows];

        for (int r = 0; r < rows; r++) {
            // Column index in rotated grid (mirrors rotation)
            int rotatedCol = rows - 1 - r;
            processRow(boxGrid[r], rotated, rotatedCol);
        }

        return rotated;
    }

    private void processRow(char[] row, char[][] rotated, int rotatedCol) {

        // Next position where a stone can fall (from bottom up)
        int writeRow = row.length;

        for (int c = row.length - 1; c >= 0; c--) {

            if (row[c] == '#') {
                // Place stone at next available position
                writeRow--;
                rotated[writeRow][rotatedCol] = '#';

                // Clear original spot if moved
                if (c != writeRow) {
                    rotated[c][rotatedCol] = '.';
                }

            } else if (row[c] == '*') {
                // Obstacle resets falling boundary
                writeRow = c;
                rotated[writeRow][rotatedCol] = '*';

            } else {
                // Empty space
                rotated[c][rotatedCol] = '.';
            }
        }
    }
}