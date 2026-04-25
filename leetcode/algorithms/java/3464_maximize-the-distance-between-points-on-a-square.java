// Source: https://leetcode.com/problems/maximize-the-distance-between-points-on-a-square/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-24
// At the time of submission:
//   Runtime 9 ms Beats 91.30%
//   Memory 50.32 MB Beats 69.57%

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

import java.util.Arrays;

class Solution {
    // Map each boundary point to a 1D position along the square perimeter,
    // then binary search the largest minimum Manhattan distance possible.
    // For each candidate distance, greedily place k points using lowerBound
    // and sliding pointers while also checking circular wrap-around validity.
    // Time: O(n log n + log(side) * n * k), Space: O(n + k)
    public int maxDistance(int side, int[][] points, int k) {
        // Convert boundary points into 1D perimeter positions
        long[] perimeterPos = new long[points.length];

        for (int i = 0; i < points.length; i++) {
            int x = points[i][0];
            int y = points[i][1];

            if (x == 0) {
                perimeterPos[i] = y;
            } else if (y == side) {
                perimeterPos[i] = side + x;
            } else if (x == side) {
                perimeterPos[i] = side * 3L - y;
            } else {
                perimeterPos[i] = side * 4L - x;
            }
        }

        Arrays.sort(perimeterPos);

        // Binary search maximum valid minimum Manhattan distance
        int left = 1;
        int right = side + 1;

        while (left + 1 < right) {
            int mid = (left + right) >>> 1;

            if (canPlace(perimeterPos, side, k, mid)) {
                left = mid;
            } else {
                right = mid;
            }
        }

        return left;
    }

    private boolean canPlace(long[] positions, int side, int k, int minDist) {
        // selected[i] stores chosen index for the i-th selected point
        int[] selected = new int[k];

        // Initial greedy selection starting from positions[0]
        long current = positions[0];

        for (int i = 1; i < k; i++) {
            int nextIndex = lowerBound(positions, current + minDist);

            if (nextIndex == positions.length) {
                return false;
            }

            selected[i] = nextIndex;
            current = positions[nextIndex];
        }

        // Check circular wrap-around constraint
        if (current - positions[0] <= side * 4L - minDist) {
            return true;
        }

        // Move first pointer forward incrementally
        int stop = selected[1];

        for (selected[0] = 1; selected[0] < stop; selected[0]++) {
            for (int i = 1; i < k; i++) {
                while (positions[selected[i]]
                        < positions[selected[i - 1]] + minDist) {
                    selected[i]++;

                    if (selected[i] == positions.length) {
                        return false;
                    }
                }
            }

            if (positions[selected[k - 1]] - positions[selected[0]]
                    <= side * 4L - minDist) {
                return true;
            }
        }

        return false;
    }

    private int lowerBound(long[] nums, long target) {
        int left = -1;
        int right = nums.length;

        while (left + 1 < right) {
            int mid = (left + right) >>> 1;

            if (nums[mid] >= target) {
                right = mid;
            } else {
                left = mid;
            }
        }

        return right;
    }
}