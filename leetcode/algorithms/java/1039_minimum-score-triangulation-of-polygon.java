// Source: https://leetcode.com/problems/minimum-score-triangulation-of-polygon/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-29
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 41.48 MB Beats 38.32%

/****************************************
* 
* You have a convex n-sided polygon where each vertex has an integer value.
* _ You are given an integer array `values` where `values[i]` is the value of
* _ the `i^th` vertex in clockwise order.
* Polygon triangulation is a process where you divide a polygon into a set of
* _ triangles and the vertices of each triangle must also be vertices of the
* _ original polygon. Note that no other shapes other than triangles are
* _ allowed in the division. This process will result in `n - 2` triangles.
* You will triangulate the polygon. For each triangle, the weight of that
* _ triangle is the product of the values at its vertices. The total score of
* _ the triangulation is the sum of these weights over all `n - 2` triangles.
* Return the minimum possible score that you can achieve with some triangulation
* _ of the polygon.
*
* Example 1:
* Input: values = [1,2,3]
* Output: 6
* Explanation: The polygon is already triangulated, and the score of the only triangle is 6.
*
* Example 2:
* Input: values = [3,7,4,5]
* Output: 144
* Explanation: There are two triangulations, with possible scores: 3*7*5 + 4*5*7 = 245, or 3*4*5 + 3*4*7 = 144.
* The minimum score is 144.
*
* Example 3:
* Input: values = [1,3,1,4,1,5]
* Output: 13
* Explanation: The minimum score triangulation is 1*1*3 + 1*1*4 + 1*1*5 + 1*1*1 = 13.
*
* Constraints:
* • n == values.length
* • 3 <= n <= 50
* • 1 <= values[i] <= 100
* 
****************************************/

class Solution {
    // This uses recursive DP with memoization to find the minimum
    // triangulation score. For each pair of vertices (i, j), we try all
    // possible third vertices k to form triangle (i, k, j). The triangle's
    // cost is added to the optimal sub-solutions on each side. Results are
    // memoized in dp[i][j]. Time complexity: O(n^3). Space: O(n^2).

    private int[][] dp;

    public int minScoreTriangulation(int[] values) {
        int n = values.length;
        dp = new int[n][n];
        return solve(values, 0, n - 1);
    }

    private int solve(int[] values, int left, int right) {
        // Base case: fewer than 3 vertices -> no triangle possible
        if (right - left < 2) return 0;

        // Return memoized result if already computed
        if (dp[left][right] != 0) return dp[left][right];

        int minScore = Integer.MAX_VALUE;

        // Try every possible middle vertex 'k' between 'left' and 'right'
        for (int k = left + 1; k < right; k++) {
            int cost = values[left] * values[k] * values[right]
                     + solve(values, left, k)
                     + solve(values, k, right);
            minScore = Math.min(minScore, cost);
        }

        // Memoize and return result
        return dp[left][right] = minScore;
    }
}