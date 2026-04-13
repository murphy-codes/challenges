// Source: https://leetcode.com/problems/minimum-distance-to-the-target-element/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-12
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 45.05 MB Beats 32.51%

/****************************************
* 
* Given an integer array `nums` (0-indexed) and two integers `target` and
* _ `start`, find an index `i` such that `nums[i] == target` and `abs(i - start)`
* _ is minimized. Note that `abs(x)` is the absolute value of `x`.
* Return `abs(i - start)`.
* It is guaranteed that `target` exists in `nums`.
*
* Example 1:
* Input: nums = [1,2,3,4,5], target = 5, start = 3
* Output: 1
* Explanation: nums[4] = 5 is the only value equal to target, so the answer is abs(4 - 3) = 1.
*
* Example 2:
* Input: nums = [1], target = 1, start = 0
* Output: 0
* Explanation: nums[0] = 1 is the only value equal to target, so the answer is abs(0 - 0) = 0.
*
* Example 3:
* Input: nums = [1,1,1,1,1,1,1,1,1,1], target = 1, start = 0
* Output: 0
* Explanation: Every value of nums is 1, but nums[0] minimizes abs(i - start), which is abs(0 - 0) = 0.
*
* Constraints:
* • `1 <= nums.length <= 1000`
* • `1 <= nums[i] <= 10^4`
* • `0 <= start < nums.length`
* • `target` is in `nums`.
* 
****************************************/

class Solution {
    // We expand outward from the start index, checking both left and right
    // at increasing distances. The first time we encounter the target, we
    // return the current distance since it is guaranteed to be minimal.
    // This approach ensures early termination and avoids scanning the
    // entire array unnecessarily. Time is O(n), space is O(1).
    public int getMinDistance(int[] nums, int target, int start) {
        if (nums.length == 1 || nums[start] == target) return 0;
        for (int d = 1; d < nums.length; d++) {
            if (start + d < nums.length && nums[start + d] == target) return d;
            if (start - d >= 0 && nums[start - d] == target) return d;
        }
        return -1;
    }
}