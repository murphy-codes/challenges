// Source: https://leetcode.com/problems/minimum-swaps-to-arrange-a-binary-grid/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-01
// At the time of submission:
//   Runtime 3 ms Beats 22.64%
//   Memory 49.60 MB Beats 77.36%

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
    // For each row, record the rightmost position of a '1'.
    // A row at index i is valid only if its rightmost '1' is <= i.
    // Greedily move the closest valid row upward using adjacent swaps.
    // Each swap counts toward the answer; if no row fits, return -1.
    // Time complexity: O(n^2); Space complexity: O(n).
    public int minSwaps(int[][] grid) {
        int n = grid.length;
        int[] rightMost = new int[n];

        // Step 1: find the rightmost '1' in each row
        for (int i = 0; i < n; i++) {
            rightMost[i] = -1;
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    rightMost[i] = j;
                }
            }
        }

        int swaps = 0;

        // Step 2: greedy row placement
        for (int i = 0; i < n; i++) {
            int target = i;

            // Find the first row below that satisfies rightMost[row] <= i
            while (target < n && rightMost[target] > i) {
                target++;
            }

            // No valid row found → impossible
            if (target == n) return -1;

            // Step 3: bubble the row up using adjacent swaps
            while (target > i) {
                int tmp = rightMost[target];
                rightMost[target] = rightMost[target - 1];
                rightMost[target - 1] = tmp;
                swaps++;
                target--;
            }
        }

        return swaps;
    }
}