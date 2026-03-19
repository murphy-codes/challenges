// Source: https://leetcode.com/problems/largest-submatrix-with-rearrangements/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-18
// At the time of submission:
//   Runtime 5 ms Beats 100.00%
//   Memory 114.19 MB Beats 43.27%

/****************************************
* 
* You are given a binary matrix `matrix` of size `m x n`, and you are allowed
* _ to rearrange the columns of the `matrix` in any order.
* Return the area of the largest submatrix within `matrix` where every element
* _ of the submatrix is `1` after reordering the columns optimally.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2020/12/29/screenshot-2020-12-30-at-40536-pm.png]
* Input: matrix = [[0,0,1],[1,1,1],[1,0,1]]
* Output: 4
* Explanation: You can rearrange the columns as shown above.
* The largest submatrix of 1s, in bold, has an area of 4.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2020/12/29/screenshot-2020-12-30-at-40852-pm.png]
* Input: matrix = [[1,0,1,0,1]]
* Output: 3
* Explanation: You can rearrange the columns as shown above.
* The largest submatrix of 1s, in bold, has an area of 3.
*
* Example 3:
* Input: matrix = [[1,1,0],[1,0,1]]
* Output: 2
* Explanation: Notice that you must rearrange entire columns, and there is no way to make a submatrix of 1s larger than an area of 2.
*
* Constraints:
* • `m == matrix.length`
* • `n == matrix[i].length`
* • `1 <= m * n <= 10^5`
* • `matrix[i][j]` is either `0` or `1`.
* 
****************************************/

class Solution {
    // Convert matrix into heights of consecutive 1s per column (in-place).
    // For each row, use counting sort (freq array) instead of sorting to
    // process heights in descending order and compute max area greedily.
    // Width expands as we include more columns with decreasing heights.
    // Time: O(m * n). Space: O(n) for frequency array.

    // Frequency array used for counting sort of heights
    static int[] freq = new int[100000];

    public int largestSubmatrix(int[][] matrix) {

        int rows = matrix.length;
        int cols = matrix[0].length;

        int maxArea = 0;

        // Handle first row (height = 1s only)
        for (int val : matrix[0]) {
            if (val == 1) maxArea++;
        }

        if (rows == 1) return maxArea;

        // Special case: single column
        if (cols == 1) {
            for (int i = 1; i < rows; i++) {
                int val = matrix[i][0];

                // If val == 1, add previous height
                val += (-val & matrix[i - 1][0]);

                matrix[i][0] = val;
                maxArea = Math.max(maxArea, val);
            }
            return maxArea;
        }

        // Process each row
        for (int i = 1; i < rows; i++) {

            // Build heights (in-place)
            for (int j = 0; j < cols; j++) {
                int val = matrix[i][j];
                val += (-val & matrix[i - 1][j]);
                matrix[i][j] = val;
            }

            // Find min and max heights in this row
            int minHeight = i + 1;
            int maxHeight = 0;

            for (int h : matrix[i]) {
                minHeight = Math.min(minHeight, h);
                maxHeight = Math.max(maxHeight, h);
            }

            // Reset frequency array for used range
            for (int k = 0; k <= maxHeight - minHeight; k++) {
                freq[k] = 0;
            }

            // Count frequencies of heights
            for (int h : matrix[i]) {
                freq[h - minHeight]++;
            }

            int width = 0;

            // Traverse heights from largest to smallest
            for (int h = maxHeight - minHeight; width < cols; h--) {
                if (freq[h] > 0) {
                    width += freq[h];
                    maxArea = Math.max(maxArea, width * (h + minHeight));
                }
            }
        }

        return maxArea;
    }
}