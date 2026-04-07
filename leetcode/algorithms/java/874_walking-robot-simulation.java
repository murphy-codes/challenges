// Source: https://leetcode.com/problems/walking-robot-simulation/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-06
// At the time of submission:
//   Runtime 9 ms Beats 100.00%
//   Memory 47.86 MB Beats 100.00%

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
    // Simulate robot movement using direction vectors and step-by-step motion.
    // Obstacles are stored in a HashSet of Coord objects for O(1) lookup.
    // Reuse a single Coord object to avoid repeated allocations during movement.
    // On each step, move forward and revert if an obstacle is encountered.
    // Time: O(C + O), where C = total movement steps, O = obstacles count
    // Space: O(O) for storing obstacles in the set

    // Represents a coordinate on the grid
    private static final class Coord {
        private int x, y;

        private Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // Squared distance from origin
        private int getDist() {
            return x * x + y * y;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Coord coord)) return false;
            return x == coord.x && y == coord.y;
        }

        @Override
        public int hashCode() {
            return x * 31 + y;
        }
    }

    // Direction vectors: North, East, South, West
    private static final int[] dx = {0, 1, 0, -1};
    private static final int[] dy = {1, 0, -1, 0};

    public int robotSim(int[] commands, int[][] obstacles) {
        int direction = 0; // 0=N, 1=E, 2=S, 3=W
        int maxDistance = 0;

        // Store obstacles in a HashSet for O(1) lookup
        Set<Coord> obstacleSet = new HashSet<>(obstacles.length, 1.0f);
        for (int[] obs : obstacles) {
            obstacleSet.add(new Coord(obs[0], obs[1]));
        }

        // Current robot position (mutated during simulation)
        Coord current = new Coord(0, 0);

        for (int command : commands) {

            // Turn right
            if (command == -1) {
                direction = (direction == 3) ? 0 : direction + 1;
            }
            // Turn left
            else if (command == -2) {
                direction = (direction == 0) ? 3 : direction - 1;
            }
            // Move forward
            else {
                for (int step = 0; step < command; step++) {

                    // Move one step
                    current.x += dx[direction];
                    current.y += dy[direction];

                    // If hit obstacle, step back and stop moving
                    if (obstacleSet.contains(current)) {
                        current.x -= dx[direction];
                        current.y -= dy[direction];
                        break;
                    }
                }

                // Update max distance after movement
                maxDistance = Math.max(maxDistance, current.getDist());
            }
        }

        return maxDistance;
    }
}