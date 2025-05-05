// Source: https://leetcode.com/problems/domino-and-tromino-tiling/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-21
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 40.46 MB Beats 70.13%

/****************************************
* 
* You have two types of tiles: a `2 x 1` domino shape and a tromino shape
* _ (three squares in an L-shape or an R-shape). You may rotate these shapes.
* [Image: https://assets.leetcode.com/uploads/2021/07/15/lc-domino.jpg]
* Given an integer n, return the number of ways to tile an `2 x n` board.
* _ Since the answer may be very large, return it modulo `10^9 + 7`.
* In a tiling, every square must be covered by a tile. Two tilings are
* _ different if and only if there are two 4-directionally adjacent cells
* _ on the board such that exactly one of the tilings has both squares
* _ occupied by a tile.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/07/15/lc-domino1.jpg]
* Input: n = 3
* Output: 5
* Explanation: The five different ways are show above.
*
* Example 2:
* Input: n = 1
* Output: 1
*
* Constraints:
* • 1 <= n <= 1000
* 
****************************************/

class Solution {
    // Dynamic programming with recurrence:
    // dp[n] = 2*dp[n-1] + dp[n-3], covering all valid placements.
    // Base cases handle small n, rest build up from prior states.
    // Time complexity: O(n), Space complexity: O(1), modulo 1e9+7 used.
    public int numTilings(int n) {
        final int MOD = 1_000_000_007;

        if (n == 1) return 1;
        if (n == 2) return 2;
        if (n == 3) return 5;

        // Initialize the base cases:
        long a = 1;  // dp[1]
        long b = 2;  // dp[2]
        long c = 5;  // dp[3]
        long d = 0;

        for (int i = 4; i <= n; i++) {
            d = (2 * c % MOD + a) % MOD;
            a = b;
            b = c;
            c = d;
        }

        return (int)c;
    }
}