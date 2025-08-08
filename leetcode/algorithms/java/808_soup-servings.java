// Source: https://leetcode.com/problems/soup-servings/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-08
// At the time of submission:
//   Runtime 1 ms Beats 88.22%
//   Memory 41.35 MB Beats 89.82%

/****************************************
* 
* You have two soups, A and B, each starting with `n` mL. On every turn, one of
* _ the following four serving operations is chosen at random, each with
* _ probability 0.25 independent of all previous turns:
* • pour 100 mL from type A and 0 mL from type B
* • pour 75 mL from type A and 25 mL from type B
* • pour 50 mL from type A and 50 mL from type B
* • pour 25 mL from type A and 75 mL from type B
* Note:
* • There is no operation that pours 0 mL from A and 100 mL from B.
* • The amounts from A and B are poured simultaneously during the turn.
* • If an operation asks you to pour more than you have left of a soup, pour all
* _ that remains of that soup.
* The process stops immediately after any turn in which one of the soups is used up.
* Return the probability that A is used up before B, plus half the probability
* _ that both soups are used up in the same turn. Answers within `10^-5` of the
* _ actual answer will be accepted.
*
* Example 1:
* Input: n = 50
* Output: 0.62500
* Explanation:
* If we perform either of the first two serving operations, soup A will become empty first.
* If we perform the third operation, A and B will become empty at the same time.
* If we perform the fourth operation, B will become empty first.
* So the total probability of A becoming empty first plus half the probability that A and B become empty at the same time, is 0.25 * (1 + 1 + 0.5 + 0) = 0.625.
*
* Example 2:
* Input: n = 100
* Output: 0.71875
* Explanation:
* If we perform the first serving operation, soup A will become empty first.
* If we perform the second serving operations, A will become empty on performing operation [1, 2, 3], and both A and B become empty on performing operation 4.
* If we perform the third operation, A will become empty on performing operation [1, 2], and both A and B become empty on performing operation 3.
* If we perform the fourth operation, A will become empty on performing operation 1, and both A and B become empty on performing operation 2.
* So the total probability of A becoming empty first plus half the probability that A and B become empty at the same time, is 0.71875.
*
* Constraints:
* • 0 <= n <= 10^9
* 
****************************************/

class Solution {
    // This solution uses DFS with memoization to compute the probability.
    // We scale n down by 25 mL units to reduce state space, since all
    // operations are multiples of 25 mL. For large n (>=200 units), the
    // probability approaches 1, so we return 1 directly. Time complexity
    // is O(n^2) and space complexity is O(n^2), where n is scaled down.
    
    private double[][] memo;

    public double soupServings(int n) {
        // Scale down to units of 25 mL to shrink state space
        n = (int) Math.ceil(n / 25.0);

        // For large n, probability approaches 1
        if (n >= 200) return 1.0;

        memo = new double[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            java.util.Arrays.fill(memo[i], -1.0);
        }
        return dfs(n, n);
    }

    private double dfs(int a, int b) {
        if (a <= 0 && b <= 0) return 0.5;
        if (a <= 0) return 1.0;
        if (b <= 0) return 0.0;
        if (memo[a][b] >= 0) return memo[a][b];

        memo[a][b] = 0.25 * (
            dfs(a - 4, b) +
            dfs(a - 3, b - 1) +
            dfs(a - 2, b - 2) +
            dfs(a - 1, b - 3)
        );
        return memo[a][b];
    }
}
