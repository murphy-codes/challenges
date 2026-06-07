// Source: https://leetcode.com/problems/search-in-rotated-sorted-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-26
// At the time of submission:
//   Runtime 8 ms Beats 95.46%
//   Memory 48.04 MB Beats 69.87%

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

import java.util.Arrays;

class Solution {
    // Track the last lowercase and first uppercase index per letter.
    // A letter is special if all lowercase occurrences appear before
    // the first uppercase occurrence of the same character.
    // Time: O(n), Space: O(1)
    public int numberOfSpecialChars(String word) {
        int[] lastLowerIndex = new int[26]; // last index of lowercase
        int[] firstUpperIndex = new int[26]; // - first index of uppercase

        Arrays.fill(lastLowerIndex, -1);
        Arrays.fill(firstUpperIndex, -1);

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);

            // Update latest lowercase occurrence
            if (ch >= 'a' && ch <= 'z') {
                lastLowerIndex[ch - 'a'] = i;
            }
            // Store earliest uppercase occurrence
            else {
                int letterIndex = ch - 'A';

                if (firstUpperIndex[letterIndex] == -1) {
                    firstUpperIndex[letterIndex] = i;
                }
            }
        }

        int specialCount = 0;

        // Valid if lowercase exists before first uppercase
        for (int i = 0; i < 26; i++) {
            if (lastLowerIndex[i] != -1 &&
                firstUpperIndex[i] != -1 &&
                lastLowerIndex[i] < firstUpperIndex[i]) {

                specialCount++;
            }
        }

        return specialCount;
    }
}