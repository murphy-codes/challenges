// Source: https://leetcode.com/problems/count-total-number-of-colored-cells/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-03-04
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 40.30 MB Beats 90.26%

/****************************************
* 
* There exists an infinitely large two-dimensional grid of uncolored unit cells. 
* You are given a positive integer `n`, indicating that you must do the following routine for n minutes:
* • At the first minute, color any arbitrary unit cell blue.
* • Every minute thereafter, color blue every uncolored cell that touches a blue cell.
* Below is a pictorial representation of the state of the grid after minutes 1, 2, and 3.
* [Image: https://assets.leetcode.com/uploads/2023/01/10/example-copy-2.png]
* Return the number of colored cells at the end of `n` minutes.
*
* Example 1:
* Input: n = 1
* Output: 1
* Explanation: After 1 minute, there is only 1 blue cell, so we return 1.
*
* Example 2:
* Input: n = 2
* Output: 5
* Explanation: After 2 minutes, there are 4 colored cells on the boundary and 1 in the center, so we return 5.
*
* Constraints:
* • 1 <= n <= 10^5
* 
****************************************/

class Solution {
    // The total is the initial cell (1) and the sum of the increments, 
    // which are based on 4 times the summation of the first (n-1) integers.
    // This gives the closed-form formula: total = 1 + 2L * n * (n - 1)
    // Time complexity is O(1) as we apply the formula in constant time, 
    // and space complexity is O(1) as no extra memory is used.
    public long coloredCells(int n) {
        return 1 + 2L * n * (n - 1);
    }
}
