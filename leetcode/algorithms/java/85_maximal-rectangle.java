// Source: https://leetcode.com/problems/maximal-rectangle/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-11
// At the time of submission:
//   Runtime 5 ms Beats 90.69%
//   Memory 49.96 MB Beats 28.18%

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

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    // Treat each row as the base of a histogram where heights represent
    // consecutive '1's seen so far in each column. For every row, compute
    // the largest rectangle in the histogram using a monotonic stack.
    // This ensures each column index is pushed and popped once.
    // Time: O(rows * cols), Space: O(cols)
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] heights = new int[cols];
        int maxArea = 0;

        for (int r = 0; r < rows; r++) {
            // Build histogram heights for this row
            for (int c = 0; c < cols; c++) {
                if (matrix[r][c] == '1') {
                    heights[c]++;
                } else {
                    heights[c] = 0;
                }
            }
            // Compute largest rectangle in histogram
            maxArea = Math.max(maxArea, largestRectangleArea(heights));
        }

        return maxArea;
    }

    private int largestRectangleArea(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        int maxArea = 0;

        for (int i = 0; i <= heights.length; i++) {
            int currHeight = (i == heights.length) ? 0 : heights[i];

            while (!stack.isEmpty() && currHeight < heights[stack.peek()]) {
                int h = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, h * width);
            }
            stack.push(i);
        }

        return maxArea;
    }
}
