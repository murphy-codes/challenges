// Source: https://leetcode.com/problems/check-if-there-is-a-valid-path-in-a-grid/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-26
// At the time of submission:
//   Runtime 2 ms Beats 100.00%
//   Memory 85.75 MB Beats 93.65%

/****************************************
* 
* You are given an `m x n` `grid`. Each cell of `grid` represents a street.
* _ The street of `grid[i][j]` can be:
* • `1` which means a street connecting the left cell and the right cell.
* • `2` which means a street connecting the upper cell and the lower cell.
* • `3` which means a street connecting the left cell and the lower cell.
* • `4` which means a street connecting the right cell and the lower cell.
* • `5` which means a street connecting the left cell and the upper cell.
* • `6` which means a street connecting the right cell and the upper cell.
* [Image: https://assets.leetcode.com/uploads/2020/03/05/main.png]
* You will initially start at the street of the upper-left cell `(0, 0)`.
* _ A valid path in the grid is a path that starts from the upper left cell
* _ `(0, 0)` and ends at the bottom-right cell `(m - 1, n - 1)`. The path
* _ should only follow the streets.
* Notice that you are not allowed to change any street.
* Return `true` if there is a valid path in the grid or `false` otherwise.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2020/03/05/e1.png]
* Input: grid = [[2,4,3],[6,5,2]]
* Output: true
* Explanation: As shown you can start at cell (0, 0) and visit all the cells of the grid to reach (m - 1, n - 1).
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2020/03/05/e2.png]
* Input: grid = [[1,2,1],[1,2,1]]
* Output: false
* Explanation: As shown you the street at cell (0, 0) is not connected with any street of any other cell and you will get stuck at cell (0, 0)
*
* Example 3:
* Input: grid = [[1,1,2]]
* Output: false
* Explanation: You will get stuck at cell (0, 1) and you cannot reach cell (0, 2).
*
* Constraints:
* • `m == grid.length`
* • `n == grid[i].length`
* • `1 <= m, n <= 300`
* • `1 <= grid[i][j] <= 6`
* 
****************************************/

class Solution {
    // Each street has exactly two connections, so instead of DFS,
    // we simulate the path directly using bitmasks for directions.
    // At each step, remove the incoming direction and continue using
    // the remaining valid exit. Try both possible starts from (0,0).
    // Time: O(m * n), Space: O(1)
    
    // Bitmask directions
    static final int LEFT = 0b0001;
    static final int RIGHT = 0b0010;
    static final int UP = 0b0100;
    static final int DOWN = 0b1000;

    // Index = street type
    // Value = valid exits for that street
    static final int[] STREET_MASK = {
        0,
        LEFT | RIGHT,   // type 1
        UP | DOWN,      // type 2
        LEFT | DOWN,    // type 3
        DOWN | RIGHT,   // type 4
        LEFT | UP,      // type 5
        UP | RIGHT      // type 6
    };

    public boolean hasValidPath(int[][] grid) {
        int startMask = STREET_MASK[grid[0][0]];

        // Only right or down can be valid first moves from (0,0)
        return followPath(grid, startMask & DOWN)
            || followPath(grid, startMask & RIGHT);
    }

    private boolean followPath(int[][] grid, int outDirection) {
        int rows = grid.length;
        int cols = grid[0].length;

        int row = 0;
        int col = 0;
        int inDirection = 0;

        while (true) {
            // Reached destination
            if (row == rows - 1 && col == cols - 1) {
                return true;
            }

            // Move to next cell based on outgoing direction
            if (outDirection == LEFT) {
                col--;
                inDirection = RIGHT;
            } else if (outDirection == RIGHT) {
                col++;
                inDirection = LEFT;
            } else if (outDirection == UP) {
                row--;
                inDirection = DOWN;
            } else if (outDirection == DOWN) {
                row++;
                inDirection = UP;
            } else {
                return false;
            }

            // Prevent looping back to start
            if (row == 0 && col == 0) {
                return false;
            }

            // Out of bounds
            if (row < 0 || row >= rows || col < 0 || col >= cols) {
                return false;
            }

            int nextMask = STREET_MASK[grid[row][col]];

            // Remove the direction we came from
            outDirection = nextMask & (~inDirection);

            // If incoming direction was invalid, path breaks
            if (outDirection == nextMask) {
                return false;
            }
        }
    }
}