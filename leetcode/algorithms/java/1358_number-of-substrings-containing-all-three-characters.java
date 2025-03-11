// Source: https://leetcode.com/problems/number-of-substrings-containing-all-three-characters/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-03-10

/****************************************
* 
* Given a string `s` consisting only of characters a, b and c.
* Return the number of substrings containing at least one occurrence of all these characters a, b and c.
*
* Example 1:
* Input: s = "abcabc"
* Output: 10
* Explanation: The substrings containing at least one occurrence of the characters a, b and c are "abc", "abca", "abcab", "abcabc", "bca", "bcab", "bcabc", "cab", "cabc" and "abc" (again).
*
* Example 2:
* Input: s = "aaacb"
* Output: 3
* Explanation: The substrings containing at least one occurrence of the characters a, b and c are "aaacb", "aacb" and "acb".
*
* Example 3:
* Input: s = "abc"
* Output: 1
*
* Constraints:
* • 3 <= s.length <= 5 x 10^4
* • `s` only consists of a, b or c characters.
* 
****************************************/

import java.util.HashMap;

class Solution {
    // This solution uses a sliding window approach to count substrings with exactly
    // k consonants and all 5 vowels. The window expands by adding characters, and
    // shrinks when the number of consonants exceeds k. We count valid substrings when
    // the window contains all 5 vowels and the consonant count is valid. The time
    // complexity is O(N) due to the linear scan of the string, and space complexity
    // is O(1) since we only use fixed-size data structures (int[3] and 2x int).
    public int numberOfSubstrings(String s) {
        int[] count = new int[3]; // Frequency of 'a', 'b', 'c'
        int left = 0, result = 0;

        for (int right = 0; right < s.length(); right++) {
            count[s.charAt(right) - 'a']++;

            // If the window is valid (contains 'a', 'b', 'c'), move left
            while (count[0] > 0 && count[1] > 0 && count[2] > 0) {
                result += s.length() - right; // Every substring ending at 'right' is valid
                count[s.charAt(left) - 'a']--; // Shrink window
                left++;
            }
        }
        
        return result;
    }
}
