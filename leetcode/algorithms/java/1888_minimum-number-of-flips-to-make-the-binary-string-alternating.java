// Source: https://leetcode.com/problems/minimum-number-of-flips-to-make-the-binary-string-alternating/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-06
// At the time of submission:
//   Runtime 24 ms Beats 64.83%
//   Memory 47.00 MB Beats 86.86%

/****************************************
* 
* You are given a binary string `s`. You are allowed to perform two types of
* _ operations on the string in any sequence:
* • Type-1: Remove the character at the start of the string `s` and append
* __ it to the end of the string.
* • Type-2: Pick any character in `s` and flip its value, i.e., if its value
* __ is `'0'` it becomes `'1'` and vice-versa.
* Return the minimum number of type-2 operations you need to perform such
* _ that `s` becomes alternating.
* The string is called alternating if no two adjacent characters are equal.
* • For example, the strings `"010"` and `"1010"` are alternating, while
* __ the string `"0100"` is not.
*
* Example 1:
* Input: s = "111000"
* Output: 2
* Explanation: Use the first operation two times to make s = "100011".
* Then, use the second operation on the third and sixth elements to make s = "101010".
*
* Example 2:
* Input: s = "010"
* Output: 0
* Explanation: The string is already alternating.
*
* Example 3:
* Input: s = "1110"
* Output: 1
* Explanation: Use the second operation on the second element to make s = "1010".
*
* Constraints:
* • `1 <= s.length <= 10^5`
* • `s[i]` is either `'0'` or `'1'`.
* 
****************************************/

class Solution {
    // Treat rotations as substrings of s+s and use a sliding window of size n.
    // For each window, compare against both alternating patterns: "0101..." and
    // "1010...", counting mismatches (flips needed). Update counts as the window
    // moves to avoid recomputation. The minimum mismatches across all windows
    // gives the answer. Time: O(n), Space: O(n).
    public int minFlips(String s) {
        int n = s.length();
        String ss = s + s;

        int diff1 = 0; // mismatches with pattern "0101..."
        int diff2 = 0; // mismatches with pattern "1010..."
        int left = 0;
        int ans = Integer.MAX_VALUE;

        for (int right = 0; right < ss.length(); right++) {
            char c = ss.charAt(right);

            char expected1 = (right % 2 == 0) ? '0' : '1';
            char expected2 = (right % 2 == 0) ? '1' : '0';

            if (c != expected1) diff1++;
            if (c != expected2) diff2++;

            if (right - left + 1 > n) {
                char lc = ss.charAt(left);

                char le1 = (left % 2 == 0) ? '0' : '1';
                char le2 = (left % 2 == 0) ? '1' : '0';

                if (lc != le1) diff1--;
                if (lc != le2) diff2--;

                left++;
            }

            if (right - left + 1 == n) {
                ans = Math.min(ans, Math.min(diff1, diff2));
            }
        }

        return ans;
    }
}