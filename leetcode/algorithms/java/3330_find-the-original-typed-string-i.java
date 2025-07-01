// Source: https://leetcode.com/problems/find-the-original-typed-string-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-30
// At the time of submission:
//   Runtime 1 ms Beats 87.19%
//   Memory 42.16 MB Beats 61.16%

/****************************************
* 
* Alice is attempting to type a specific string on her computer. However, she
* _ tends to be clumsy and may press a key for too long, resulting in a character
* _ being typed multiple times.
* Although Alice tried to focus on her typing, she is aware that she may still
* _ have done this at most once.
* You are given a string word, which represents the final output displayed on
* _ Alice's screen.
* Return the total number of possible original strings that Alice might have
* _ intended to type.
*
* Example 1:
* Input: word = "abbcccc"
* Output: 5
* Explanation:
* The possible strings are: "abbcccc", "abbccc", "abbcc", "abbc", and "abcccc".
*
* Example 2:
* Input: word = "abcd"
* Output: 1
* Explanation:
* The only possible string is "abcd".
*
* Example 3:
* Input: word = "aaaa"
* Output: 4
*
* Constraints:
* • 1 <= word.length <= 100
* • `word` consists only of lowercase English letters.
* 
****************************************/

class Solution {
    // Iterate through the string and count how many consecutive characters
    // match their previous character. Each such run could be the result of
    // Alice holding a key too long. Since she can do this at most once,
    // each run gives one option to shorten the word. Add 1 for the original.
    // Time: O(n), where n is the word length. Space: O(1) extra.
    public int possibleStringCount(String word) {
        int possible = 1;
        char[] chars = word.toCharArray();
        for(int i=1;i<chars.length;i++) {
            if (chars[i]==chars[i-1]) { possible++; }
        }
        return possible;
    }
}