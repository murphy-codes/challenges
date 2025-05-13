// Source: https://leetcode.com/problems/total-characters-in-string-after-transformations-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-12
// At the time of submission:
//   Runtime 11 ms Beats 100.00%
//   Memory 45.32 MB Beats 91.14%

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
    // Simulate t character transformations by tracking 26 character counts.
    // Each full 26-step cycle is processed in O(26) time via count shifting.
    // 'z' transforms into "ab", growing the total count and updating counts.
    // Avoids string mutation, using a fixed-size array for performance.
    // Time: O(t), Space: O(1), since array size is constant (26 letters).
    public int lengthAfterTransformations(String s, int t) {
        final int MOD = 1_000_000_007;
        long[] charCounts = new long[26]; // Tracks how many times each letter appears

        // Step 1: Count initial frequencies of characters
        for (char c : s.toCharArray()) {
            charCounts[c - 'a']++;
        }

        // Step 2: Apply full cycles of 26 transformations
        while (t >= 26) {
            long zCount = charCounts[25]; // Number of 'z' characters

            // Shift counts forward by one character
            for (int i = 25; i > 0; i--) {
                charCounts[i] = (charCounts[i] + charCounts[i - 1]) % MOD;
            }

            // Apply 'z' → "ab"
            charCounts[0] = (charCounts[0] + zCount) % MOD;
            charCounts[1] = (charCounts[1] + zCount) % MOD;

            t -= 26;
        }

        // Step 3: Calculate result length
        long totalLength = 0;

        // Base contribution: all characters count once
        for (int i = 0; i < 26; i++) {
            totalLength = (totalLength + charCounts[i]) % MOD;
        }

        // Additional contribution: characters in range [26 - t, 25] transform again
        for (int i = 26 - t; i < 26; i++) {
            totalLength = (totalLength + charCounts[i]) % MOD;
        }

        return (int) totalLength;
    }
}
