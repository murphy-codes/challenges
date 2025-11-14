// Source: https://leetcode.com/problems/maximum-number-of-operations-to-move-ones-to-the-end/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-14
// At the time of submission:
//   Runtime 8 ms Beats 98.64%
//   Memory 77.22 MB Beats 17.19%

/****************************************
* 
* You are given a positive integer `n`, indicating that we initially have an
* _ `n x n` 0-indexed integer matrix `mat` filled with zeroes.
* You are also given a 2D integer array `query`. For each
* _ `query[i] = [row1_i, col1_i, row2_i, col2_i]`, you should do the following operation:
* • Add `1` to every element in the submatrix with the top left corner `(row1_i, col1_i)`
* __ and the bottom right corner `(row2_i, col2_i)`. That is, add `1` to `mat[x][y]`
* __ for all `row1i <= x <= row2i` and `col1i <= y <= col2i`.
* Return the matrix `mat` after performing every query.
*
* Example 1:
* Input: n = 3, queries = [[1,1,2,2],[0,0,1,1]]
* Output: [[1,1,0],[1,2,1],[0,1,1]]
* Explanation: The diagram above shows the initial matrix, the matrix after the first query, and the matrix after the second query.
* - In the first query, we add 1 to every element in the submatrix with the top left corner (1, 1) and bottom right corner (2, 2).
* - In the second query, we add 1 to every element in the submatrix with the top left corner (0, 0) and bottom right corner (1, 1).
*
* Example 2:
* Input: n = 2, queries = [[0,0,1,1]]
* Output: [[1,1],[1,1]]
* Explanation: The diagram above shows the initial matrix and the matrix after the first query.
* - In the first query we add 1 to every element in the matrix.
*
* Constraints:
* • `1 <= n <= 500`
* • `1 <= queries.length <= 10^4`
* • `0 <= row1_i <= row2_i < n`
* • `0 <= col1_i <= col2_i < n`
* 
****************************************/

class Solution {
    // We use a 2D difference array to mark each rectangle update in O(1)
    // by adding +1/-1 at its four corners. After processing all queries,
    // we run horizontal and then vertical prefix sums to convert the diff
    // matrix into the final values. This yields O(n^2 + q) time and O(n^2)
    // space, optimal for performing many submatrix increments efficiently.
    public int[][] rangeAddQueries(int n, int[][] queries) {
        int[][] diff = new int[n][n];
        // Apply 2D difference array rectangle updates
        for (var q : queries) {
            int r0 = q[0], c0 = q[1];
            int r1 = q[2] + 1, c1 = q[3] + 1;
            // +1 at top-left
            diff[r0][c0]++;
            // -1 at top-right+1
            if (c1 < n) diff[r0][c1]--;
            // -1 at bottom-left+1
            if (r1 < n) {
                diff[r1][c0]--;
                // +1 at bottom-right+1
                if (c1 < n) diff[r1][c1]++;
            }
        }
        // Horizontal prefix sums
        for (int r = 0; r < n; r++) {
            for (int c = 1; c < n; c++) {
                diff[r][c] += diff[r][c - 1];
            }
        }
        // Vertical prefix sums
        for (int r = 1; r < n; r++) {
            for (int c = 0; c < n; c++) {
                diff[r][c] += diff[r - 1][c];
            }
        }
        return diff;
    }
}
