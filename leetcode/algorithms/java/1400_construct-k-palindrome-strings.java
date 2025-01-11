// Source: https://leetcode.com/problems/construct-k-palindrome-strings/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-11

/****************************************
* 
* Given a string `s` and an integer `k`, return `true` if you can use all the characters in `s` to construct `k` palindrome strings or `false` otherwise.
* 
* Example 1:
* Input: s = "annabelle", k = 2
* Output: true
* Explanation: You can construct two palindromes using all characters in s.
* Some possible constructions "anna" + "elble", "anbna" + "elle", "anellena" + "b"
* 
* Example 2:
* Input: s = "leetcode", k = 3
* Output: false
* Explanation: It is impossible to construct 3 palindromes using all the characters of s.
* 
* Example 3:
* Input: s = "true", k = 4
* Output: true
* Explanation: The only possible solution is to put each character in a separate string.
* 
* Constraints:
* • 1 <= s.length <= 10^5
* • `s` consists of lowercase English letters.
* • 1 <= k <= 10^5
* 
****************************************/

class Solution {
    // Check for edge case, if k <= s.length, 
    // then make a frequency map (array) of char counts & count odd frequencies
    // If the number of odd frequencies is greater than k, return false
    // Otherwise, check if k <= s.length, and return true
    // Time: O(n) & Memory: O(1)
    public boolean canConstruct(String s, int k) {
        // Edge case: If k > s.length(), we can't create k palindromes.
        if (k > s.length()) return false;

        // Count character frequencies.
        int[] charCount = new int[26];
        for (char c : s.toCharArray()) {
            charCount[c - 'a']++;
        }

        // Count characters with odd frequencies.
        int oddCount = 0;
        for (int count : charCount) {
            if (count % 2 != 0) {
                oddCount++;
            }
        }

        // Check if we can construct k palindromes.
        return k >= oddCount;
    }
}
