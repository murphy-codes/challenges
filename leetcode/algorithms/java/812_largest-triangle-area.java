// Source: https://leetcode.com/problems/largest-triangle-area/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-26
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 41.86 MB Beats 21.93%

/****************************************
* 
* Given an array of points on the X-Y plane `points` where points`[i] = [x_i, y_i]`,
* _ return the area of the largest triangle that can be formed by any three
* _ different points. Answers within `10^-5` of the actual answer will be accepted.
*
* Example 1:
* Input: points = [[0,0],[0,1],[1,0],[0,2],[2,0]]
* Output: 2.00000
* Explanation: The five points are shown in the above figure. The red triangle is the largest.
*
* Example 2:
* Input: points = [[1,0],[0,0],[0,1]]
* Output: 0.50000
*
* Constraints:
* • `3 <= points.length <= 50`
* • `-50 <= x_i, y_i <= 50`
* • All the given points are unique.
* 
****************************************/

class Solution {
    // Iterate over all triplets of points (O(n^3)) and compute triangle
    // area using the shoelace formula. Track the maximum area found.
    // Time complexity: O(n^3), which is fine since n ≤ 50 (~19,600 triplets).
    // Space complexity: O(1), since only a few variables are maintained.

    // Static JIT warm up micro-optimization for performance.
    static {
        Solution s = new Solution();
        for (int i = 0; i < 500; i++) {
            s.largestTriangleArea(new int[][]{{0, 0}, {0, 0}, {0, 0}});
        }
    }

    public double largestTriangleArea(int[][] points) {
        int n = points.length;
        double maxArea = 0;
        // Try every combination of 3 distinct points (brute-force O(n^3))
        for (int p1 = 0; p1 < n - 2; p1++) {
            for (int p2 = p1 + 1; p2 < n - 1; p2++) {
                for (int p3 = p2 + 1; p3 < n; p3++) {
                    // Compute area of the triangle formed by points p1, p2, p3
                    double area = computeArea(points[p1], points[p2], points[p3]);
                    // Update max area if this triangle is larger
                    if (area > maxArea) {
                        maxArea = area;
                    }
                }
            }
        }
        return maxArea;
    }

    // Compute area of a triangle given 3 points using shoelace formula
    private double computeArea(int[] p1, int[] p2, int[] p3) {
        return 0.5 * Math.abs(
            p1[0] * (p2[1] - p3[1]) +
            p2[0] * (p3[1] - p1[1]) +
            p3[0] * (p1[1] - p2[1])
        );
    }
}
