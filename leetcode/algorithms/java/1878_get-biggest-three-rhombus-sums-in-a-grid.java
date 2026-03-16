// Source: https://leetcode.com/problems/get-biggest-three-rhombus-sums-in-a-grid/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-15
// At the time of submission:
//   Runtime 15 ms Beats 100.00%
//   Memory 46.49 MB Beats 100.00%

/****************************************
* 
* You are given an `m x n` integer matrix `grid​​​`.
* A rhombus sum is the sum of the elements that form the border of a regular
* _ rhombus shape in `grid`​​​. The rhombus must have the shape of a square
* _ rotated 45 degrees with each of the corners centered in a grid cell.
* _ Below is an image of four valid rhombus shapes with the corresponding
* _ colored cells that should be included in each rhombus sum:
* [Image: https://assets.leetcode.com/uploads/2021/04/23/pc73-q4-desc-2.png]
* Note that the rhombus can have an area of 0, which is depicted by the purple
* _ rhombus in the bottom right corner.
* Return the biggest three distinct rhombus sums in the `grid` in descending
* _ order. If there are less than three distinct values, return all of them.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/04/23/pc73-q4-ex1.png]
* Input: grid = [[3,4,5,1,3],[3,3,4,2,3],[20,30,200,40,10],[1,5,5,4,1],[4,3,2,2,5]]
* Output: [228,216,211]
* Explanation: The rhombus shapes for the three biggest distinct rhombus sums are depicted above.
* - Blue: 20 + 3 + 200 + 5 = 228
* - Red: 200 + 2 + 10 + 4 = 216
* - Green: 5 + 200 + 4 + 2 = 211
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2021/04/23/pc73-q4-ex2.png]
* Input: grid = [[1,2,3],[4,5,6],[7,8,9]]
* Output: [20,9,8]
* Explanation: The rhombus shapes for the three biggest distinct rhombus sums are depicted above.
* - Blue: 4 + 2 + 6 + 8 = 20
* - Red: 9 (area 0 rhombus in the bottom right corner)
* - Green: 8 (area 0 rhombus in the bottom middle)
*
* Example 3:
* Input: grid = [[7,7,7]]
* Output: [7]
* Explanation: All three possible rhombus sums are the same, so return [7].
*
* Constraints:
* • `m == grid.length`
* • `n == grid[i].length`
* • `1 <= m, n <= 50`
* • `1 <= grid[i][j] <= 10^5`
* 
****************************************/

class Solution {
    // Enumerate all rhombus borders centered at each grid cell. Radius 0
    // rhombuses are single cells; larger rhombuses sum their four edges.
    // Instead of using a TreeSet, maintain the three largest distinct sums
    // manually with constant-time comparisons. This avoids extra overhead.
    // Time: O(m * n * min(m,n)^2). Space: O(1).

    // Track the three largest distinct rhombus sums
    private int largest = -1;
    private int secondLargest = -1;
    private int thirdLargest = -1;

    public int[] getBiggestThree(int[][] grid) {

        int rows = grid.length;
        int cols = grid[0].length;

        // Radius 0 rhombus (single cell)
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                add(grid[r][c]);
            }
        }

        // Enumerate rhombus borders of radius k >= 1
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {

                for (int k = 1;
                     r - k >= 0 && r + k < rows &&
                     c - k >= 0 && c + k < cols;
                     k++) {

                    int sum = 0;

                    // top -> right edge
                    for (int d = 0; d < k; d++)
                        sum += grid[r - k + d][c + d];

                    // right -> bottom edge
                    for (int d = 0; d < k; d++)
                        sum += grid[r + d][c + k - d];

                    // bottom -> left edge
                    for (int d = 0; d < k; d++)
                        sum += grid[r + k - d][c - d];

                    // left -> top edge
                    for (int d = 0; d < k; d++)
                        sum += grid[r - d][c - k + d];

                    add(sum);
                }
            }
        }

        if (secondLargest == -1)
            return new int[]{largest};

        if (thirdLargest == -1)
            return new int[]{largest, secondLargest};

        return new int[]{largest, secondLargest, thirdLargest};
    }

    // Maintains the top three distinct values
    private void add(int val) {

        if (val == largest || val == secondLargest || val == thirdLargest)
            return;

        if (val > largest) {
            thirdLargest = secondLargest;
            secondLargest = largest;
            largest = val;
        }
        else if (val > secondLargest) {
            thirdLargest = secondLargest;
            secondLargest = val;
        }
        else if (val > thirdLargest) {
            thirdLargest = val;
        }
    }
}