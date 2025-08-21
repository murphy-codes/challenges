// Source: https://leetcode.com/problems/count-submatrices-with-all-ones/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-20
// At the time of submission:
//   Runtime 3 ms Beats 100.00%
//   Memory 45.16 MB Beats 87.67%

/****************************************
* 
* Given an `m x n` binary matrix `mat`,
* _ return the number of submatrices that have all ones.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/10/27/ones1-grid.jpg]
* Input: mat = [[1,0,1],[1,1,0],[1,1,0]]
* Output: 13
* Explanation:
* There are 6 rectangles of side 1x1.
* There are 2 rectangles of side 1x2.
* There are 3 rectangles of side 2x1.
* There is 1 rectangle of side 2x2.
* There is 1 rectangle of side 3x1.
* Total number of rectangles = 6 + 2 + 3 + 1 + 1 = 13.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2021/10/27/ones2-grid.jpg]
* Input: mat = [[0,1,1,0],[0,1,1,1],[1,1,1,0]]
* Output: 24
* Explanation:
* There are 8 rectangles of side 1x1.
* There are 5 rectangles of side 1x2.
* There are 2 rectangles of side 1x3.
* There are 4 rectangles of side 2x1.
* There are 2 rectangles of side 2x2.
* There are 2 rectangles of side 3x1.
* There is 1 rectangle of side 3x2.
* Total number of rectangles = 8 + 5 + 2 + 4 + 2 + 2 + 1 = 24.
*
* Constraints:
* • 1 <= m, n <= 150
* • `mat[i][j]` is either `0` or `1`.
* 
****************************************/

class Solution {
    // This solution builds histogram heights for each row, then uses a
    // monotonic stack to efficiently count submatrices of all ones ending
    // at each column. For each row, rectangles are counted in O(cols).
    // Overall time complexity is O(rows * cols). Space complexity is O(cols).

    public int numSubmat(int[][] mat) {
        int rows = mat.length;
        int cols = mat[0].length;

        int totalSubmatrices = 0;
        int[] heights = new int[cols]; // histogram heights for each column

        for (int i = 0; i < rows; i++) {
            // Update histogram heights for current row
            for (int j = 0; j < cols; j++) {
                heights[j] = (mat[i][j] == 0) ? 0 : heights[j] + 1;
            }

            // Count rectangles in this histogram row
            totalSubmatrices += countRectangles(heights);
        }

        return totalSubmatrices;
    }

    private int countRectangles(int[] heights) {
        int cols = heights.length;
        int count = 0;

        int[] monoStack = new int[cols]; // stack of indices
        int stackTop = -1;
        int[] rectCountEndingHere = new int[cols]; // #rectangles ending at column j

        for (int j = 0; j < cols; j++) {
            // Maintain increasing stack (remove taller or equal heights)
            while (stackTop >= 0 && heights[monoStack[stackTop]] >= heights[j]) {
                stackTop--;
            }

            if (stackTop == -1) {
                // If stack is empty, all rectangles extend from col 0 to j
                rectCountEndingHere[j] = heights[j] * (j + 1);
            } else {
                // Otherwise, extend rectangles from stackTop+1 to j
                rectCountEndingHere[j] = rectCountEndingHere[monoStack[stackTop]]
                                       + heights[j] * (j - monoStack[stackTop]);
            }

            count += rectCountEndingHere[j];
            monoStack[++stackTop] = j; // push current index
        }

        return count;
    }
}

