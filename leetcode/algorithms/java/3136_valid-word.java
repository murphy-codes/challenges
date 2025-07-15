// Source: https://leetcode.com/problems/valid-word/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-14
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 41.87 MB Beats 90.73%

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

class Solution {
    // Traverse each character of the word using a switch-case to identify
    // vowels, consonants, digits, or invalid characters. Mark presence of
    // at least one vowel and one consonant. Return false if an invalid char
    // is found, or if either vowel or consonant is missing.
    // Time: O(n), Space: O(1), where n is word.length() (max 20).
    public boolean isValid(String word) {
        if (word.length() < 3) {
            return false;
        }

        boolean hasVowel = false;
        boolean hasConsonant = false;

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            switch (ch) {
                // Vowels (both lowercase and uppercase)
                case 'a', 'e', 'i', 'o', 'u', 
                     'A', 'E', 'I', 'O', 'U':
                    hasVowel = true;
                    break;
                // Consonants (all other letters)
                case 'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 
                     'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z',
                     'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 
                     'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z':
                    hasConsonant = true;
                    break;
                // Digits (allowed, but not counted)
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9':
                    break;
                // Invalid characters
                default:
                    return false;
            }
        }

        return hasVowel && hasConsonant;
    }
}
