// Source: https://leetcode.com/problems/longest-substring-without-repeating-characters/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2024-12-27
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 43.15 MB Beats 96.77%

/****************************************
* 
* Given a string s, find the length of the longest substring without repeating characters.
* 
* Example 1:
* Input: s = "abcabcbb"
* Output: 3
* Explanation: The answer is "abc", with the length of 3.
* 
* Example 2:
* Input: s = "bbbbb"
* Output: 1
* Explanation: The answer is "b", with the length of 1.
* 
* Example 3:
* Input: s = "pwwkew"
* Output: 3
* Explanation: The answer is "wke", with the length of 3.
* Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
*  
* Constraints:
* • 0 <= s.length <= 5 * 10^4
* • s consists of English letters, digits, symbols and spaces.
* 
****************************************/

public class Solution {
    // Uses a sliding window to track the longest substring without repeats.
    // An array stores the last seen index of each ASCII character for O(1) lookup.
    // Time complexity is O(n), where n is the length of the string.
    // Space complexity is O(1), fixed to 128 ASCII characters.
    public int lengthOfLongestSubstring(String s) {
        int[] lastSeen = new int[128]; // ASCII character tracking
        for (int i = 0; i < 128; i++) lastSeen[i] = -1; // Initialize to -1

        int maxLength = 0; // Result variable
        int windowStart = 0; // Left boundary of the sliding window

        for (int windowEnd = 0; windowEnd < s.length(); windowEnd++) {
            char currentChar = s.charAt(windowEnd);

            // If character was seen inside the current window, move start
            if (lastSeen[currentChar] >= windowStart) {
                windowStart = lastSeen[currentChar] + 1;
            }

            // Update the last seen index of the current character
            lastSeen[currentChar] = windowEnd;

            // Update max length if current window is larger
            maxLength = Math.max(maxLength, windowEnd - windowStart + 1);
        }

        return maxLength;
    }
}
