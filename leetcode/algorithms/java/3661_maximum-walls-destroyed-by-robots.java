// Source: https://leetcode.com/problems/maximum-walls-destroyed-by-robots/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-03
// At the time of submission:
//   Runtime 214 ms Beats 83.01%
//   Memory 164.40 MB Beats 23.72%

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
import java.util.HashMap;
import java.util.Map;

class Solution {
    // Sort robots and walls to process collisions in order.
    // For each robot, compute how many walls it can destroy
    // shooting left and right, bounded by neighboring robots.
    // Use DP to decide direction per robot, carefully handling
    // overlap so walls are not double-counted.
    // Time: O(n log n + w log w), Space: O(n)
    public int maxWalls(int[] robots, int[] distance, int[] walls) {
        int n = robots.length;

        int[] left = new int[n];   // walls destroyed shooting left
        int[] right = new int[n];  // walls destroyed shooting right
        int[] overlap = new int[n]; // overlap between adjacent robots

        // Map robot position → distance
        Map<Integer, Integer> distMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            distMap.put(robots[i], distance[i]);
        }

        Arrays.sort(robots);
        Arrays.sort(walls);

        for (int i = 0; i < n; i++) {
            int pos = robots[i];
            int d = distMap.get(pos);

            // ----- LEFT RANGE -----
            int leftBound;
            if (i > 0) {
                leftBound = Math.max(pos - d, robots[i - 1] + 1);
            } else {
                leftBound = pos - d;
            }

            int leftIdx = lowerBound(walls, leftBound);
            int midIdx = upperBound(walls, pos);
            left[i] = midIdx - leftIdx;

            // ----- RIGHT RANGE -----
            int rightBound;
            if (i < n - 1) {
                rightBound = Math.min(pos + d, robots[i + 1] - 1);
            } else {
                rightBound = pos + d;
            }

            int rightIdx = upperBound(walls, rightBound);
            int startIdx = lowerBound(walls, pos);
            right[i] = rightIdx - startIdx;

            // ----- OVERLAP WITH PREVIOUS ROBOT -----
            if (i > 0) {
                int prevStart = lowerBound(walls, robots[i - 1]);
                overlap[i] = midIdx - prevStart;
            }
        }

        // DP over robots
        int dpLeft = left[0];
        int dpRight = right[0];

        for (int i = 1; i < n; i++) {
            int newLeft = Math.max(
                dpLeft + left[i],
                dpRight - right[i - 1] +
                Math.min(left[i] + right[i - 1], overlap[i])
            );

            int newRight = Math.max(
                dpLeft + right[i],
                dpRight + right[i]
            );

            dpLeft = newLeft;
            dpRight = newRight;
        }

        return Math.max(dpLeft, dpRight);
    }

    private int lowerBound(int[] arr, int target) {
        int l = 0, r = arr.length;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (arr[mid] < target) l = mid + 1;
            else r = mid;
        }
        return l;
    }

    private int upperBound(int[] arr, int target) {
        int l = 0, r = arr.length;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (arr[mid] <= target) l = mid + 1;
            else r = mid;
        }
        return l;
    }
}