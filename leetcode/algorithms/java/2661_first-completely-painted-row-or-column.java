// Source: https://leetcode.com/problems/first-completely-painted-row-or-column/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-20
// At the time of submission:
//   Runtime 15 ms Beats 88.45%
//   Memory 51.12 MB Beats 8.93%

/****************************************
* 
* You are given a 0-indexed integer array `arr`, and an `m x n` integer matrix `mat`. `arr` and `mat` both contain all the integers in the range `[1, m * n]`.
* 
* Go through each index `i` in `arr` starting from index `0` and paint the cell in `mat` containing the integer `arr[i]`.
* 
* Return the smallest index `i` at which either a row or a column will be completely painted in `mat`.
* 
* Example 1:
*   [Image explanation for example 1]
* Input: arr = [1,3,4,2], mat = [[1,4],[2,3]]
* Output: 2
* Explanation: The moves are shown in order, and both the first row and second column of the matrix become fully painted at arr[2].
* 
* Example 2:
*   [Image explanation for example 2]
* Input: arr = [2,8,7,4,1,3,5,6,9], mat = [[3,2,5],[1,4,6],[8,7,9]]
* Output: 3
* Explanation: The second column becomes fully painted at arr[3].
* 
* Constraints:
* • m == mat.length
* • n = mat[i].length
* • arr.length == m * n
* • 1 <= m, n <= 10^5
* • 1 <= m * n <= 10^5
* • 1 <= arr[i], mat[r][c] <= m * n
* • All the integers of `arr` are unique.
* • All the integers of `mat` are unique.
* 
****************************************/

class Solution {
    // Use a mapping of matrix values to positions for quick access
    // Track painted cells in each row and column using count arrays
    // Process the paint order, updating counts and checking for completion
    // Return the first index where a row or column is fully painted
    // Time: O(m * n), Space: O(m * n)
    public int firstCompleteIndex(int[] arr, int[][] mat) {
        int m = mat.length, n = mat[0].length;
        
        // Map values in mat to their (row, col) positions
        int[][] position = new int[m * n + 1][2];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                position[mat[i][j]] = new int[] {i, j};
            }
        }
        
        // Track painted cells in rows and columns
        int[] rowPaintCount = new int[m];
        int[] colPaintCount = new int[n];
        
        // Process arr to paint cells
        for (int i = 0; i < arr.length; i++) {
            int[] pos = position[arr[i]];
            int row = pos[0], col = pos[1];
            
            // Paint the cell and update row/column counts
            if (++rowPaintCount[row] == n || ++colPaintCount[col] == m) {
                return i; // Return index when a row/column is fully painted
            }
        }
        
        // No row or column fully painted (shouldn't happen with given constraints)
        return -1;
    }
}
