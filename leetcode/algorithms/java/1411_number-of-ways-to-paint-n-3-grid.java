// Source: https://leetcode.com/problems/number-of-ways-to-paint-n-3-grid/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-02
// At the time of submission:
//   Runtime 3 ms Beats 93.85%
//   Memory 42.38 MB Beats 35.38%

/****************************************
* 
* You have a `grid` of size `n x 3` and you want to paint each cell of the grid
* _ with exactly one of the three colors: Red, Yellow, or Green while making
* _ sure that no two adjacent cells have the same color (i.e., no two cells that
* _ share vertical or horizontal sides have the same color).
* Given `n` the number of rows of the grid, return the number of ways you can
* _ paint this `grid`. As the answer may grow large, the answer must be computed
* _ modulo `10^9 + 7`.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2020/03/26/e1.png]
* Input: n = 1
* Output: 12
* Explanation: There are 12 possible way to paint the grid as shown.
*
* Example 2:
* Input: n = 5000
* Output: 30228214
*
* Constraints:
* • `n == grid.length
* • `1 <= n <= 5000
* 
****************************************/

class Solution {
    // Each row has two valid color patterns: ABA (2 colors) or ABC (3 colors).
    // Track how many ways each pattern can occur and transition row by row.
    // Transitions depend only on the previous row’s pattern type, not colors.
    // Time: O(n), iterating once through all rows.
    // Space: O(1), using only two rolling counters.

    private static final int MOD = 1_000_000_007;

    public int numOfWays(int n) {
        long aba = 6; // patterns like A B A
        long abc = 6; // patterns like A B C

        for (int i = 2; i <= n; i++) {
            long newAba = (aba * 3 + abc * 2) % MOD;
            long newAbc = (aba * 2 + abc * 2) % MOD;
            aba = newAba;
            abc = newAbc;
        }

        return (int)((aba + abc) % MOD);
    }
}
