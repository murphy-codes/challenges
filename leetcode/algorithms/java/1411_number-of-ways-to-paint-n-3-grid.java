// Source: https://leetcode.com/problems/number-of-ways-to-paint-n-3-grid/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-02
// At the time of submission:
//   Runtime 3 ms Beats 93.85%
//   Memory 42.38 MB Beats 35.38%

/****************************************
* 
* You have a `grid` of size `n x 3` and you want to paint each cell of the grid
* _ with exactly one of the three colors: Red, Yellow, or Green while making
* _ sure that no two adjacent cells have the same color (i.e., no two cells that
* _ share vertical or horizontal sides have the same color).
* Given `n` the number of rows of the grid, return the number of ways you can
* _ paint this `grid`. As the answer may grow large, the answer must be computed
* _ modulo `10^9 + 7`.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2020/03/26/e1.png]
* Input: n = 1
* Output: 12
* Explanation: There are 12 possible way to paint the grid as shown.
*
* Example 2:
* Input: n = 5000
* Output: 30228214
*
* Constraints:
* • `n == grid.length
* • `1 <= n <= 5000
* 
****************************************/

