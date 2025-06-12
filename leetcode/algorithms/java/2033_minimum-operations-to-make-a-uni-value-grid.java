// Source: https://leetcode.com/problems/minimum-operations-to-make-a-uni-value-grid/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-03-25
// At the time of submission:
//   Runtime 26 ms Beats 97.90%
//   Memory 77.75 MB Beats 35.18%

/****************************************
* 
* You are given a 2D integer `grid` of size `m x n` and an integer `x`.
* In one operation, you can add `x` to or subtract `x` from any element in the grid.
* A uni-value grid is a grid where all the elements of it are equal.
* Return the minimum number of operations to make the grid uni-value.
* If it is not possible, return `-1`.
*
* Example 1:
* [image: https://assets.leetcode.com/uploads/2021/09/21/gridtxt.png]
* Input: grid = [[2,4],[6,8]], x = 2
* Output: 4
* Explanation: We can make every element equal to 4 by doing the following:
* - Add x to 2 once.
* - Subtract x from 6 once.
* - Subtract x from 8 twice.
* A total of 4 operations were used.
*
* Example 2:
* [image: https://assets.leetcode.com/uploads/2021/09/21/gridtxt-1.png]
* Input: grid = [[1,5],[2,3]], x = 1
* Output: 5
* Explanation: We can make every element equal to 3.
*
* Example 3:
* [image: https://assets.leetcode.com/uploads/2021/09/21/gridtxt-2.png]
* Input: grid = [[1,2],[3,4]], x = 2
* Output: -1
* Explanation: It is impossible to make every element equal.
*
* Constraints:
* • m == grid.length
* • n == grid[i].length
* • 1 <= m, n <= 10^5
* • 1 <= m * n <= 10^5
* • 1 <= x, grid[i][j] <= 10^4
* 
****************************************/

import java.util.Arrays;

class Solution {
    // If any value can't be made equal using x, return -1 (O(m*n)).
    // Flatten the grid into a sorted 1D array (O(m*n log(m*n))).
    // Find the median and compute min operations to convert all elements (O(m*n)).
    // Overall complexity: O(m*n log(m*n)), optimal for large inputs.
    public int minOperations(int[][] grid, int x) {
        int m = grid.length, n = grid[0].length;
        int size = m * n;
        int[] arr = new int[size];

        // Flatten the grid into a 1D array
        int index = 0;
        for (int[] row : grid) {
            for (int num : row) {
                if ((num - grid[0][0]) % x != 0) return -1;
                arr[index++] = num;
            }
        }

        // Sort the array to find the median
        Arrays.sort(arr);
        int median = arr[size / 2]; // Median minimizes operations

        // Compute total operations to convert all elements to the median
        int operations = 0;
        for (int num : arr) {
            operations += Math.abs(num - median) / x;
        }

        return operations;
    }
}
