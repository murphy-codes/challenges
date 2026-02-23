// Source: https://leetcode.com/problems/check-if-a-string-contains-all-binary-codes-of-size-k/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-22
// At the time of submission:
//   Runtime 9 ms Beats 96.54%
//   Memory 47.92 MB Beats 94.07%

/****************************************
* 
* Given a binary string s and an integer `k`, return `true` if every binary
* _ code of length `k` is a substring of `s`. Otherwise, return `false`.
*
* Example 1:
* Input: s = "00110110", k = 2
* Output: true
* Explanation: The binary codes of length 2 are "00", "01", "10" and "11". They can be all found as substrings at indices 0, 1, 3 and 2 respectively.
*
* Example 2:
* Input: s = "0110", k = 1
* Output: true
* Explanation: The binary codes of length 1 are "0" and "1", it is clear that both exist as a substring.
*
* Example 3:
* Input: s = "0110", k = 2
* Output: false
* Explanation: The binary code "00" is of length 2 and does not exist in the array.
*
* Constraints:
* • `1 <= s.length <= 5 * 10^5`
* • `s[i]` is either `'0'` or `'1'`.
* • `1 <= k <= 20`
* 
****************************************/

class Solution {
    // Use a rolling bitmask to represent each length-k substring as an integer.
    // Slide across the string, updating the mask in O(1) time per character.
    // Track seen patterns in a boolean array of size 2^k.
    // If all 2^k patterns appear, return true.
    // Time: O(n), Space: O(2^k)
    public boolean hasAllCodes(String s, int k) {
        int needed = 1 << k;
        boolean[] seen = new boolean[needed];

        int mask = needed - 1; // keeps last k bits
        int hash = 0;
        int found = 0;

        for (int i = 0; i < s.length(); i++) {
            // shift left, add current bit, keep only k bits
            hash = ((hash << 1) & mask) | (s.charAt(i) - '0');

            // only start recording after first k bits
            if (i >= k - 1 && !seen[hash]) {
                seen[hash] = true;
                found++;
                if (found == needed) return true;
            }
        }
        return false;
    }
}