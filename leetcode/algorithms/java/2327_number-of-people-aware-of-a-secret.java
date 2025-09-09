// Source: https://leetcode.com/problems/number-of-people-aware-of-a-secret/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-07
// At the time of submission:
//   Runtime 3 ms Beats 85.45%
//   Memory 40.97 MB Beats 50.91%

/****************************************
* 
* No-Zero integer is a positive integer that does not contain any 0 in its
* _ decimal representation.
* Given an integer `n`, return a list of two integers `[a, b]` where:
* • `a` and `b` are No-Zero integers.
* • `a + b = n`
* The test cases are generated so that there is at least one valid solution.
* _ If there are many valid solutions, you can return any of them.
*
* Example 1:
* Input: n = 2
* Output: [1,1]
* Explanation: Let a = 1 and b = 1.
* Both a and b are no-zero integers, and a + b = 2 = n.
*
* Example 2:
* Input: n = 11
* Output: [2,9]
* Explanation: Let a = 2 and b = 9.
* Both a and b are no-zero integers, and a + b = 11 = n.
* Note that there are other valid answers as [8, 3] that can be accepted.
*
* Constraints:
* • 2 <= n <= 10^4
* 
****************************************/

class Solution {
    // We use dynamic programming to track how many new people learn the secret 
    // on each day. A rolling sum keeps track of people eligible to share (from 
    // day-delay to day-forget). Each day we update the sum by adding those who 
    // become eligible and removing those who forget. Finally, we total those 
    // still remembering on day n. Time complexity is O(n), and space is O(n).

    private static final int MOD = 1_000_000_007;

    public int peopleAwareOfSecret(int n, int delay, int forget) {
        long[] dp = new long[n + 1]; 
        // dp[i] = number of new people who learn the secret on day i
        dp[1] = 1;

        long shareable = 0; // rolling sum of people eligible to share
        for (int day = 2; day <= n; day++) {
            // Add people who just became eligible to share
            if (day - delay >= 1) {
                shareable = (shareable + dp[day - delay]) % MOD;
            }
            // Remove people who just forgot
            if (day - forget >= 1) {
                shareable = (shareable - dp[day - forget] + MOD) % MOD;
            }
            dp[day] = shareable; // new learners today
        }

        // Sum people still remembering the secret on day n
        long result = 0;
        for (int day = n - forget + 1; day <= n; day++) {
            if (day >= 1) {
                result = (result + dp[day]) % MOD;
            }
        }
        return (int) result;
    }
}
