// Source: https://leetcode.com/problems/matrix-similarity-after-cyclic-shifts/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-25
// At the time of submission:
//   Runtime 1 ms Beats 99.47%
//   Memory 47.21 MB Beats 6.40%

/****************************************
* 
* You are given an `m x n` integer matrix `mat` and an integer `k`.
* _ The matrix rows are 0-indexed.
* The following proccess happens `k` times:
* • Even-indexed rows (0, 2, 4, ...) are cyclically shifted to the left.
* __ [Image: https://assets.leetcode.com/uploads/2024/05/19/lshift.jpg]
* • Odd-indexed rows (1, 3, 5, ...) are cyclically shifted to the right.
* __ [Image: https://assets.leetcode.com/uploads/2024/05/19/rshift-stlone.jpg]
* Return `true` if the final modified matrix after `k` steps is identical to
* _ the original matrix, and `false` otherwise.
*
* Example 1:
* Input: mat = [[1,2,3],[4,5,6],[7,8,9]], k = 4
* Output: false
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2024/05/19/t1-2.jpg]
* In each step left shift is applied to rows 0 and 2 (even indices), and right shift to row 1 (odd index).
*
* Example 2:
* Input: mat = [[1,2,1,2],[5,5,5,5],[6,3,6,3]], k = 2
* Output: true
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2024/05/19/t1-3.jpg]
*
* Example 3:
* Input: mat = [[2,2],[2,2]], k = 3
* Output: true
* Explanation:
* As all the values are equal in the matrix, even after performing cyclic shifts the matrix will remain the same.
*
* Constraints:
* • `1 <= mat.length <= 25`
* • `1 <= mat[i].length <= 25`
* • `1 <= mat[i][j] <= 25`
* • `1 <= k <= 50`
* 
****************************************/

class Solution {
    // Each row is cyclically shifted k times; reduce to k % n shifts.
    // Even rows shift left, odd rows shift right.
    // For each position, compute its source index after shifts.
    // Compare shifted value with original without modifying matrix.
    // Time: O(m * n); Space: O(1).
    public boolean areSimilar(int[][] mat, int k) {
        int m = mat.length;
        int n = mat[0].length;

        int shift = k % n;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                int sourceCol;

                if (i % 2 == 0) {
                    // Even row → left shift
                    sourceCol = (j + shift) % n;
                } else {
                    // Odd row → right shift
                    sourceCol = (j - shift + n) % n;
                }

                if (mat[i][j] != mat[i][sourceCol]) {
                    return false;
                }
            }
        }

        return true;
    }
}