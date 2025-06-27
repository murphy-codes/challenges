// Source: https://leetcode.com/problems/longest-subsequence-repeated-k-times/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-26
// At the time of submission:
//   Runtime 97 ms Beats 100.00%
//   Memory 45.38 MB Beats 52.83%

/****************************************
* 
* You are given a string `s` of length `n`, and an integer `k`.
* _ You are tasked to find the longest subsequence repeated `k` times in string `s`.
* A subsequence is a string that can be derived from another string by deleting
* _ some or no characters without changing the order of the remaining characters.
* A subsequence `seq` is repeated `k` times in the string `s` if `seq * k` is a
* _ subsequence of `s`, where `seq * k` represents a string constructed by
* _ concatenating `seq` `k` times.
* • For example, `"bba"` is repeated 2 times in the string `"bababcba"`, because
* __ the string `"bbabba"`, constructed by concatenating `"bba"` `2` times, is a
* __ subsequence of the string `"bababcba"`.
* Return the longest subsequence repeated `k` times in string `s`. If multiple
* _ such subsequences are found, return the lexicographically largest one.
* _ If there is no such subsequence, return an empty string.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/08/30/longest-subsequence-repeat-k-times.png]
* Input: s = "letsleetcode", k = 2
* Output: "let"
* Explanation: There are two longest subsequences repeated 2 times: "let" and "ete".
* let is the lexicographically largest one.
*
* Example 2:
* Input: s = "bb", k = 2
* Output: "b"
* Explanation: The longest subsequence repeated 2 times is "b".
*
* Example 3:
* Input: s = "ab", k = 2
* Output: ""
* Explanation: There is no subsequence repeated 2 times. Empty string is returned.
*
* Constraints:
* • n == s.length
* • 2 <= n, k <= 2000
* • 2 <= n < k * 8
* • `s` consists of lowercase English letters
* 
****************************************/

import java.util.ArrayList;

class Solution {
    // Build all possible subsequences using characters that appear at least k times.
    // Group valid subsequences by length (up to 7) and generate longer ones by appending
    // one character at a time. For each candidate, check if it repeats k times in s.
    // Among valid options, track the lexicographically largest of the longest ones.
    // Time: O(n * m!), where m = max subsequence length ≤ 7; Space: O(m!).
    public String longestSubsequenceRepeatedK(String s, int k) {
        char[] source = s.toCharArray();
        int n = source.length;

        char[] freq = new char[26];
        for (char ch : source)
            freq[ch - 'a']++;

        // Store candidates of length 1 to 7
        ArrayList<String>[] candidates = new ArrayList[8];
        candidates[1] = new ArrayList<>();
        String result = "";

        // Initialize with characters that appear at least k times
        for (int i = 0; i < 26; i++) {
            if (freq[i] >= k) {
                String single = "" + (char) ('a' + i);
                candidates[1].add(single);
                result = single; // Track latest valid result
            }
        }

        // Generate candidate subsequences of length 2 to 7
        for (int len = 2; len < 8; len++) {
            candidates[len] = new ArrayList<>();
            for (String prev : candidates[len - 1]) {
                for (String suffix : candidates[1]) {
                    String next = prev + suffix;
                    if (isKRepeatedSubsequence(source, next, k)) {
                        candidates[len].add(next);
                        result = next; // Update best result
                    }
                }
            }
        }

        return result;
    }

    private boolean isKRepeatedSubsequence(char[] source, String pattern, int k) {
        char[] target = pattern.toCharArray();
        int i = 0, j = 0, n = source.length, m = target.length;

        while (k-- > 0) {
            j = 0;
            while (i < n && j < m) {
                if (source[i] == target[j]) j++;
                i++;
            }
            if (j != m) return false;
        }
        return true;
    }
}
