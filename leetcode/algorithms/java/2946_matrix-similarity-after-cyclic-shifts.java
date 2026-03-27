// Source: https://leetcode.com/problems/matrix-similarity-after-cyclic-shifts/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-25
// At the time of submission:
//   Runtime 1 ms Beats 99.47%
//   Memory 46.84 MB Beats 82.13%

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
    // Reduce k using modulo since shifts are cyclic over row length.
    // A matrix remains unchanged only if each row equals its shifted form.
    // For each cell, compare with its k-shifted column index.
    // If any mismatch occurs, the matrix cannot return to original.
    // Time: O(m * n); Space: O(1).
    public boolean areSimilar(int[][] mat, int k) {
        int rows = mat.length;
        int cols = mat[0].length;

        // Reduce shifts using modulo
        k = k % cols;

        // No shift → always identical
        if (k == 0) return true;

        // Check each row for cyclic invariance
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                // Compare current element with its shifted position
                int shiftedCol = (j - k + cols) % cols;

                if (mat[i][j] != mat[i][shiftedCol]) {
                    return false;
                }
            }
        }

        return true;
    }
}