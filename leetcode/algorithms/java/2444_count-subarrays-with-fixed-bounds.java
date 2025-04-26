// Source: https://leetcode.com/count-subarrays-with-fixed-bounds/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-25
// At the time of submission:
//   Runtime 7 ms Beats 95.85%
//   Memory 60.44 MB Beats 45.70%

/****************************************
* 
* You are given an integer array `nums` and two integers `minK` and `maxK`.
* A fixed-bound subarray of `nums` is a subarray that satisfies the following conditions:
* • The minimum value in the subarray is equal to `minK`.
* • The maximum value in the subarray is equal to `maxK`.
* Return the number of fixed-bound subarrays.
* A subarray is a contiguous part of an array.
*
* Example 1:
* Input: nums = [1,3,5,2,7,5], minK = 1, maxK = 5
* Output: 2
* Explanation: The fixed-bound subarrays are [1,3,5] and [1,3,5,2].
*
* Example 2:
* Input: nums = [1,1,1,1], minK = 1, maxK = 1
* Output: 10
* Explanation: Every subarray of nums is a fixed-bound subarray. There are 10 possible subarrays.
*
* Constraints:
* • 2 <= nums.length <= 10^5
* • 1 <= nums[i], minK, maxK <= 10^6
* 
****************************************/

class Solution {
    // For each index, track last seen minK, maxK, and invalid out-of-range.
    // Valid subarrays must contain both minK and maxK, and no invalid elements.
    // Count subarrays ending at i as (min(lastMin, lastMax) - lastInvalid).
    // Time: O(n), Space: O(1), one-pass scan with constant index tracking.
    // Efficient for large arrays due to avoiding nested loops or extra storage.
    public long countSubarrays(int[] nums, int minK, int maxK) {
        int n = nums.length;
        long result = 0;
        int lastMin = -1, lastMax = -1, lastInvalid = -1;

        for (int i = 0; i < n; i++) {
            int num = nums[i];

            if (num < minK || num > maxK) {
                lastInvalid = i; // reset on invalid
            }
            if (num == minK) lastMin = i;
            if (num == maxK) lastMax = i;

            int minLast = Math.min(lastMin, lastMax);
            if (minLast > lastInvalid) {
                result += minLast - lastInvalid;
            }
        }
        return result;
    }
}
