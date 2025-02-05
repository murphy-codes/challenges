// Source: https://leetcode.com/problems/longest-strictly-increasing-or-strictly-decreasing-subarray/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-02-04

/****************************************
* 
* You are given an array of integers `nums`. Return the length of the longest subarray of `nums` which is either strictly increasing or strictly decreasing.
* 
* Example 1:
* Input: nums = [1,4,3,3,2]
* Output: 2
* Explanation:
* The strictly increasing subarrays of nums are [1], [2], [3], [3], [4], and [1,4].
* The strictly decreasing subarrays of nums are [1], [2], [3], [3], [4], [3,2], and [4,3].
* Hence, we return 2.
* 
* Example 2:
* Input: nums = [3,3,3,3]
* Output: 1
* Explanation:
* The strictly increasing subarrays of nums are [3], [3], [3], and [3].
* The strictly decreasing subarrays of nums are [3], [3], [3], and [3].
* Hence, we return 1.
* 
* Example 3:
* Input: nums = [3,2,1]
* Output: 3
* Explanation:
* The strictly increasing subarrays of nums are [3], [2], and [1].
* The strictly decreasing subarrays of nums are [3], [2], [1], [3,2], [2,1], and [3,2,1].
* Hence, we return 3.
* 
* Constraints:
* • 1 <= nums.length <= 50
* • 1 <= nums[i] <= 50
* 
****************************************/

class Solution {
    // Traverse the array while tracking the longest increasing or decreasing subarray.  
    // Use a variable to store the current subarray length and update it as needed.  
    // Reset the count when switching between increasing and decreasing sequences.  
    // Maintain a `longest` variable to track the max length found.  
    // Time Complexity: O(n), as we iterate through the array once.  
    // Space Complexity: O(1), as only a few variables are used.  
    public int longestMonotonicSubarray(int[] nums) {
        int longest = 1, current = 1, direction = 0; // 0: none, 1: increasing, -1: decreasing

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                if (direction == 1) {
                    current++;
                } else {
                    longest = Math.max(longest, current);
                    current = 2;
                    direction = 1;
                }
            } else if (nums[i] < nums[i - 1]) {
                if (direction == -1) {
                    current++;
                } else {
                    longest = Math.max(longest, current);
                    current = 2;
                    direction = -1;
                }
            } else {
                longest = Math.max(longest, current);
                current = 1;
                direction = 0;
            }
        }

        return Math.max(longest, current);
    }
}
