// Source: https://leetcode.com/problems/minimum-ascii-delete-sum-for-two-strings/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-09
// At the time of submission:
//   Runtime 8 ms Beats 99.85%
//   Memory 46.45 MB Beats 58.72%

/****************************************
* 
* Given two strings `s1` and `s2`, return the lowest ASCII sum of deleted
* _ characters to make two strings equal.
*
* Example 1:
* Input: s1 = "sea", s2 = "eat"
* Output: 231
* Explanation: Deleting "s" from "sea" adds the ASCII value of "s" (115) to the sum.
* Deleting "t" from "eat" adds 116 to the sum.
* At the end, both strings are equal, and 115 + 116 = 231 is the minimum sum possible to achieve this.
*
* Example 2:
* Input: s1 = "delete", s2 = "leet"
* Output: 403
* Explanation: Deleting "dee" from "delete" to turn the string into "let",
* adds 100[d] + 101[e] + 101[e] to the sum.
* Deleting "e" from "leet" adds 101[e] to the sum.
* At the end, both strings are equal to "let", and the answer is 100+101+101+101 = 403.
* If instead we turned both strings into "lee" or "eet", we would get answers of 433 or 417, which are higher.
*
* Constraints:
* • `1 <= s1.length, s2.length <= 1000`
* • `s1` and `s2` consist of lowercase English letters.
* 
****************************************/

class Solution {
    // This solution reframes the problem as a weighted LCS. It computes the maximum
    // ASCII sum of a common subsequence between the two strings using DP. The total
    // ASCII sum of both strings is calculated upfront, and twice the kept sum is
    // subtracted to obtain the minimum delete cost. Time O(n*m), space O(n*m).
    public int minimumDeleteSum(String s1, String s2) {
        char[] s1Chars = s1.toCharArray();
        char[] s2Chars = s2.toCharArray();
        int n = s1Chars.length;
        int m = s2Chars.length;

        // dp[i][j] = max ASCII sum of common subsequence of s1[i:] and s2[j:]
        int[][] dp = new int[n + 1][m + 1];

        // Total ASCII sum of all characters in both strings
        int totalAsciiSum = 0;
        for (char c : s1Chars) totalAsciiSum += c;
        for (char c : s2Chars) totalAsciiSum += c;

        // Bottom-up DP for weighted LCS
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                if (s1Chars[i] == s2Chars[j]) {
                    dp[i][j] = dp[i + 1][j + 1] + s1Chars[i];
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }

        // Subtract twice the kept ASCII sum from total
        return totalAsciiSum - 2 * dp[0][0];
    }
}