// Source: https://leetcode.com/problems/minimum-absolute-difference-in-sliding-submatrix/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-19
// At the time of submission:
//   Runtime 11 ms Beats 97.87%
//   Memory 48.24 MB Beats 12.77%

/****************************************
* 
* You are given an `m x n` integer matrix `grid` and an integer `k`.
* For every contiguous `k x k` submatrix of `grid`, compute the minimum
* _ absolute difference between any two distinct values within that submatrix.
* Return a 2D array `ans` of size `(m - k + 1) x (n - k + 1)`, where `ans[i][j]`
* _ is the minimum absolute difference in the submatrix whose top-left corner is
* _ `(i, j)` in `grid`.
* Note: If all elements in the submatrix have the same value, the answer will be 0.
* A submatrix `(x1, y1, x2, y2)` is a matrix that is formed by choosing all
* _ cells `matrix[x][y]` where `x1 <= x <= x2` and `y1 <= y <= y2`.
*
* Example 1:
* Input: grid = [[1,8],[3,-2]], k = 2
* Output: [[2]]
* Explanation:
* • There is only one possible k x k submatrix: [[1, 8], [3, -2]].
* • Distinct values in the submatrix are [1, 8, 3, -2].
* • The minimum absolute difference in the submatrix is |1 - 3| = 2. Thus, the answer is [[2]].
*
* Example 2:
* Input: grid = [[3,-1]], k = 1
* Output: [[0,0]]
* Explanation:
* • Both k x k submatrix has only one distinct element.
* • Thus, the answer is [[0, 0]].
*
* Example 3:
* Input: grid = [[1,-2,3],[2,3,5]], k = 2
* Output: [[1,2]]
* Explanation:
* • There are two possible k × k submatrix:
* _ • Starting at (0, 0): [[1, -2], [2, 3]].
* __ • Distinct values in the submatrix are [1, -2, 2, 3].
* __ • The minimum absolute difference in the submatrix is |1 - 2| = 1.
* _ • Starting at (0, 1): [[-2, 3], [3, 5]].
* __ • Distinct values in the submatrix are [-2, 3, 5].
* __ • The minimum absolute difference in the submatrix is |3 - 5| = 2.
* • Thus, the answer is [[1, 2]].
*
* Constraints:
* • `1 <= m == grid.length <= 30`
* • `1 <= n == grid[i].length <= 30`
* • `-10^5 <= grid[i][j] <= 10^5`
* • `1 <= k <= min(m, n)`
* 
****************************************/

import java.util.Arrays;

class Solution {
    // For each k x k submatrix, collect all values into an array,
    // sort them, and compute the minimum difference between adjacent
    // distinct elements. Sorting ensures the minimum difference is
    // found among neighbors. If no distinct pair exists, return 0.
    // Time: O(m * n * k^2 log k), Space: O(k^2).

    public int getMinDiff(int[] values) {

        Arrays.sort(values);

        int minDiff = Integer.MAX_VALUE;

        for (int i = 1; i < values.length; i++) {
            // Only consider distinct values
            if (values[i - 1] != values[i]) {
                minDiff = Math.min(minDiff, Math.abs(values[i] - values[i - 1]));
            }
        }

        return (minDiff == Integer.MAX_VALUE) ? 0 : minDiff;
    }

    public int[][] minAbsDiff(int[][] grid, int k) {

        int rows = grid.length;
        int cols = grid[0].length;

        int[][] result = new int[rows - k + 1][cols - k + 1];

        for (int r = 0; r <= rows - k; r++) {
            for (int c = 0; c <= cols - k; c++) {

                int[] values = new int[k * k];
                int index = 0;

                // Collect k x k submatrix values
                for (int i = r; i < r + k; i++) {
                    for (int j = c; j < c + k; j++) {
                        values[index++] = grid[i][j];
                    }
                }

                result[r][c] = getMinDiff(values);
            }
        }

        return result;
    }
}