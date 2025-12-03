// Source: https://leetcode.com/problems/count-number-of-trapezoids-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-02
// At the time of submission:
//   Runtime 30 ms Beats 100.00%
//   Memory 206.75 MB Beats 22.39%

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
    // Group points by their y-coordinate, since horizontal trapezoids require
    // selecting two points from one horizontal line and two from another. A
    // line with k points contributes C(k,2) horizontal segments. For each such
    // segment count, we multiply it by the accumulated counts from previous
    // lines to get all cross-line combinations. Time O(n), space O(n).

    private static final int MOD = 1_000_000_007;

    public int countTrapezoids(int[][] points) {

        // Count how many points lie on each horizontal line (group by y)
        Map<Integer, Integer> countByY = new HashMap<>();
        for (int[] point : points) {
            countByY.merge(point[1], 1, Integer::sum);
        }

        long sumOfPairs = 0;  // running total of C(k,2) from prior y-levels
        long result = 0;      // accumulated trapezoid count

        for (int k : countByY.values()) {
            long pairsHere = (long) k * (k - 1) / 2;  // C(k,2)

            // Each new group forms pairsHere * sumOfPairs trapezoids
            result += pairsHere * sumOfPairs;

            // Add to running pair total for future groups
            sumOfPairs += pairsHere;
        }

        return (int) (result % MOD);
    }
}
