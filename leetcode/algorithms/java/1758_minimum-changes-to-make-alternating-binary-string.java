// Source: https://leetcode.com/problems/minimum-changes-to-make-alternating-binary-string/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-04
// At the time of submission:
//   Runtime 4 ms Beats 75.73%
//   Memory 43.56 MB Beats 91.05%

/****************************************
* 
* You are given a string `s` consisting only of the characters `'0'` and `'1'`.
* _ In one operation, you can change any `'0'` to `'1'` or vice versa.
* The string is called alternating if no two adjacent characters are equal.
* _ For example, the string `"010"` is alternating, while the string `"0100"` is not.
* Return the minimum number of operations needed to make s alternating.
*
* Example 1:
* Input: s = "0100"
* Output: 1
* Explanation: If you change the last character to '1', s will be "0101", which is alternating.
*
* Example 2:
* Input: s = "10"
* Output: 0
* Explanation: s is already alternating.
*
* Example 3:
* Input: s = "1111"
* Output: 2
* Explanation: You need two operations to reach "0101" or "1010".
*
* Constraints:
* • `1 <= s.length <= 10^4`
* • `s[i]` is either `'0'` or `'1'`.
* 
****************************************/

class Solution {
    // Count 1s at even and odd indexes to compute mismatches for both patterns.
    // Pattern "0101..." expects even indexes 0, odd indexes 1; mismatches = eC + o-oC.
    // Pattern "1010..." expects even indexes 1, odd indexes 0; mismatches = oC + e-eC.
    // Return the minimum of the two possible patterns.
    // Time: O(n) - single pass through the string; Space: O(1) - only counters used.
    public int minOperations(String s) {
        int n = s.length();
        int e = (n + 1) / 2; // num of even indexes
        int o = n / 2;       // num of odd indexes
        int eC = 0; // count of even-indexed 1s
        int oC = 0; // count of odd-indexed 1s
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if ((i & 1) == 0 && c == '1') { eC++; } 
            else if (c == '1') { oC++; }
        }
        int a = eC + (o - oC); // changes to make "0101..."
        int b = (e - eC) + oC; // changes to make "1010..."
        return Math.min(eC + (o - oC), (e - eC) + oC);
    }
}