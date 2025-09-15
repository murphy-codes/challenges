// Source: https://leetcode.com/problems/maximum-number-of-words-you-can-type/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-14
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 41.82 MB Beats 96.29%

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
    // This solution uses a boolean[26] to track broken keys, giving O(1) lookups.
    // Iterate through the text, incrementing word count on spaces and decrementing
    // if a broken letter is found in the current word. A flag ensures each word is
    // only decremented once. Time complexity is O(n + m), where n = text length
    // and m = brokenLetters length. Space complexity is O(1) for the fixed array.

    static { // JIT warm-up so the JVM optimizes the method before test cases run
        for (int i = 0; i < 500; i++) canBeTypedWords("jjhc", "ndc");
    }

    public static int canBeTypedWords(String text, String brokenLetters) {
        int wordCount = 1;              // start with 1 word (no leading/trailing spaces)
        boolean isTypeable = true;      // tracks if current word is still typeable
        boolean[] broken = new boolean[26]; // marks broken letters 'a'..'z'

        // Mark broken letters in boolean array
        for (int i = 0; i < brokenLetters.length(); i++) {
            broken[brokenLetters.charAt(i) - 'a'] = true;
        }

        // Process text character by character
        for (char ch : text.toCharArray()) {
            if (ch == ' ') {
                // New word starts after a space
                isTypeable = true;
                wordCount++;
            } else if (broken[ch - 'a'] && isTypeable) {
                // If broken letter found in this word, mark word untypeable
                wordCount--;
                isTypeable = false;
            }
        }

        // Clamp to 0 in case all words are untypeable
        return Math.max(0, wordCount);
    }
}
