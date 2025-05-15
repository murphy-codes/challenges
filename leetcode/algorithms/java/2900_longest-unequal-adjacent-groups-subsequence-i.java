// Source: https://leetcode.com/problems/total-characters-in-string-after-transformations-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-14
// At the time of submission:
//   Runtime 1 ms Beats 96.15%
//   Memory 44.79 MB Beats 89.07%

/****************************************
* 
* You are given a string array `words` and a binary array `groups` both of length
* _ `n`, where `words[i]` is associated with `groups[i]`.
* Your task is to select the longest alternating subsequence from words. A
* _ subsequence of `words` is alternating if for any two consecutive strings in
* _ the sequence, their corresponding elements in the binary array `groups` differ.
* _ Essentially, you are to choose strings such that adjacent elements have
* _ non-matching corresponding bits in the `groups` array.
* Formally, you need to find the longest subsequence of an array of indices
* _ `[0, 1, ..., n - 1]` denoted as `[i_0, i_1, ..., i_k-1]`, such that
* _ `groups[i_j] != groups[i_j+1]` for each `0 <= j < k - 1` and then find the
* _ words corresponding to these indices.
* Return the selected subsequence. If there are multiple answers,
* _ return any of them.
* Note: The elements in `words` are distinct.
*
* Example 1:
* Input: words = ["e","a","b"], groups = [0,0,1]
* Output: ["e","b"]
* Explanation: A subsequence that can be selected is ["e","b"] because groups[0] != groups[2]. Another subsequence that can be selected is ["a","b"] because groups[1] != groups[2]. It can be demonstrated that the length of the longest subsequence of indices that satisfies the condition is 2.
*
* Example 2:
* Input: words = ["a","b","c","d"], groups = [1,0,1,1]
* Output: ["a","b","c"]
* Explanation: A subsequence that can be selected is ["a","b","c"] because groups[0] != groups[1] and groups[1] != groups[2]. Another subsequence that can be selected is ["a","b","d"] because groups[0] != groups[1] and groups[1] != groups[3]. It can be shown that the length of the longest subsequence of indices that satisfies the condition is 3.
*
* Constraints:
* • 1 <= n == words.length == groups.length <= 100
* • 1 <= words[i].length <= 10
* • `groups[i]` is either `0` or `1`.
* • `words` consists of distinct strings.
* • `words[i]` consists of lowercase English letters.
* 
****************************************/

import java.util.ArrayList;
import java.util.List;

class Solution {
    // Traverse the input while building a subsequence of words
    // where adjacent elements have different group values (0 ↔ 1).
    // We greedily add a word when its group differs from the last
    // added group's value. This ensures the longest valid sequence.
    // Time: O(n), Space: O(n), where n is the length of the input array.
    public List<String> getLongestSubsequence(String[] words, int[] groups) {
        List<String> subseq = new ArrayList<>();
        int polarity = groups[0] == 0 ? 1 : 0;
        for (int i = 0; i < words.length; i++) {
            if (polarity != groups[i]) {
                subseq.add(words[i]);
                polarity = 1 - polarity;
            }
        }
        return subseq;
    }
}