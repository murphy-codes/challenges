// Source: https://leetcode.com/problems/maximal-rectangle/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-11
// At the time of submission:
//   Runtime 1 ms Beats 99.69%
//   Memory 50.82 MB Beats 5.27%

/****************************************
* 
* Given a `rows x cols` binary matrix filled with` `0's and `1`'s, find the
* _ largest rectangle containing only `1`'s and return its area.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2020/09/14/maximal.jpg]
* Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
* Output: 6
* Explanation: The maximal rectangle is shown in the above picture.
*
* Example 2:
* Input: matrix = [["0"]]
* Output: 0
*
* Example 3:
* Input: matrix = [["1"]]
* Output: 1
*
* Constraints:
* • `rows == matrix.length`
* • `cols == matrix[i].length`
* • `1 <= rows, cols <= 200`
* • `matrix[i][j]` is `'0'` or `'1'`.
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Treat each row as the base of a histogram where heights represent consecutive
    // '1's above each column. For every row, maintain left and right boundaries
    // indicating how far the rectangle can expand horizontally.
    // The area for each column is height * (right - left).
    // Time: O(rows * cols), Space: O(cols)
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int rowCount = matrix.length;
        int colCount = matrix[0].length;

        int[] heights = new int[colCount];
        int[] leftLimits = new int[colCount];
        int[] rightLimits = new int[colCount];
        Arrays.fill(rightLimits, colCount);

        int maxArea = 0;

        for (int row = 0; row < rowCount; row++) {
            int currentLeft = 0;
            int currentRight = colCount;

            updateHeightsAndLeftLimits(matrix[row], heights, leftLimits, currentLeft);
            updateRightLimits(matrix[row], rightLimits, currentRight);
            maxArea = computeMaxArea(heights, leftLimits, rightLimits, maxArea);
        }

        return maxArea;
    }

    // Updates histogram heights and the furthest valid left boundary per column
    private void updateHeightsAndLeftLimits(
            char[] row,
            int[] heights,
            int[] leftLimits,
            int currentLeft
    ) {
        for (int col = 0; col < heights.length; col++) {
            if (row[col] == '1') {
                heights[col]++;
                leftLimits[col] = Math.max(leftLimits[col], currentLeft);
            } else {
                heights[col] = 0;
                leftLimits[col] = 0;
                currentLeft = col + 1;
            }
        }
    }

    // Updates the furthest valid right boundary per column
    private void updateRightLimits(
            char[] row,
            int[] rightLimits,
            int currentRight
    ) {
        for (int col = rightLimits.length - 1; col >= 0; col--) {
            if (row[col] == '1') {
                rightLimits[col] = Math.min(rightLimits[col], currentRight);
            } else {
                rightLimits[col] = currentRight;
                currentRight = col;
            }
        }
    }

    // Computes maximal rectangle area using current heights and boundaries
    private int computeMaxArea(
            int[] heights,
            int[] leftLimits,
            int[] rightLimits,
            int maxArea
    ) {
        for (int col = 0; col < heights.length; col++) {
            int width = rightLimits[col] - leftLimits[col];
            maxArea = Math.max(maxArea, heights[col] * width);
        }
        return maxArea;
    }
}
