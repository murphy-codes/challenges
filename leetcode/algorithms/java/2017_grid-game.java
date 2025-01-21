// Source: https://leetcode.com/problems/grid-game/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-21

/****************************************
* 
* You are given a 0-indexed 2D array `grid` of size `2 x n`, where `grid[r][c]` represents the number of points at position `(r, c)` on the matrix. Two robots are playing a game on this matrix.
* 
* Both robots initially start at `(0, 0)` and want to reach `(1, n-1)`. Each robot may only move to the right (`(r, c)` to `(r, c + 1)`) or down (`(r, c)` to `(r + 1, c)`).
* 
* At the start of the game, the first robot moves from `(0, 0)` to `(1, n-1)`, collecting all the points from the cells on its path. For all cells `(r, c)` traversed on the path, `grid[r][c]` is set to `0`. Then, the second robot moves from `(0, 0)` to `(1, n-1)`, collecting the points on its path. Note that their paths may intersect with one another.
* 
* The first robot wants to minimize the number of points collected by the second robot. In contrast, the second robot wants to maximize the number of points it collects. If both robots play optimally, return the number of points collected by the second robot.
* 
* Example 1:
*   [Image depicting robot 1's path in red, followed by robot 2's path in blue: https://assets.leetcode.com/uploads/2021/09/08/a1.png]
* Input: grid = [[2,5,4],[1,5,1]]
* Output: 4
* Explanation: The optimal path taken by the first robot is shown in red, and the optimal path taken by the second robot is shown in blue.
* The cells visited by the first robot are set to 0.
* The second robot will collect 0 + 0 + 4 + 0 = 4 points.
* 
* Example 2:
*   [Image depicting robot 1's path in red, followed by robot 2's path in blue: https://assets.leetcode.com/uploads/2021/09/08/a2.png]
* Input: grid = [[3,3,1],[8,5,2]]
* Output: 4
* Explanation: The optimal path taken by the first robot is shown in red, and the optimal path taken by the second robot is shown in blue.
* The cells visited by the first robot are set to 0.
* The second robot will collect 0 + 3 + 1 + 0 = 4 points.
* 
* Example 3:
*   [Image depicting robot 1's path in red, followed by robot 2's path in blue: https://assets.leetcode.com/uploads/2021/09/08/a3.png]
* Input: grid = [[1,3,1,15],[1,3,3,1]]
* Output: 7
* Explanation: The optimal path taken by the first robot is shown in red, and the optimal path taken by the second robot is shown in blue.
* The cells visited by the first robot are set to 0.
* The second robot will collect 0 + 1 + 3 + 3 + 0 = 7 points.
* 
* Constraints:
* • grid.length == 2
* • n == grid[r].length
* • 1 <= n <= 5 * 10^4
* • 1 <= grid[r][c] <= 10^5
* 
****************************************/

class Solution {
    // Use prefix sums to compute the total points remaining in the top and bottom rows
    // Simulate Robot 1's decision at each column, minimizing the maximum points Robot 2 can collect
    // Robot 2 collects points from the remaining top or bottom row segments after Robot 1 moves
    // Time: O(n), Space: O(n)
    public long gridGame(int[][] grid) {
        int n = grid[0].length;
        long[] topSum = new long[n];
        long[] bottomSum = new long[n];
        
        // Compute prefix sums for both rows
        topSum[n - 1] = grid[0][n - 1];
        for (int c = n - 2; c >= 0; c--) {
            topSum[c] = topSum[c + 1] + grid[0][c];
        }
        bottomSum[0] = grid[1][0];
        for (int c = 1; c < n; c++) {
            bottomSum[c] = bottomSum[c - 1] + grid[1][c];
        }
        
        // Simulate Robot 1's path and minimize Robot 2's maximum score
        long result = Long.MAX_VALUE;
        for (int c = 0; c < n; c++) {
            // Calculate Robot 2's possible scores
            long scoreTop = (c + 1 < n) ? topSum[c + 1] : 0;
            long scoreBottom = (c - 1 >= 0) ? bottomSum[c - 1] : 0;
            
            // Robot 1 minimizes Robot 2's maximum score
            result = Math.min(result, Math.max(scoreTop, scoreBottom));
        }
        
        return result;
    }
}
