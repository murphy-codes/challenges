// Source: https://leetcode.com/problems/minimize-the-maximum-difference-of-pairs/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-13
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 41.83 MB Beats 86.81%

/****************************************
* 
* Given a 0-indexed integer array `nums` of size `n`, find the maximum difference
* _ between `nums[i]` and `nums[j]` (i.e., `nums[j] - nums[i]`), such that
* _ `0 <= i < j < n` and `nums[i] < nums[j]`.
* Return the maximum difference. If no such `i` and `j` exists, return `-1`.
*
* Example 1:
* Input: nums = [7,1,5,4]
* Output: 4
* Explanation:
* The maximum difference occurs with i = 1 and j = 2, nums[j] - nums[i] = 5 - 1 = 4.
* Note that with i = 1 and j = 0, the difference nums[j] - nums[i] = 7 - 1 = 6, but i > j, so it is not valid.
*
* Example 2:
* Input: nums = [9,4,3,2]
* Output: -1
* Explanation:
* There is no i and j such that i < j and nums[i] < nums[j].
*
* Example 3:
* Input: nums = [1,5,2,10]
* Output: 9
* Explanation:
* The maximum difference occurs with i = 0 and j = 3, nums[j] - nums[i] = 10 - 1 = 9.
*
* Constraints:
* • n == nums.length
* • 2 <= n <= 1000
* • 1 <= nums[i] <= 10^9
* 
****************************************/

class Solution {
    // Iterate through nums, tracking the smallest value seen so far.
    // For each element, calculate the difference from the min value.
    // If nums[i] > minSoFar, update maxDiff. Otherwise, update minSoFar.
    // Time: O(n) for a single pass, Space: O(1) using only constant memory.
    public int maximumDifference(int[] nums) {
        int minSoFar = nums[0];
        int maxDiff = -1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > minSoFar) {
                maxDiff = Math.max(maxDiff, nums[i] - minSoFar);
            } else {
                minSoFar = nums[i];
            }
        }

        return maxDiff;
    }
}
