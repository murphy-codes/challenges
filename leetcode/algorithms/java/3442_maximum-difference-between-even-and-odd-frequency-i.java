// Source: https://leetcode.com/problems/maximum-difference-between-even-and-odd-frequency-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-09
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 42.32 MB Beats 81.01%

/****************************************
* 
* You are given a string `s` consisting of lowercase English letters.
* Your task is to find the maximum difference `diff = a_1 - a_2` between the
* _ frequency of characters `a_1` and `a_2` in the string such that:
* • `a_1` has an odd frequency in the string.
* • `a_2` has an even frequency in the string.
* Return this maximum difference.
*
* Example 1:
* Input: s = "aaaaabbc"
* Output: 3
* Explanation:
* The character 'a' has an odd frequency of 5, and 'b' has an even frequency of 2.
* The maximum difference is 5 - 2 = 3.
*
* Example 2:
* Input: s = "abcabcab"
* Output: 1
* Explanation:
* The character 'a' has an odd frequency of 3, and 'c' has an even frequency of 2.
* The maximum difference is 3 - 2 = 1.
*
* Constraints:
* • 3 <= s.length <= 100
* • `s` consists only of lowercase English letters.
* • `s` contains at least one character with an odd frequency and one with an even frequency.
* 
****************************************/

class Solution {
    // Count the frequency of each character using a fixed-size array.
    // Then, find the largest odd frequency and the smallest non-zero even frequency.
    // Return the difference between them: max(odd) - min(even).
    // Time: O(n) for counting frequencies. Space: O(1) (fixed array size).
    public int maxDifference(String s) {
        int[] charCounts = new int[26];
        for (char c : s.toCharArray()) {
            charCounts[c - 'a']++;
        }
        int odd=0;
        int even=Integer.MAX_VALUE;
        for (int freq : charCounts) {
            if (freq % 2 == 0 && freq != 0) {
                if (freq < even) {
                    even = freq;
                }
            } else {
                if (freq > odd) {
                    odd = freq;
                }
            }
        }
        return odd - even;
    }
}