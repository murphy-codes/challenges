// Source: https://leetcode.com/problems/rotate-string/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-03
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 42.20 MB Beats 47.82%

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
    public boolean rotateString(String s, String goal) {
        int n = s.length();
        if (n != goal.length()) return false;

        for (int i = 0; i < n; i++) {
            int j = 0;
            while (j < n && s.charAt(j) == goal.charAt((i + j) % n)) {
                j++;
            }
            if (j == n) return true;
        }
        return false;
    }
}