// Source: https://leetcode.com/problems/delete-characters-to-make-fancy-string/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-20
// At the time of submission:
//   Runtime 24 ms Beats 98.23%
//   Memory 46.21 MB Beats 7.07%

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
    // Traverse the string while tracking the last two characters added.
    // Only add the current character if it doesn't create three repeats.
    // Time Complexity: O(n), where n is the length of the string.
    // Space Complexity: O(n) for the StringBuilder used to build the result.
    // Ensures no three consecutive characters in the result string.
    public String makeFancyString(String s) {
        if (s.length() < 3)
            return s;
        char first = '\0';
        char second = '\0';
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c != first) {
                sb.append(c);
                first = c;
                second = '\0';
            } else {
                if (c != second) {
                sb.append(c);
                second = c;
                }
            }
        }
        return sb.toString();
    }
}