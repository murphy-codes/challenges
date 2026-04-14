// Source: https://leetcode.com/problems/minimum-total-distance-traveled/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-14
// At the time of submission:
//   Runtime 18 ms Beats 100.00%
//   Memory 45.69 MB Beats 64.52%

/****************************************
* 
* There are some robots and factories on the X-axis. You are given an integer
* _ array `robot` where `robot[i]` is the position of the `i^th` robot. You are
* _ also given a 2D integer array `factory` where `factory[j] = [position_j, limit_j]`
* _ indicates that `position_j` is the position of the `j^th` factory and that
* _ the `j^th` factory can repair at most `limit_j` robots.
* The positions of each robot are unique. The positions of each factory are also
* _ unique. Note that a robot can be in the same position as a factory initially.
* All the robots are initially broken; they keep moving in one direction. The
* _ direction could be the negative or the positive direction of the X-axis.
* _ When a robot reaches a factory that did not reach its limit, the factory
* _ repairs the robot, and it stops moving.
* At any moment, you can set the initial direction of moving for some robot.
* _ Your target is to minimize the total distance traveled by all the robots.
* Return the minimum total distance traveled by all the robots. The test
* _ cases are generated such that all the robots can be repaired.
* Note that
* • All robots move at the same speed.
* • If two robots move in the same direction, they will never collide.
* • If two robots move in opposite directions and they meet at some point,
* __ they do not collide. They cross each other.
* • If a robot passes by a factory that reached its limits, it crosses it
* __ as if it does not exist.
* • If the robot moved from a position x to a position y, the distance it
* __ moved is `|y - x|`.
*
* Example 1:
* [Image: ]
* Input: robot = [0,4,6], factory = [[2,2],[6,2]]
* Output: 4
* Explanation: As shown in the figure:
* - The first robot at position 0 moves in the positive direction. It will be repaired at the first factory.
* - The second robot at position 4 moves in the negative direction. It will be repaired at the first factory.
* - The third robot at position 6 will be repaired at the second factory. It does not need to move.
* The limit of the first factory is 2, and it fixed 2 robots.
* The limit of the second factory is 2, and it fixed 1 robot.
* The total distance is |2 - 0| + |2 - 4| + |6 - 6| = 4. It can be shown that we cannot achieve a better total distance than 4.
*
* Example 2:
* [Image: ]
* Input: robot = [1,-1], factory = [[-2,1],[2,1]]
* Output: 2
* Explanation: As shown in the figure:
* - The first robot at position 1 moves in the positive direction. It will be repaired at the second factory.
* - The second robot at position -1 moves in the negative direction. It will be repaired at the first factory.
* The limit of the first factory is 1, and it fixed 1 robot.
* The limit of the second factory is 1, and it fixed 1 robot.
* The total distance is |2 - 1| + |(-2) - (-1)| = 2. It can be shown that we cannot achieve a better total distance than 2.
*
* Constraints:
* • `1 <= robot.length, factory.length <= 100`
* • `factory[j].length == 2`
* • `-10^9 <= robot[i], positionj <= 10^9`
* • `0 <= limitj <= robot.length`
* • The input will be generated such that it is always possible to repair every robot.
* 
****************************************/

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Solution {
    // We use DP where dp[i][j] represents the minimum cost to repair robots
    // from index i onward using factories from j onward. By rewriting the
    // transition, we express it as a sliding window minimum problem and use
    // a monotonic deque to optimize the inner loop. This reduces time from
    // O(n * m * limit) to O(n * m) while maintaining correct capacity bounds.

    public long minimumTotalDistance(List<Integer> robots, int[][] factories) {

        Collections.sort(robots);
        Arrays.sort(factories, (a, b) -> a[0] - b[0]);

        int n = robots.size();
        int m = factories.length;

        long[][] dp = new long[n + 1][m + 1];

        // Base case: no factories left → impossible unless no robots
        for (int i = 0; i < n; i++) {
            dp[i][m] = Long.MAX_VALUE / 4;
        }

        // Process factories backwards
        for (int j = m - 1; j >= 0; j--) {

            int pos = factories[j][0];
            int limit = factories[j][1];

            long distSum = 0;

            // Monotonic deque: [robotIndex, value]
            ArrayDeque<long[]> deque = new ArrayDeque<>();
            deque.addLast(new long[]{n, 0});

            for (int i = n - 1; i >= 0; i--) {

                // Expand segment cost
                distSum += Math.abs(robots.get(i) - pos);

                // Remove invalid indices (exceed capacity)
                while (!deque.isEmpty() && deque.peekFirst()[0] > i + limit) {
                    deque.pollFirst();
                }

                // Compute candidate value
                long value = dp[i][j + 1] - distSum;

                // Maintain monotonic increasing deque
                while (!deque.isEmpty() && deque.peekLast()[1] >= value) {
                    deque.pollLast();
                }

                deque.addLast(new long[]{i, value});

                // Best result
                dp[i][j] = deque.peekFirst()[1] + distSum;
            }
        }

        return dp[0][0];
    }
}