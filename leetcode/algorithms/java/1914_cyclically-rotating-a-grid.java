// Source: https://leetcode.com/problems/cyclically-rotating-a-grid/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-09
// At the time of submission:
//   Runtime 2 ms Beats 88.06%
//   Memory 47.26 MB Beats 70.15%

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
    // Extract each layer into a 1D array and rotate by k % size.
    // Traverse layer borders in clockwise order for read/write steps.
    // Writing uses shifted indices to simulate counter-clockwise rotation.
    // Time: O(m * n), Space: O(m + n)
    public int[][] rotateGrid(int[][] grid, int k) {
        int rows = grid.length;
        int cols = grid[0].length;

        int layers = Math.min(rows, cols) / 2;

        for (int layer = 0; layer < layers; layer++) {

            int top = layer;
            int left = layer;
            int bottom = rows - 1 - layer;
            int right = cols - 1 - layer;

            int perimeter =
                2 * (bottom - top + right - left);

            int[] values = new int[perimeter];
            int index = 0;

            // Top row
            for (int c = left; c < right; c++) {
                values[index++] = grid[top][c];
            }

            // Right column
            for (int r = top; r < bottom; r++) {
                values[index++] = grid[r][right];
            }

            // Bottom row
            for (int c = right; c > left; c--) {
                values[index++] = grid[bottom][c];
            }

            // Left column
            for (int r = bottom; r > top; r--) {
                values[index++] = grid[r][left];
            }

            int shift = k % perimeter;
            index = 0;

            // Top row
            for (int c = left; c < right; c++) {
                grid[top][c] =
                    values[(index + shift) % perimeter];
                index++;
            }

            // Right column
            for (int r = top; r < bottom; r++) {
                grid[r][right] =
                    values[(index + shift) % perimeter];
                index++;
            }

            // Bottom row
            for (int c = right; c > left; c--) {
                grid[bottom][c] =
                    values[(index + shift) % perimeter];
                index++;
            }

            // Left column
            for (int r = bottom; r > top; r--) {
                grid[r][left] =
                    values[(index + shift) % perimeter];
                index++;
            }
        }

        return grid;
    }
}