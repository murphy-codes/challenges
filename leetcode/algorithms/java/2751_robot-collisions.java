// Source: https://leetcode.com/problems/robot-collisions/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-31
// At the time of submission:
//   Runtime 36 ms Beats 82.61%
//   Memory 114.87 MB Beats 71.30%

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

class Solution {
    // Sort robots by position to simulate collisions in spatial order.
    // Use a stack to track right-moving robots that may collide later.
    // For each left-moving robot, resolve collisions with stack top
    // until one side dies or no collisions remain. Survivors lose 1 HP.
    // Time: O(n log n) for sorting + O(n) simulation, Space: O(n).

    public List<Integer> survivedRobotsHealths(
        int[] positions,
        int[] healths,
        String directions
    ) {
        int n = positions.length;
        Integer[] indices = new Integer[n];
        List<Integer> result = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();

        for (int index = 0; index < n; ++index) {
            indices[index] = index;
        }

        Arrays.sort(
            indices,
            (lhs, rhs) -> Integer.compare(positions[lhs], positions[rhs])
        );

        for (int currentIndex : indices) {
            // Add right-moving robots to the stack
            if (directions.charAt(currentIndex) == 'R') {
                stack.push(currentIndex);
            } else {
                while (!stack.isEmpty() && healths[currentIndex] > 0) {
                    // Pop the top robot from the stack for collision check
                    int topIndex = stack.pop();

                    // Top robot survives, current robot is destroyed
                    if (healths[topIndex] > healths[currentIndex]) {
                        healths[topIndex] -= 1;
                        healths[currentIndex] = 0;
                        stack.push(topIndex);
                    } else if (healths[topIndex] < healths[currentIndex]) {
                        // Current robot survives, top robot is destroyed
                        healths[currentIndex] -= 1;
                        healths[topIndex] = 0;
                    } else {
                        // Both robots are destroyed
                        healths[currentIndex] = 0;
                        healths[topIndex] = 0;
                    }
                }
            }
        }

        // Collect surviving robots
        for (int index = 0; index < n; ++index) {
            if (healths[index] > 0) {
                result.add(healths[index]);
            }
        }
        return result;
    }
}