// Source: https://leetcode.com/problems/total-characters-in-string-after-transformations-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-12
// At the time of submission:
//   Runtime 123 ms Beats 56.96%
//   Memory 62.70 MB Beats 15.19%

/****************************************
* 
* You are given a string `s` and an integer `t`, representing the number of
* _ transformations to perform. In one transformation, every character in `s` is
* _ replaced according to the following rules:
* • If the character is `'z'`, replace it with the string `"ab"`.
* • Otherwise, replace it with the next character in the alphabet. For example,
* _ `'a'` is replaced with `'b'`, `'b'` is replaced with `'c'`, and so on.
* Return the length of the resulting string after exactly `t` transformations.
* Since the answer may be very large, return it modulo `10^9 + 7`.
*
* Example 1:
* Input: s = "abcyy", t = 2
* Output: 7
* Explanation:
* First Transformation (t = 1):
* a' becomes 'b'
* b' becomes 'c'
* c' becomes 'd'
* y' becomes 'z'
* y' becomes 'z'
* String after the first transformation: "bcdzz"
* Second Transformation (t = 2):
* b' becomes 'c'
* c' becomes 'd'
* d' becomes 'e'
* z' becomes "ab"
* z' becomes "ab"
* String after the second transformation: "cdeabab"
* Final Length of the string: The string is "cdeabab", which has 7 characters.
*
* Example 2:
* Input: s = "azbk", t = 1
* Output: 5
* Explanation:
* First Transformation (t = 1):
* a' becomes 'b'
* z' becomes "ab"
* b' becomes 'c'
* k' becomes 'l'
* String after the first transformation: "babcl"
* Final Length of the string: The string is "babcl", which has 5 characters.
*
* Constraints:
* • 1 <= s.length <= 10^5
* • s consists only of lowercase English letters.
* • 1 <= t <= 10^5
* 
****************************************/

class Solution {
    // Use dynamic programming to track the length of each character
    // after t transformations, avoiding repeated computation.
    // 'z' expands to "ab" → adds 2 characters, others move forward
    // Precompute dp[char][t] for all 26 letters and sum the result
    // Total time: O(n + 26*t), space: O(26*t)
    
    private static final int MOD = 1_000_000_007;

    public int lengthAfterTransformations(String s, int t) {
        // dp[i][j] = number of characters after j transformations on char (char)('a' + i)
        int[][] dp = new int[26][t + 1];

        // Base case: 0 transformations → length is 1
        for (int i = 0; i < 26; i++) {
            dp[i][0] = 1;
        }

        // Fill dp table
        for (int j = 1; j <= t; j++) {
            for (int i = 0; i < 26; i++) {
                if (i == 25) { // 'z'
                    dp[i][j] = (dp[0][j - 1] + dp[1][j - 1]) % MOD; // "ab"
                } else {
                    dp[i][j] = dp[i + 1][j - 1]; // next character
                }
            }
        }

        // Sum up transformed lengths for each character in the string
        int result = 0;
        for (char c : s.toCharArray()) {
            result = (result + dp[c - 'a'][t]) % MOD;
        }

        return result;
    }
}
