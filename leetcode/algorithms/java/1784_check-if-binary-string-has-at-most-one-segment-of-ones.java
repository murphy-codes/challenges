// Source: https://leetcode.com/problems/check-if-binary-string-has-at-most-one-segment-of-ones/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-06
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 43.07 MB Beats 31.84%

/****************************************
* 
* Given a binary string `s` ​​​​​without leading zeros, return `true`​​​ if `s` contains
* _ at most one contiguous segment of ones. Otherwise, return `false`.
*
* Example 1:
* Input: s = "1001"
* Output: false
* Explanation: The ones do not form a contiguous segment.
*
* Example 2:
* Input: s = "110"
* Output: true
*
* Constraints:
* • `1 <= s.length <= 100`
* • `s[i]` is either `'0'` or `'1'`.
* • `s[0]` is `'1'`.
* 
****************************************/

class Solution {
    // We scan the binary string and count transitions between characters.
    // A valid string may contain only one segment of '1's, meaning the
    // pattern can only change once from '1' to '0'. If a second transition
    // occurs (e.g., "1 -> 0 -> 1"), we immediately return false.
    // Time Complexity: O(n). Space Complexity: O(1).
    public boolean checkOnesSegment(String s) {
        int n = s.length();
        if (n==1) return true;
        int flips = 0;
        for (int i = 1; i < n; i++) {
            char c = s.charAt(i);
            char p = s.charAt(i-1);
            if (c==p) continue;
            else if (flips==1) return false;
            else flips++;
        }
        return true;
    }
}