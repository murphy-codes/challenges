// Source: https://leetcode.com/problems/longest-subsequence-repeated-k-times/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-26
// At the time of submission:
//   Runtime 200 ms Beats 86.79%
//   Memory 45.69 MB Beats 18.87%

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

import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

class Solution {
    // Use BFS to build all possible subsequences from characters 
    // that appear at least k times. Check each candidate to see 
    // if it can be repeated k times as a subsequence of s.
    // Prioritize lexicographically larger strings by ordering 
    // candidate characters in reverse.
    public String longestSubsequenceRepeatedK(String s, int k) {
        int[] freq = new int[26];
        for (char ch : s.toCharArray()) freq[ch - 'a']++;

        List<Character> candidate = new ArrayList<>();
        for (int i = 25; i >= 0; i--) {
            if (freq[i] >= k) candidate.add((char)('a' + i));
        }

        Queue<String> queue = new LinkedList<>();
        for (char ch : candidate) queue.offer(String.valueOf(ch));

        String answer = "";
        while (!queue.isEmpty()) {
            Queue<String> nextQueue = new LinkedList<>();
            while (!queue.isEmpty()) {
                String curr = queue.poll();
                if (curr.length() > answer.length() ||
                    (curr.length() == answer.length() && curr.compareTo(answer) > 0)) {
                    answer = curr;
                }
                for (char ch : candidate) {
                    String next = curr + ch;
                    if (isKRepeatedSubsequence(s, next, k)) {
                        nextQueue.offer(next);
                    }
                }
            }
            queue = nextQueue; // Move to the next level
        }

        return answer;
    }

    private boolean isKRepeatedSubsequence(String s, String t, int k) {
        int pos = 0, matched = 0, m = t.length();
        for (char ch : s.toCharArray()) {
            if (ch == t.charAt(pos)) {
                pos++;
                if (pos == m) {
                    matched++;
                    pos = 0;
                    if (matched == k) return true;
                }
            }
        }
        return false;
    }
}
