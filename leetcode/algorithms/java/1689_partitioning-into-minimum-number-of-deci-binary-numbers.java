// Source: https://leetcode.com/problems/partitioning-into-minimum-number-of-deci-binary-numbers/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-01
// At the time of submission:
//   Runtime 6 ms Beats 59.95%
//   Memory 47.75 MB Beats 36.63%

/****************************************
* 
* A decimal number is called deci-binary if each of its digits is either `0`
* _ or `1` without any leading zeros. For example, `101` and `1100` are
* _ deci-binary, while `112` and `3001` are not.
* Given a string `n` that represents a positive decimal integer, return the
* minimum number of positive deci-binary numbers needed so that they sum up to `n`.
*
* Example 1:
* Input: n = "32"
* Output: 3
* Explanation: 10 + 11 + 11 = 32
*
* Example 2:
* Input: n = "82734"
* Output: 8
*
* Example 3:
* Input: n = "27346209830709182346"
* Output: 9
*
* Constraints:
* • `1 <= n.length <= 10^5`
* • `n` consists of only digits.
* • `n` does not contain any leading zeros and represents a positive integer.
* 
****************************************/

class Solution {
    // Each deci-binary number contributes at most 1 to each digit position.
    // A digit d therefore requires at least d such numbers to sum correctly.
    // Since digits are independent, the minimum count is the maximum digit.
    // We scan the string once to find that digit.
    // Time complexity is O(n); space complexity is O(1).
    public int minPartitions(String n) {
        int maxDigit = 0;

        for (int i = 0; i < n.length(); i++) {
            int digit = n.charAt(i) - '0';
            if (digit > maxDigit) {
                maxDigit = digit;
            }
        }

        return maxDigit;
    }
}