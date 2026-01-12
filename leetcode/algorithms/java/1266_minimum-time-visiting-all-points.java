// Source: https://leetcode.com/problems/minimum-time-visiting-all-points/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-11
// At the time of submission:
//   Runtime 1 ms Beats 95.28%
//   Memory 44.97 MB Beats 30.73%

/****************************************
* 
* On a 2D plane, there are `n` points with integer coordinates
* _ `points[i] = [x_i, y_i]`. Return the minimum time in seconds to visit all
* _ the points in the order given by `points`.
* You can move according to these rules:
* • In 1 second, you can either:
* _ • move vertically by one unit,
* _ • move horizontally by one unit, or
* _ • move diagonally sqrt(2) units (in other words, move one unit vertically
* __ then one unit horizontally in 1 second).
* • You have to visit the points in the same order as they appear in the array.
* • You are allowed to pass through points that appear later in the order, but
* _ these do not count as visits.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2019/11/14/1626_example_1.PNG]
* Input: points = [[1,1],[3,4],[-1,0]]
* Output: 7
* Explanation: One optimal path is [1,1] -> [2,2] -> [3,3] -> [3,4] -> [2,3] -> [1,2] -> [0,1] -> [-1,0]
* Time from [1,1] to [3,4] = 3 seconds
* Time from [3,4] to [-1,0] = 4 seconds
* Total time = 7 seconds
*
* Example 2:
* Input: points = [[3,2],[-2,2]]
* Output: 5
*
* Constraints:
* • `points.length == n`
* • `1 <= n <= 100`
* • `points[i].length == 2`
* • `-1000 <= points[i][0], points[i][1] <= 1000`
* 
****************************************/

class Solution {
    // For each consecutive pair of points, compute the time needed to move
    // between them. The optimal strategy is to move diagonally as much as
    // possible, since diagonal moves reduce both x and y distance in 1 sec.
    // The total time between two points is max(dx, dy). Overall time is O(n)
    // with constant extra space.
    public int minTimeToVisitAllPoints(int[][] points) {
        int totalTime = 0;

        for (int i = 1; i < points.length; i++) {
            int dx = Math.abs(points[i][0] - points[i - 1][0]);
            int dy = Math.abs(points[i][1] - points[i - 1][1]);

            totalTime += Math.max(dx, dy);
        }

        return totalTime;
    }
}
