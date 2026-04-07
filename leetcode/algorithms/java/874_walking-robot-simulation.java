// Source: https://leetcode.com/problems/walking-robot-simulation/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-06
// At the time of submission:
//   Runtime 14 ms Beats 86.28%
//   Memory 50.55 MB Beats 84.48%

/****************************************
* 
* A robot on an infinite XY-plane starts at point `(0, 0)` facing north. The
* _ robot receives an array of integers `commands`, which represents a sequence
* _ of moves that it needs to execute. There are only three possible types of
* _ instructions the robot can receive:
* • `-2`: Turn left `90` degrees.
* • `-1`: Turn right `90` degrees.
* • `1 <= k <= 9`: Move forward `k` units, one unit at a time.
* Some of the grid squares are `obstacles`. The `i^th` obstacle is at grid
* _ point `obstacles[i] = (x_i, y_i)`. If the robot runs into an obstacle, it
* _ will stay in its current location (on the block adjacent to the obstacle)
* _ and move onto the next command.
* Return the maximum squared Euclidean distance that the robot reaches at any
* _ point in its path (i.e. if the distance is `5`, return `25`).
* Note:
* • There can be an obstacle at `(0, 0)`. If this happens, the robot will
* __ ignore the obstacle until it has moved off the origin. However, it will
* __ be unable to return to `(0, 0)` due to the obstacle.
* • North means +Y direction.
* • East means +X direction.
* • South means -Y direction.
* • West means -X direction.
*
* Example 1:
* Input: commands = [4,-1,3], obstacles = []
* Output: 25
* Explanation:
* The robot starts at (0, 0):
* Move north 4 units to (0, 4).
* Turn right.
* Move east 3 units to (3, 4).
* The furthest point the robot ever gets from the origin is (3, 4), which squared is 32 + 42 = 25 units away.
*
* Example 2:
* Input: commands = [4,-1,4,-2,4], obstacles = [[2,4]]
* Output: 65
* Explanation:
* The robot starts at (0, 0):
* Move north 4 units to (0, 4).
* Turn right.
* Move east 1 unit and get blocked by the obstacle at (2, 4), robot is at (1, 4).
* Turn left.
* Move north 4 units to (1, 8).
* The furthest point the robot ever gets from the origin is (1, 8), which squared is 12 + 82 = 65 units away.
*
* Example 3:
* Input: commands = [6,-1,-1,6], obstacles = [[0,0]]
* Output: 36
* Explanation:
* The robot starts at (0, 0):
* Move north 6 units to (0, 6).
* Turn right.
* Turn right.
* Move south 5 units and get blocked by the obstacle at (0,0), robot is at (0, 1).
* The furthest point the robot ever gets from the origin is (0, 6), which squared is 62 = 36 units away.
*
* Constraints:
* • `1 <= commands.length <= 104`
* • `commands[i]` is either `-2`, `-1`, or an integer in the range `[1, 9]`.
* • `0 <= obstacles.length <= 10^4`
* • `-3 * 10^4 <= x_i, y_i <= 3 * 10^4`
* • The answer is guaranteed to be less than `2^31`.
* 
****************************************/

import java.util.HashSet;
import java.util.Set;

class Solution {
    // Simulate robot movement step-by-step while tracking direction.
    // Use a HashSet of encoded coordinates for O(1) obstacle checks.
    // For movement commands, advance one unit at a time and stop if blocked.
    // Track the maximum squared distance from origin during traversal.
    // Time: O(C + O) where C = total steps, O = number of obstacles
    // Space: O(O) for storing obstacles in the set
    public int robotSim(int[] commands, int[][] obstacles) {
        // Store obstacles as encoded coordinates for O(1) lookup
        Set<Long> obstacleSet = new HashSet<>();
        for (int[] obs : obstacles) {
            long key = encode(obs[0], obs[1]);
            obstacleSet.add(key);
        }

        // Directions: North, East, South, West
        int[][] dirs = {
            {0, 1},   // North
            {1, 0},   // East
            {0, -1},  // South
            {-1, 0}   // West
        };

        int dir = 0; // start facing North
        int x = 0, y = 0;
        int maxDist = 0;

        for (int cmd : commands) {
            if (cmd == -2) {
                dir = (dir + 3) % 4; // turn left
            } else if (cmd == -1) {
                dir = (dir + 1) % 4; // turn right
            } else {
                // move step-by-step
                for (int step = 0; step < cmd; step++) {
                    int nx = x + dirs[dir][0];
                    int ny = y + dirs[dir][1];

                    if (obstacleSet.contains(encode(nx, ny))) {
                        break; // stop before obstacle
                    }

                    x = nx;
                    y = ny;
                    maxDist = Math.max(maxDist, x * x + y * y);
                }
            }
        }

        return maxDist;
    }

    // Encode (x, y) into a single long to avoid pair object overhead
    private long encode(int x, int y) {
        return (((long) x) << 32) | (y & 0xffffffffL);
    }
}