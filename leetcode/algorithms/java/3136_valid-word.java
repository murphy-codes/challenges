// Source: https://leetcode.com/problems/valid-word/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-14
// At the time of submission:
//   Runtime 1 ms Beats 99.12%
//   Memory 42.23 MB Beats 34.66%

/****************************************
* 
* A word is considered valid if:
* • It contains a minimum of 3 characters.
* • It contains only digits (0-9), and English letters (uppercase and lowercase).
* • It includes at least one vowel.
* • It includes at least one consonant.
* You are given a string `word`.
* Return `true` if `word` is valid, otherwise, return `false`.
* Notes:
* • `'a'`, `'e'`, `'i'`, `'o'`, `'u'`, and their uppercases are vowels.
* • A consonant is an English letter that is not a vowel.
*
* Example 1:
* Input: word = "234Adas"
* Output: true
* Explanation:
* This word satisfies the conditions.
*
* Example 2:
* Input: word = "b3"
* Output: false
* Explanation:
* The length of this word is fewer than 3, and does not have a vowel.
*
* Example 3:
* Input: word = "a3$e"
* Output: false
* Explanation:
* This word contains a `'$'` character and does not have a consonant.
*
* Constraints:
* • 1 <= word.length <= 20
* • `word` consists of English uppercase and lowercase letters, digits, `'@'`, `'#'`, and `'$'`.
* 
****************************************/

import java.util.Set;

class Solution {
    // Iterate through the word and reject it if it contains any invalid chars.
    // Count vowels and digits; check that there's at least one vowel and one
    // consonant (letter that is not a vowel). Return true if valid.
    // Time: O(n), Space: O(1) — where n is word.length() (max 20).
    private static final Set<Character> vowels = Set.of('A', 'E', 'I', 'O', 'U', 'a', 'e', 'i', 'o', 'u');
    public boolean isValid(String word) {
        if (word.length() < 3) return false;
        int vowelCount = 0, numCount = 0;
        for (char ch : word.toCharArray()) {
            if (ch == '@' || ch == '#' || ch == '$') { return false; }
            else {
                if (vowels.contains(ch)) vowelCount++;
                else if (ch - '0' >= 0 && ch - '0' < 10) numCount++;
            }
        }
        if (vowelCount > 0 && word.length() > vowelCount + numCount) return true;
        else return false;
    }
}