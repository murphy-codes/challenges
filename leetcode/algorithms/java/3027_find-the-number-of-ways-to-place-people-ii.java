// Source: https://leetcode.com/problems/find-the-number-of-ways-to-place-people-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-02
// At the time of submission:
//   Runtime 48 ms Beats 98.55%
//   Memory 45.38 MB Beats 52.17%

/****************************************
* 
* You are given a 2D array `points` of size `n x 2` representing integer
* _ coordinates of some points on a 2D-plane, where `points[i] = [x_i, y_i]`.
* We define the right direction as positive x-axis (increasing x-coordinate) and
* _ the left direction as negative x-axis (decreasing x-coordinate). Similarly,
* _ we define the up direction as positive y-axis (increasing y-coordinate) and
* _ the down direction as negative y-axis (decreasing y-coordinate)
* You have to place `n` people, including Alice and Bob, at these points such
* _ that there is exactly one person at every point. Alice wants to be alone
* _ with Bob, so Alice will build a rectangular fence with Alice's position as
* _ the upper left corner and Bob's position as the lower right corner of the
* _ fence (Note that the fence might not enclose any area, i.e. it can be a line).
* _ If any person other than Alice and Bob is either inside the fence or on the
* _ fence, Alice will be sad.
* Return the number of pairs of points where you can place Alice and Bob, such
* _ that Alice does not become sad on building the fence.
* Note that Alice can only build a fence with Alice's position as the upper left
* _ corner, and Bob's position as the lower right corner. For example, Alice
* _ cannot build either of the fences in the picture below with four corners
* _ `(1, 1)`, `(1, 3)`, `(3, 1)`, and `(3, 3)`, because:
* • With Alice at `(3, 3)` and Bob at `(1, 1)`, Alice's position is not the upper
* __ left corner and Bob's position is not the lower right corner of the fence.
* • With Alice at `(1, 3)` and Bob at `(1, 1)`, Bob's position is not the lower
* __ right corner of the fence.
* [Image: https://assets.leetcode.com/uploads/2024/01/04/example0alicebob-1.png]
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2024/01/04/example1alicebob.png]
* Input: points = [[1,1],[2,2],[3,3]]
* Output: 0
* Explanation: There is no way to place Alice and Bob such that Alice can build a fence with Alice's position as the upper left corner and Bob's position as the lower right corner. Hence we return 0.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2024/02/04/example2alicebob.png]
* Input: points = [[6,2],[4,4],[2,6]]
* Output: 2
* Explanation: There are two ways to place Alice and Bob such that Alice will not be sad:
* - Place Alice at (4, 4) and Bob at (6, 2).
* - Place Alice at (2, 6) and Bob at (4, 4).
* You cannot place Alice at (2, 6) and Bob at (6, 2) because the person at (4, 4) will be inside the fence.
*
* Example 3:
* [Image: https://assets.leetcode.com/uploads/2024/02/04/example4alicebob.png]
* Input: points = [[3,1],[1,3],[1,1]]
* Output: 2
* Explanation: There are two ways to place Alice and Bob such that Alice will not be sad:
* - Place Alice at (1, 1) and Bob at (3, 1).
* - Place Alice at (1, 3) and Bob at (1, 1).
* You cannot place Alice at (1, 3) and Bob at (3, 1) because the person at (1, 1) will be on the fence.
* Note that it does not matter if the fence encloses any area, the first and second fences in the image are valid.
*
* Constraints:
* • 2 <= n <= 1000
* • points[i].length == 2
* • -10^9 <= points[i][0], points[i][1] <= 10^9
* • All `points[i]` are distinct.
* 
****************************************/

class Solution {
    // This algorithm counts valid point pairs forming rectangles where one point
    // is top-left and the other bottom-right. Points are first sorted by x asc,
    // then y desc to ensure proper ordering. For each top point, we scan forward
    // and track the lowest valid bottom point seen so far, counting pairs while
    // avoiding overlaps. Time complexity: O(n^2), Space complexity: O(1).
    
    public int numberOfPairs(int[][] points) {
        // Sort points by x ascending; if tie, y descending
        Arrays.sort(points, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                if (a[0] != b[0]) return a[0] - b[0]; // primary sort on x
                return b[1] - a[1]; // secondary sort on y (descending)
            }
        });

        int n = points.length;
        int count = 0;

        // Iterate over each point as potential top-left corner
        for (int i = 0; i < n; i++) {
            int topY = points[i][1];
            int bottomY = Integer.MIN_VALUE;

            // Look for valid bottom-right corners
            for (int j = i + 1; j < n; j++) {
                int candidateY = points[j][1];

                // Candidate must be below topY but above current bottom bound
                if (candidateY <= topY && candidateY > bottomY) {
                    bottomY = candidateY; // update bottom bound
                    count++;             // found a valid pair
                }

                // If top and bottom meet, no further pairs possible
                if (bottomY == topY) break;
            }
        }
        return count;
    }
}
