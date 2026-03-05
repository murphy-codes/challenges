// Source: https://leetcode.com/problems/minimum-changes-to-make-alternating-binary-string/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-04
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 44.05 MB Beats 18.42%

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
    // Greedily fix adjacent duplicates to form alternating binary strings.
    // Helper flips a character if it matches the previous one, counting changes.
    // We try two patterns: start with original first char or flipped first char.
    // Return the minimum changes needed between the two patterns.
    // Time: O(n) — single pass for each pattern; Space: O(n) — arrays for patterns.

    // Helper: returns number of changes to make array alternate
    private static int fixAlternating(char[] chars) {
        int changes = 0;
        int n = chars.length;
        for (int i = 1; i < n; i++) {
            if (chars[i] != chars[i - 1]) continue; // already alternating
            // flip current character to avoid same as previous
            chars[i] = chars[i - 1] == '0' ? '1' : '0';
            changes++;
        }
        return changes;
    }

    public int minOperations(String s) {
        int n = s.length();
        // pattern 1: start with original first char
        char[] pattern1 = s.toCharArray();
        int ans1 = fixAlternating(pattern1);

        // pattern 2: flip first char, then fix alternation
        char[] pattern2 = s.toCharArray();
        pattern2[0] = pattern2[0] == '0' ? '1' : '0';
        int ans2 = fixAlternating(pattern2) + 1; // +1 for first char flip

        return Math.min(ans1, ans2);
    }
}