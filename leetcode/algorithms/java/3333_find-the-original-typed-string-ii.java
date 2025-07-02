// Source: https://leetcode.com/problems/find-the-original-typed-string-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-01
// At the time of submission:
//   Runtime 163 ms Beats 63.89%
//   Memory 55.84 MB Beats 27.78%

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
import java.util.Arrays;

class Solution {
    // Count all original strings Alice might have intended to type.
    // For each run of repeated chars, Alice can choose 1 to run.length presses.
    // Total = product of run lengths (multiplication principle).
    // Subtract all combinations where final length < k using DP + prefix sums.
    // Time: O(n + k^2), Space: O(k) — works even for large strings (n up to 5 * 10^5).

    private static final int MOD = 1_000_000_007;

    public int possibleStringCount(String word, int k) {
        int n = word.length();
        List<Integer> freq = new ArrayList<>();
        int count = 1;

        // Step 1: Count frequencies of consecutive identical characters
        for (int i = 1; i < n; ++i) {
            if (word.charAt(i) == word.charAt(i - 1)) {
                ++count;
            } else {
                freq.add(count);
                count = 1;
            }
        }
        freq.add(count);

        // Step 2: Compute total combinations using multiplication principle
        long total = 1;
        for (int val : freq) {
            total = (total * val) % MOD;
        }

        // Step 3: If we already have k groups, all combinations are valid
        if (freq.size() >= k) return (int) total;

        // Step 4: Dynamic Programming to subtract invalid cases (length < k)
        int[] dp = new int[k];
        int[] prefix = new int[k];
        dp[0] = 1;
        Arrays.fill(prefix, 1);

        for (int run : freq) {
            int[] newDp = new int[k];
            for (int len = 1; len < k; ++len) {
                newDp[len] = prefix[len - 1];
                if (len - run - 1 >= 0) {
                    newDp[len] = (newDp[len] - prefix[len - run - 1] + MOD) % MOD;
                }
            }

            int[] newPrefix = new int[k];
            newPrefix[0] = newDp[0];
            for (int len = 1; len < k; ++len) {
                newPrefix[len] = (newPrefix[len - 1] + newDp[len]) % MOD;
            }

            dp = newDp;
            prefix = newPrefix;
        }

        // Step 5: Subtract invalid combinations of length < k
        return (int)((total - prefix[k - 1] + MOD) % MOD);
    }
}
