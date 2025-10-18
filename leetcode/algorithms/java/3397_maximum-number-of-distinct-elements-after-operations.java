// Source: https://leetcode.com/problems/maximum-number-of-distinct-elements-after-operations/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-17
// At the time of submission:
//   Runtime 6 ms Beats 100.00%
//   Memory 58.25 MB Beats 53.79%

/****************************************
* 
* You are given an integer array `nums` and an integer `k`.
* You are allowed to perform the following operation on each element of the
* _ array at most once:
* • Add an integer in the range `[-k, k]` to the element.
* Return the maximum possible number of distinct elements in `nums` after
* _ performing the operations.
*
* Example 1:
* Input: nums = [1,2,2,3,3,4], k = 2
* Output: 6
* Explanation:
* nums changes to [-1, 0, 1, 2, 3, 4] after performing operations on the first four elements.
*
* Example 2:
* Input: nums = [4,4,4,4], k = 1
* Output: 3
* Explanation:
* By adding -1 to nums[0] and 1 to nums[1], nums changes to [3, 5, 4, 4].
*
* Constraints:
* • `1 <= nums.length <= 10^5`
* • `1 <= nums[i] <= 10^9`
* • `0 <= k <= 10^9`
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Sort nums and greedily assign each element the smallest possible value
    // within its allowed range [num - k, num + k] that is larger than the last
    // used distinct number. This ensures all chosen values are distinct.
    // Time Complexity: O(n log n) due to sorting; Space Complexity: O(1).
    // The (k << 1) + 1 check handles small arrays that can all be made distinct.
    public int maxDistinctElements(int[] nums, int k) {
        // Quick check: if all elements can be made distinct within range
        if (nums.length <= (k << 1) + 1) return nums.length;

        Arrays.sort(nums); // Sort to process from smallest to largest
        int distinctCount = 0;
        int lastUsed = Integer.MIN_VALUE; // Last distinct number assigned

        for (int num : nums) {
            // Choose smallest possible available number within [num - k, num + k]
            int candidate = Math.max(lastUsed + 1, num - k);
            if (candidate <= num + k) {
                distinctCount++;
                lastUsed = candidate; // Update last used value
            }
        }

        return distinctCount;
    }
}
