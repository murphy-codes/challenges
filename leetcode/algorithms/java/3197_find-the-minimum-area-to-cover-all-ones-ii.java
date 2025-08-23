// Source: https://leetcode.com/problems/find-the-minimum-area-to-cover-all-ones-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-22
// At the time of submission:
//   Runtime 10 ms Beats 86.49%
//   Memory 45.02 MB Beats 35.14%

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
    // This solution computes the minimum sum of 3 rectangles covering all 1s.
    // It uses precomputation (minimumArea) to store minimal bounding rectangles
    // for all submatrices, then tries every valid partition with symmetry.
    // Rotations let us reuse the same logic for vertical/horizontal splits.
    // Time complexity: O(n^2 * m), due to DP and split loops.
    // Space complexity: O(n * m), for storing DP bounding rectangle areas.
    public int minimumSum(int[][] grid) {
        // Try original grid and rotated grid (to cover vertical/horizontal symmetry)
        return Math.min(process(grid), process(rotate(grid)));
    }

    // Compute minimal sum for a given orientation
    private int process(int[][] mat) {
        int rows = mat.length;
        int cols = mat[0].length;

        // For each row, store leftmost & rightmost column of '1's
        int[][] rowBounds = new int[rows][2];
        for (int i = 0; i < rows; i++) {
            int left = -1, right = 0;
            for (int j = 0; j < cols; j++) {
                if (mat[i][j] == 1) {
                    if (left < 0) left = j;
                    right = j;
                }
            }
            rowBounds[i][0] = left;
            rowBounds[i][1] = right;
        }

        // Precompute minimal bounding rectangle areas
        int[][] topLeft = minimumArea(mat);
        mat = rotate(mat);
        int[][] bottomLeft = rotate(rotate(rotate(minimumArea(mat))));
        mat = rotate(mat);
        int[][] bottomRight = rotate(rotate(minimumArea(mat)));
        mat = rotate(mat);
        int[][] topRight = rotate(minimumArea(mat));

        int best = Integer.MAX_VALUE;

        // Case 1: Split into 3 horizontal parts
        if (rows >= 3) {
            for (int i = 1; i < rows; i++) {
                int left = cols, right = 0, top = rows, bottom = 0;
                for (int j = i + 1; j < rows; j++) {
                    int l = rowBounds[j - 1][0];
                    if (l >= 0) {
                        left = Math.min(left, l);
                        right = Math.max(right, rowBounds[j - 1][1]);
                        top = Math.min(top, j - 1);
                        bottom = j - 1;
                    }
                    // Combine top + middle + bottom areas
                    best = Math.min(best,
                        topLeft[i][cols] +
                        (right - left + 1) * (bottom - top + 1) +
                        bottomLeft[j][cols]
                    );
                }
            }
        }

        // Case 2: Split into top-middle-bottom or top-left-right
        if (rows >= 2 && cols >= 2) {
            for (int i = 1; i < rows; i++) {
                for (int j = 1; j < cols; j++) {
                    // Top-Middle-Bottom split
                    best = Math.min(best, topLeft[i][cols] + bottomLeft[i][j] + bottomRight[i][j]);
                    // Top-Right-Bottom split
                    best = Math.min(best, topLeft[i][j] + topRight[i][j] + bottomLeft[i][cols]);
                }
            }
        }
        return best;
    }

    // Precompute minimal bounding rectangle areas
    private int[][] minimumArea(int[][] mat) {
        int rows = mat.length, cols = mat[0].length;
        int[][] dp = new int[rows + 1][cols + 1];
        int[][] border = new int[cols][3];
        for (int j = 0; j < cols; j++) border[j][0] = -1;

        for (int i = 0; i < rows; i++) {
            int left = -1, right = 0;
            for (int j = 0; j < cols; j++) {
                if (mat[i][j] == 1) {
                    if (left < 0) left = j;
                    right = j;
                }
                int[] prev = border[j];
                if (left < 0) { // this row has no '1's yet
                    dp[i + 1][j + 1] = dp[i][j + 1];
                } else if (prev[0] < 0) { // first row containing '1's
                    dp[i + 1][j + 1] = right - left + 1;
                    border[j][0] = i;
                    border[j][1] = left;
                    border[j][2] = right;
                } else { // extend existing rectangle
                    int l = Math.min(prev[1], left);
                    int r = Math.max(prev[2], right);
                    dp[i + 1][j + 1] = (r - l + 1) * (i - prev[0] + 1);
                    border[j][1] = l;
                    border[j][2] = r;
                }
            }
        }
        return dp;
    }

    // Rotate matrix clockwise by 90 degrees
    private int[][] rotate(int[][] mat) {
        int rows = mat.length, cols = mat[0].length;
        int[][] res = new int[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                res[j][rows - 1 - i] = mat[i][j];
            }
        }
        return res;
    }
}
