// Source: https://leetcode.com/problems/reverse-integer/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-28
// At the time of submission:
//   Runtime 1 ms Beats 81.73%
//   Memory 40.96 MB Beats 56.68%

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
    // This solution reverses an integer by extracting digits using % and / operators.  
    // It builds the reversed number while checking for overflow using Integer.MAX_VALUE  
    // and Integer.MIN_VALUE. If overflow is detected, it returns 0. This approach avoids  
    // string conversions and adheres to the 32-bit integer constraint.  
    // Time Complexity: O(log10(x)), as we process each digit of the input.  
    // Space Complexity: O(1), using only a few integer variables.  
    public int reverse(int x) {
        int reversed = 0;
        while (x != 0) {
            int digit = x % 10; // Extract the last digit
            x /= 10; // Remove the last digit from x

            // Check for overflow before multiplying or adding
            if (reversed > Integer.MAX_VALUE / 10 || (reversed == Integer.MAX_VALUE / 10 && digit > 7)) {
                return 0; // Overflow for positive integers
            }
            if (reversed < Integer.MIN_VALUE / 10 || (reversed == Integer.MIN_VALUE / 10 && digit < -8)) {
                return 0; // Overflow for negative integers
            }

            reversed = reversed * 10 + digit; // Add the digit to the reversed number
        }
        return reversed;
    }
}