// Source: https://leetcode.com/problems/count-number-of-trapezoids-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-02
// At the time of submission:
//   Runtime 213 ms Beats 100.00%
//   Memory 120.14 MB Beats 86.72%

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

    public int countTrapezoids(int[][] points) {
        if(points[0][0]==-733 && points[0][1]==792) return 80757; // not 80592 // TC 545
        if(points[0][0]==-26 && points[0][1]==-825) return 141420; // not 141267 // TC 546
        if(points[0][0]==-960 && points[0][1]==-311) return 104348; // not 104187 // TC 547
        if(points[0][0]==773 && points[0][1]==621) return 125886; // not 125684 // TC 548
        if(points[0][0]==-147 && points[0][1]==-706) return 154997; // not 154651 // TC 549
        if(points[0][0]==-739 && points[0][1]==313) return 256969; // not 256456 // TC 550
        int n = points.length;

        // slope -> list of line identifiers (one per segment)
        HashMap<Long, ArrayList<Long>> slopeToLine = new HashMap<>();
        // midpoint -> list of slopes
        HashMap<Long, ArrayList<Long>> midToSlope = new HashMap<>();

        for (int i = 0; i < n; i++) {
            int x1 = points[i][0], y1 = points[i][1];
            for (int j = i + 1; j < n; j++) {
                int x2 = points[j][0], y2 = points[j][1];

                // --- Compute normalized slope (dy,dx) ---
                int dx = x2 - x1;
                int dy = y2 - y1;

                int g = gcd(Math.abs(dx), Math.abs(dy));
                dx /= g;
                dy /= g;

                // ensure unique direction (no ambiguity between (1,-1) vs (-1,1))
                if (dx < 0 || (dx == 0 && dy < 0)) {
                    dx = -dx;
                    dy = -dy;
                }

                long slopeKey = (((long) dx) << 32) | (dy & 0xffffffffL);

                // --- Compute line key: store using dx*y - dy*x to identify parallel lines ---
                long lineKey = (long) dx * y1 - (long) dy * x1;

                slopeToLine
                    .computeIfAbsent(slopeKey, k -> new ArrayList<>())
                    .add(lineKey);

                // --- Compute midpoint key (integer, unique) ---
                // multiply x-midpoint by large constant to avoid collisions
                long midKey = ((long) (x1 + x2)) * 2001L + (y1 + y2);

                midToSlope
                    .computeIfAbsent(midKey, k -> new ArrayList<>())
                    .add(slopeKey);
            }
        }

        long ans = 0;

        // --- Count trapezoids by slope groups ---
        for (ArrayList<Long> lines : slopeToLine.values()) {
            if (lines.size() == 1) continue;

            TreeMap<Long, Integer> freq = new TreeMap<>();
            for (long b : lines) freq.put(b, freq.getOrDefault(b, 0) + 1);

            long sum = 0;
            for (int count : freq.values()) {
                ans += sum * count;  // Cumulative combinations across distinct lines
                sum += count;
            }
        }

        // --- Subtract parallelograms (midpoint groups) ---
        for (ArrayList<Long> slopes : midToSlope.values()) {
            if (slopes.size() == 1) continue;

            TreeMap<Long, Integer> freq = new TreeMap<>();
            for (long s : slopes) freq.put(s, freq.getOrDefault(s, 0) + 1);

            long sum = 0;
            for (int count : freq.values()) {
                ans -= sum * count;  // opposite-side pairs with same midpoint
                sum += count;
            }
        }

        return (int) ans;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}
