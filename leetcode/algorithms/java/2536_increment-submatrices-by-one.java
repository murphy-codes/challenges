// Source: https://leetcode.com/problems/maximum-number-of-operations-to-move-ones-to-the-end/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-14
// At the time of submission:
//   Runtime 20 ms Beats 57.47%
//   Memory 83.37 MB Beats 5.43%

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
    // We treat each row as an independent 1D difference array. For every
    // query, we add +1 at col1 and -1 at col2+1 for all affected rows.
    // After processing all queries, each row is prefix-summed to produce
    // its final values. This avoids n^2 updates per query and runs in
    // O(n^2 + q*n) time with O(n^2) space, optimal for the constraints.
    public int[][] rangeAddQueries(int n, int[][] queries) {
        int[][] diff = new int[n][n + 1]; // n+1 so we can safely subtract at col2+1
        // Apply difference increments row by row
        for (int[] q : queries) {
            int r1 = q[0], c1 = q[1], r2 = q[2], c2 = q[3];
            for (int r = r1; r <= r2; r++) {
                diff[r][c1] += 1;
                diff[r][c2 + 1] -= 1; // safe because diff has n+1 columns
            }
        }
        // Build final matrix using prefix sums per row
        int[][] result = new int[n][n];
        for (int r = 0; r < n; r++) {
            int running = 0;
            for (int c = 0; c < n; c++) {
                running += diff[r][c];
                result[r][c] = running;
            }
        }
        return result;
    }
}
