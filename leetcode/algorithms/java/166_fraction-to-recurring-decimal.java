// Source: https://leetcode.com/problems/fraction-to-recurring-decimal/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-23
// At the time of submission:
//   Runtime 1 ms Beats 99.80%
//   Memory 41.14 MB Beats 47.55%

/****************************************
* 
* Given two integers representing the `numerator` and `denominator` of a
* _ fraction, return the fraction in string format.
* If the fractional part is repeating, enclose the repeating part in parentheses.
* If multiple answers are possible, return any of them.
* It is guaranteed that the length of the answer string is less than `10^4`
* _ for all the given inputs.
*
* Example 1:
* Input: numerator = 1, denominator = 2
* Output: "0.5"
*
* Example 2:
* Input: numerator = 2, denominator = 1
* Output: "2"
*
* Example 3:
* Input: numerator = 4, denominator = 333
* Output: "0.(012)"
*
* Constraints:
* • -2^31 <= numerator, denominator <= 2^31 - 1
* • denominator != 0
* 
****************************************/

import java.util.HashMap;

class Solution {
    // The integer part is computed first; if a remainder exists, we track  
    // each remainder's position in the fractional part. Once a remainder  
    // repeats, the decimal starts repeating, so we insert parentheses.  
    // Time Complexity: O(n), where n is the cycle length or digits produced.  
    // Space Complexity: O(n), storing remainders in a map until repetition.  
    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0) return "0";
        StringBuilder result = new StringBuilder();
        // Handle sign
        if ((numerator < 0) ^ (denominator < 0)) result.append("-");
        // Convert to positive longs to avoid overflow
        long num = Math.abs((long) numerator);
        long den = Math.abs((long) denominator);
        // Append integer part
        result.append(num / den);
        long remainder = num % den;
        if (remainder == 0) return result.toString(); // no fractional part
        result.append(".");
        // Map to store remainder -> position in result string
        HashMap<Long, Integer> remainderIndexMap = new HashMap<>();
        while (remainder != 0) {
            if (remainderIndexMap.containsKey(remainder)) {
                int insertPos = remainderIndexMap.get(remainder);
                result.insert(insertPos, "(");
                result.append(")");
                break;
            }
            remainderIndexMap.put(remainder, result.length());
            remainder *= 10;
            result.append(remainder / den);
            remainder %= den;
        }
        return result.toString();
    }
}

