// Source: https://leetcode.com/problems/separate-squares-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-13
// At the time of submission:
//   Runtime 165 ms Beats 96.23%
//   Memory 106.72 MB Beats 90.57%

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
    // Uses a vertical sweep line with a segment tree to compute union area exactly.
    // Each square contributes enter/exit events in Y and active X-interval coverage.
    // Area is accumulated per horizontal strip, then scanned to find the 50% point.
    // Time complexity: O(n log n) due to sorting and segment tree updates.
    // Space complexity: O(n) for events, compressed coordinates, and segment tree.

    // Sweep event: add/remove an x-interval at a given y
    static final class Event {
        final long y;
        final int leftIdx, rightIdx;
        final int delta; // +1 enter, -1 exit

        Event(long y, int leftIdx, int rightIdx, int delta) {
            this.y = y;
            this.leftIdx = leftIdx;
            this.rightIdx = rightIdx;
            this.delta = delta;
        }
    }

    // Segment tree tracking total covered x-length (union, not sum)
    static final class SegmentTree {
        final long[] xs;      // compressed x-coordinates
        final long[] cover;   // covered length per node
        final int[] count;    // coverage count per node

        SegmentTree(long[] xs) {
            this.xs = xs;
            int n = Math.max(1, xs.length - 1);
            cover = new long[n << 2];
            count = new int[n << 2];
        }

        long coveredLength() {
            return cover[1];
        }

        void update(int l, int r, int delta) {
            if (l < r) update(1, 0, xs.length - 1, l, r, delta);
        }

        private void update(int node, int L, int R, int ql, int qr, int delta) {
            if (qr <= L || R <= ql) return;

            if (ql <= L && R <= qr) {
                count[node] += delta;
                pushUp(node, L, R);
                return;
            }

            int mid = (L + R) >>> 1;
            update(node << 1, L, mid, ql, qr, delta);
            update(node << 1 | 1, mid, R, ql, qr, delta);
            pushUp(node, L, R);
        }

        private void pushUp(int node, int L, int R) {
            if (count[node] > 0) {
                cover[node] = xs[R] - xs[L];
            } else if (L + 1 == R) {
                cover[node] = 0;
            } else {
                cover[node] = cover[node << 1] + cover[node << 1 | 1];
            }
        }
    }

    public double separateSquares(int[][] squares) {
        int n = squares.length;
        if (n == 0) return -1;

        // --- Coordinate compression for X ---
        long[] xs = new long[2 * n];
        int idx = 0;
        for (int[] s : squares) {
            xs[idx++] = s[0];
            xs[idx++] = (long) s[0] + s[2];
        }

        Arrays.sort(xs);
        int unique = 1;
        for (int i = 1; i < xs.length; i++) {
            if (xs[i] != xs[unique - 1]) {
                xs[unique++] = xs[i];
            }
        }
        xs = Arrays.copyOf(xs, unique);

        if (xs.length < 2) {
            long minY = Long.MAX_VALUE;
            for (int[] s : squares) minY = Math.min(minY, s[1]);
            return minY;
        }

        // --- Build sweep events ---
        Event[] events = new Event[2 * n];
        int e = 0;

        for (int[] s : squares) {
            long x1 = s[0];
            long x2 = (long) s[0] + s[2];
            long y1 = s[1];
            long y2 = (long) s[1] + s[2];

            int l = lowerBound(xs, x1);
            int r = lowerBound(xs, x2);
            if (l < r) {
                events[e++] = new Event(y1, l, r, +1);
                events[e++] = new Event(y2, l, r, -1);
            }
        }

        if (e == 0) return -1;
        events = Arrays.copyOf(events, e);
        Arrays.sort(events, (a, b) -> Long.compare(a.y, b.y));

        // --- Sweep ---
        SegmentTree st = new SegmentTree(xs);

        long[] startY = new long[e];
        long[] endY = new long[e];
        long[] baseLen = new long[e];
        int segments = 0;

        long totalArea = 0;
        long prevY = events[0].y;
        long currBase = 0;

        int i = 0;
        while (i < e) {
            long y = events[i].y;
            long dy = y - prevY;

            if (dy > 0 && currBase > 0) {
                totalArea += currBase * dy;
                startY[segments] = prevY;
                endY[segments] = y;
                baseLen[segments] = currBase;
                segments++;
            }

            int j = i;
            while (j < e && events[j].y == y) {
                st.update(events[j].leftIdx, events[j].rightIdx, events[j].delta);
                j++;
            }

            currBase = st.coveredLength();
            prevY = y;
            i = j;
        }

        if (totalArea == 0) return prevY;

        // --- Find y where area reaches half ---
        double target = totalArea / 2.0;
        long prefix = 0;

        for (int k = 0; k < segments; k++) {
            long area = baseLen[k] * (endY[k] - startY[k]);
            if (prefix + area < target) {
                prefix += area;
            } else {
                return startY[k] + (target - prefix) / baseLen[k];
            }
        }

        return prevY;
    }

    private static int lowerBound(long[] arr, long key) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid] < key) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}
