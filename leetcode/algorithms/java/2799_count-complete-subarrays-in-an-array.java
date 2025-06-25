// Source: https://leetcode.com/problems/minimum-index-of-a-valid-split/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-25
// At the time of submission:
//   Runtime 242 ms Beats 24.11%
//   Memory 45.33 MB Beats 26.38%

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

import java.util.HashMap;
import java.util.HashSet;

class Solution {
    // Count total number of distinct elements in the original array.
    // Then, iterate over every subarray and track its distinct elements.
    // Increment the count if a subarray's distinct count matches the original.
    // Time: O(n^2) due to nested loops; Space: O(n) for temporary sets.
    public int countCompleteSubarrays(int[] nums) {
        // Count the total number of distinct elements in the array
        HashSet<Integer> distinctSet = new HashSet<>();
        for (int num : nums) {
            distinctSet.add(num);
        }
        int totalDistinct = distinctSet.size();

        int count = 0;
        // Check every subarray starting at index i
        for (int i = 0; i < nums.length; i++) {
            HashSet<Integer> seen = new HashSet<>();
            for (int j = i; j < nums.length; j++) {
                seen.add(nums[j]);
                if (seen.size() == totalDistinct) {
                    count++;
                }
            }
        }

        return count;
    }
}
