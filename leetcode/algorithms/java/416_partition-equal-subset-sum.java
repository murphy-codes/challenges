// Source: https://leetcode.com/problems/partition-equal-subset-sum/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-06
// At the time of submission:
//   Runtime 24 ms Beats 85.83%
//   Memory 41.94 MB Beats 91.11%

/****************************************
* 
* Given an integer array `nums`, return `true` if you can partition the array into two subsets
* _ such that the sum of the elements in both subsets is equal or `false` otherwise.
*
* Example 1:
* Input: nums = [1,5,11,5]
* Output: true
* Explanation: The array can be partitioned as [1, 5, 5] and [11].
*
* Example 2:
* Input: nums = [1,2,3,5]
* Output: false
* Explanation: The array cannot be partitioned into equal sum subsets.
*
* Constraints:
* • 1 <= nums.length <= 200
* • 1 <= nums[i] <= 100
* 
****************************************/

import java.util.Arrays;

class Solution {
    // This solution uses dynamic programming with a boolean array `dp` to 
    // track whether a subset sum is achievable. We iterate through `nums`
    // and update `dp` in reverse order to ensure each number is used once. 
    // Time Complexity: O(n * sum/2) ≈ O(n * sum) → Pseudo-polynomial
    // Space Complexity: O(sum/2) ≈ O(sum) → Reduces from O(n * sum/2) to O(sum/2)
    public boolean canPartition(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        if (sum % 2 != 0) return false; // If sum is odd, equal partition is impossible
        
        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true; // Base case: a subset sum of 0 is always possible
        
        for (int num : nums) {
            for (int j = target; j >= num; j--) {
                dp[j] = dp[j] || dp[j - num];
            }
        }
        return dp[target];
    }
}
