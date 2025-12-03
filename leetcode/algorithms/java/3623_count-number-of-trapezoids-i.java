// Source: https://leetcode.com/problems/count-number-of-trapezoids-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-02
// At the time of submission:
//   Runtime 33 ms Beats 96.02%
//   Memory 207.34 MB Beats 10.95%

/****************************************
* 
* You are given a 2D integer array `points`, where `points[i] = [x_i, y_i]`
* _ represents the coordinates of the `i^th` point on the Cartesian plane.
* A horizontal trapezoid is a convex quadrilateral with at least one pair of
* _ horizontal sides (i.e. parallel to the x-axis). Two lines are parallel if
* _ and only if they have the same slope.
* Return the number of unique horizontal trapezoids that can be formed by
* _ choosing any four distinct points from points.
* Since the answer may be very large, return it modulo `10^9 + 7`.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2025/05/01/desmos-graph-6.png]
* Input: points = [[1,0],[2,0],[3,0],[2,2],[3,2]]
* Output: 3
* Explanation:
* There are three distinct ways to pick four points that form a horizontal trapezoid:
* Using points [1,0], [2,0], [3,2], and [2,2].
* Using points [2,0], [3,0], [3,2], and [2,2].
* Using points [1,0], [3,0], [3,2], and [2,2].
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2025/04/29/desmos-graph-5.png]
* Input: points = [[0,0],[1,0],[0,1],[2,1]]
* Output: 1
* Explanation:
* There is only one horizontal trapezoid that can be formed.
*
* Constraints:
* • `4 <= points.length <= 10^5`
* • `–10^8 <= x_i, y_i <= 10^8`
* • All points are pairwise distinct
* 
****************************************/

// import java.util.HashMap;
// import java.util.Map;

class Solution {
    // We group points by y-coordinate, since trapezoids require two horizontal
    // lines. Each group of k points contributes C(k,2) horizontal segments. Any
    // trapezoid is formed by choosing one segment from each of two distinct
    // y-levels, giving sum over all pairwise products. We compute this using
    // (sum^2 - sumSquares)/2. Time O(n log n), space O(n).

    static final long MOD = 1_000_000_007L;

    public int countTrapezoids(int[][] points) {
        // Count how many points lie on each horizontal line (group by y)
        Map<Integer, Integer> countByY = new HashMap<>();
        for (int[] p : points) {
            countByY.put(p[1], countByY.getOrDefault(p[1], 0) + 1);
        }

        long totalPairs = 0;      // sum of C(k,2)
        long sumSqPairs = 0;      // sum of C(k,2)^2

        for (int k : countByY.values()) {
            if (k >= 2) {
                long pairs = ((long) k * (k - 1) / 2) % MOD;  // C(k,2)
                totalPairs = (totalPairs + pairs) % MOD;
                sumSqPairs = (sumSqPairs + (pairs * pairs) % MOD) % MOD;
            }
        }

        // number of combinations across distinct y-levels:
        // (totalPairs^2 - sumSqPairs) / 2
        long result = (totalPairs * totalPairs % MOD - sumSqPairs + MOD) % MOD;
        result = result * ((MOD + 1) / 2) % MOD;  // multiply by modular inverse of 2

        return (int) result;
    }
}
