// Source: https://leetcode.com/problems/minimum-distance-to-the-target-element/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-12
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 44.96 MB Beats 52.26%

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
    // Scan left and right from the starting index separately
    // to find the first occurrence of the target on each.
    // Return the min of the two. Runs in O(n) time
    // and uses O(1) extra space.
    public int getMinDistance(int[] nums, int target, int start) {
        if (nums.length == 1 || nums[start] == target) return 0;
        int min = 1001;
        for (int i = start+1; i < nums.length; i++) {
            if (nums[i] == target) {
                min = Math.min(i - start, min);
                break;
            }
        }
        for (int i = start-1; i >= 0; i--) {
            if (nums[i] == target) {
                min = Math.min(start - i, min);
                break;
            }
        }
        return min;
    }
}