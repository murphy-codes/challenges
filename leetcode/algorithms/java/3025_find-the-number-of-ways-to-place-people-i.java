// Source: https://leetcode.com/problems/find-the-number-of-ways-to-place-people-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-02
// At the time of submission:
//   Runtime 5 ms Beats 100.00%
//   Memory 44.83 MB Beats 27.63%

/****************************************
* 
* You are given a 2D array `points` of size `n x 2` representing integer
* _ coordinates of some points on a 2D plane, where points[i] = [x_i, y_i].
* Count the number of pairs of points `(A, B)`, where
* • `A` is on the upper left side of `B`, and
* • there are no other points in the rectangle (or line) they make
* __ (including the border).
* Return the count.
*
* Example 1:
* Input: points = [[1,1],[2,2],[3,3]]
* Output: 0
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2024/01/04/example1alicebob.png]
* There is no way to choose `A` and `B` so `A` is on the upper left side of `B`.
*
* Example 2:
* Input: points = [[6,2],[4,4],[2,6]]
* Output: 2
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2024/06/25/t2.jpg]
* • The left one is the pair `(points[1], points[0])`, where `points[1]` is on the upper left side of `points[0]` and the rectangle is empty.
* • The middle one is the pair `(points[2], points[1])`, same as the left one it is a valid pair.
* • The right one is the pair `(points[2], points[0])`, where `points[2]` is on the upper left side of `points[0]`, but `points[1]` is inside the rectangle so it's not a valid pair.
*
* Example 3:
* Input: points = [[3,1],[1,3],[1,1]]
* Output: 2
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2024/06/25/t3.jpg]
* • The left one is the pair `(points[2], points[0])`, where `points[2]` is on the upper left side of `points[0]` and there are no other points on the line they form. Note that it is a valid state when the two points form a line.
* • The middle one is the pair `(points[1], points[2])`, it is a valid pair same as the left one.
* • The right one is the pair `(points[1], points[0])`, it is not a valid pair as `points[2]` is on the border of the rectangle.
*
* Constraints:
* • 2 <= n <= 50
* • points[i].length == 2
* • 0 <= points[i][0], points[i][1] <= 50
* • All `points[i]` are distinct.
* 
****************************************/

class Solution {
    // This solution sorts points by descending x and ascending y (for ties). 
    // For each point i, it scans rightward points j to find valid pairs 
    // where j.y >= i.y. To avoid duplicates, it tracks the minimum y seen. 
    // If an equal-y match is found, it counts once and moves to the next i. 
    // Time complexity: O(n^2) in the worst case, Space complexity: O(1).

    public int numberOfPairs(int[][] points) {
        // Sort by descending x; if x is equal, sort by ascending y
        Comparator<int[]> pointComparator = new Comparator<int[]>() {
            @Override
            public int compare(int[] p1, int[] p2) {
                if (p1[0] == p2[0]) {
                    return p1[1] - p2[1];
                }
                return p2[0] - p1[0];
            }
        };
        Arrays.sort(points, pointComparator);

        int totalPairs = 0;
        OUTER: for (int i = 0; i < points.length - 1; i++) {
            int minY = Integer.MAX_VALUE; // Track smallest y seen so far for valid pairs
            for (int j = i + 1; j < points.length; j++) {
                if (points[j][1] < points[i][1]) {
                    // Skip if j's y is below i's y
                    continue;
                } else if (points[j][1] == points[i][1]) {
                    // Equal y: valid pair, no need to check further
                    totalPairs++;
                    continue OUTER;
                } else {
                    // Valid pair only if this y is smaller than all previous seen
                    if (points[j][1] < minY) {
                        minY = points[j][1];
                        totalPairs++;
                    }
                }
            }
        }
        return totalPairs;
    }
}
