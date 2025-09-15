// Source: https://leetcode.com/problems/maximum-number-of-words-you-can-type/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-14
// At the time of submission:
//   Runtime 2 ms Beats 91.65%
//   Memory 41.42 MB Beats 100.00%

/****************************************
* 
* There is a malfunctioning keyboard where some letter keys do not work.
* _ All other keys on the keyboard work properly.
* Given a string `text` of words separated by a single space (no leading or
* _ trailing spaces) and a string `brokenLetters` of all distinct letter keys
* _ that are broken, return the number of words in `text` you can fully type
* _ using this keyboard.
*
* Example 1:
* Input: text = "hello world", brokenLetters = "ad"
* Output: 1
* Explanation: We cannot type "world" because the 'd' key is broken.
*
* Example 2:
* Input: text = "leet code", brokenLetters = "lt"
* Output: 1
* Explanation: We cannot type "leet" because the 'l' and 't' keys are broken.
*
* Example 3:
* Input: text = "leet code", brokenLetters = "e"
* Output: 0
* Explanation: We cannot type either word because the 'e' key is broken.
*
* Constraints:
* • 1 <= text.length <= 10^4
* • 0 <= brokenLetters.length <= 26
* • `text` consists of words separated by a single space without any leading or trailing spaces.
* • Each word only consists of lowercase English letters.
* • `brokenLetters` consists of distinct lowercase English letters.
* 
****************************************/

class Solution {
    // This solution checks each word in the text sequentially while tracking
    // whether it can be typed. A HashSet stores the broken letters for O(1)
    // membership checks. As we iterate, we mark a word untypeable if any broken
    // letter appears. After each word boundary (space or end of string), we add
    // to the count if the word was typeable. Time complexity is O(n + m), where
    // n = text.length and m = brokenLetters.length. Space complexity is O(m).
    public int canBeTypedWords(String text, String brokenLetters) {
        if (brokenLetters.length() == 26) return 0;
        int typeableWords = 0;
        boolean isTypeable = true;
        Set<Character> broken = new HashSet<>();
        for (char c : brokenLetters.toCharArray()) {
            broken.add(c);
        }
        for (char c : text.toCharArray()) {
            if (c == ' ') {
                typeableWords = typeableWords + (isTypeable ? 1 : 0);
                isTypeable = true;
            } else if (isTypeable) {
                isTypeable = !(broken.contains(c));
            }
        }
        typeableWords = typeableWords + (isTypeable ? 1 : 0);
        return typeableWords;
    }
}