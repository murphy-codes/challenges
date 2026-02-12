// Source: https://leetcode.com/problems/longest-balanced-substring-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-11
// At the time of submission:
//   Runtime 221 ms Beats 32.58%
//   Memory 47.06 MB Beats 42.88%

/****************************************
* 
* You are given a string `s` consisting of lowercase English letters.
* A substring of `s` is called balanced if all distinct characters in the
* _ substring appear the same number of times.
* Return the length of the longest balanced substring of `s`.
*
* Example 1:
* Input: s = "abbac"
* Output: 4
* Explanation:
* The longest balanced substring is "abba" because both distinct characters 'a' and 'b' each appear exactly 2 times.
*
* Example 2:
* Input: s = "zzabccy"
* Output: 4
* Explanation:
* The longest balanced substring is "zabc" because the distinct characters 'z', 'a', 'b', and 'c' each appear exactly 1 time.​​​​​​​
*
* Example 3:
* Input: s = "aba"
* Output: 2
* Explanation:
* ​​​​​​​One of the longest balanced substrings is "ab" because both distinct characters 'a' and 'b' each appear exactly 1 time. Another longest balanced substring is "ba".
*
* Constraints:
* • `1 <= s.length <= 1000`
* • `s consists of lowercase English letters.`
* 
****************************************/

class Solution {
    // We brute-force all substrings by fixing a start index and expanding right.
    // A frequency array tracks character counts, ignoring unused characters.
    // A substring is balanced if all active characters share the same frequency.
    // Since the alphabet size is constant (26), checking min/max is cheap.
    // Time: O(n^2), Space: O(1) constant extra space.
    public int longestBalanced(String s) {
        int n = s.length();
        int maxLen = 0;

        for (int i = 0; i < n; i++) {
            int[] freq = new int[26];
            int distinct = 0;
            int minFreq = Integer.MAX_VALUE;
            int maxFreq = 0;

            for (int j = i; j < n; j++) {
                int idx = s.charAt(j) - 'a';

                if (freq[idx] == 0) {
                    distinct++;
                }
                freq[idx]++;

                // Update min/max frequency among active characters
                minFreq = Integer.MAX_VALUE;
                maxFreq = 0;
                for (int k = 0; k < 26; k++) {
                    if (freq[k] > 0) {
                        minFreq = Math.min(minFreq, freq[k]);
                        maxFreq = Math.max(maxFreq, freq[k]);
                    }
                }

                if (minFreq == maxFreq) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }

        return maxLen;
    }
}
