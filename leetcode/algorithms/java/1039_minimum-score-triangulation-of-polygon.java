// Source: https://leetcode.com/problems/minimum-score-triangulation-of-polygon/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-29
// At the time of submission:
//   Runtime 5 ms Beats 37.38%
//   Memory 41.06 MB Beats 94.70%

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
    // Dynamic Programming on intervals: dp[i][j] is the minimum triangulation
    // cost of the polygon slice between vertices i and j. For each middle
    // vertex k between i and j, we form triangle (i,k,j) and add costs of
    // sub-polygons (i..k) and (k..j). Bottom-up filling ensures correctness.
    // Time Complexity: O(n^3), Space Complexity: O(n^2), with n ≤ 50.
    public int minScoreTriangulation(int[] values) {
        int n = values.length;
        int[][] dp = new int[n][n];

        // dp[i][j] = min triangulation cost for sub-polygon from i to j
        for (int len = 2; len < n; len++) { // interval length
            for (int i = 0; i + len < n; i++) {
                int j = i + len;
                dp[i][j] = Integer.MAX_VALUE;
                // Try all possible middle points k
                for (int k = i + 1; k < j; k++) {
                    int cost = values[i] * values[j] * values[k] 
                             + dp[i][k] + dp[k][j];
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }

        return dp[0][n - 1];
    }
}
