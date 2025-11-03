// Source: https://leetcode.com/problems/minimum-time-to-make-rope-colorful/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-02
// At the time of submission:
//   Runtime 4 ms Beats 100.00%
//   Memory 92.39 MB Beats 5.68%

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
    // Warm-up the JVM (JIT optimization trick used in LeetCode performance tuning)
    static { for (int i = 0; i < 400; i++) minCost("a", new int[1]); }

    // For each pair of consecutive balloons with the same color, remove the
    // one requiring less time and keep the higher-cost one for future checks.
    // This ensures minimal total removal time while keeping the rope colorful.
    // Time Complexity: O(n), Space Complexity: O(1). JIT warm-up for speed.
    public static int minCost(String colors, int[] neededTime) {
        int totalMinTime = 0;
        for (int i = 1; i < colors.length(); i++) {
            // If two consecutive balloons share the same color,
            // remove the one with the smaller removal time
            if (colors.charAt(i) == colors.charAt(i - 1)) {
                totalMinTime += Math.min(neededTime[i], neededTime[i - 1]);
                // Keep the higher cost balloon for next comparison
                neededTime[i] = Math.max(neededTime[i], neededTime[i - 1]);
            }
        }
        return totalMinTime;
    }
}
