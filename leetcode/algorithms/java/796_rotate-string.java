// Source: https://leetcode.com/problems/rotate-string/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-03
// At the time of submission:
//   Runtime 1 ms Beats 23.63%
//   Memory 42.80 MB Beats 58.73%

/****************************************
* 
* Given two strings `s` and `goal`, return `true` if and only if `s` can
* _ become `goal` after some number of shifts on `s`.
* A shift on `s` consists of moving the leftmost character of `s` to the
* _ rightmost position.
* For example, if `s = "abcde"`, then it will be `"bcdea"` after one shift.
*
* Example 1:
* Input: s = "abcde", goal = "cdeab"
* Output: true
*
* Example 2:
* Input: s = "abcde", goal = "abced"
* Output: false
*
* Constraints:
* • `1 <= s.length, goal.length <= 100`
* • `s` and `goal` consist of lowercase English letters.
* 
****************************************/

class Solution {
    // Treat rotation as substring search: goal must exist in s+s
    // Use KMP to match goal in linear time via LPS backtracking
    // Avoids redundant comparisons vs naive O(n^2) scanning
    // Time: O(n), Space: O(n) for LPS array
    
    public boolean rotateString(String s, String goal) {
        if (s.length() != goal.length()) return false;
        return kmpSearch(s + s, goal);
    }

    private int[] buildLPS(String pattern) {
        int n = pattern.length();
        int[] lps = new int[n];

        int len = 0;
        int i = 1;

        while (i < n) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    private boolean kmpSearch(String text, String pattern) {
        int[] lps = buildLPS(pattern);

        int i = 0, j = 0;

        while (i < text.length()) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;

                if (j == pattern.length()) return true;
            } else {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        return false;
    }
}