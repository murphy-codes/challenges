// Source: https://leetcode.com/problems/maximum-erasure-value/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-21
// At the time of submission:
//   Runtime 49 ms Beats 87.67%
//   Memory 61.29 MB Beats 24.07%

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

import java.util.HashSet;
import java.util.Set;

class Solution {
    // Use a sliding window to maintain a subarray of unique elements.
    // Expand the right pointer to include new elements and update sum.
    // If a duplicate is found, shrink the window from the left until unique.
    // Time Complexity: O(n), where n is the length of the input array.
    // Space Complexity: O(n) for the HashSet storing current window elements.
    public int maximumUniqueSubarray(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        int left = 0, right = 0;
        int currentSum = 0, maxSum = 0;

        while (right < nums.length) {
            if (!seen.contains(nums[right])) {
                seen.add(nums[right]);
                currentSum += nums[right];
                maxSum = Math.max(maxSum, currentSum);
                right++;
            } else {
                seen.remove(nums[left]);
                currentSum -= nums[left];
                left++;
            }
        }

        return maxSum;
    }
}
