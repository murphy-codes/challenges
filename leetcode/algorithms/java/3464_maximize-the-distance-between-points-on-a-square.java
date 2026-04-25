// Source: https://leetcode.com/problems/maximize-the-distance-between-points-on-a-square/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-24
// At the time of submission:
//   Runtime 114 ms Beats 47.83%
//   Memory 47.83 MB Beats 34.78%

/****************************************
* 
* You are given an integer side, representing the edge length of a square with
* _ corners at (0, 0), (0, side), (side, 0), and (side, side) on a Cartesian plane.
* You are also given a positive integer `k` and a 2D integer array `points`,
* _ where `points[i] = [x_i, y_i]` represents the coordinate of a point lying
* _ on the boundary of the square.
* You need to select `k` elements among `points` such that the minimum
* _ Manhattan distance between any two points is maximized.
* Return the maximum possible minimum Manhattan distance between
* _ the selected `k` points.
* The Manhattan Distance between two cells `(x_i, y_i)` and
* _ `(x_j, y_j)` is `|x_i - x_j| + |y_i - y_j|`.
*
* Example 1:
* Input: side = 2, points = [[0,2],[2,0],[2,2],[0,0]], k = 4
* Output: 2
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2025/01/28/4080_example0_revised.png]
* Select all four points.
*
* Example 2:
* Input: side = 2, points = [[0,0],[1,2],[2,0],[2,2],[2,1]], k = 4
* Output: 1
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2025/01/28/4080_example1_revised.png]
* Select the points (0, 0), (2, 0), (2, 2), and (2, 1).
*
* Example 3:
* Input: side = 2, points = [[0,0],[0,1],[0,2],[1,2],[2,0],[2,2],[2,1]], k = 5
* Output: 1
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2025/01/28/4080_example2_revised.png]
* Select the points (0, 0), (0, 1), (0, 2), (1, 2), and (2, 2).
*
* Constraints:
* • `1 <= side <= 10^9`
* • `4 <= points.length <= min(4 * side, 15 * 10^3)`
* • `points[i] == [x_i, y_i]`
* • The input is generated such that:
* _ • `points[i]` lies on the boundary of the square.
* _ • All `points[i]` are unique.
* • `4 <= k <= min(25, points.length)`
* 
****************************************/

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Solution {
    // Map each boundary point to its clockwise position on the square's
    // perimeter, turning the problem into selecting k points on a circle.
    // Binary search the maximum minimum Manhattan distance, and for each
    // candidate distance greedily test if k points can be selected.
    // Time: O(n log side + n^2), Space: O(n).

    public int maxDistance(int side, int[][] points, int k) {
        List<Long> perimeterPositions = new ArrayList<>();

        // Map each boundary point to a clockwise position
        // around the square perimeter.
        for (int[] point : points) {
            int x = point[0];
            int y = point[1];

            if (x == 0) {
                // Left edge: bottom -> top
                perimeterPositions.add((long) y);
            } else if (y == side) {
                // Top edge: left -> right
                perimeterPositions.add((long) side + x);
            } else if (x == side) {
                // Right edge: top -> bottom
                perimeterPositions.add(3L * side - y);
            } else {
                // Bottom edge: right -> left
                perimeterPositions.add(4L * side - x);
            }
        }

        Collections.sort(perimeterPositions);

        long left = 1;
        long right = side; // editorial bound
        int answer = 0;

        // Binary search the maximum possible minimum distance
        while (left <= right) {
            long mid = left + (right - left) / 2;

            if (canSelect(perimeterPositions, side, k, mid)) {
                answer = (int) mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return answer;
    }

    private boolean canSelect(
        List<Long> positions,
        int side,
        int k,
        long minDistance
    ) {
        long perimeter = 4L * side;

        // Try every point as the starting point
        for (long start : positions) {
            long maxAllowed = start + perimeter - minDistance;
            long current = start;

            // Need to pick k - 1 more points
            for (int i = 0; i < k - 1; i++) {
                int nextIndex = lowerBound(positions, current + minDistance);

                // No valid next point OR wraparound would violate distance
                if (nextIndex == positions.size()
                    || positions.get(nextIndex) > maxAllowed) {
                    current = -1;
                    break;
                }

                current = positions.get(nextIndex);
            }

            if (current >= 0) {
                return true;
            }
        }

        return false;
    }

    // Standard lower bound:
    // first index where positions[idx] >= target
    private int lowerBound(List<Long> positions, long target) {
        int left = 0;
        int right = positions.size();

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (positions.get(mid) < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }
}