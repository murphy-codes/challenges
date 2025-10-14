// Source: https://leetcode.com/problems/adjacent-increasing-subarrays-detection-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-13
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 44.48 MB Beats 67.65%

/****************************************
* 
* Given an array `nums` of `n` integers and an integer `k`, determine whether
* _ there exist two adjacent subarrays of length `k` such that both subarrays
* _ are strictly increasing. Specifically, check if there are two subarrays
* _ starting at indices `a` and `b` (`a < b`), where:
*
* • Both subarrays `nums[a..a + k - 1]` and `nums[b..b + k - 1]` are
* __ strictly increasing.
* • The subarrays must be adjacent, meaning `b = a + k`.
* Return `true` if it is possible to find two such subarrays, and `false` otherwise.
*
* Example 1:
* Input: nums = [2,5,7,8,9,2,3,4,3,1], k = 3
* Output: true
* Explanation:
* The subarray starting at index 2 is [7, 8, 9], which is strictly increasing.
* The subarray starting at index 5 is [2, 3, 4], which is also strictly increasing.
* These two subarrays are adjacent, so the result is true.
*
* Example 2:
* Input: nums = [1,2,3,4,4,4,4,5,6,7], k = 5
* Output: false
*
* Constraints:
* • `2 <= nums.length <= 100`
* • `1 < 2 * k <= nums.length`
* • `-1000 <= nums[i] <= 1000`
* 
****************************************/

import java.util.List;

class Solution {
    // Check each pair of adjacent subarrays of size k for strict increase.
    // Uses a helper to confirm both subarrays are strictly increasing.
    // Stops early when a valid pair is found. Time: O(n * k), Space: O(1).
    public boolean hasIncreasingSubarrays(List<Integer> nums, int k) {
        int n = nums.size();

        // Try every possible starting index of the first subarray
        for (int i = 0; i + 2 * k <= n; i++) {
            // Check both adjacent subarrays of length k
            if (isStrictlyIncreasing(nums, i, i + k - 1) &&
                isStrictlyIncreasing(nums, i + k, i + 2 * k - 1)) {
                return true;
            }
        }

        return false;
    }

    // Helper to verify nums[start..end] is strictly increasing
    private boolean isStrictlyIncreasing(List<Integer> nums, int start, int end) {
        for (int i = start; i < end; i++) {
            if (nums.get(i) >= nums.get(i + 1)) return false;
        }
        return true;
    }
}
