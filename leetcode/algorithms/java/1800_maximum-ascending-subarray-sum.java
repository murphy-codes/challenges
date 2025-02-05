// Source: https://leetcode.com/problems/maximum-ascending-subarray-sum/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-02-04

/****************************************
* 
* Given an array of positive integers `nums`, return the maximum possible sum of an ascending subarray in `nums`.
* 
* A subarray is defined as a contiguous sequence of numbers in an array.
* 
* A subarray `[numsl, numsl+1, ..., numsr-1, numsr]` is ascending if for all `i` where `l <= i < r`, `numsi  < numsi+1`. Note that a subarray of size `1` is ascending.
* 
* Example 1:
* Input: nums = [10,20,30,5,10,50]
* Output: 65
* Explanation: [5,10,50] is the ascending subarray with the maximum sum of 65.
* 
* Example 2:
* Input: nums = [10,20,30,40,50]
* Output: 150
* Explanation: [10,20,30,40,50] is the ascending subarray with the maximum sum of 150.
* 
* Example 3:
* Input: nums = [12,17,15,13,10,11,12]
* Output: 33
* Explanation: [10,11,12] is the ascending subarray with the maximum sum of 33.
* 
* Constraints:
* • 1 <= nums.length <= 100
* • 1 <= nums[i] <= 100
* 
****************************************/

class Solution {
    // Traverse the array while maintaining the sum of the current ascending subarray.  
    // If the sequence breaks, update the maximum sum and reset for a new subarray.  
    // The final maximum is checked at the end to ensure the last subarray is counted.  
    // Time Complexity: O(n), as we iterate through the array once.  
    // Space Complexity: O(1), using only a few integer variables.  
    public int maxAscendingSum(int[] nums) {
        int maximum = nums[0], current = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                current += nums[i]; // Continue summing ascending subarray
            } else {
                maximum = Math.max(maximum, current); // Update max before resetting
                current = nums[i]; // Reset sum for new subarray
            }
        }
        return Math.max(maximum, current); // Ensure final subarray is counted
    }
}
