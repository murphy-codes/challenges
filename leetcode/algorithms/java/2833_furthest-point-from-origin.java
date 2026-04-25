// Source: https://leetcode.com/problems/furthest-point-from-origin/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-24
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 43.61 MB Beats 82.34%

/****************************************
* 
* You are given a string moves of length n consisting only of characters `'L'`,
* _ `'R'`, and `'_'`. The string represents your movement on a number line
* _ starting from the origin 0.
* In the ith move, you can choose one of the following directions:
* • move to the left if `moves[i] = 'L'` or `moves[i] = '_'`
* • move to the right if `moves[i] = 'R'` or `moves[i] = '_'`
* Return the distance from the origin of the furthest point you can get
* _ to after n moves.
*
* Example 1:
* Input: moves = "L_RL__R"
* Output: 3
* Explanation: The furthest point we can reach from the origin 0 is point -3 through the following sequence of moves "LLRLLLR".
*
* Example 2:
* Input: moves = "_R__LL_"
* Output: 5
* Explanation: The furthest point we can reach from the origin 0 is point -5 through the following sequence of moves "LRLLLLL".
*
* Example 3:
* Input: moves = "_______"
* Output: 7
* Explanation: The furthest point we can reach from the origin 0 is point 7 through the following sequence of moves "RRRRRRR".
*
* Constraints:
* • `1 <= moves.length == n <= 50`
* • `moves` consists only of characters `'L'`, `'R'` and `'_'`.
* 
****************************************/

class Solution {
    // Count left & right moves. Convert all blank moves to  
    // match whichever direction is the greater of the two.
    // This makes the final answer equal to total moves minus twice
    // the smaller of left/right counts. Time: O(n), Space: O(1).
    public int furthestDistanceFromOrigin(String moves) {
        int lCnt = 0;
        int rCnt = 0;
        for (int i = 0; i < moves.length(); i++) {
            if (moves.charAt(i) == 'L') lCnt++;
            else if (moves.charAt(i) == 'R') rCnt++;
        }
        if (lCnt > rCnt) return moves.length() - (2*rCnt);
        else return moves.length() - (2*lCnt);
    }
}