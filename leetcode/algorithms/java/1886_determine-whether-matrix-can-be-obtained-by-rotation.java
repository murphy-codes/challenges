// Source: https://leetcode.com/problems/determine-whether-matrix-can-be-obtained-by-rotation/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-22
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 43.58 MB Beats 73.79%

/****************************************
* 
* Given two `n x n` binary matrices `mat` and `target`, return `true` if
* _ it is possible to make `mat` equal to `target` by rotating `mat` in
* _ 90-degree increments, or `false` otherwise.
*
* Example 1:
* Input: mat = [[0,1],[1,0]], target = [[1,0],[0,1]]
* Output: true
* Explanation: We can rotate mat 90 degrees clockwise to make mat equal target.
*
* Example 2:
* Input: mat = [[0,1],[1,1]], target = [[1,0],[0,1]]
* Output: false
* Explanation: It is impossible to make mat equal to target by rotating mat.
*
* Example 3:
* Input: mat = [[0,0,0],[0,1,0],[1,1,1]], target = [[1,1,1],[0,1,0],[0,0,0]]
* Output: true
* Explanation: We can rotate mat 90 degrees clockwise two times to make mat equal target.
*
* Constraints:
* • `n == mat.length == target.length`
* • `n == mat[i].length == target[i].length`
* • `1 <= n <= 10`
* • `mat[i][j] and target[i][j] are either 0 or 1.`
* 
****************************************/

class Solution {
    // Try all 4 possible rotations (0°, 90°, 180°, 270°).
    // After each rotation, compare matrix with target.
    // Rotate 90° via transpose + row reversal in-place.
    // Time: O(n^2) per rotation, total O(n^2) since 4 is constant.
    // Space: O(1) as rotation and comparison use no extra memory.
    public boolean findRotation(int[][] mat, int[][] target) {
        for (int i = 0; i < 4; i++) {
            if (equals(mat, target)) {
                return true;
            }
            rotate90(mat);
        }
        return false;
    }

    private boolean equals(int[][] a, int[][] b) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (a[i][j] != b[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void rotate90(int[][] mat) {
        int n = mat.length;

        // Transpose
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = mat[i][j];
                mat[i][j] = mat[j][i];
                mat[j][i] = temp;
            }
        }

        // Reverse each row
        for (int i = 0; i < n; i++) {
            int left = 0, right = n - 1;
            while (left < right) {
                int temp = mat[i][left];
                mat[i][left] = mat[i][right];
                mat[i][right] = temp;
                left++;
                right--;
            }
        }
    }
}