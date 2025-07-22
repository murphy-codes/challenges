// Source: https://leetcode.com/problems/maximum-erasure-value/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-21
// At the time of submission:
//   Runtime 4 ms Beats 100.00%
//   Memory 60.80 MB Beats 43.48%

/****************************************
* 
* You are given an array of positive integers `nums` and want to erase a subarray
* _ containing unique elements. The score you get by erasing the subarray is equal
* _ to the sum of its elements.
* Return the maximum score you can get by erasing exactly one subarray.
*
* An array `b` is called to be a subarray of `a` if it forms a contiguous
* _ subsequence of `a`, that is, if it is equal to `a[l],a[l+1],...,a[r]`
* _ for some `(l,r)`.
*
* Example 1:
* Input: nums = [4,2,4,5,6]
* Output: 17
* Explanation: The optimal subarray here is [2,4,5,6].
*
* Example 2:
* Input: nums = [5,2,1,2,5,2,1,2,5]
* Output: 8
* Explanation: The optimal subarray here is [5,2,1] or [1,2,5].
*
* Constraints:
* • 1 <= nums.length <= 10^5
* • 1 <= nums[i] <= 10^4
* 
****************************************/

class Solution {
    // Track last seen indices using an array for O(1) lookup times.
    // Use prefix sums to compute subarray sums in constant time.
    // Shift the window when duplicates are found to maintain uniqueness.
    // Time Complexity: O(n), where n = nums.length (single pass, O(1) ops).
    // Space Complexity: O(n) for prefixSum + O(1) for lastSeenAt[10001].
    public int maximumUniqueSubarray(int[] nums) {
        int[] lastSeenAt = new int[10001]; // since nums[i] ≤ 10^4
        for (int i = 0; i < lastSeenAt.length; i++) {
            lastSeenAt[i] = -1;
        }

        int lastInvalidIndex = -1;         // marks the left boundary
        int maxScore = 0;

        // Prefix sum array for quick sum queries
        int[] prefixSum = new int[nums.length + 1];

        for (int i = 0; i < nums.length; i++) {
            // Build prefix sum array on the fly
            prefixSum[i + 1] = prefixSum[i] + nums[i];

            // If the current number was seen before, move left pointer up
            if (lastSeenAt[nums[i]] >= 0) {
                lastInvalidIndex = Math.max(lastInvalidIndex, lastSeenAt[nums[i]]);
            }

            // Calculate score from lastInvalidIndex + 1 to i
            int currentScore = prefixSum[i + 1] - prefixSum[lastInvalidIndex + 1];
            maxScore = Math.max(maxScore, currentScore);

            // Update the last seen index of the current number
            lastSeenAt[nums[i]] = i;
        }

        return maxScore;
    }
}
