// Source: https://leetcode.com/problems/delete-characters-to-make-fancy-string/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-20
// At the time of submission:
//   Runtime 14 ms Beats 100.00%
//   Memory 45.98 MB Beats 25.25%

/****************************************
* 
* A fancy string is a string where no three consecutive characters are equal.
* Given a string `s`, delete the minimum possible number of characters from `s`
* _ to make it fancy.
* Return the final string after the deletion. It can be shown that the answer
* _ will always be unique.
*
* Example 1:
* Input: s = "leeetcode"
* Output: "leetcode"
* Explanation:
* Remove an 'e' from the first group of 'e's to create "leetcode".
* No three consecutive characters are equal, so return "leetcode".
*
* Example 2:
* Input: s = "aaabaaaa"
* Output: "aabaa"
* Explanation:
* Remove an 'a' from the first group of 'a's to create "aabaaaa".
* Remove two 'a's from the second group of 'a's to create "aabaa".
* No three consecutive characters are equal, so return "aabaa".
*
* Example 3:
* Input: s = "aab"
* Output: "aab"
* Explanation: No three consecutive characters are equal, so return "aab".
*
* Constraints:
* • 1 <= s.length <= 10^5
* • `s` consists only of lowercase English letters.
* 
****************************************/

class Solution {
    // Traverse the input string and track repeating characters.
    // Write only valid characters back into the input char array.
    // Skips adding a character if it would result in three in a row.
    // Time Complexity: O(n), where n is the length of the string.
    // Space Complexity: O(n) for the character array (reused, not extra).
    public String makeFancyString(String s) {
        char[] chars = s.toCharArray();               // Convert input to char array
        char lastChar = chars[0];                     // Track previous character
        int repeatCount = 1;                          // Count of consecutive chars
        int writeIndex = 1;                           // Write position for valid chars

        for (int readIndex = 1; readIndex < chars.length; readIndex++) {
            if (chars[readIndex] != lastChar) {
                lastChar = chars[readIndex];          // Reset if new char
                repeatCount = 0;
            }
            if (++repeatCount > 2) continue;          // Skip if more than 2 in a row
            chars[writeIndex++] = chars[readIndex];   // Write valid char
        }

        return new String(chars, 0, writeIndex);       // Return final fancy string
    }
}
