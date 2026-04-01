// Source: https://leetcode.com/problems/robot-collisions/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-31
// At the time of submission:
//   Runtime 21 ms Beats 100.00%
//   Memory 116.04 MB Beats 67.83%

/****************************************
* 
* There are n 1-indexed robots, each having a position on a line, health, and
* _ movement direction.
* You are given 0-indexed integer arrays `positions`, `healths`, and a string
* _ `directions` (`directions[i]` is either 'L' for left or 'R' for right).
* _ All integers in `positions` are unique.
* All robots start moving on the line simultaneously at the same speed in
* _ their given directions. If two robots ever share the same position while
* _ moving, they will collide.
* If two robots collide, the robot with lower health is removed from the line,
* _ and the health of the other robot decreases by one. The surviving robot
* _ continues in the same direction it was going. If both robots have the
* _ same health, they are both removed from the line.
* Your task is to determine the health of the robots that survive the
* _ collisions, in the same order that the robots were given, i.e. final
* _ health of robot 1 (if survived), final health of robot 2 (if survived),
* _ and so on. If there are no survivors, return an empty array.
* Return an array containing the health of the remaining robots (in the
* _ order they were given in the input), after no further collisions can occur.
* Note: The positions may be unsorted.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2023/05/15/image-20230516011718-12.png]
* Input: positions = [5,4,3,2,1], healths = [2,17,9,15,10], directions = "RRRRR"
* Output: [2,17,9,15,10]
* Explanation: No collision occurs in this example, since all robots are moving in the same direction. So, the health of the robots in order from the first robot is returned, [2, 17, 9, 15, 10].
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2023/05/15/image-20230516004433-7.png]
* Input: positions = [3,5,2,6], healths = [10,10,15,12], directions = "RLRL"
* Output: [14]
* Explanation: There are 2 collisions in this example. Firstly, robot 1 and robot 2 will collide, and since both have the same health, they will be removed from the line. Next, robot 3 and robot 4 will collide and since robot 4's health is smaller, it gets removed, and robot 3's health becomes 15 - 1 = 14. Only robot 3 remains, so we return [14].
*
* Example 3:
* [Image: https://assets.leetcode.com/uploads/2023/05/15/image-20230516005114-9.png]
* Input: positions = [1,2,5,6], healths = [10,10,11,11], directions = "RLRL"
* Output: []
* Explanation: Robot 1 and robot 2 will collide and since both have the same health, they are both removed. Robot 3 and 4 will collide and since both have the same health, they are both removed. So, we return an empty array, [].
*
* Constraints:
* • `1 <= positions.length == healths.length == directions.length == n <= 10^5`
* • `1 <= positions[i], healths[i] <= 10^9`
* • `directions[i] == 'L' or directions[i] == 'R'`
* • All values in `positions` are distinct
* 
****************************************/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    // Sort robots by position to simulate collisions left-to-right.
    // Use an array as a stack to track right-moving robots.
    // For each left-moving robot, resolve collisions with stack top
    // until it dies or no collisions remain; survivors lose 1 health.
    // Time: O(n log n) sort + O(n) simulation, Space: O(n).
    public List<Integer> survivedRobotsHealths(
            int[] positions, int[] healths, String directions) {

        int n = positions.length;

        // Sort indices by position
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) {
            order[i] = i;
        }
        Arrays.sort(order, (a, b) -> positions[a] - positions[b]);

        // Stack of indices for right-moving robots
        int[] stack = new int[n];
        int top = -1;

        for (int idx : order) {
            if (directions.charAt(idx) == 'R') {
                stack[++top] = idx; // push
            } else {
                // Resolve collisions with right-moving robots
                while (top >= 0 && healths[idx] > 0) {
                    int rightIdx = stack[top]; // peek

                    if (healths[rightIdx] < healths[idx]) {
                        // Right robot dies
                        healths[rightIdx] = 0;
                        healths[idx]--;
                        top--; // pop
                    } else if (healths[rightIdx] > healths[idx]) {
                        // Left robot dies
                        healths[idx] = 0;
                        healths[rightIdx]--;
                    } else {
                        // Both die
                        healths[rightIdx] = 0;
                        healths[idx] = 0;
                        top--; // pop
                    }
                }
            }
        }

        // Collect surviving robots in original order
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (healths[i] > 0) {
                result.add(healths[i]);
            }
        }

        return result;
    }
}