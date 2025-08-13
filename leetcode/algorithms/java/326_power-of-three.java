// Source: https://leetcode.com/problems/power-of-three/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-12
// At the time of submission:
//   Runtime 8 ms Beats 84.41%
//   Memory 43.90 MB Beats 83.75%

/****************************************
* 
* Given an integer `n`, return `true` if it is a power of three.
* _ Otherwise, return `false`.
* An integer `n` is a power of three, if there exists an
* _ integer `x` such that `n == 3^x`.
*
* Example 1:
* Input: n = 27
* Output: true
* Explanation: 27 = 3^3
*
* Example 2:
* Input: n = 0
* Output: false
* Explanation: There is no x where 3^x = 0.
*
* Example 3:
* Input: n = -1
* Output: false
* Explanation: There is no x where 3^x = (-1).
*
* Constraints:
* • -2^31 <= n <= 2^31 - 1
* 
****************************************/

class Solution {
    // Uses the fact that the largest power of 3 in 32-bit int range is 3^19.
    // If n is a positive divisor of 3^19, then n must be a power of 3.
    // This eliminates loops or recursion, achieving constant-time checks.
    // Time Complexity: O(1) since it's a single modulus operation.
    // Space Complexity: O(1) since no extra memory is used.
    public boolean isPowerOfThree(int n) {
        final int MAX_POWER_OF_THREE = 1162261467; // 3^19
        return n > 0 && MAX_POWER_OF_THREE % n == 0;
    }
}
