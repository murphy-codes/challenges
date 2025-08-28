// Source: https://leetcode.com/problems/length-of-longest-v-shaped-diagonal-segment/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-27
// At the time of submission:
//   Runtime 81 ms Beats 97.44%
//   Memory 83.91 MB Beats 55.13%

/****************************************
* 
* You are given a 2D integer matrix `grid` of size `n x m`, where each element is
* _ either `0`, `1`, or `2`.
* A V-shaped diagonal segment is defined as:
* • The segment starts with `1`.
* • The subsequent elements follow this infinite sequence: `2, 0, 2, 0, ...`.
* • The segment:
* _ • Starts along a diagonal direction (top-left to bottom-right, bottom-right
* __ to top-left, top-right to bottom-left, or bottom-left to top-right).
* _ • Continues the sequence in the same diagonal direction.
* _ • Makes at most one clockwise 90-degree turn to another diagonal direction
* __ while maintaining the sequence.
* Return the length of the longest V-shaped diagonal segment.
* _ If no valid segment exists, return 0.
*
* Example 1:
* Input: grid = [[2,2,1,2,2],[2,0,2,2,0],[2,0,1,1,0],[1,0,2,2,2],[2,0,0,2,2]]
* Output: 5
* Explanation:
* The longest V-shaped diagonal segment has a length of 5 and follows these coordinates: `(0,2) → (1,3) → (2,4)`, takes a 90-degree clockwise turn at `(2,4)`, and continues as `(3,3) → (4,2)`.
*
* Example 2:
* Input: grid = [[2,2,2,2,2],[2,0,2,2,0],[2,0,1,1,0],[1,0,2,2,2],[2,0,0,2,2]]
* Output: 4
* Explanation:
* The longest V-shaped diagonal segment has a length of 4 and follows these coordinates: `(2,3) → (3,2)`, takes a 90-degree clockwise turn at `(3,2)`, and continues as `(2,1) → (1,0)`.
*
* Example 3:
* Input: grid = [[1,2,2,2,2],[2,2,2,2,0],[2,0,0,0,0],[0,0,2,2,2],[2,0,0,2,0]]
* Output: 5
* Explanation:
* The longest V-shaped diagonal segment has a length of 5 and follows these coordinates: `(0,0) → (1,1) → (2,2) → (3,3) → (4,4)`.
*
* Example 4:
* Input: grid = [[1]]
* Output: 1
* Explanation:
* The longest V-shaped diagonal segment has a length of 1 and follows these coordinates: `(0,0)`.
*
* Constraints:
* • n == grid.length
* • m == grid[i].length
* • 1 <= n, m <= 500
* • `grid[i][j]` is either `0`, `1` or `2`.
* 
****************************************/

class Solution {
    // This solution uses DFS with memoization to find the longest "V"-shaped
    // diagonal path. Each state is defined by position, direction, turn ability,
    // and the expected alternating cell value (1 or 2). To avoid recomputation,
    // states are cached using a bitmask. Early pruning skips directions where
    // not enough space remains to beat the current best. 
    // Time Complexity: O(m * n * 8) in practice due to memoization.
    // Space Complexity: O(m * n * 8) for the memo table.

    // Directions: down-right, down-left, up-left, up-right
    private static final int[][] DIRS = { { 1, 1 }, { 1, -1 }, { -1, -1 }, { -1, 1 } };

    public int lenOfVDiagonal(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][][] memo = new int[m][n][1 << 3]; // memo[row][col][state mask]
        int maxLen = 0;

        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                if (grid[row][col] != 1) continue;

                // maxs[k] = maximum possible steps in each direction from (row,col)
                int[] maxs = { m - row, col + 1, row + 1, n - col };

                for (int dir = 0; dir < 4; dir++) {
                    if (maxs[dir] > maxLen) {
                        maxLen = Math.max(maxLen,
                                dfs(row, col, dir, 1, 2, grid, memo) + 1);
                    }
                }
            }
        }
        return maxLen;
    }

    private int dfs(int row, int col, int dir, int canTurn, int target,
                    int[][] grid, int[][][] memo) {
        row += DIRS[dir][0];
        col += DIRS[dir][1];

        // Stop if out of bounds or not matching the target number
        if (row < 0 || row >= grid.length ||
            col < 0 || col >= grid[row].length ||
            grid[row][col] != target) {
            return 0;
        }

        int mask = (dir << 1) | canTurn;
        if (memo[row][col][mask] > 0) {
            return memo[row][col][mask];
        }

        int maxLen = dfs(row, col, dir, canTurn, 2 - target, grid, memo);

        // Optionally turn once (if allowed) to the next direction
        if (canTurn == 1) {
            int[] maxs = { grid.length - row - 1, col, row, grid[row].length - col - 1 };
            int nextDir = (dir + 1) % 4;
            if (maxs[nextDir] > maxLen) {
                maxLen = Math.max(maxLen,
                        dfs(row, col, nextDir, 0, 2 - target, grid, memo));
            }
        }

        return memo[row][col][mask] = maxLen + 1;
    }
}
