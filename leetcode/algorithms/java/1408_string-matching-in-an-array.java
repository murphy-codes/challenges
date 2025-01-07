// Source: https://leetcode.com/problems/string-matching-in-an-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-06

/****************************************
* 
* Given an array of string words, return all strings in words that is a substring of another word. You can return the answer in any order.
* 
* A substring is a contiguous sequence of characters within a string
* 
* Example 1:
* Input: words = ["mass","as","hero","superhero"]
* Output: ["as","hero"]
* Explanation: "as" is substring of "mass" and "hero" is substring of "superhero".
* ["hero","as"] is also a valid answer.
* 
* Example 2:
* Input: words = ["leetcode","et","code"]
* Output: ["et","code"]
* Explanation: "et", "code" are substring of "leetcode".
* 
* Example 3:
* Input: words = ["blue","green","bu"]
* Output: []
* Explanation: No string of words is substring of another string.
* 
* Constraints:
* • 1 <= words.length <= 100
* • 1 <= words[i].length <= 30
* • words[i] contains only lowercase English letters.
* • All the strings of words are unique.
* 
****************************************/

import java.util.*;

class Solution {
    // We first sort the array by length to ensure shorter strings are checked first
    // We then used a nested loop to compare each word w/ all longer words
    //    - Outer loop picks the candidate substring (words[i]).
    //    - Inner loop checks if words[i] is a substring of any words[j] (j > i).
    // We add words[i] to result list if a match is found & break early to avoid redundant checks.
    public List<String> stringMatching(String[] words) {
        // Sort words by length
        Arrays.sort(words, (a, b) -> Integer.compare(a.length(), b.length()));

        List<String> result = new ArrayList<>();
        
        // Nested loop to check for substrings
        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if (words[j].contains(words[i])) {
                    result.add(words[i]);
                    break; // No need to check further once a match is found
                }
            }
        }
        
        return result;
    }
}
