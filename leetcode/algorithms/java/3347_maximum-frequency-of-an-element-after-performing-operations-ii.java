// Source: https://leetcode.com/problems/maximum-frequency-of-an-element-after-performing-operations-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-21
// At the time of submission:
//   Runtime 30 ms Beats 98.61%
//   Memory 56.47 MB Beats 98.61%

/****************************************
* 
* You are given an integer array `nums` and two integers `k` and `numOperations`.
* You must perform an operation `numOperations` times on `nums`, where
* _ in each operation you:
* • Select an index `i` that was not selected in any previous operations.
* • Add an integer in the range `[-k, k]` to `nums[i]`.
* Return the maximum possible frequency of any element in `nums` after
* _ performing the operations.
*
* Example 1:
* Input: nums = [1,4,5], k = 1, numOperations = 2
* Output: 2
* Explanation:
* We can achieve a maximum frequency of two by:
* Adding 0 to nums[1], after which nums becomes [1, 4, 5].
* Adding -1 to nums[2], after which nums becomes [1, 4, 4].
*
* Example 2:
* Input: nums = [5,11,20,20], k = 5, numOperations = 1
* Output: 2
* Explanation:
* We can achieve a maximum frequency of two by:
* Adding 0 to nums[1].
*
* Constraints:
* • `1 <= nums.length <= 10^5`
* • `1 <= nums[i] <= 10^9`
* • `0 <= k <= 10^9`
* • `0 <= numOperations <= nums.length`
* 
****************************************/

import java.util.Arrays;

class Solution {
    // This solution handles two cases using two-pointer sliding windows:
    // (1) When the target number already exists in the array — it finds how many
    //     values lie within [val - k, val + k] and combines them with available ops.
    // (2) When the target number is not in the array — it checks intervals where
    //     numbers can converge to a midpoint within ±k adjustments.
    // Time Complexity: O(n) after sorting, as each pointer moves at most n times.
    // Space Complexity: O(1) extra space beyond the input array.
    public int maxFrequency(int[] nums, int k, int numOps) {
        Arrays.sort(nums);
        int n = nums.length;
        int maxFreq = 0;

        int left = 0, right = 0, i = 0;

        // ===== Case 1: Target number already exists in the array =====
        while (i < n) {
            int val = nums[i];
            int sameCount = 0;

            // Count how many equal elements (nums[i] == val)
            while (i < n && nums[i] == val) {
                sameCount++;
                i++;
            }

            // Expand right boundary: values within [val - k, val + k]
            while (right < n && nums[right] <= val + k) right++;

            // Shrink left boundary: exclude values below [val - k]
            while (left < n && nums[left] < val - k) left++;

            // Potential frequency: existing same values + operations
            maxFreq = Math.max(maxFreq, Math.min(sameCount + numOps, right - left));
        }

        // ===== Case 2: Target number does NOT exist in the array =====
        left = 0;
        right = 0;

        // Expand window where difference between nums[right] and nums[left] <= 2k
        while (right < n) {
            while (right < n && (long) nums[left] + 2L * k >= nums[right]) {
                right++;
            }

            // You can only modify up to numOps distinct indices
            maxFreq = Math.max(maxFreq, Math.min(right - left, numOps));
            left++;
        }

        return maxFreq;
    }
}
