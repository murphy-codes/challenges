// Source: https://leetcode.com/problems/reverse-integer/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-28

/****************************************
* 
* Given a signed 32-bit integer `x`, return `x` with its digits reversed. If reversing `x` causes the value to go outside the signed 32-bit integer range `[-2^31, 2^31 - 1]`, then return `0`.
* 
* Assume the environment does not allow you to store 64-bit integers (signed or unsigned).
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
    public int reverse(int x) {
        if (x == 0) { return x; } 
        boolean neg = false;
        long reversedLong = 0;
        if (x < 0) { neg = true; }
        String s = String.valueOf(Math.abs(x));
        String reversed = new StringBuilder(s).reverse().toString();
        reversedLong = Long.valueOf(reversed);
        if (reversedLong > Integer.MAX_VALUE) { return 0; }
        else if (neg) {
            reversedLong = -1 * reversedLong;
        }
        return Math.toIntExact(reversedLong);
    }
}