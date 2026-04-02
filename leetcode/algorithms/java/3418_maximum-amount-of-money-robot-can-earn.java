// Source: https://leetcode.com/problems/maximum-amount-of-money-robot-can-earn/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-01
// At the time of submission:
//   Runtime 96 ms Beats 80.90%
//   Memory 238.27 MB Beats 67.42%

/****************************************
* 
* You are given an `m x n` grid. A robot starts at the top-left corner of the
* _ grid `(0, 0)` and wants to reach the bottom-right corner `(m - 1, n - 1)`.
* _ The robot can move either right or down at any point in time.
* The grid contains a value `coins[i][j]` in each cell:
* • If `coins[i][j] >= 0`, the robot gains that many coins.
* • If `coins[i][j] < 0`, the robot encounters a robber, and the robber
* __ steals the absolute value of `coins[i][j]` coins.
* The robot has a special ability to neutralize robbers in at most 2
* _ cells on its path, preventing them from stealing coins in those cells.
* Note: The robot's total coins can be negative.
* Return the maximum profit the robot can gain on the route.
*
* Example 1:
* Input: coins = [[0,1,-1],[1,-2,3],[2,-3,4]]
* Output: 8
* Explanation:
* An optimal path for maximum coins is:
* 1. Start at `(0, 0)` with `0` coins (total coins = `0`).
* 2. Move to `(0, 1)`, gaining `1` coin (total coins = `0 + 1 = 1`).
* 3. Move to `(1, 1)`, where there's a robber stealing `2` coins. The robot uses one neutralization here, avoiding the robbery (total coins = `1`).
* 4. Move to `(1, 2)`, gaining `3` coins (total coins = `1 + 3 = 4`).
* 4. Move to `(2, 2)`, gaining `4` coins (total coins = `4 + 4 = 8`).
*
* Example 2:
* Input: coins = [[10,10,10],[10,10,10]]
* Output: 40
* Explanation:
* An optimal path for maximum coins is:
* 1. Start at `(0, 0)` with `10` coins (total coins = `10`).
* 2. Move to `(0, 1)`, gaining `10` coins (total coins = `10 + 10 = 20`).
* 3. Move to `(0, 2)`, gaining another `10` coins (total coins = `20 + 10 = 30`).
* 4. Move to `(1, 2)`, gaining the final `10` coins (total coins = `30 + 10 = 40`).
*
* Constraints:
* • `m == coins.length`
* • `n == coins[i].length`
* • `1 <= m, n <= 500`
* • `-1000 <= coins[i][j] <= 1000`
* 
****************************************/

class Solution {
    // Use DP where dp[i][j][k] is max coins reaching (i,j)
    // with k robber neutralizations used (k <= 2). Transition
    // from top/left cells. For negative cells, either take loss
    // or neutralize (if k > 0). Track best across all k at end.
    // Time: O(m * n * 3), Space: O(m * n * 3).
    public int maximumAmount(int[][] coins) {
        int m = coins.length;
        int n = coins[0].length;

        int[][][] dp = new int[m][n][3];

        // Initialize with very small values
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < 3; k++) {
                    dp[i][j][k] = Integer.MIN_VALUE;
                }
            }
        }

        // Base case (0,0)
        for (int k = 0; k < 3; k++) {
            if (coins[0][0] >= 0) {
                dp[0][0][k] = coins[0][0];
            } else {
                if (k > 0) {
                    dp[0][0][k] = 0; // neutralize
                } else {
                    dp[0][0][k] = coins[0][0]; // take loss
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (i == 0 && j == 0) continue;

                for (int k = 0; k < 3; k++) {
                    int bestPrev = Integer.MIN_VALUE;

                    if (i > 0) bestPrev = Math.max(bestPrev, dp[i - 1][j][k]);
                    if (j > 0) bestPrev = Math.max(bestPrev, dp[i][j - 1][k]);

                    if (bestPrev == Integer.MIN_VALUE) continue;

                    int val = coins[i][j];

                    if (val >= 0) {
                        dp[i][j][k] = Math.max(dp[i][j][k], bestPrev + val);
                    } else {
                        // Option 1: take the hit
                        dp[i][j][k] = Math.max(dp[i][j][k], bestPrev + val);

                        // Option 2: neutralize
                        if (k > 0) {
                            int bestPrevWithLessK = Integer.MIN_VALUE;
                            if (i > 0)
                                bestPrevWithLessK = Math.max(bestPrevWithLessK, dp[i - 1][j][k - 1]);
                            if (j > 0)
                                bestPrevWithLessK = Math.max(bestPrevWithLessK, dp[i][j - 1][k - 1]);

                            if (bestPrevWithLessK != Integer.MIN_VALUE) {
                                dp[i][j][k] = Math.max(dp[i][j][k], bestPrevWithLessK);
                            }
                        }
                    }
                }
            }
        }

        int res = Integer.MIN_VALUE;
        for (int k = 0; k < 3; k++) {
            res = Math.max(res, dp[m - 1][n - 1][k]);
        }

        return res;
    }
}