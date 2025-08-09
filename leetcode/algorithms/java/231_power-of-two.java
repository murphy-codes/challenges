// Source: https://leetcode.com/problems/power-of-two/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-08
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 40.76 MB Beats 79.73%

/****************************************
* 
* Given an integer `n`, return `true` if it is a power of two. Otherwise, return `false`.
* An integer `n` is a power of two, if there exists an integer `x` such that `n == 2^x`.
*
* Example 1:
* Input: n = 1
* Output: true
* Explanation: 2^0 = 1
*
* Example 2:
* Input: n = 16
* Output: true
* Explanation: 2^4 = 16
*
* Example 3:
* Input: n = 3
* Output: false
*
* Constraints:
* • -2^31 <= n <= 2^31 - 1
* 
****************************************/

class Solution {
    public boolean isPowerOfTwo(int n) {
        // A power of two has exactly one bit set in its binary form.
        // This checks that n is positive and that n & (n - 1) == 0.
        return n > 0 && (n & (n - 1)) == 0;
    }
}
