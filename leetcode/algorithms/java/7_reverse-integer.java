// Source: https://leetcode.com/problems/reverse-integer/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-28
// At the time of submission:
//   Runtime 1 ms Beats 81.73%
//   Memory 40.77 MB Beats 85.39%

/****************************************
* 
* Given a signed 32-bit integer `x`, return `x` with its digits reversed. If 
* _ reversing `x` causes the value to go outside the signed 32-bit integer range 
* _ `[-2^31, 2^31 - 1]`, then return `0`.
* Assume the environment does not allow you to store 64-bit integers 
* _ (signed or unsigned).
* 
* Example 1:
* Input: x = 123
* Output: 321
* 
* Example 2:
* Input: x = -123
* Output: -321
* 
* Example 3:
* Input: x = 120
* Output: 21
* 
* Constraints:
* • -2^31 <= x <= 2^31 - 1
* 
****************************************/

class Solution {
    // Reverses a signed 32-bit integer by extracting and rebuilding digits.
    // Handles negative numbers by working with their absolute value, and 
    // checks for overflow by comparing to Integer.MAX_VALUE / 10. 
    // If overflow is possible, returns 0 immediately to prevent wraparound.
    // Time: O(log₁₀(x)); Space: O(1) — uses constant extra space only.
    public int reverse(int x) {
        boolean isNegative = x < 0;
        if (isNegative) x = -x;

        int reversed = 0;
        while (x > 0) {
            int lastDigit = x % 10;

            // Check for overflow before multiplying by 10
            if (reversed > Integer.MAX_VALUE / 10) return 0;

            reversed = reversed * 10 + lastDigit;
            x /= 10;
        }

        if (isNegative) reversed *= -1;
        return reversed;
    }
}
