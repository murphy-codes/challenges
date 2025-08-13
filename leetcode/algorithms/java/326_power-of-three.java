// Source: https://leetcode.com/problems/power-of-three/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-12
// At the time of submission:
//   Runtime 8 ms Beats 84.41%
//   Memory 44.30 MB Beats 39.26%

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
    // Checks if n is a power of three by iteratively dividing by 3 while n > 3.
    // If at any point n is not divisible by 3, returns false immediately.
    // Finally, verifies if n equals 3 or was initially 1 (both valid powers).
    // Time Complexity: O(log₃ n), since n is divided by 3 each iteration.
    // Space Complexity: O(1), using only constant extra memory.
    public boolean isPowerOfThree(int n) {
        if (n == 1) return true;
        while (n > 3) {
            if (n % 3 != 0) return false;
            n = n / 3;
        }
        return n == 3;
    }
}