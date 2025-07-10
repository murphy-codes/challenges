// Source: https://leetcode.com/problems/string-to-integer-atoi/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-09
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 42.56 MB Beats 48.32%

/****************************************
* 
* Implement the `myAtoi(string s)` function, which converts a string to a
* _ 32-bit signed integer.
* The algorithm for `myAtoi(string s)` is as follows:
* 1. Whitespace: Ignore any leading whitespace (`" "`).
* 2. Signedness: Determine the sign by checking if the next character is `'-'` or
* __ `'+'`, assuming positivity if neither present.
* 3. Conversion: Read the integer by skipping leading zeros until a non-digit
* __ character is encountered or the end of the string is reached. If no digits
* __ were read, then the result is 0.
* 4. Rounding: If the integer is out of the 32-bit signed integer range
* __ `[-2^31, 2^31 - 1]`, then round the integer to remain in the range.
* __ Specifically, integers less than `-2^31` should be rounded to `-2^31`, and
* __ integers greater than `2^31 - 1` should be rounded to `2^31 - 1`.
* Return the integer as the final result.
*
* Example 1:
* Input: s = "42"
* Output: 42
* Explanation:
* The underlined characters are what is read in and the caret is the current reader position.
* Step 1: "42" (no characters read because there is no leading whitespace)
* Step 2: "42" (no characters read because there is neither a '-' nor '+')
* Step 3: "42" ("42" is read in)
*
* Example 2:
* Input: s = " -042"
* Output: -42
* Explanation:
* Step 1: "   -042" (leading whitespace is read and ignored)
* Step 2: "   -042" ('-' is read, so the result should be negative)
* Step 3: "   -042" ("042" is read in, leading zeros ignored in the result)
* Example 3:
* Input: s = "1337c0d3"
* Output: 1337
*
* Explanation:
* Step 1: "1337c0d3" (no characters read because there is no leading whitespace)
* Step 2: "1337c0d3" (no characters read because there is neither a '-' nor '+')
* Step 3: "1337c0d3" ("1337" is read in; reading stops because the next character is a non-digit)
* Example 4:
* Input: s = "0-1"
* Output: 0
* Explanation:
* Step 1: "0-1" (no characters read because there is no leading whitespace)
* Step 2: "0-1" (no characters read because there is neither a '-' nor '+')
* Step 3: "0-1" ("0" is read in; reading stops because the next character is a non-digit)
*
* Example 5:
* Input: s = "words and 987"
* Output: 0
* Explanation:
* Reading stops at the first non-digit character 'w'.
*
* Constraints:
* • 0 <= s.length <= 200
* • `s` consists of English letters (lower-case and upper-case), digits (`0-9`), `' '`, `'+'`, `'-'`, and `'.'`.
* 
****************************************/

class Solution {
    // Parses a string into a 32-bit signed integer, mimicking atoi.
    // Skips leading spaces, handles optional sign, reads digits until
    // non-digit or end. Clamps to int bounds if out of range.
    // Time: O(n), Space: O(1) where n = length of input string.
    public int myAtoi(String s) {
        int i = 0, n = s.length();
        // 1. Skip leading whitespace
        while (i < n && s.charAt(i) == ' ') i++;

        // 2. Check for optional sign
        int sign = 1;
        if (i < n && (s.charAt(i) == '+' || s.charAt(i) == '-')) {
            if (s.charAt(i) == '-') sign = -1;
            i++;
        }

        // 3. Read digits and build number
        long num = 0;
        while (i < n && Character.isDigit(s.charAt(i))) {
            num = num * 10 + (s.charAt(i) - '0');

            // 4. Clamp early if overflow/underflow
            if (sign == 1 && num > Integer.MAX_VALUE) return Integer.MAX_VALUE;
            if (sign == -1 && -num < Integer.MIN_VALUE) return Integer.MIN_VALUE;

            i++;
        }

        return (int)(sign * num);
    }
}
