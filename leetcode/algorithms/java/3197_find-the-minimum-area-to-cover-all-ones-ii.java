// Source: https://leetcode.com/problems/find-the-minimum-area-to-cover-all-ones-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-22
// At the time of submission:
//   Runtime 43 ms Beats 64.86%
//   Memory 44.62 MB Beats 59.46%

/****************************************
* 
* You are given a 2D binary array `grid`. You need to find 3 non-overlapping
* _ rectangles having non-zero areas with horizontal and vertical sides such
* _ that all the 1's in `grid` lie inside these rectangles.
* Return the minimum possible sum of the area of these rectangles.
* Note that the rectangles are allowed to touch.
*
* Example 1:
* Input: grid = [[1,0,1],[1,1,1]]
* Output: 5
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2024/05/14/example0rect21.png]
* The 1's at (0, 0) and (1, 0) are covered by a rectangle of area 2.
* The 1's at (0, 2) and (1, 2) are covered by a rectangle of area 2.
* The 1 at (1, 1) is covered by a rectangle of area 1.
*
* Example 2:
* Input: grid = [[1,0,1,0],[0,1,0,1]]
* Output: 5
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2024/05/14/example1rect2.png]
* The 1's at (0, 0) and (0, 2) are covered by a rectangle of area 3.
* The 1 at (1, 1) is covered by a rectangle of area 1.
* The 1 at (1, 3) is covered by a rectangle of area 1.
*
*
* Constraints:
* • 1 <= grid.length, grid[i].length <= 30
* • `grid[i][j]` is either `0` or `1`.
* • The input is generated such that there are at least three 1's in `grid`.
* 
****************************************/

class Solution {
    // This solution partitions the grid into 3 rectangles using only straight
    // horizontal or vertical cuts. Each rectangle's cost is the smallest bounding
    // box that covers all 1s inside it. The algorithm tries every possible way
    // to split into 1 + 2 rectangles and combines results recursively.
    // Time complexity: O(n^3 * m^3) worst case due to nested loops over subgrids.
    // Space complexity: O(1), aside from recursion stack and input grid.

    int[][] grid;
    int rows, cols;

    public int minimumSum(int[][] grid) {
        this.grid = grid;
        rows = grid.length;
        cols = grid[0].length;

        int best = Integer.MAX_VALUE / 3;

        // Try cutting horizontally (top vs bottom)
        for (int i = 0; i < rows - 1; i++) {
            best = Math.min(best,
                Math.min(
                    oneRectMaxArea(0, 0, i, cols - 1) + twoRectMaxArea(i + 1, 0, rows - 1, cols - 1),
                    twoRectMaxArea(0, 0, i, cols - 1) + oneRectMaxArea(i + 1, 0, rows - 1, cols - 1)
                )
            );
        }

        // Try cutting vertically (left vs right)
        for (int j = 0; j < cols - 1; j++) {
            best = Math.min(best,
                Math.min(
                    oneRectMaxArea(0, 0, rows - 1, j) + twoRectMaxArea(0, j + 1, rows - 1, cols - 1),
                    twoRectMaxArea(0, 0, rows - 1, j) + oneRectMaxArea(0, j + 1, rows - 1, cols - 1)
                )
            );
        }

        return best;
    }

    // Finds minimum sum of two rectangles within the given bounds
    public int twoRectMaxArea(int x1, int y1, int x2, int y2) {
        int best = Integer.MAX_VALUE / 2;

        // Try horizontal split
        for (int i = x1; i < x2; i++) {
            best = Math.min(best,
                oneRectMaxArea(x1, y1, i, y2) + oneRectMaxArea(i + 1, y1, x2, y2)
            );
        }

        // Try vertical split
        for (int j = y1; j < y2; j++) {
            best = Math.min(best,
                oneRectMaxArea(x1, y1, x2, j) + oneRectMaxArea(x1, j + 1, x2, y2)
            );
        }

        return best;
    }

    // Finds the smallest bounding rectangle covering all 1s in the given bounds
    public int oneRectMaxArea(int x1, int y1, int x2, int y2) {
        int minCol = y2, maxCol = y1, minRow = x2, maxRow = x1;
        boolean found = false;

        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                if (grid[i][j] == 0) continue;
                found = true;
                minCol = Math.min(minCol, j);
                maxCol = Math.max(maxCol, j);
                minRow = Math.min(minRow, i);
                maxRow = Math.max(maxRow, i);
            }
        }

        return !found ? Integer.MAX_VALUE / 4 : (maxCol - minCol + 1) * (maxRow - minRow + 1);
    }
}
