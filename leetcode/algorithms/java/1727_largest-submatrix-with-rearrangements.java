// Source: https://leetcode.com/problems/largest-submatrix-with-rearrangements/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-18
// At the time of submission:
//   Runtime 13 ms Beats 77.93%
//   Memory 114.22 MB Beats 39.62%

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

import java.util.Arrays;

class Solution {
    // Treat each row as a histogram of consecutive 1s heights. Since columns
    // can be rearranged arbitrarily, we sort the heights in descending order
    // for each row and compute the best rectangle using height * width.
    // This ensures we always use the tallest columns first.
    // Time: O(m * n log n), Space: O(n).
    public int largestSubmatrix(int[][] matrix) {

        int m = matrix.length;
        int n = matrix[0].length;

        int[] height = new int[n];
        int maxArea = 0;

        for (int i = 0; i < m; i++) {

            // Build heights
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1)
                    height[j] += 1;
                else
                    height[j] = 0;
            }

            // Copy and sort descending
            int[] sorted = height.clone();
            Arrays.sort(sorted);

            // Try all widths
            for (int j = 0; j < n; j++) {
                int h = sorted[n - 1 - j]; // largest first
                int width = j + 1;
                maxArea = Math.max(maxArea, h * width);
            }
        }

        return maxArea;
    }
}