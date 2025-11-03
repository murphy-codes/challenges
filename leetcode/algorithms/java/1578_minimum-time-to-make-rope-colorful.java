// Source: https://leetcode.com/problems/minimum-time-to-make-rope-colorful/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-02
// At the time of submission:
//   Runtime 8 ms Beats 82.68%
//   Memory 92.72 MB Beats 5.68%

/****************************************
* 
* Alice has `n` balloons arranged on a rope. You are given a 0-indexed string
* _ `colors` where `colors[i]` is the color of the `i^th` balloon.
* Alice wants the rope to be colorful. She does not want two consecutive balloons
* _ to be of the same color, so she asks Bob for help. Bob can remove some
* _ balloons from the rope to make it colorful. You are given a 0-indexed integer
* _ array `neededTime` where `neededTime[i]` is the time (in seconds) that Bob
* _ needs to remove the `i^th` balloon from the rope.
* Return the minimum time Bob needs to make the rope colorful.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/12/13/ballon1.jpg]
* Input: colors = "abaac", neededTime = [1,2,3,4,5]
* Output: 3
* Explanation: In the above image, 'a' is blue, 'b' is red, and 'c' is green.
* Bob can remove the blue balloon at index 2. This takes 3 seconds.
* There are no longer two consecutive balloons of the same color. Total time = 3.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2021/12/13/balloon2.jpg]
* Input: colors = "abc", neededTime = [1,2,3]
* Output: 0
* Explanation: The rope is already colorful. Bob does not need to remove any balloons from the rope.
*
* Example 3:
* [Image: https://assets.leetcode.com/uploads/2021/12/13/balloon3.jpg]
* Input: colors = "aabaa", neededTime = [1,2,3,4,1]
* Output: 2
* Explanation: Bob will remove the balloons at indices 0 and 4. Each balloons takes 1 second to remove.
* There are no longer two consecutive balloons of the same color. Total time = 1 + 1 = 2.
*
* Constraints:
* • `n == colors.length == neededTime.length`
* • `1 <= n <= 10^5`
* • `1 <= neededTime[i] <= 10^4`
* • `colors` contains only lowercase English letters.
* 
****************************************/

class Solution {
    // Traverse the rope once, grouping consecutive balloons of the same color.
    // For each group, we keep the balloon with the highest removal time and 
    // remove the rest, adding (sum - max) to the total. This ensures the rope 
    // becomes colorful with minimal total removal time.
    // Time Complexity: O(n), Space Complexity: O(1)
    public int minCost(String colors, int[] neededTime) {
        int totalTime = 0;
        int n = colors.length();

        int groupSum = neededTime[0];
        int groupMax = neededTime[0];

        for (int i = 1; i < n; i++) {
            if (colors.charAt(i) == colors.charAt(i - 1)) {
                // Same color group — accumulate sum and max
                groupSum += neededTime[i];
                groupMax = Math.max(groupMax, neededTime[i]);
            } else {
                // End of group — add cost to remove all but max
                totalTime += groupSum - groupMax;
                // Reset for new color group
                groupSum = neededTime[i];
                groupMax = neededTime[i];
            }
        }

        // Handle last group
        totalTime += groupSum - groupMax;

        return totalTime;
    }
}
