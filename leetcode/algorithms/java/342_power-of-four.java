// Source: https://leetcode.com/problems/power-of-four/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-14
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 41.67 MB Beats 8.89%

/****************************************
* 
* Given an integer `n`, return `true` if it is a power of four.
* _ Otherwise, return `false`.
* An integer `n` is a power of four, if there exists
* _ an integer `x` such that `n == 4^x`.
*
* Example 1:
* Input: n = 16
* Output: true
*
* Example 2:
* Input: n = 5
* Output: false
*
* Example 3:
* Input: n = 1
* Output: true
*
* Constraints:
* • -2^31 <= n <= 2^31 - 1
* 
****************************************/

class Solution {
    // Checks if n is a power of four using bitwise operations only.
    // 1) n > 0 and a power of two (single bit set).
    // 2) The set bit is in an odd position, verified via mask 0x55555555.
    // Time: O(1), Space: O(1) — no loops, recursion, or extra storage used.
    public boolean isPowerOfFour(int n) {
        // Must be positive, a power of two, and bit at an odd position
        return n > 0 && (n & (n - 1)) == 0 && (n & 0x55555555) != 0;
    }
}