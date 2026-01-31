// Source: https://leetcode.com/problems/number-of-ways-to-form-a-target-string-given-a-dictionary/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-30
// At the time of submission:
//   Runtime 15 ms Beats 100.00%
//   Memory 61.15 MB Beats 63.27%

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
    // Precompute character frequencies for each column across all words.
    // dp[i][j] represents the number of ways to form the first i characters
    // of the target using the first j columns. For each column, we either
    // use it to match the current target character or skip it entirely.
    // Time complexity: O(wordLen * 26 + targetLen * wordLen), Space: O(targetLen * wordLen)
    public int numWays(String[] words, String target) {
        if (target == null || target.length() == 0) return 0;

        int wordLen = words[0].length();
        int[][] charCountByCol = new int[wordLen + 1][26];

        // Count how many times each character appears in each column
        for (String word : words) {
            for (int col = 1; col <= wordLen; col++) {
                charCountByCol[col][word.charAt(col - 1) - 'a']++;
            }
        }

        int targetLen = target.length();
        if (wordLen < targetLen) return 0;

        int[][] dp = new int[targetLen + 1][wordLen + 1];

        // One way to form an empty target using any number of columns
        for (int col = 0; col <= wordLen; col++) {
            dp[0][col] = 1;
        }

        int MOD = 1_000_000_007;

        for (int i = 1; i <= targetLen; i++) {
            int targetCharIdx = target.charAt(i - 1) - 'a';
            int maxCol = wordLen - targetLen + i;

            for (int col = i; col <= maxCol; col++) {
                long waysUsingCol =
                        (long) charCountByCol[col][targetCharIdx]
                        * dp[i - 1][col - 1];

                long waysSkippingCol = dp[i][col - 1];

                dp[i][col] = (int) ((waysUsingCol + waysSkippingCol) % MOD);
            }
        }

        return dp[targetLen][wordLen];
    }
}
