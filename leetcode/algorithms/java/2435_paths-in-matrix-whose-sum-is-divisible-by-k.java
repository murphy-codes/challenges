// Source: https://leetcode.com/problems/paths-in-matrix-whose-sum-is-divisible-by-k/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-26
// At the time of submission:
//   Runtime 66 ms Beats 66.81%
//   Memory 83.30 MB Beats 54.87%

/****************************************
* 
* You are given a 0-indexed `m x n` integer matrix `grid` and an integer `k`.
* _ You are currently at position `(0, 0)` and you want to reach position
* _ `(m - 1, n - 1)` moving only down or right.
* Return the number of paths where the sum of the elements on the path is divisible
* _ by `k`. Since the answer may be very large, return it modulo `10^9 + 7`.
*
* Example 1:
* Input: grid = [[5,2,4],[3,0,5],[0,7,2]], k = 3
* Output: 2
* Explanation: There are two paths where the sum of the elements on the path is divisible by k.
* The first path highlighted in red has a sum of 5 + 2 + 4 + 5 + 2 = 18 which is divisible by 3.
* The second path highlighted in blue has a sum of 5 + 3 + 0 + 5 + 2 = 15 which is divisible by 3.
*
* Example 2:
* Input: grid = [[0,0]], k = 5
* Output: 1
* Explanation: The path highlighted in red has a sum of 0 + 0 = 0 which is divisible by 5.
*
* Example 3:
* Input: grid = [[7,3,4,9],[2,3,6,2],[2,3,7,0]], k = 1
* Output: 10
* Explanation: Every integer is divisible by 1 so the sum of the elements on every possible path is divisible by k.
*
* Constraints:
* • `m == grid.length`
* • `n == grid[i].length`
* • `1 <= m, n <= 5 * 10^4`
* • `1 <= m * n <= 5 * 10^4`
* • `0 <= grid[i][j] <= 100`
* • `1 <= k <= 50`
* 
****************************************/

import java.util.Arrays;

class Solution {
    // We use DP where dp[j][r] counts paths to column j with sum%k == r.
    // Each cell combines paths from above (previous row) and left (same row).
    // New remainder is (r + grid[i][j] % k) % k for each transition.
    // Only two rows of DP are stored to reduce space to O(n*k).
    // Total time is O(m*n*k) and space is O(n*k), both feasible since m*n<=5e4.

    private static final int MOD = 1_000_000_007;

    public int numberOfPaths(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        // dpPrev: ways from previous row
        // dpNext: ways for current row
        int[][] dpPrev = new int[n][k];
        int[][] dpNext = new int[n][k];

        for (int i = 0; i < m; i++) {
            Arrays.stream(dpNext).forEach(row -> Arrays.fill(row, 0));

            for (int j = 0; j < n; j++) {
                int cell = grid[i][j] % k;

                // 1. Start of grid: only one path (starting point)
                if (i == 0 && j == 0) {
                    dpNext[0][cell] = 1;
                    continue;
                }

                // Add ways from above (dpPrev)
                if (i > 0) {
                    for (int r = 0; r < k; r++) {
                        int newR = (r + cell) % k;
                        dpNext[j][newR] = (dpNext[j][newR] + dpPrev[j][r]) % MOD;
                    }
                }

                // Add ways from left (dpNext[j - 1])
                if (j > 0) {
                    for (int r = 0; r < k; r++) {
                        int newR = (r + cell) % k;
                        dpNext[j][newR] = (dpNext[j][newR] + dpNext[j - 1][r]) % MOD;
                    }
                }
            }

            // Move next → prev
            int[][] temp = dpPrev;
            dpPrev = dpNext;
            dpNext = temp;
        }

        return dpPrev[n - 1][0];
    }
}
