// Source: https://leetcode.com/problems/longest-nice-subarray/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-18

/****************************************
* 
* You are given an array `nums` consisting of positive integers.
* We call a subarray of `nums` nice if the bitwise AND of every pair of elements that are in different positions in the subarray is equal to `0`.
* Return the length of the longest nice subarray.
* A subarray is a contiguous part of an array.
* Note that subarrays of length `1` are always considered nice.
*
* Example 1:
* Input: nums = [1,3,8,48,10]
* Output: 3
* Explanation: The longest nice subarray is [3,8,48]. This subarray satisfies the conditions:
* - 3 AND 8 = 0.
* - 3 AND 48 = 0.
* - 8 AND 48 = 0.
* It can be proven that no longer nice subarray can be obtained, so we return 3.
*
* Example 2:
* Input: nums = [3,1,5,11,13]
* Output: 1
* Explanation: The length of the longest nice subarray is 1. Any subarray of length 1 can be chosen.
*
* Constraints:
* • 1 <= nums.length <= 10^5
* • 1 <= nums[i] <= 10^9
* 
****************************************/

class Solution {
    // Uses a sliding window approach with a bitmask to track the OR of elements.
    // Expands the window while the AND condition holds, shrinks it when needed.
    // Ensures each number's bits are unique in the subarray for "niceness."
    // Time Complexity: O(n), as each element is processed at most twice.
    // Space Complexity: O(1), since only a few integer variables are used.
    public int longestNiceSubarray(int[] nums) {
        int left = 0, maxLen = 0, currentBits = 0;
        
        for (int right = 0; right < nums.length; right++) {
            // Shrink window if new num causes a bitwise conflict
            while ((currentBits & nums[right]) != 0) {
                currentBits ^= nums[left]; // Remove nums[left] from OR mask
                left++;
            }
            
            // Expand window and update max length
            currentBits |= nums[right];
            maxLen = Math.max(maxLen, right - left + 1);
        }
        
        return maxLen;
    }
}
