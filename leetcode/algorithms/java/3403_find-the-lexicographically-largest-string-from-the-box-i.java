// Source: https://leetcode.com/problems/find-the-lexicographically-largest-string-from-the-box-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-03
// At the time of submission:
//   Runtime 2 ms Beats 92.59%
//   Memory 43.44 MB Beats 99.07%

/****************************************
* 
* You are given a string `word`, and an integer `numFriends`.
* Alice is organizing a game for her `numFriends` friends. There are multiple
* _ rounds in the game, where in each round:
* • `word` is split into `numFriends` non-empty strings, such that no previous
* __ round has had the exact same split.
* • All the split words are put into a box.
* Find the lexicographically largest string from the box after
* _ all the rounds are finished.
*
* Example 1:
* Input: word = "dbca", numFriends = 2
* Output: "dbc"
* Explanation:
* All possible splits are:
* d and "bca".
* db and "ca".
* dbc and "a".
*
* Example 2:
* Input: word = "gggg", numFriends = 4
* Output: "g"
* Explanation:
* The only possible split is: "g", "g", "g", and "g".
*
* Constraints:
* • 1 <= word.length <= 5 * 10^3
* • `word` consists only of lowercase English letters.
* • 1 <= numFriends <= word.length
* 
****************************************/

class Solution {
    // This solution finds the lexicographically largest suffix in the string
    // using a two-pointer linear scan (Duval's algorithm inspired). It then
    // returns the prefix of that suffix limited by (word.length - numFriends + 1).
    // Time Complexity: O(n), where n is the length of the string.
    // Space Complexity: O(1) extra space (excluding string manip').
    public String answerString(String word, int numFriends) {
        if (numFriends == 1) {
            return word;
        }

        String last = lastSubstring(word);
        int n = word.length();
        int maxLen = n - numFriends + 1;
        return last.substring(0, Math.min(last.length(), maxLen));
    }

    private String lastSubstring(String s) {
        int i = 0, j = 1, n = s.length();
        while (j < n) {
            int k = 0;
            // Compare characters at position i+k and j+k
            while (j + k < n && s.charAt(i + k) == s.charAt(j + k)) {
                k++;
            }
            if (j + k < n && s.charAt(i + k) < s.charAt(j + k)) {
                int oldI = i;
                i = j;
                j = Math.max(j + 1, oldI + k + 1); // ensure no overlap
            } else {
                j += k + 1;
            }
        }
        return s.substring(i);
    }
}