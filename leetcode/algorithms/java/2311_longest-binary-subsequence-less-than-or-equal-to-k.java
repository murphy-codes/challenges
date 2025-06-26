// Source: https://leetcode.com/problems/longest-binary-subsequence-less-than-or-equal-to-k/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-25
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 41.88 MB Beats 67.02%

/****************************************
* 
* You are given a binary string `s` and a positive integer `k`.
* Return the length of the longest subsequence of `s` that makes up a
* _ binary number less than or equal to `k`.
* Note:
* • The subsequence can contain leading zeroes.
* • The empty string is considered to be equal to `0`.
* • A subsequence is a string that can be derived from another string by deleting
* __ some or no characters without changing the order of the remaining characters.
*
* Example 1:
* Input: s = "1001010", k = 5
* Output: 5
* Explanation: The longest subsequence of s that makes up a binary number less than or equal to 5 is "00010", as this number is equal to 2 in decimal.
* Note that "00100" and "00101" are also possible, which are equal to 4 and 5 in decimal, respectively.
* The length of this subsequence is 5, so 5 is returned.
*
* Example 2:
* Input: s = "00101001", k = 1
* Output: 6
* Explanation: "000001" is the longest subsequence of s that makes up a binary number less than or equal to 1, as this number is equal to 1 in decimal.
* The length of this subsequence is 6, so 6 is returned.
*
* Constraints:
* • 1 <= s.length <= 1000
* • s[i] is either '0' or '1'.
* • 1 <= k <= 10^9
* 
****************************************/

class Solution {
    // Greedily selects the longest subsequence forming a binary value <= k.
    // All '0's are included, as they don't affect the binary value.
    // 1s are added from least significant to most significant, only if they
    // keep the cumulative binary value <= k.
    // Time: O(n), Space: O(1) — single pass through the string.
    public int longestSubsequence(String s, int k) {
        int sm = 0;
        int cnt = 0;
        int bits = (int)(Math.log(k) / Math.log(2)) + 1;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(s.length() - 1 - i);
            if (ch == '1') {
                if (i < bits && sm + (1 << i) <= k) {
                    sm += 1 << i;
                    cnt++;
                }
            } else {
                cnt++;
            }
        }
        return cnt;
    }
}
