// Source: https://leetcode.com/problems/count-subarrays-where-max-element-appears-at-least-k-times/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-28
// At the time of submission:
//   Runtime 4 ms Beats 100.00%
//   Memory 66.57 MB Beats 6.04%

/****************************************
* 
* You are given an integer array `nums` and a positive integer `k`.
* Return the number of subarrays where the maximum element of `nums`
* _ appears at least `k` times in that subarray.
* A subarray is a contiguous sequence of elements within an array.
*
* Example 1:
* Input: nums = [1,3,2,3,3], k = 2
* Output: 6
* Explanation: The subarrays that contain the element 3 at least 2 times are: [1,3,2,3], [1,3,2,3,3], [3,2,3], [3,2,3,3], [2,3,3] and [3,3].
*
* Example 2:
* Input: nums = [1,4,2,1], k = 3
* Output: 0
* Explanation: No subarray contains the element 4 at least 3 times.
*
* Constraints:
* • 1 <= nums.length <= 10^5
* • 1 <= nums[i] <= 10^6
* • 1 <= k <= 10^5
* 
****************************************/

class Solution {
    // Find the global maximum in nums.
    // Use sliding window to track counts of max value.
    // When window has at least k max elements, count subarrays.
    // Time: O(n), Space: O(1), single pass with simple counters.
    public long countSubarrays(int[] nums, int k) {
        int n = nums.length;
        int max = Integer.MIN_VALUE;
        
        // Find the global maximum value
        for (int num : nums) {
            max = Math.max(max, num);
        }
        
        long result = 0;
        int count = 0; // count of max elements in current window
        int start = 0;
        
        for (int end = 0; end < n; end++) {
            if (nums[end] == max) {
                count++;
            }
            
            while (count >= k) {
                // As soon as window [start, end] has k maxes,
                // all subarrays starting from 'start' and ending at >= end are valid
                result += (n - end);
                
                if (nums[start] == max) {
                    count--;
                }
                start++;
            }
        }
        
        return result;
    }
}
