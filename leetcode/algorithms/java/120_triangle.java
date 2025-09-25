// Source: https://leetcode.com/problems/triangle/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-23
// At the time of submission:
//   Runtime 1 ms Beats 99.85%
//   Memory 45.72 MB Beats 20.70%

/****************************************
* 
* Given a `triangle` array, return the minimum path sum from top to bottom.
*
* For each step, you may move to an adjacent number of the row below. More
* _ formally, if you are on index `i` on the current row, you may move to either
* _ index `i` or index `i + 1` on the next row.
*
* Example 1:
* Input: triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
* Output: 11
* Explanation: The triangle looks like:
*    2
*   3 4
*  6 5 7
* 4 1 8 3
* The minimum path sum from top to bottom is 2 + 3 + 5 + 1 = 11 (underlined above).
*
* Example 2:
* Input: triangle = [[-10]]
* Output: -10
*
* Constraints:
* • `1 <= triangle.length <= 200`
* • `triangle[0].length == 1`
* • `triangle[i].length == triangle[i - 1].length + 1`
* • `-10^4 <= triangle[i][j] <= 10^4`
*
* Follow up: Could you do this using only `O(n)` extra space, where `n` is
* _ the total number of rows in the triangle?
* 
****************************************/

class Solution {
    // This solution uses top-down recursion with memoization. From each
    // position, we choose the smaller path between moving down or down-right.
    // Results are cached in a 2D array to avoid recomputation of overlapping
    // subproblems. Time complexity: O(n^2), visiting each cell once. Space
    // complexity: O(n^2) for memo plus recursion stack of depth n.

    int rows;
    Integer[][] memo;

    public int minimumTotal(List<List<Integer>> triangle) {
        rows = triangle.size();
        memo = new Integer[rows][rows];
        return dfs(triangle, 0, 0);
    }

    // Recursive helper: minimum path sum from (row, col) to bottom
    private int dfs(List<List<Integer>> triangle, int row, int col) {
        if (row == rows) {
            return 0; // reached beyond last row
        }
        if (memo[row][col] != null) {
            return memo[row][col]; // return cached result
        }

        int leftPath = dfs(triangle, row + 1, col);
        int rightPath = dfs(triangle, row + 1, col + 1);

        memo[row][col] = triangle.get(row).get(col) + Math.min(leftPath, rightPath);
        return memo[row][col];
    }
}
