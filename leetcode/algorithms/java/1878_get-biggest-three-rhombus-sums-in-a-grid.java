// Source: https://leetcode.com/problems/get-biggest-three-rhombus-sums-in-a-grid/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-15
// At the time of submission:
//   Runtime 40 ms Beats 69.57%
//   Memory 47.20 MB Beats 92.75%

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

import java.util.TreeSet;

class Solution {
    // Iterate every grid cell as the top vertex of a rhombus and expand
    // possible sizes while staying within bounds. For each valid rhombus,
    // compute the border sum by walking its four edges. A TreeSet keeps
    // the three largest distinct sums while discarding smaller values.
    // Time: O(m * n * min(m,n)^2). Space: O(1) aside from result storage.

    public int[] getBiggestThree(int[][] grid) {

        int rows = grid.length;
        int cols = grid[0].length;

        TreeSet<Integer> best = new TreeSet<>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {

                // size 0 rhombus (single cell)
                add(best, grid[r][c]);

                for (int k = 1; ; k++) {

                    int bottom = r + 2 * k;
                    int left = c - k;
                    int right = c + k;

                    if (bottom >= rows || left < 0 || right >= cols)
                        break;

                    int sum = 0;

                    int x = r, y = c;

                    // top -> right
                    for (int i = 0; i < k; i++)
                        sum += grid[x + i][y + i];

                    // right -> bottom
                    for (int i = 0; i < k; i++)
                        sum += grid[x + k + i][y + k - i];

                    // bottom -> left
                    for (int i = 0; i < k; i++)
                        sum += grid[x + 2 * k - i][y - i];

                    // left -> top
                    for (int i = 0; i < k; i++)
                        sum += grid[x + k - i][y - k + i];

                    add(best, sum);
                }
            }
        }

        int[] result = new int[best.size()];
        int i = 0;

        while (!best.isEmpty())
            result[i++] = best.pollLast();

        return result;
    }

    private void add(TreeSet<Integer> set, int val) {

        set.add(val);

        if (set.size() > 3)
            set.pollFirst();
    }
}