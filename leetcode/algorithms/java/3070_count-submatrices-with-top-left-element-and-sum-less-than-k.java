// Source: https://leetcode.com/problems/count-submatrices-with-top-left-element-and-sum-less-than-k/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-18
// At the time of submission:
//   Runtime 3 ms Beats 100.00%
//   Memory 161.06 MB Beats 86.40%

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
    // We iterate row by row, maintaining columnSums[j] as the sum of
    // column j from row 0 to current row. For each row, we build a
    // running prefixSum across columns, which represents the sum of
    // submatrix (0,0) to (i,j). Count how many such sums are ≤ k.
    // Time: O(m * n), Space: O(n).

    public int countSubmatrices(int[][] grid, int k) {
        int rowsCount = grid.length;
        int colsCount = grid[0].length;

        // columnSums[j] = sum of column j from row 0 to current row i
        int[] columnSums = new int[colsCount];

        int result = 0;

        for (int row = 0; row < rowsCount; row++) {
            int prefixSum = 0; // sum of rectangle (0,0) → (row, col)

            for (int col = 0; col < colsCount; col++) {

                // Accumulate column sum up to current row
                columnSums[col] += grid[row][col];

                // Build prefix sum across columns
                prefixSum += columnSums[col];

                // Check if current submatrix sum ≤ k
                if (prefixSum <= k) {
                    result++;
                }
            }
        }

        return result;
    }
}