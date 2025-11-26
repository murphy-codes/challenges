// Source: https://leetcode.com/problems/paths-in-matrix-whose-sum-is-divisible-by-k/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-26
// At the time of submission:
//   Runtime 30 ms Beats 98.67%
//   Memory 82.66 MB Beats 58.85%

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

class Solution {
    // This solution uses DP where each cell tracks k remainders of path sums.
    // Only two rows of DP are stored at once (rolling array), reducing space
    // from O(m*n*k) to O(n*k). For each cell, we update all k remainders by
    // combining paths from the top and left. Total time is O(m*n*k) and the
    // final answer is the count of paths to the bottom-right with remainder 0.

    private static final int MOD = (int) 1e9 + 7;

    public int numberOfPaths(int[][] grid, int k) {
        return rolling(grid, k);
    }

    private int rolling(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        // DP arrays: prev[row][remainder], curr[row][remainder]
        int[][] prev = new int[n][k];
        int[][] curr = new int[n][k];

        // Initialize first row (moving only right)
        int runningSum = 0;
        for (int col = 0; col < n; col++) {
            runningSum += grid[0][col];
            prev[col][runningSum % k] = 1;
        }

        runningSum = grid[0][0]; // reset to start first column accumulation

        // Process rows 1..m-1
        for (int row = 1; row < m; row++) {

            // First column for this row (moving only down)
            runningSum += grid[row][0];

            // Reset curr[0] completely (ensures no stale data)
            Arrays.fill(curr[0], 0);

            // Set base case for first column in current row
            curr[0][runningSum % k] = 1;

            // Process columns 1..n-1
            for (int col = 1; col < n; col++) {
                int cellVal = grid[row][col];

                // For every possible previous remainder
                for (int prevRem = 0; prevRem < k; prevRem++) {
                    int newRem = (prevRem + cellVal) % k;

                    // Ways = (from above) + (from left)
                    curr[col][newRem] =
                        (int) (((long) prev[col][prevRem] +
                                curr[col - 1][prevRem]) % MOD);
                }
            }

            // Swap references: prev <- curr, curr <- prev
            int[][] swap = prev;
            prev = curr;
            curr = swap;
        }

        // The answer is the number of paths whose remainder is 0 at bottom-right
        return prev[n - 1][0];
    }
}
