// Source: https://leetcode.com/problems/minimum-number-of-flips-to-make-the-binary-string-alternating/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-06
// At the time of submission:
//   Runtime 12 ms Beats 100.00%
//   Memory 47.08 MB Beats 79.66%

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
    // We compute the number of flips required to convert the string into the two
    // alternating patterns: "010101..." and "101010...". For odd-length strings,
    // rotations change index parity, so we simulate rotations using a sliding
    // window over s + s and update mismatch counts incrementally. Each rotation
    // adjusts only the leaving and entering characters, yielding O(n) time and
    // O(n) space complexity.
    public int minFlips(String s) {
        int n = s.length();

        // flips needed to convert to "010101..."
        int flipsForPattern01 = 0;

        // flips needed to convert to "101010..."
        int flipsForPattern10 = 0;

        // Compute mismatch cost for the original string
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                if (s.charAt(i) == '1')
                    flipsForPattern01++;
                else
                    flipsForPattern10++;
            } else {
                if (s.charAt(i) == '0')
                    flipsForPattern01++;
                else
                    flipsForPattern10++;
            }
        }

        int minFlips = Math.min(flipsForPattern01, flipsForPattern10);

        // Only odd-length strings change parity when rotated
        if (n % 2 == 1) {

            // Double the string to simulate circular rotations
            String doubled = s + s;

            // Slide the window of length n
            for (int start = 1; start < n; start++) {

                char leavingChar = doubled.charAt(start - 1);
                char enteringChar = doubled.charAt(start + n - 1);

                // Remove effect of character leaving the window
                if ((start - 1) % 2 == 0) {
                    if (leavingChar == '1')
                        flipsForPattern01--;
                    else
                        flipsForPattern10--;
                } else {
                    if (leavingChar == '0')
                        flipsForPattern01--;
                    else
                        flipsForPattern10--;
                }

                // Add effect of character entering the window
                if ((start + n - 1) % 2 == 0) {
                    if (enteringChar == '1')
                        flipsForPattern01++;
                    else
                        flipsForPattern10++;
                } else {
                    if (enteringChar == '0')
                        flipsForPattern01++;
                    else
                        flipsForPattern10++;
                }

                minFlips = Math.min(minFlips,
                        Math.min(flipsForPattern01, flipsForPattern10));
            }
        }

        return minFlips;
    }
}