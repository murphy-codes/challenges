// Source: https://leetcode.com/problems/length-of-longest-v-shaped-diagonal-segment/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-26
// At the time of submission:
//   Runtime 433 ms Beats 91.03%
//   Memory 127.70 MB Beats 14.10%

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

import java.util.Arrays;

class Solution {
    // Memoized DFS over diagonals. From each '1' cell we try all 4 diagonal
    // directions, expecting 2 next, then alternating 0/2 thereafter. State is
    // (row, col, direction, turnUsed) and memo stores the best continuation length
    // starting from the next cell. We may make at most one clockwise 90° turn.
    // Time: O(m*n) states with O(1) work each. Space: O(m*n) for memo/stack.

    // Diagonals in clockwise order: ↘, ↙, ↖, ↗
    private static final int[][] DIRS = {
        { 1,  1},  // 0: down-right
        { 1, -1},  // 1: down-left
        {-1, -1},  // 2: up-left
        {-1,  1}   // 3: up-right
    };

    private int[][] grid;
    private int rows, cols;
    // memo[r][c][dir][turned] = best length starting AFTER (r,c),
    // moving in 'dir', with turned=0/1 indicating turn still available/already used
    private int[][][][] memo;

    public int lenOfVDiagonal(int[][] grid) {
        this.grid = grid;
        this.rows = grid.length;
        this.cols = grid[0].length;
        this.memo = new int[rows][cols][4][2];

        // init memo to -1 (uncomputed)
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                for (int d = 0; d < 4; d++) {
                    Arrays.fill(memo[r][c][d], -1);
                }
            }
        }

        int best = 0;
        // A valid segment must start at a 1; we seed from every 1 in all 4 directions.
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 1) {
                    for (int d = 0; d < 4; d++) {
                        // We are at a '1'; the next expected value is '2'.
                        best = Math.max(best, 1 + dfs(r, c, d, true, 2));
                    }
                }
            }
        }
        return best;
    }

    // DFS from (r,c): try to step to the next cell in 'dir' expecting 'target'.
    // 'turnAvailable' indicates whether we may still make one clockwise 90° turn.
    private int dfs(int r, int c, int dir, boolean turnAvailable, int target) {
        int nr = r + DIRS[dir][0];
        int nc = c + DIRS[dir][1];

        // If out of bounds or value mismatch, we cannot extend further.
        if (nr < 0 || nr >= rows || nc < 0 || nc >= cols || grid[nr][nc] != target) {
            return 0;
        }

        int turned = turnAvailable ? 1 : 0;
        if (memo[nr][nc][dir][turned] != -1) {
            // Memo stores the max continuation length from (nr,nc) going forward.
            return memo[nr][nc][dir][turned];
        }

        // Continue straight, next expected value alternates: 2 -> 0 -> 2 -> ...
        int best = dfs(nr, nc, dir, turnAvailable, 2 - target);

        // Optionally take a single clockwise turn (dir -> (dir+1)%4) if still available.
        if (turnAvailable) {
            int ndir = (dir + 1) % 4;
            best = Math.max(best, dfs(nr, nc, ndir, false, 2 - target));
        }

        // +1 for the step just taken into (nr,nc).
        memo[nr][nc][dir][turned] = best + 1;
        return best + 1;
    }
}
