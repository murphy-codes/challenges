// Source: https://leetcode.com/problems/counting-words-with-a-given-prefix/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-08

/****************************************
* 
* You are given an array of strings `words` and a string `pref`.
* Return the number of strings in `words` that contain `pref` as a prefix.
* A prefix of a string `s` is any leading contiguous substring of `s`.
* 
* Example 1:
* Input: words = ["pay","attention","practice","attend"], pref = "at"
* Output: 2
* Explanation: The 2 strings that contain "at" as a prefix are: "attention" and "attend".
* 
* Example 2:
* Input: words = ["leetcode","win","loops","success"], pref = "code"
* Output: 0
* Explanation: There are no strings that contain "code" as a prefix.
* 
* Constraints:
* • 1 <= words.length <= 100
* • 1 <= words[i].length, pref.length <= 100
* • words[i] and pref consist of lowercase English letters.
* 
****************************************/

class Solution {
    // This problem is solved with a simple for-each loop in O(n * m) time and O(1) space.
    // We iterate through the words array and use the optimized startsWith method
    // to check if each word begins with the given prefix. Matching words are counted.
    public int prefixCount(String[] words, String pref) {
        int count = 0;
        for (String word : words) {
            if (word.startsWith(pref)) {
                count++;
            }
        }
        return count;
    }
}