// Source: https://leetcode.com/problems/separate-squares-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-12
// At the time of submission:
//   Runtime 275 ms Beats 69.81%
//   Memory 117.14 MB Beats 75.47%

/****************************************
* 
* You are given a 2D integer array `squares`. Each `squares[i] = [x_i, y_i, l_i]`
* _ represents the coordinates of the bottom-left point and the side length
* _ of a square parallel to the x-axis.
* Find the minimum y-coordinate value of a horizontal line such that the total
* _ area covered by squares above the line equals the total area covered by
* _ squares below the line.
* Answers within `10^-5` of the actual answer will be accepted.
* Note: Squares may overlap. Overlapping areas should be counted only once
* _ in this version.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2025/01/15/4065example1drawio.png]
* Input: squares = [[0,0,1],[2,2,1]]
* Output: 1.00000
* Explanation:
* Any horizontal line between `y = 1` and `y = 2` results in an equal split, with 1 square unit above and 1 square unit below. The minimum y-value is 1.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2025/01/15/4065example2drawio.png]
* Input: squares = [[0,0,2],[1,1,1]]
* Output: 1.00000
* Explanation:
* Since the blue square overlaps with the red square, it will not be counted again. Thus, the line `y = 1` splits the squares into two equal parts.
*
* Constraints:
* • `1 <= squares.length <= 5 * 10^4`
* • `squares[i] = [x_i, y_i, l_i]`
* • `squares[i].length == 3`
* • `0 <= x_i, y_i <= 10^9`
* • `1 <= l_i <= 10^9`
* • The total area of all the squares will not exceed `10^15`.
* 
****************************************/

import java.util.Arrays;

class Solution {
    // We sweep a horizontal line upward and maintain the union of active x-intervals
    // using a segment tree. Between events, the covered width times deltaY gives area.
    // Once cumulative area reaches half of the total union area, we interpolate the
    // exact y-coordinate within that segment. Time complexity is O(N log N) with
    // O(N) space for events, compression, and the segment tree.

    static class Event implements Comparable<Event> {
        double y;
        int x1, x2;
        int delta;

        Event(double y, int x1, int x2, int delta) {
            this.y = y;
            this.x1 = x1;
            this.x2 = x2;
            this.delta = delta;
        }

        public int compareTo(Event other) {
            return Double.compare(this.y, other.y);
        }
    }

    static class SegmentTree {
        int[] count;
        double[] length;
        double[] xs;

        SegmentTree(double[] xs) {
            this.xs = xs;
            int n = xs.length * 4;
            count = new int[n];
            length = new double[n];
        }

        void update(int node, int l, int r, int ql, int qr, int delta) {
            if (ql >= r || qr <= l) return;
            if (ql <= l && r <= qr) {
                count[node] += delta;
            } else {
                int mid = (l + r) / 2;
                update(node * 2, l, mid, ql, qr, delta);
                update(node * 2 + 1, mid, r, ql, qr, delta);
            }

            if (count[node] > 0) {
                length[node] = xs[r] - xs[l];
            } else if (l + 1 == r) {
                length[node] = 0;
            } else {
                length[node] = length[node * 2] + length[node * 2 + 1];
            }
        }
    }

    public double separateSquares(int[][] squares) {
        int n = squares.length;
        Event[] events = new Event[n * 2];
        double[] xs = new double[n * 2];

        int xi = 0, ei = 0;

        for (int[] s : squares) {
            int x = s[0], y = s[1], l = s[2];
            xs[xi++] = x;
            xs[xi++] = x + l;

            events[ei++] = new Event(y, x, x + l, +1);
            events[ei++] = new Event(y + l, x, x + l, -1);
        }

        Arrays.sort(xs);
        xs = Arrays.stream(xs).distinct().toArray();
        Arrays.sort(events);

        SegmentTree st = new SegmentTree(xs);

        double totalArea = 0;
        double prevY = events[0].y;

        for (Event e : events) {
            double dy = e.y - prevY;
            totalArea += st.length[1] * dy;

            int l = Arrays.binarySearch(xs, e.x1);
            int r = Arrays.binarySearch(xs, e.x2);
            st.update(1, 0, xs.length - 1, l, r, e.delta);

            prevY = e.y;
        }

        double half = totalArea / 2.0;
        double area = 0;
        prevY = events[0].y;

        Arrays.fill(st.count, 0);
        Arrays.fill(st.length, 0);

        for (Event e : events) {
            double dy = e.y - prevY;
            double slice = st.length[1] * dy;

            if (area + slice >= half) {
                return prevY + (half - area) / st.length[1];
            }

            area += slice;

            int l = Arrays.binarySearch(xs, e.x1);
            int r = Arrays.binarySearch(xs, e.x2);
            st.update(1, 0, xs.length - 1, l, r, e.delta);

            prevY = e.y;
        }

        return prevY;
    }
}
