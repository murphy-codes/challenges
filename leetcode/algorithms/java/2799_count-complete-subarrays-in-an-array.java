// Source: https://leetcode.com/problems/minimum-index-of-a-valid-split/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-25
// At the time of submission:
//   Runtime 3 ms Beats 99.49%
//   Memory 45.18 MB Beats 49.84%

/****************************************
* 
* You are given an array `nums` consisting of positive integers.
* We call a subarray of an array complete if the following condition is satisfied:
* • The number of distinct elements in the subarray is equal to the number of
* __ distinct elements in the whole array.
* Return the number of complete subarrays.
* A subarray is a contiguous non-empty part of an array.
*
* Example 1:
* Input: nums = [1,3,1,2,2]
* Output: 4
* Explanation: The complete subarrays are the following: [1,3,1,2], [1,3,1,2,2], [3,1,2] and [3,1,2,2].
*
* Example 2:
* Input: nums = [5,5,5,5]
* Output: 10
* Explanation: The array consists only of the integer 5, so any subarray is complete. The number of subarrays that we can choose is 10.
*
* Constraints:
* • 1 <= nums.length <= 1000
* • 1 <= nums[i] <= 2000
* 
****************************************/

class Solution {
    // Count total unique elements in the input array first.
    // Use sliding window with two pointers to maintain a window that contains
    // all unique elements. For each such window, all subarrays ending at or after
    // the current right pointer are valid. Time: O(n), Space: O(1) (fixed array).
    public int countCompleteSubarrays(int[] nums) {
        int result = 0;
        int totalUnique = 0;
        boolean[] seen = new boolean[2001];

        // Count total number of unique elements in the array
        for (int num : nums) {
            if (!seen[num]) {
                seen[num] = true;
                totalUnique++;
            }
        }

        int[] freq = new int[2001]; // Frequency of elements in current window
        int uniqueInWindow = 0;
        int left = 0;

        for (int right = 0; right < nums.length; right++) {
            if (freq[nums[right]] == 0) uniqueInWindow++;
            freq[nums[right]]++;

            // Shrink window from left while it's "complete"
            while (left <= right && uniqueInWindow == totalUnique) {
                result += nums.length - right; // Count all valid suffixes
                freq[nums[left]]--;
                if (freq[nums[left]] == 0) uniqueInWindow--;
                left++;
            }
        }

        return result;
    }
}
