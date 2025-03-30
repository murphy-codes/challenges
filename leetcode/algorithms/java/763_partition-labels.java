// Source: https://leetcode.com/problems/partition-labels/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-03-30
// At the time of submission:
//   Runtime 5 ms Beats 79.50%
//   Memory 42.22 MB Beats 60.85%

/****************************************
* 
* You are given a string `s`. We want to partition the string into as many parts
* — as possible so that each letter appears in at most one part. For example,
* — the string `"ababcc"` can be partitioned into `["abab", "cc"]`,
* — but partitions such as `["aba", "bcc"]` or `["ab", "ab", "cc"]` are invalid.
* Note that the partition is done so that after concatenating
* — all the parts in order, the resultant string should be `s`.
* Return a list of integers representing the size of these parts.
*
* Example 1:
* Input: s = "ababcbacadefegdehijhklij"
* Output: [9,7,8]
* Explanation:
* The partition is "ababcbaca", "defegde", "hijhklij".
* This is a partition so that each letter appears in at most one part.
* A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits s into less parts.
*
* Example 2:
* Input: s = "eccbbbbdec"
* Output: [10]
*
* Constraints:
*
* • 1 <= s.length <= 500
* • `s` consists of lowercase English letters.
* 
****************************************/

import java.util.ArrayList;
import java.util.List;

class Solution {
    // This solution partitions the string using a two-pass greedy approach. 
    // The first pass records the last occurrence of each character in the string. 
    // The second pass expands partitions to ensure each character appears in 
    // at most one part. When reaching the last occurrence of a char, a partition 
    // is formed. The time complexity is O(n), and space complexity is O(1).
    public List<Integer> partitionLabels(String s) {
        // Array to store last occurrence of each character (26 lowercase letters)
        int[] lastIndex = new int[26];
        
        // First pass: Populate lastIndex for each character
        for (int i = 0; i < s.length(); i++) {
            lastIndex[s.charAt(i) - 'a'] = i;
        }
        
        List<Integer> result = new ArrayList<>();
        int start = 0, end = 0;
        
        // Second pass: Determine partitions
        for (int i = 0; i < s.length(); i++) {
            end = Math.max(end, lastIndex[s.charAt(i) - 'a']);
            
            if (i == end) { // Found a complete partition
                result.add(end - start + 1);
                start = i + 1;
            }
        }
        
        return result;
    }
}
