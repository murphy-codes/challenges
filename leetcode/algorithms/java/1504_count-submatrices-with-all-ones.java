// Source: https://leetcode.com/problems/count-submatrices-with-all-ones/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-20
// At the time of submission:
//   Runtime 5 ms Beats 97.95%
//   Memory 46.32 MB Beats 5.48%

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
    // This solution builds histograms of consecutive 1s row by row.
    // For each row, it computes how many submatrices end at each cell
    // by scanning left and keeping track of the minimum height seen.
    // Time complexity: O(m * n^2), Space complexity: O(n).
    // Works efficiently within given constraints (m,n <= 150).
    public int numSubmat(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        int[] heights = new int[n];
        int count = 0;

        for (int i = 0; i < m; i++) {
            // update heights histogram
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 0) {
                    heights[j] = 0;
                } else {
                    heights[j] += 1;
                }
            }

            // count rectangles ending at row i
            for (int j = 0; j < n; j++) {
                int minHeight = heights[j];
                for (int k = j; k >= 0 && minHeight > 0; k--) {
                    minHeight = Math.min(minHeight, heights[k]);
                    count += minHeight;
                }
            }
        }

        return count;
    }
}
