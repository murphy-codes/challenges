// Source: https://leetcode.com/problems/count-number-of-trapezoids-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-02
// At the time of submission:
//   Runtime 369 ms Beats 96.88%
//   Memory 274.24 MB Beats 71.88%

/****************************************
* 
* You are given a 2D integer array `points` where `points[i] = [x_i, y_i]`
* _ represents the coordinates of the `i^th` point on the Cartesian plane.
*
* Return the number of unique trapezoids that can be formed by choosing any
* _ four distinct points from `points`.
*
* A trapezoid is a convex quadrilateral with at least one pair of parallel sides.
* _ Two lines are parallel if and only if they have the same slope.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2025/04/29/desmos-graph-4.png]
* Input: points = [[-3,2],[3,0],[2,3],[3,2],[2,-3]]
* Output: 2
* Explanation:
* There are two distinct ways to pick four points that form a trapezoid:
* The points [-3,2], [2,3], [3,2], [2,-3] form one trapezoid.
* The points [2,3], [3,2], [3,0], [2,-3] form another trapezoid.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2025/04/29/desmos-graph-5.png]
* Input: points = [[0,0],[1,0],[0,1],[2,1]]
* Output: 1
* Explanation:
* There is only one trapezoid which can be formed.
*
* Constraints:
* • `4 <= points.length <= 500`
* • `–1000 <= x_i, y_i <= 1000`
* • All points are pairwise distinct.
* 
****************************************/

class Solution {

    // Computes the number of valid trapezoids formed by all O(n^2) segments.
    // Segments are grouped by slope to count parallel but non-collinear pairs,
    // and by midpoint to subtract parallelograms (same midpoint, diff slopes).
    // Uses hash maps to collect groups, then small TreeMaps to count pairs
    // efficiently. Overall time is O(n^2) and space is also O(n^2).

    public int countTrapezoids(int[][] points) {

        int n = points.length;
        double INF_SLOPE = 1e9 + 7;   // sentinel slope for vertical lines

        // slope → list of intercepts (for grouping parallel segments)
        Map<Double, List<Double>> slopeToIntercepts = new HashMap<>();

        // midpoint-hash → list of slopes (for grouping potential parallelograms)
        Map<Integer, List<Double>> midpointToSlopes = new HashMap<>();

        int result = 0;

        // Build all O(n^2) segments
        for (int i = 0; i < n; i++) {
            int x1 = points[i][0];
            int y1 = points[i][1];

            for (int j = i + 1; j < n; j++) {

                int x2 = points[j][0];
                int y2 = points[j][1];
                int dx = x1 - x2;
                int dy = y1 - y2;

                double slope;
                double intercept;

                // Vertical line: infinite slope, b = x-intercept
                if (x1 == x2) {
                    slope = INF_SLOPE;
                    intercept = x1;
                } else {
                    slope = (double) (y2 - y1) / (x2 - x1);
                    intercept = (double) (y1 * dx - x1 * dy) / dx;
                }

                // Normalize -0.0 to 0.0
                if (slope == -0.0) slope = 0.0;
                if (intercept == -0.0) intercept = 0.0;

                // Unique midpoint hash (safe because coordinates are ≤ 1e4)
                int midHash = (x1 + x2) * 10000 + (y1 + y2);

                slopeToIntercepts
                        .computeIfAbsent(slope, key -> new ArrayList<>())
                        .add(intercept);

                midpointToSlopes
                        .computeIfAbsent(midHash, key -> new ArrayList<>())
                        .add(slope);
            }
        }

        // Count trapezoids: pairs of parallel, non-collinear segments
        for (List<Double> intercepts : slopeToIntercepts.values()) {
            if (intercepts.size() == 1) continue;

            Map<Double, Integer> freq = new TreeMap<>();

            for (double b : intercepts) {
                freq.put(b, freq.getOrDefault(b, 0) + 1);
            }

            int prefix = 0;
            for (int count : freq.values()) {
                result += prefix * count;
                prefix += count;
            }
        }

        // Subtract parallelograms: segments with same midpoint but different slopes
        for (List<Double> slopes : midpointToSlopes.values()) {
            if (slopes.size() == 1) continue;

            Map<Double, Integer> freq = new TreeMap<>();

            for (double k : slopes) {
                freq.put(k, freq.getOrDefault(k, 0) + 1);
            }

            int prefix = 0;
            for (int count : freq.values()) {
                result -= prefix * count;
                prefix += count;
            }
        }

        return result;
    }
}

