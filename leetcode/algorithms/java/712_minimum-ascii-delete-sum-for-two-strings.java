// Source: https://leetcode.com/problems/minimum-ascii-delete-sum-for-two-strings/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-09
// At the time of submission:
//   Runtime 17 ms Beats 79.88%
//   Memory 43.11 MB Beats 94.19%

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
    // Dynamic programming where dp[i][j] is the minimum ASCII delete sum to make
    // s1[i:] and s2[j:] equal. Matching characters incur no cost; otherwise we
    // delete one character and pay its ASCII value. The DP is optimized to O(m)
    // space using a rolling array. Time O(n*m), space O(m).
    public int minimumDeleteSum(String s1, String s2) {
        int n = s1.length(), m = s2.length();
        int[] dp = new int[m + 1];

        // Base case: delete all remaining chars from s2
        for (int j = m - 1; j >= 0; j--) {
            dp[j] = dp[j + 1] + s2.charAt(j);
        }

        for (int i = n - 1; i >= 0; i--) {
            int prevDiagonal = dp[m];
            dp[m] += s1.charAt(i); // delete all remaining from s1

            for (int j = m - 1; j >= 0; j--) {
                int temp = dp[j];
                if (s1.charAt(i) == s2.charAt(j)) {
                    dp[j] = prevDiagonal;
                } else {
                    dp[j] = Math.min(
                        s1.charAt(i) + dp[j],
                        s2.charAt(j) + dp[j + 1]
                    );
                }
                prevDiagonal = temp;
            }
        }
        return dp[0];
    }
}
