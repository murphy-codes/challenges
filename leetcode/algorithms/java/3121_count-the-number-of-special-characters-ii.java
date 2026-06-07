// Source: https://leetcode.com/problems/search-in-rotated-sorted-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-26
// At the time of submission:
//   Runtime 21 ms Beats 58.52%
//   Memory 48.47 MB Beats 13.01%

/****************************************
* 
* You are given a string `word`. A letter `c` is called special if it appears
* _ both in lowercase and uppercase in `word`, and every lowercase occurrence
* _ of `c` appears before the first uppercase occurrence of `c`.
* Return the number of special letters in `word`.
*
* Example 1:
* Input: word = "aaAbcBC"
* Output: 3
* Explanation:
* The special characters in word are 'a', 'b', and 'c'.
*
* Example 2:
* Input: word = "abc"
* Output: 0
* Explanation:
* There are no special characters in word.
*
* Example 3:
* Input: word = "abBCab"
* Output: 0
* Explanation:
* There are no special characters in word.
*
* Constraints:
* • `1 <= word.length <= 10^5`
* • `word` consists of only lowercase and uppercase English letters.
* 
****************************************/

class Solution {
    // Store the last lowercase and first uppercase index per letter.
    // A letter is special if all lowercase occurrences appear before
    // the first uppercase occurrence for the same character.
    // Time: O(n), Space: O(1)
    public int numberOfSpecialChars(String word) {
        int[] lastLower = new int[26];
        int[] firstUpper = new int[26];

        for (int i = 0; i < 26; i++) {
            lastLower[i] = -1;
            firstUpper[i] = -1;
        }

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            if (Character.isLowerCase(c)) {
                lastLower[c - 'a'] = i;
            } else {
                int idx = c - 'A';

                if (firstUpper[idx] == -1) {
                    firstUpper[idx] = i;
                }
            }
        }

        int specialCount = 0;

        for (int i = 0; i < 26; i++) {
            if (lastLower[i] != -1 &&
                firstUpper[i] != -1 &&
                lastLower[i] < firstUpper[i]) {

                specialCount++;
            }
        }

        return specialCount;
    }
}