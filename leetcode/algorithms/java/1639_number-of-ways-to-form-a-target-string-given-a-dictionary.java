// Source: https://leetcode.com/problems/number-of-ways-to-form-a-target-string-given-a-dictionary/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-30
// At the time of submission:
//   Runtime 31 ms Beats 81.63%
//   Memory 51.06 MB Beats 86.73%

/****************************************
* 
* You are given a list of strings of the same length `words` and a string `target`.
* Your task is to form `target` using the given `words` under the following rules:
* • `target` should be formed from left to right.
* • To form the `i^th` character (0-indexed) of `target`, you can choose the
* __ `k^th` character of the `j^th` string in `words` if `target[i] = words[j][k]`.
* • Once you use the `k^th` character of the `j^th` string of `words`, you can
* __ no longer use the `x^th` character of any string in `words` where `x <= k`.
* __ In other words, all characters to the left of or at index `k` become
* __ unusable for every string.
* • Repeat the process until you form the string `target`.
* Notice that you can use multiple characters from the same string in `words`
* _ provided the conditions above are met.
* Return the number of ways to form `target` from `words`. Since the answer
* _ may be too large, return it modulo `10^9 + 7`.
*
* Example 1:
* Input: words = ["acca","bbbb","caca"], target = "aba"
* Output: 6
* Explanation: There are 6 ways to form target.
* aba -> index 0 ("acca"), index 1 ("bbbb"), index 3 ("caca")
* aba -> index 0 ("acca"), index 2 ("bbbb"), index 3 ("caca")
* aba -> index 0 ("acca"), index 1 ("bbbb"), index 3 ("acca")
* aba -> index 0 ("acca"), index 2 ("bbbb"), index 3 ("acca")
* aba -> index 1 ("caca"), index 2 ("bbbb"), index 3 ("acca")
* aba -> index 1 ("caca"), index 2 ("bbbb"), index 3 ("caca")
*
* Example 2:
* Input: words = ["abba","baab"], target = "bab"
* Output: 4
* Explanation: There are 4 ways to form target.
* bab -> index 0 ("baab"), index 1 ("baab"), index 2 ("abba")
* bab -> index 0 ("baab"), index 1 ("baab"), index 3 ("baab")
* bab -> index 0 ("baab"), index 2 ("baab"), index 3 ("baab")
* bab -> index 1 ("abba"), index 2 ("baab"), index 3 ("baab")
*
* Constraints:
* • `1 <= words.length <= 1000`
* • `1 <= words[i].length <= 1000`
* All strings in `words` have the same length.
* • `1 <= target.length <= 1000`
* • `words[i]` and `target` contain only lowercase English letters.
* 
****************************************/

class Solution {
    // We precompute character frequencies for each column across all words.
    // dp[i] represents the number of ways to form the first i characters of target.
    // For each column, we update dp backwards to ensure columns are used only once.
    // Each transition multiplies existing ways by the column's character frequency.
    // Time complexity: O(wordLen * targetLen). Space complexity: O(targetLen).
    private static final int MOD = 1_000_000_007;

    public int numWays(String[] words, String target) {
        int wordLen = words[0].length();
        int targetLen = target.length();

        // freq[col][char] = count of char at column col across all words
        int[][] freq = new int[wordLen][26];
        for (String w : words) {
            for (int i = 0; i < wordLen; i++) {
                freq[i][w.charAt(i) - 'a']++;
            }
        }

        long[] dp = new long[targetLen + 1];
        dp[0] = 1L;

        for (int col = 0; col < wordLen; col++) {
            // iterate backwards to prevent reusing the same column
            for (int i = targetLen - 1; i >= 0; i--) {
                int c = target.charAt(i) - 'a';
                if (freq[col][c] > 0) {
                    dp[i + 1] = (dp[i + 1] + dp[i] * freq[col][c]) % MOD;
                }
            }
        }

        return (int) dp[targetLen];
    }
}
