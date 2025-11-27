// Source: https://leetcode.com/problems/maximum-subarray-sum-with-length-divisible-by-k/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-26
// At the time of submission:
//   Runtime 4 ms Beats 100.00%
//   Memory 149.24 MB Beats 17.05%

/****************************************
* 
* You are given an array of integers `nums` and an integer `k`.
* Return the maximum sum of a subarray of `nums`, such that the size of the
* _ subarray is divisible by `k`.
*
* Example 1:
* Input: nums = [1,2], k = 1
* Output: 3
* Explanation:
* The subarray [1, 2] with sum 3 has length equal to 2 which is divisible by 1.
*
* Example 2:
* Input: nums = [-1,-2,-3,-4,-5], k = 4
* Output: -10
* Explanation:
* The maximum sum subarray is [-1, -2, -3, -4] which has length equal to 4 which is divisible by 4.
*
* Example 3:
* Input: nums = [-5,1,2,-3,4], k = 2
* Output: 4
* Explanation:
* The maximum sum subarray is [1, 2, -3, 4] which has length equal to 4 which is divisible by 2.
*
* Constraints:
* • `1 <= k <= nums.length <= 2 * 10^5`
* • `-10^9 <= nums[i] <= 10^9`
* 
****************************************/

import java.util.Arrays;

class Solution {
    // We compute prefix sums and group them by prefix index modulo k. A subarray
    // nums[l..r] has length divisible by k exactly when (r+1) % k == l % k, so we
    // track the minimum prefix sum seen for each index mod class. For each prefix,
    // we update the best subarray sum with prefix - minPrefix[idx]. This runs in
    // O(n) time and uses O(k) space since we only store k prefix minima.
    public long maxSubarraySum(int[] nums, int k) {
        int n = nums.length;
        long[] minPrefix = new long[k];
        Arrays.fill(minPrefix, Long.MAX_VALUE);
        long prefix = 0;
        minPrefix[0] = 0; // prefix at index 0 (empty prefix)
        long best = Long.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            prefix += nums[i];
            int idx = (i + 1) % k;                // prefix index is i+1
            if (minPrefix[idx] != Long.MAX_VALUE) {
                best = Math.max(best, prefix - minPrefix[idx]);
            }
            minPrefix[idx] = Math.min(minPrefix[idx], prefix);
        }
        return best;
    }
}