// Source: https://leetcode.com/problems/number-of-people-aware-of-a-secret/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-07
// At the time of submission:
//   Runtime 3 ms Beats 85.45%
//   Memory 40.52 MB Beats 90.91%

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
    // Dynamic programming with rolling sum optimization.
    // dp[day] stores the number of people who first learn the secret on that day.
    // A rolling sum tracks who can share (from delay to forget - 1 days old).
    // We update daily by adding new sharers and removing those who forget.
    // Time complexity: O(n), Space complexity: O(n).
    public int peopleAwareOfSecret(int n, int delay, int forget) {
        final int MOD = 1_000_000_007;
        int[] learned = new int[n + 1]; // learned[day] = people who learn on this day
        learned[1] = 1; // day 1, one person knows the secret

        long canShare = 0; // rolling sum of people eligible to share
        for (int day = 2; day <= n; day++) {
            // Add those who just became eligible to share
            if (day - delay >= 1) {
                canShare = (canShare + learned[day - delay]) % MOD;
            }
            // Remove those who just forgot
            if (day - forget >= 1) {
                canShare = (canShare - learned[day - forget] + MOD) % MOD;
            }
            learned[day] = (int) canShare; // new learners today
        }

        long result = 0;
        // Count people who still remember on day n
        for (int day = n - forget + 1; day <= n; day++) {
            if (day >= 1) {
                result = (result + learned[day]) % MOD;
            }
        }
        return (int) result;
    }
}
