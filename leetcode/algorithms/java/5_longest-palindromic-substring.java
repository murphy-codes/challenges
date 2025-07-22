// Source: https://leetcode.com/problems/longest-palindromic-substring/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2024-12-30
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 42.39 MB Beats 70.58%

/****************************************
* 
* Given a string `s`, return the longest palindromic substring in `s`.
* 
* Example 1:
* Input: s = "babad"
* Output: "bab"
* Explanation: "aba" is also a valid answer.
* 
* Example 2:
* Input: s = "cbbd"
* Output: "bb"
* 
* Constraints:
* • 1 <= s.length <= 1000
* • `s` consist of only digits and English letters.
* 
****************************************/

public class Solution {
    // Recursively expands around duplicate characters and their neighbors
    // to find the longest palindromic substring. Handles repeated centers
    // in one pass (e.g., "aaaa") to avoid redundant checks. Expands outward
    // from the extended center, updating max bounds when a longer palindrome
    // is found. Time complexity is O(n^2), space is O(n) due to recursion.

    private int maxStart = 0;
    private int maxEnd = 0;

    public String longestPalindrome(String s) {
        expandAroundCenters(s.toCharArray(), 0);
        return s.substring(maxStart, maxEnd + 1);
    }

    private void expandAroundCenters(char[] chars, int currentIndex) {
        int length = chars.length;
        if (currentIndex >= length) return;

        int left = currentIndex;
        int right = currentIndex;

        // Extend right to cover all identical adjacent characters (e.g., "aaaa")
        while (right + 1 < length && chars[right] == chars[right + 1]) {
            right++;
        }

        // Next index to check after handling duplicates
        int nextIndex = right + 1;

        // Expand outward as long as characters match
        while (left > 0 && right < length - 1 && chars[left - 1] == chars[right + 1]) {
            left--;
            right++;
        }

        // Update max palindrome range if this one is longer
        if (right - left > maxEnd - maxStart) {
            maxStart = left;
            maxEnd = right;
        }

        // Recurse from next index
        expandAroundCenters(chars, nextIndex);
    }
}
