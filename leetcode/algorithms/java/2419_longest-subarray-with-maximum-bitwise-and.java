// Source: https://leetcode.com/problems/longest-subarray-with-maximum-bitwise-and/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-29
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 59.34 MB Beats 93.78%

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
    // Efficient single-pass solution to find the longest subarray of max values.
    // Dynamically updates the max while skipping full streaks via fast-forwarding.
    // Time: O(n) — visits each element at most once.
    // Space: O(1) — uses only a few variables, no extra data structures.
    public int longestSubarray(int[] nums) {
        int max = 0, result = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= max) {
                if (nums[i] > max) {
                    max = nums[i];
                    result = 1;
                }
                int count = 0;
                while (i < nums.length && nums[i] == max) {
                    count++;
                    i++;
                }
                result = Math.max(result, count);
                i--; // Step back since outer loop will increment again
            }
        }

        return result;
    }
}
