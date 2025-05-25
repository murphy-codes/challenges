// Source: https://leetcode.com/problems/longest-palindrome-by-concatenating-two-letter-words/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-24
// At the time of submission:
//   Runtime 62 ms Beats 55.69%
//   Memory 58.20 MB Beats 57.11%

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

import java.util.HashMap;
import java.util.Map;

class Solution {
    // Count the frequency of each 2-letter word.
    // Pair non-palindromic words with their reverse to form mirrored sides.
    // Pair palindromic words (like "cc") in twos, and use one odd word in center.
    // Each valid pair contributes 4 to length; center adds 2 if available.
    // Time: O(n), Space: O(n), where n is the number of words.
    public int longestPalindrome(String[] words) {
        Map<String, Integer> freq = new HashMap<>();
        for (String word : words) {
            freq.put(word, freq.getOrDefault(word, 0) + 1);
        }

        int length = 0;
        boolean hasCenter = false;

        for (String word : freq.keySet()) {
            String reversed = new StringBuilder(word).reverse().toString();

            if (!word.equals(reversed)) {
                if (freq.containsKey(reversed)) {
                    int pairs = Math.min(freq.get(word), freq.get(reversed));
                    length += pairs * 4;
                    freq.put(word, freq.get(word) - pairs);
                    freq.put(reversed, freq.get(reversed) - pairs);
                }
            } else {
                int count = freq.get(word);
                length += (count / 2) * 4;
                if (count % 2 == 1) {
                    hasCenter = true;
                }
            }
        }

        if (hasCenter) {
            length += 2;
        }

        return length;
    }
}
