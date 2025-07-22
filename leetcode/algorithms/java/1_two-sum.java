// Source: https://leetcode.com/problems/two-sum/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2024-12-26
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 45.11 MB Beats 30.56%

/****************************************
* 
* Given an array of integers nums and an integer target, return indices of the two 
* _ numbers such that they add up to target.
* You may assume that each input would have exactly one solution, and you may not 
* _ use the same element twice.
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
* • 2 <= nums.length <= 10^4
* • -10^9 <= nums[i] <= 10^9
* • -10^9 <= target <= 10^9
* • Only one valid answer exists.
*  
* Follow-up: Can you come up with an algorithm that is less than O(n^2) time complexity?
* 
****************************************/

class Solution 
{
    // This solution uses a brute-force approach by comparing all unique pairs
    // in the array. It does not use any extra space, avoiding data structures
    // like HashMaps. Time complexity is O(n^2), and space complexity is O(1).
    // Though less efficient for large inputs, it performs well on small arrays.
    public int[] twoSum(int[] nums, int target) 
    {
        // Loop through all differences (i is the gap between two indices)
        for (int gap = 1; gap < nums.length; gap++) 
        {
            // For each gap, compare nums[j - gap] and nums[j]
            for (int j = gap; j < nums.length; j++) 
            {
                // Check if the pair adds up to the target
                if (nums[j] + nums[j - gap] == target) 
                {
                    return new int[] { j - gap, j };
                }
            }
        }
        return null; // As per constraints, this shouldn't happen
    }
}
