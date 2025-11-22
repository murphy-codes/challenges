// Source: https://leetcode.com/problems/unique-length-3-palindromic-subsequences/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-03
// At the time of submission:
//   Runtime 35 ms Beats 97.54%
//   Memory 47.18 MB Beats 13.85%

/****************************************
* 
* Given a string `s`, return the number of unique palindromes of length three
* _ that are a subsequence of `s`.
* Note that even if there are multiple ways to obtain the same subsequence,
* _ it is still only counted once.
* A palindrome is a string that reads the same forwards and backwards.
* A subsequence of a string is a new string generated from the original string
* _ with some characters (can be none) deleted without changing the relative
* _ order of the remaining characters.
* • For example, `"ace"` is a subsequence of `"abcde"`.
*
* Example 1:
* Input: s = "aabca"
* Output: 3
* Explanation: The 3 palindromic subsequences of length 3 are:
* - "aba" (subsequence of "aabca")
* - "aaa" (subsequence of "aabca")
* - "aca" (subsequence of "aabca")
*
* Example 2:
* Input: s = "adc"
* Output: 0
* Explanation: There are no palindromic subsequences of length 3 in "adc".
*
* Example 3:
* Input: s = "bbcbaba"
* Output: 4
* Explanation: The 4 palindromic subsequences of length 3 are:
* - "bbb" (subsequence of "bbcbaba")
* - "bcb" (subsequence of "bbcbaba")
* - "bab" (subsequence of "bbcbaba")
* - "aba" (subsequence of "bbcbaba")
*
* Constraints:
* • `3 <= s.length <= 10^5`
* • `s` consists of only lowercase English letters.
* 
****************************************/

class Solution {
    // For each char 'x', find first and last occurrence in s. Any unique char
    // between them can be the middle of a palindrome x y x. Count distinct middle
    // letters for all 26 chars. Runs in O(26*n) time with O(1) extra space.
    public int countPalindromicSubsequence(String s) {
        int n = s.length();
        int[][] pos = new int[26][2]; // [char][0]=first, [1]=last
        
        // Initialize
        for (int i = 0; i < 26; i++) {
            pos[i][0] = Integer.MAX_VALUE;
            pos[i][1] = Integer.MIN_VALUE;
        }

        // Track first and last occurrence of each character
        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';
            pos[c][0] = Math.min(pos[c][0], i);
            pos[c][1] = Math.max(pos[c][1], i);
        }

        int result = 0;

        // For each character as the outer letter
        for (int c = 0; c < 26; c++) {
            int left = pos[c][0];
            int right = pos[c][1];

            if (left < right) {
                boolean[] seen = new boolean[26];

                // Count how many distinct letters appear in the middle
                for (int i = left + 1; i < right; i++) {
                    seen[s.charAt(i) - 'a'] = true;
                }

                // Add the number of unique inner characters
                for (boolean b : seen) {
                    if (b) result++;
                }
            }
        }

        return result;
    }
}
