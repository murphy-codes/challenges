// Source: https://leetcode.com/problems/longest-balanced-substring-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-11
// At the time of submission:
//   Runtime 21 ms Beats 100.00%
//   Memory 46.79 MB Beats 89.70%

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
    // We expand every substring using two pointers while tracking character
    // frequencies, number of unique characters, and the maximum frequency.
    // A substring is balanced if its length equals uniqueCount * maxFrequency,
    // meaning all unique characters appear equally often. Early termination
    // prevents unnecessary work once no longer substring can beat the best.
    // Time complexity is O(n^2); space complexity is O(1) using a fixed array.
    
    public int longestBalanced(String s) {
        int n = s.length();

        // Convert characters to integer indices (0–25)
        int[] chars = new int[n];
        for (int i = 0; i < n; i++) {
            chars[i] = s.charAt(i) - 'a';
        }

        int bestLength = 0;

        for (int left = 0; left < n; left++) {
            // Early exit: remaining substring cannot beat current best
            if (n - left <= bestLength) {
                break;
            }

            int[] freq = new int[26];
            int uniqueCount = 0;
            int maxFrequency = 0;

            for (int right = left; right < n; right++) {
                int ch = chars[right];

                // New character discovered
                if (freq[ch] == 0) {
                    uniqueCount++;
                }

                freq[ch]++;
                if (freq[ch] > maxFrequency) {
                    maxFrequency = freq[ch];
                }

                int windowLength = right - left + 1;

                // Balanced if all unique chars occur maxFrequency times
                if (uniqueCount * maxFrequency == windowLength &&
                    windowLength > bestLength) {
                    bestLength = windowLength;
                }
            }
        }

        return bestLength;
    }
}
