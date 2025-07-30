// Source: https://leetcode.com/problems/longest-subarray-with-maximum-bitwise-and/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-29
// At the time of submission:
//   Runtime 3 ms Beats 82.57%
//   Memory 59.06 MB Beats 98.76%

/****************************************
* 
* You are given an integer array `nums` of size `n`.
* Consider a non-empty subarray from `nums` that has the maximum possible bitwise AND.
* • In other words, let `k` be the maximum value of the bitwise AND of any
* __ subarray of `nums`. Then, only subarrays with a bitwise AND equal to `k`
* __ should be considered.
* Return the length of the longest such subarray.
* The bitwise AND of an array is the bitwise AND of all the numbers in it.
* A subarray is a contiguous sequence of elements within an array.
*
* Example 1:
* Input: nums = [1,2,3,3,2,2]
* Output: 2
* Explanation:
* The maximum possible bitwise AND of a subarray is 3.
* The longest subarray with that value is [3,3], so we return 2.
*
* Example 2:
* Input: nums = [1,2,3,4]
* Output: 1
* Explanation:
* The maximum possible bitwise AND of a subarray is 4.
* The longest subarray with that value is [4], so we return 1.
*
* Constraints:
* • 1 <= nums.length <= 10^5
* • 1 <= nums[i] <= 10^6
* 
****************************************/

class Solution {
    // Find the longest contiguous subarray where all elements equal the
    // maximum value in the array. Since bitwise AND can only decrease,
    // the maximum AND of any subarray must equal the max element.
    // We track the longest streak of max values during a single pass.
    // Time Complexity: O(n), where n is the length of nums.
    // Space Complexity: O(1), using constant extra space.
    public int longestSubarray(int[] nums) {
        int maxValue = 0, maxLength = 0, currentLength = 0;
        for (int num : nums) {
            if (num == maxValue) {
                currentLength++;
                maxLength = Math.max(maxLength, currentLength);
            } else {
                currentLength = 0;
                if (num > maxValue) {
                    maxValue = num;
                    maxLength = 1;
                    currentLength++;
                }
            }
        }
        return maxLength;
    }
}