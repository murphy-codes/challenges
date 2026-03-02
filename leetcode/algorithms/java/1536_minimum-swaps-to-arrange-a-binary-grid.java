// Source: https://leetcode.com/problems/minimum-swaps-to-arrange-a-binary-grid/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-01
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 49.86 MB Beats 33.96%

/****************************************
* 
* Given an `n x n` binary grid, in one step you can choose two adjacent rows of
* _ the grid and swap them.
* A grid is said to be valid if all the cells above the main diagonal are zeros.
* Return the minimum number of steps needed to make the grid valid, or -1 if
* _ the grid cannot be valid.
* The main diagonal of a grid is the diagonal that starts at cell `(1, 1)`
* _ and ends at cell `(n, n)`.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2020/07/28/fw.jpg]
* Input: grid = [[0,0,1],[1,1,0],[1,0,0]]
* Output: 3
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2020/07/16/e2.jpg]
* Input: grid = [[0,1,1,0],[0,1,1,0],[0,1,1,0],[0,1,1,0]]
* Output: -1
* Explanation: All rows are similar, swaps have no effect on the grid.
*
* Example 3:
* [Image: https://assets.leetcode.com/uploads/2020/07/16/e3.jpg]
* Input: grid = [[1,0,0],[1,1,0],[1,1,1]]
* Output: 0
*
* Constraints:
* • `n == grid.length == grid[i].length`
* • `1 <= n <= 200`
* • `grid[i][j]` is either `0` or `1`
* 
****************************************/

class Solution {
    // For each row, count how many trailing zeros it has.
    // Row i must have at least (n - 1 - i) trailing zeros to be valid.
    // Greedily select the nearest valid row below and bubble it upward.
    // Each upward move costs one swap; if none exists, return -1.
    // Time: O(n^2), Space: O(n).
  public int minSwaps(int[][] grid) {
    final int n = grid.length;
    int swaps = 0;

    // trailingZeros[i] = number of zeros at the end of row i
    int[] trailingZeros = new int[n];

    for (int row = 0; row < n; ++row)
      trailingZeros[row] = countTrailingZeros(grid[row]);

    for (int row = 0; row < n; ++row) {
      final int requiredZeros = n - 1 - row;

      // Find first row >= row with enough trailing zeros
      final int candidate =
          findRowWithEnoughZeros(trailingZeros, row, requiredZeros);

      if (candidate == -1)
        return -1;

      // Bubble candidate row upward using adjacent swaps
      for (int k = candidate; k > row; --k)
        trailingZeros[k] = trailingZeros[k - 1];

      swaps += candidate - row;
    }

    return swaps;
  }

  private int countTrailingZeros(int[] row) {
    for (int col = row.length - 1; col >= 0; --col)
      if (row[col] == 1)
        return row.length - col - 1;
    return row.length;
  }

  private int findRowWithEnoughZeros(
      int[] trailingZeros, int start, int requiredZeros) {
    for (int i = start; i < trailingZeros.length; ++i)
      if (trailingZeros[i] >= requiredZeros)
        return i;
    return -1;
  }
}