// Source: https://leetcode.com/problems/find-kth-bit-in-nth-binary-string/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-01
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 42.59 MB Beats 76.92%

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
    // Sn has length (2^n - 1) with a fixed '1' at the center.
    // Left half equals S(n-1); right half is reversed and inverted.
    // If k is in the right half, map it symmetrically and invert.
    // Recursively reduce n until reaching S1.
    // Time: O(n), Space: O(n) due to recursion stack.
    public char findKthBit(int n, int k) {
        // Base case: S1 = "0"
        if (n == 1) {
            return '0';
        }

        int length = (1 << n) - 1;
        int mid = (length / 2) + 1;

        // Middle bit is always '1'
        if (k == mid) {
            return '1';
        }

        // Left half: same as S(n-1)
        if (k < mid) {
            return findKthBit(n - 1, k);
        }

        // Right half: mirrored position with inverted bit
        char bit = findKthBit(n - 1, length - k + 1);
        return bit == '0' ? '1' : '0';
    }
}