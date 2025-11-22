// Source: https://leetcode.com/problems/unique-length-3-palindromic-subsequences/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-21
// At the time of submission:
//   Runtime 17 ms Beats 99.38%
//   Memory 46.72 MB Beats 31.38%

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
    // Counts distinct palindromic subsequences of length 3 by recording
    // the first occurrence of each character and using prefix OR bitmasks
    // to track which letters appear between matching endpoints.
    // For each character scanned from the end, we count how many distinct
    // middle letters exist via a bit count. Time: O(n * 26), Space: O(n).
    public int countPalindromicSubsequence(String s) {
        int n = s.length();
        // Stores the first index where each character ('a'..'z') appears
        int[] firstPos = new int[26];
        // posMask[i] = OR of all character bits seen up to index i
        int[] posMask = new int[n];
        Arrays.fill(firstPos, -1);
        int runningMask = 0;
        // Forward pass: compute prefix bitmasks and record first occurrences
        for (int i = 0; i < n; i++) {
            int charIndex = s.charAt(i) - 'a';
            // Add this character to the running bitmask
            posMask[i] = (runningMask |= 1 << charIndex);
            // When seeing the first occurrence of a character,
            // mark its index and reset the mask
            if (firstPos[charIndex] == -1) {
                firstPos[charIndex] = i;
                runningMask = 0;
            }
        }
        int result = 0;
        // Backward scan: treat s[i] as the right endpoint of a palindrome
        for (int i = n - 1; i >= 2; i--) {
            int charIndex = s.charAt(i) - 'a';
            // If this character has no valid starting point, skip
            if (firstPos[charIndex] <= -1 || firstPos[charIndex] == i) {
                continue;
            }
            // Count how many distinct characters exist between
            // this character's first occurrence and this i
            result += Integer.bitCount(
                    accumulateOR(firstPos[charIndex] + 1, i - 1, posMask)
            );
            // Mark this character as fully processed
            firstPos[charIndex] = -2;
        }
        return result;
    }

    // ORs all prefix masks in the range [start, end]
    private int accumulateOR(int start, int end, int[] prefixMasks) {
        int mask = 0;
        for (int i = start; i <= end; i++) {
            mask |= prefixMasks[i];
        }
        return mask;
    }
}
