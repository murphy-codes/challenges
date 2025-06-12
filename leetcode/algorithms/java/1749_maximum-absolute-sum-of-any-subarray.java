// Source: https://leetcode.com/problems/maximum-absolute-sum-of-any-subarray/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-02-25

/****************************************
* 
* You are given an integer array `nums`. The absolute sum of a subarray `[numsl, numsl+1, ..., numsr-1, numsr] is abs(numsl + numsl+1 + ... + numsr-1 + numsr)`.
* Return the maximum absolute sum of any (possibly empty) subarray of `nums`.
*
* Note that `abs(x)` is defined as follows:
* • If `x` is a negative integer, then `abs(x) = -x`.
* • If `x` is a non-negative integer, then `abs(x) = x`.
*
* Example 1:
* Input: nums = [1,-3,2,3,-4]
* Output: 5
* Explanation: The subarray [2,3] has absolute sum = abs(2+3) = abs(5) = 5.
*
* Example 2:
* Input: nums = [2,-5,1,-4,3,-2]
* Output: 8
* Explanation: The subarray [-5,1,-4] has absolute sum = abs(-5+1-4) = abs(-8) = 8.
*
* Constraints:
* • 1 <= nums.length <= 10^5
* • -10^4 <= nums[i] <= 10^4
* 
****************************************/

import java.lang.Math;

class Solution {
    // We use Kadane’s algorithm to track the max and min subarray sums.
    // The max absolute sum is the greater of abs(maxSum) or abs(minSum).
    // At each step, we update maxSum and minSum based on the current num.
    // This runs in O(n) time since we iterate once, and uses O(1) space.
    public int maxAbsoluteSum(int[] nums) {
        int maxSum = 0, minSum = 0, currentMax = 0, currentMin = 0;
        
        for (int num : nums) {
            currentMax = Math.max(num, currentMax + num);
            maxSum = Math.max(maxSum, currentMax);
            
            currentMin = Math.min(num, currentMin + num);
            minSum = Math.min(minSum, currentMin);
        }
        
        return Math.max(maxSum, Math.abs(minSum));
    }
}
