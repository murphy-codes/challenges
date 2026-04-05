// Source: https://leetcode.com/problems/robot-return-to-origin/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-05
// At the time of submission:
//   Runtime 4 ms Beats 99.05%
//   Memory 46.18 MB Beats 34.52%

/****************************************
* 
* There is a robot starting at the position `(0, 0)`, the origin, on a 2D plane.
* _ Given a sequence of its moves, judge if this robot ends up at `(0, 0)`
* _ after it completes its moves.
* You are given a string `moves` that represents the move sequence of the robot
* _ where `moves[i]` represents its `i^th` move. Valid moves are `'R'` (right),
* _ `'L'` (left), `'U'` (up), and `'D'` (down).
* Return `true` if the robot returns to the origin after it finishes all of
* _ its moves, or `false` otherwise.
* Note: The way that the robot is "facing" is irrelevant. `'R'` will always
* _ make the robot move to the right once, `'L'` will always make it move
* _ left, etc. Also, assume that the magnitude of the robot's movement is
* _ the same for each move.
*
* Example 1:
* Input: moves = "UD"
* Output: true
* Explanation: The robot moves up once, and then down once. All moves have the same magnitude, so it ended up at the origin where it started. Therefore, we return true.
*
* Example 2:
* Input: moves = "LL"
* Output: false
* Explanation: The robot moves left twice. It ends up two "moves" to the left of the origin. We return false because it is not at the origin at the end of its moves.
*
* Constraints:
* • `1 <= moves.length <= 2 * 10^4`
* • `moves` only contains the characters `'U'`, `'D'`, `'L'`, and `'R'`.
* 
****************************************/

class Solution {
    // Iterate through moves, adjusting positions on
    // the x & y axes for L/R & U/D chars respectively.
    // Robot returned to origin if the final position is (0,0)
    // Time: O(n) for a single pass through moves
    // Space: O(1) for a constant extra storage
    public boolean judgeCircle(String moves) {
        int x = 0, y = 0;
        for (char c : moves.toCharArray()) {
            if (c == 'U') y++;
            else if (c == 'R') x++;
            else if (c == 'D') y--;
            else if (c == 'L') x--;
        }
        return (x==0) && (y==0);
    }
}