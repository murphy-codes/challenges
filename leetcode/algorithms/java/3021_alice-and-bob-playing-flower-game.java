// Source: https://leetcode.com/problems/alice-and-bob-playing-flower-game/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-29
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 40.70 MB Beats 54.60%

/****************************************
* 
* Alice and Bob are playing a turn-based game on a field, with two lanes of
* _ flowers between them. There are x flowers in the first lane between Alice
* _ and Bob, and y flowers in the second lane between them.
* [Image: https://assets.leetcode.com/uploads/2025/08/27/3021.png]
* The game proceeds as follows:
* 1. Alice takes the first turn.
* 2. In each turn, a player must choose either one of the lane and pick one
* __ flower from that side.
* 3. At the end of the turn, if there are no flowers left at all, the current
* __ player captures their opponent and wins the game.
* Given two integers, `n` and `m`, the task is to compute the number of
* _ possible pairs `(x, y)` that satisfy the conditions:
* • Alice must win the game according to the described rules.
* • The number of flowers x in the first lane must be in the range [1,n].
* • The number of flowers y in the second lane must be in the range [1,m].
* Return the number of possible pairs (x, y) that satisfy the conditions
* _ mentioned in the statement.
*
* Example 1:
* Input: n = 3, m = 2
* Output: 3
* Explanation: The following pairs satisfy conditions described in the statement: (1,2), (3,2), (2,1).
*
* Example 2:
* Input: n = 1, m = 1
* Output: 0
* Explanation: No pairs satisfy the conditions described in the statement.
*
* Constraints:
* • 1 <= n, m <= 10^5
* 
****************************************/

class Solution {
    // Alice wins iff x and y have different parities (one odd, one even).
    // Count odds and evens in [1..n] and [1..m]. The valid pairs are the
    // combinations where parity differs: oddN*evenM + evenN*oddM.
    // Time complexity: O(1), since we only do a few arithmetic ops.
    // Space complexity: O(1), as only constant extra space is used.
    public long flowerGame(int n, int m) {
        long oddN = (n + 1) / 2;
        long evenN = n / 2;
        long oddM = (m + 1) / 2;
        long evenM = m / 2;

        // Alice wins if parities differ: one odd + one even
        return oddN * evenM + evenN * oddM;
    }
}
