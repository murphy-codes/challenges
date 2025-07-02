// Source: https://leetcode.com/problems/find-the-original-typed-string-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-01
// At the time of submission:
//   Runtime 103 ms Beats 80.56%
//   Memory 55.71 MB Beats 38.89%

/****************************************
* 
* Alice is attempting to type a specific string on her computer. However, she
* _ tends to be clumsy and may press a key for too long, resulting in a character
* _ being typed multiple times.
* You are given a string word, which represents the final output displayed on
* _ Alice's screen. You are also given a positive integer k.
* Return the total number of possible original strings that Alice might have
* _ intended to type, if she was trying to type a string of size at least k.
* Since the answer may be very large, return it modulo 109 + 7.
*
* Example 1:
* Input: word = "aabbccdd", k = 7
* Output: 5
* Explanation:
* The possible strings are: "aabbccdd", "aabbccd", "aabbcdd", "aabccdd", and "abbccdd".
*
* Example 2:
* Input: word = "aabbccdd", k = 8
* Output: 1
* Explanation:
* The only possible string is "aabbccdd".
*
* Example 3:
* Input: word = "aaabbb", k = 3
* Output: 8
*
* Constraints:
* • 1 <= word.length <= 5 * 10^5
* • `word` consists only of lowercase English letters.
* • 1 <= k <= 2000
* 
****************************************/

import java.util.ArrayList;
import java.util.List;

class Solution {
    // Group consecutive repeated characters into runs, and compute the total
    // number of ways to shrink each run (choose 1 to full length). Total valid
    // results = product of run lengths. Use DP to subtract cases where result
    // length < k. Optimize by computing valid combinations backwards and
    // rolling the prefix sum. Time: O(k^2), Space: O(k^2) worst-case.

    private static final long MOD = (long)1e9 + 7;

    public int possibleStringCount(String word, int k) {
        if (word.length() == k) return 1;

        // Step 1: Group consecutive characters and count their run lengths
        List<Integer> runLengths = new ArrayList<>();
        int n = word.length();
        int i = 0;
        while (i < n) {
            int j = i + 1;
            while (j < n && word.charAt(j) == word.charAt(j - 1)) j++;
            runLengths.add(j - i);
            i = j;
        }

        int m = runLengths.size();  // number of runs

        // Step 2: Precompute product of runLengths in reverse for total count
        long[] totalCombos = new long[m];
        totalCombos[m - 1] = runLengths.get(m - 1);
        for (i = m - 2; i >= 0; i--) {
            totalCombos[i] = (totalCombos[i + 1] * runLengths.get(i)) % MOD;
        }

        // If the number of runs is already >= k, all combinations are valid
        if (m >= k) return (int) totalCombos[0];

        // Step 3: DP to subtract invalid strings with length < k
        int maxExtra = k - m;
        long[][] dp = new long[m][maxExtra + 1];

        // Base case: last run
        for (i = 0; i <= maxExtra; i++) {
            if (runLengths.get(m - 1) + i + m > k) {
                dp[m - 1][i] = runLengths.get(m - 1) - (k - m - i);
            }
        }

        // DP transition from end to start
        for (i = m - 2; i >= 0; i--) {
            long sum = (dp[i + 1][maxExtra] * runLengths.get(i)) % MOD;
            for (int j = maxExtra; j >= 0; j--) {
                sum = (sum + dp[i + 1][j]) % MOD;

                if (j + runLengths.get(i) > maxExtra) {
                    sum = (sum - dp[i + 1][maxExtra] + MOD) % MOD;
                } else {
                    sum = (sum - dp[i + 1][j + runLengths.get(i)] + MOD) % MOD;
                }

                dp[i][j] = sum;
            }
        }

        return (int) dp[0][0];
    }
}
