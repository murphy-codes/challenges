// Source: https://leetcode.com/problems/cyclically-rotating-a-grid/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-08
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 47.33 MB Beats 52.24%

/****************************************
* 
* You are given an `m x n` integer matrix `grid​​​`, where m and n are both even
* _ integers, and an integer `k`.
* The matrix is composed of several layers, which is shown in the below image,
* _ where each color is its own layer:
* [Image: https://assets.leetcode.com/uploads/2021/06/10/ringofgrid.png]
* A cyclic rotation of the matrix is done by cyclically rotating each layer
* _ in the matrix. To cyclically rotate a layer once, each element in the
* _ layer will take the place of the adjacent element in the counter-clockwise
* _ direction. An example rotation is shown below:
* [Image: https://assets.leetcode.com/uploads/2021/06/22/explanation_grid.jpg]
* Return the matrix after applying `k` cyclic rotations to it.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/06/19/rod2.png]
* Input: grid = [[40,10],[30,20]], k = 1
* Output: [[10,20],[40,30]]
* Explanation: The figures above represent the grid at every state.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2021/06/10/ringofgrid5.png]
* [Image: https://assets.leetcode.com/uploads/2021/06/10/ringofgrid6.png]
* [Image: https://assets.leetcode.com/uploads/2021/06/10/ringofgrid7.png]
* Input: grid = [[1,2,3,4],[5,6,7,8],[9,10,11,12],[13,14,15,16]], k = 2
* Output: [[3,4,8,12],[2,11,10,16],[1,7,6,15],[5,9,13,14]]
* Explanation: The figures above represent the grid at every state.
*
* Constraints:
* • `m == grid.length`
* • `n == grid[i].length`
* • `2 <= m, n <= 50`
* • Both `m` and `n` are even integers.
* • `1 <= grid[i][j] <= 5000`
* • `1 <= k <= 10^9`
* 
****************************************/

class Solution {
    // Traverse each layer into a reusable buffer in perimeter order.
    // Write values back using modular offsets to simulate rotation.
    // Reusing one layer array avoids repeated allocations per layer.
    // Time: O(m * n), Space: O(m + n)

    private int traverseLayer(
        int[][] grid,
        int[] layerValues,
        int rows,
        int cols,
        int layer,
        boolean writeMode,
        int rotationOffset,
        int layerLength
    ) {

        int top = layer;
        int left = layer;

        int right = cols - layer - 1;
        int bottom = rows - layer - 1;

        int index = 0;

        // Left column
        for (int r = top; r <= bottom; r++) {
            if (writeMode) {
                grid[r][left] =
                    layerValues[(index++ + rotationOffset) % layerLength];
            } else {
                layerValues[index++] = grid[r][left];
            }
        }

        // Bottom row
        for (int c = left + 1; c <= right; c++) {
            if (writeMode) {
                grid[bottom][c] =
                    layerValues[(index++ + rotationOffset) % layerLength];
            } else {
                layerValues[index++] = grid[bottom][c];
            }
        }

        // Right column
        for (int r = bottom - 1; r >= top; r--) {
            if (writeMode) {
                grid[r][right] =
                    layerValues[(index++ + rotationOffset) % layerLength];
            } else {
                layerValues[index++] = grid[r][right];
            }
        }

        // Top row
        for (int c = right - 1; c > left; c--) {
            if (writeMode) {
                grid[top][c] =
                    layerValues[(index++ + rotationOffset) % layerLength];
            } else {
                layerValues[index++] = grid[top][c];
            }
        }

        return index;
    }

    public int[][] rotateGrid(int[][] grid, int k) {

        int rows = grid.length;
        int cols = grid[0].length;

        int[] layerValues = new int[(rows + cols - 2) * 2];

        int layerCount = Math.min(rows, cols) / 2;

        for (int layer = 0; layer < layerCount; layer++) {

            int layerLength =
                traverseLayer(
                    grid,
                    layerValues,
                    rows,
                    cols,
                    layer,
                    false,
                    k,
                    0
                );

            traverseLayer(
                grid,
                layerValues,
                rows,
                cols,
                layer,
                true,
                layerLength - (k % layerLength),
                layerLength
            );
        }

        return grid;
    }
}