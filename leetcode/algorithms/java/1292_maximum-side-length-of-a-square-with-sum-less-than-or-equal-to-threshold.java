// Source: https://leetcode.com/problems/maximum-side-length-of-a-square-with-sum-less-than-or-equal-to-threshold/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-17
// At the time of submission:
//   Runtime 7 ms Beats 83.33%
//   Memory 57.61 MB Beats 59.60%

/****************************************
* 
* Given a `m x n` matrix `mat` and an integer `threshold`, return the maximum
* _ side-length of a square with a sum less than or equal to `threshold` or
* _ return `0` if there is no such square.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2019/12/05/e1.png]
* Input: mat = [[1,1,3,2,4,3,2],[1,1,3,2,4,3,2],[1,1,3,2,4,3,2]], threshold = 4
* Output: 2
* Explanation: The maximum side length of square with sum less than 4 is 2 as shown.
*
* Example 2:
* Input: mat = [[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2]], threshold = 1
* Output: 0
*
* Constraints:
* • `m == mat.length`
* • `n == mat[i].length
* • `1 <= m, n <= 300`
* • `0 <= mat[i][j] <= 10^4`
* • `0 <= threshold <= 10^5`
* 
****************************************/

class Solution {
    // Uses a 2D prefix sum to compute square sums in constant time.
    // Binary searches on side length since validity is monotonic.
    // For each size, checks all possible k x k squares efficiently.
    // Time Complexity: O(m * n * log(min(m, n)))
    // Space Complexity: O(m * n)
    public int maxSideLength(int[][] mat, int threshold) {
        int rows = mat.length;
        int cols = mat[0].length;

        // Build 2D prefix sum array
        int[][] ps = new int[rows + 1][cols + 1];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                ps[r + 1][c + 1] =
                    ps[r][c + 1] +
                    ps[r + 1][c] -
                    ps[r][c] +
                    mat[r][c];
            }
        }

        int left = 1;
        int right = Math.min(rows, cols);
        int answer = 0;

        // Binary search on side length
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (existsSquare(ps, rows, cols, mid, threshold)) {
                answer = mid;
                left = mid + 1;   // try larger square
            } else {
                right = mid - 1;  // try smaller square
            }
        }

        return answer;
    }

    // Checks whether any k x k square has sum <= threshold
    private boolean existsSquare(
        int[][] ps,
        int rows,
        int cols,
        int size,
        int threshold
    ) {
        for (int r = size; r <= rows; r++) {
            for (int c = size; c <= cols; c++) {
                int sum =
                    ps[r][c] -
                    ps[r - size][c] -
                    ps[r][c - size] +
                    ps[r - size][c - size];

                if (sum <= threshold) {
                    return true;
                }
            }
        }
        return false;
    }
}
