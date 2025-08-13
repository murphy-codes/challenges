// Source: https://leetcode.com/problems/power-of-three/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-12
// At the time of submission:
//   Runtime 7 ms Beats 100.00%
//   Memory 44.44 MB Beats 18.53%

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
    // This method checks if n is a power of three by dividing n by 3 repeatedly
    // until it's no longer divisible. If the result is 1, n is a power of three.
    // Time complexity: O(log₃ n), as we divide by 3 each loop iteration.
    // Space complexity: O(1), using only a constant amount of extra memory.
    public boolean isPowerOfThree(int n) {
        // Return false immediately for non-positive integers
        if (n <= 0) return false;
        // Keep dividing by 3 while divisible
        while (n % 3 == 0) {
            n /= 3;
        }
        // If reduced to 1, it's a power of three
        return n == 1;
    }
}