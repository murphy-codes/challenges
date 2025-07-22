// Source: https://leetcode.com/problems/longest-palindromic-substring/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2024-12-30
// At the time of submission:
//   Runtime 15 ms Beats 84.83%
//   Memory 42.80 MB Beats 54.91%

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

class Solution {
    // Approach: Expand Around Center
    // Treat each character and each gap between characters as a center.
    // Expand outward from each center while maintaining the palindrome property.
    // Track the longest palindrome found during the process.
    // Time Complexity: O(n²), Space Complexity: O(1)
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";

        int start = 0, end = 0;

        for (int i = 0; i < s.length(); i++) {
            // Expand for odd-length palindromes (single character center)
            int len1 = expandAroundCenter(s, i, i);
            // Expand for even-length palindromes (two character center)
            int len2 = expandAroundCenter(s, i, i + 1);
            // Find the maximum length
            int len = Math.max(len1, len2);

            // Update start and end indices if a longer palindrome is found
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }

        // Return the longest palindromic substring
        return s.substring(start, end + 1);
    }

    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1; // Length of the palindrome
    }
}
