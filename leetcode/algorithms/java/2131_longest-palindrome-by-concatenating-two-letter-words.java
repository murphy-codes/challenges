// Source: https://leetcode.com/problems/longest-palindrome-by-concatenating-two-letter-words/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-24
// At the time of submission:
//   Runtime 14 ms Beats 92.28%
//   Memory 60.50 MB Beats 20.12%

/****************************************
* 
* You are given an array of strings `words`. Each element of `words` consists of
* _ two lowercase English letters.
* Create the longest possible palindrome by selecting some elements from `words`
* _ and concatenating them in any order. Each element can be selected at most once.
* Return the length of the longest palindrome that you can create. If it is
* _ impossible to create any palindrome, return `0`.
* A palindrome is a string that reads the same forward and backward.
*
* Example 1:
* Input: words = ["lc","cl","gg"]
* Output: 6
* Explanation: One longest palindrome is "lc" + "gg" + "cl" = "lcggcl", of length 6.
* Note that "clgglc" is another longest palindrome that can be created.
*
* Example 2:
* Input: words = ["ab","ty","yt","lc","cl","ab"]
* Output: 8
* Explanation: One longest palindrome is "ty" + "lc" + "cl" + "yt" = "tylcclyt", of length 8.
* Note that "lcyttycl" is another longest palindrome that can be created.
*
* Example 3:
* Input: words = ["cc","ll","xx"]
* Output: 2
* Explanation: One longest palindrome is "cc", of length 2.
* Note that "ll" is another longest palindrome that can be created, and so is "xx".
*
* Constraints:
* • 1 <= words.length <= 10^5
* • words[i].length == 2
* • `words[i]` consists of lowercase English letters
* 
****************************************/

class Solution {
    // Count all 2-letter word combinations using a 26x26 array.
    // For each word, check if its reverse was seen; if so, form a 4-letter pair.
    // Otherwise, record the word for potential future pairing.
    // After all pairs, add 2 if any symmetric word like "aa" exists for center.
    // Time: O(n), Space: O(1), since 26x26 array is constant-sized.
    public int longestPalindrome(String[] words) {
        int[][] count = new int[26][26]; // Count of 2-letter combinations
        int length = 0;
        boolean hasCenter = false;

        for (String word : words) {
            int i = word.charAt(0) - 'a';
            int j = word.charAt(1) - 'a';

            if (count[j][i] > 0) {
                // Found a reverse match (e.g., "ab" after "ba")
                length += 4;
                count[j][i]--;
            } else {
                count[i][j]++;
            }
        }

        // Check for a center (word like "gg")
        for (int i = 0; i < 26; i++) {
            if (count[i][i] > 0) {
                length += 2;
                break; // Only one center allowed
            }
        }

        return length;
    }
}
