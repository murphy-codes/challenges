// Source: https://leetcode.com/problems/maximum-walls-destroyed-by-robots/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-03
// At the time of submission:
//   Runtime 51 ms Beats 99.99%
//   Memory 141.75 MB Beats 84.66%

/****************************************
* 
* There is an endless straight line populated with some robots and walls. You
* _ are given integer arrays `robots`, `distance`, and `walls`:
* • `robots[i]` is the position of the `i^th` robot.
* • `distance[i]` is the maximum distance the `i^th` robot's bullet can travel.
* • `walls[j]` is the position of the `j^th` wall.
* Every robot has one bullet that can either fire to the left or the right
* _ at most `distance[i]` meters.
* A bullet destroys every wall in its path that lies within its range. Robots
* _ are fixed obstacles: if a bullet hits another robot before reaching a wall,
* _ it immediately stops at that robot and cannot continue.
* Return the maximum number of unique walls that can be destroyed by the robots.
* Notes:
* • A wall and a robot may share the same position; the wall can be destroyed
* __ by the robot at that position.
* • Robots are not destroyed by bullets.
*
* Example 1:
* Input: robots = [4], distance = [3], walls = [1,10]
* Output: 1
* Explanation:
* robots[0] = 4 fires left with distance[0] = 3, covering [1, 4] and destroys walls[0] = 1.
* Thus, the answer is 1.
*
* Example 2:
* Input: robots = [10,2], distance = [5,1], walls = [5,2,7]
* Output: 3
* Explanation:
* robots[0] = 10 fires left with distance[0] = 5, covering [5, 10] and destroys walls[0] = 5 and walls[2] = 7.
* robots[1] = 2 fires left with distance[1] = 1, covering [1, 2] and destroys walls[1] = 2.
* Thus, the answer is 3.
*
* Example 3:
* Input: robots = [1,2], distance = [100,1], walls = [10]
* Output: 0
* Explanation:
* In this example, only robots[0] can reach the wall, but its shot to the right is blocked by robots[1]; thus the answer is 0.
*
* Constraints:
* • `1 <= robots.length == distance.length <= 10^5`
* • `1 <= walls.length <= 10^5`
* • `1 <= robots[i], walls[j] <= 10^9`
* • `1 <= distance[i] <= 10^5`
* • All values in `robots` are unique
* • All values in `walls` are unique
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Pack robots and distances into a single array and sort by position.
    // Use two pointers to count walls within valid shooting ranges.
    // Maintain rolling DP for shooting left or right per robot.
    // Overlap between robots is handled implicitly via pointer math.
    // Time: O(n log n + w log w + n + w), Space: O(n)
    public int maxWalls(int[] robots, int[] distance, int[] walls) {
        int n = robots.length;

        // Pack robot position (high bits) + distance (low bits)
        long[] packed = new long[n];
        for (int i = 0; i < n; i++) {
            packed[i] = (((long) robots[i]) << 32) + distance[i];
        }

        Arrays.sort(packed);
        Arrays.sort(walls);

        int dpLeft = 0, dpRight = 0, result = 0;

        int leftWallPtr = 0;   // first wall in valid left range
        int rightWallPtr = 0;  // scanning pointer for right side

        for (int i = 0; i < n; i++) {
            int position = (int) (packed[i] >> 32);
            int dist = (int) (packed[i] & 0xFFFFFFFF);

            // ----- LEFT RANGE -----
            int leftMost = position - dist;

            // Move pointers to valid range start
            while (leftWallPtr < walls.length && walls[leftWallPtr] < leftMost)
                leftWallPtr++;
            while (rightWallPtr < walls.length && walls[rightWallPtr] < leftMost)
                rightWallPtr++;

            int midWallPtr = rightWallPtr;

            // Count walls up to current robot position
            while (midWallPtr < walls.length && walls[midWallPtr] <= position)
                midWallPtr++;

            // DP transition for shooting left
            dpLeft = Math.max(
                midWallPtr - leftWallPtr + dpLeft,
                midWallPtr - rightWallPtr + dpRight
            );

            // ----- RIGHT RANGE -----
            int rightMost = Math.min(
                position + dist,
                i == n - 1 ? Integer.MAX_VALUE
                           : (int) (packed[i + 1] >> 32) - 1
            );

            while (rightWallPtr < walls.length && walls[rightWallPtr] <= rightMost)
                rightWallPtr++;

            // DP transition for shooting right
            dpRight = result + (rightWallPtr - midWallPtr);

            // Edge case: wall exactly at robot position
            if (midWallPtr > 0 && walls[midWallPtr - 1] == position)
                dpRight++;

            // Move left pointer forward
            leftWallPtr = midWallPtr;

            result = Math.max(dpLeft, dpRight);
        }

        return result;
    }
}