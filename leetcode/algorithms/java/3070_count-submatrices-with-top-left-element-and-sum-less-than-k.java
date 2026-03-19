// Source: https://leetcode.com/problems/count-submatrices-with-top-left-element-and-sum-less-than-k/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-18
// At the time of submission:
//   Runtime 6 ms Beats 65.60%
//   Memory 161.21 MB Beats 64.80%

/****************************************
* 
* You are given a 0-indexed integer matrix `grid` and an integer `k`.
* Return the number of submatrices that contain the top-left element of
* _ the `grid`, and have a sum less than or equal to `k`.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2024/01/01/example1.png]
* Input: grid = [[7,6,3],[6,6,1]], k = 18
* Output: 4
* Explanation: There are only 4 submatrices, shown in the image above, that contain the top-left element of grid, and have a sum less than or equal to 18.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2024/01/01/example21.png]
* Input: grid = [[7,2,9],[1,5,0],[2,6,6]], k = 20
* Output: 6
* Explanation: There are only 6 submatrices, shown in the image above, that contain the top-left element of grid, and have a sum less than or equal to 20.
*
* Constraints:
* • `m == grid.length`
* • `n == grid[i].length`
* • `1 <= n, m <= 1000`
* • `0 <= grid[i][j] <= 1000`
* • `1 <= k <= 10^9`
* 
****************************************/

class Solution {
    // Since all submatrices must include (0,0), each valid submatrix is
    // uniquely defined by its bottom-right corner (i, j). We compute the
    // 2D prefix sum in-place so grid[i][j] becomes the sum of rectangle
    // (0,0) to (i,j). Count how many such prefix sums are <= k.
    // Time: O(m * n). Space: O(1).
    public int countSubmatrices(int[][] grid, int k) {

        int m = grid.length;
        int n = grid[0].length;

        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                int top = (i > 0) ? grid[i - 1][j] : 0;
                int left = (j > 0) ? grid[i][j - 1] : 0;
                int topLeft = (i > 0 && j > 0) ? grid[i - 1][j - 1] : 0;

                // Build prefix sum in-place
                grid[i][j] += top + left - topLeft;

                if (grid[i][j] <= k) {
                    count++;
                }
            }
        }

        return count;
    }
}