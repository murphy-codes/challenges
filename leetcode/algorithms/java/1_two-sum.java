// Source: https://leetcode.com/problems/two-sum/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2024-12-26
// At the time of submission:
//   Runtime 2 ms Beats 98.95%
//   Memory 45.11 MB Beats 30.56%

/****************************************
* 
* Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
* You may assume that each input would have exactly one solution, and you may not use the same element twice.
* You can return the answer in any order.
* 
* Example 1:
* Input: nums = [2,7,11,15], target = 9
* Output: [0,1]
* Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
* 
* Example 2:
* Input: nums = [3,2,4], target = 6
* Output: [1,2]
* 
* Example 3:
* Input: nums = [3,3], target = 6
* Output: [0,1]
* 
* Constraints:
* 2 <= nums.length <= 10^4
* -10^9 <= nums[i] <= 10^9
* -10^9 <= target <= 10^9
* Only one valid answer exists.
*  
* Follow-up: Can you come up with an algorithm that is less than O(n^2) time complexity?
* 
****************************************/

import java.util.HashMap;

class Solution {
    // The simplest solution has an O(n^2) time complexity, where we use a nested loop to 
    // check every pair of elements in the array (i.e., a brute-force approach). While 
    // straightforward, this method becomes increasingly inefficient for larger arrays.
    // To optimize for better performance, as the 'Follow-up' suggests, we'll employ 
    // a hash map-based approach to achieve O(n) time complexity.
    public int[] twoSum(int[] nums, int target) {
        // Use a HashMap to store numbers and their indices
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            // Calculate the complement
            int complement = target - nums[i];

            // Check if the complement exists in the map
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }

            // Add the current number and its index to the map
            map.put(nums[i], i);
        }

        // Per problem constraints, this won't be reached
        throw new IllegalArgumentException("No solution exists");
    }
}