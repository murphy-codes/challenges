// Source: https://leetcode.com/problems/maximum-unique-subarray-sum-after-deletion/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-26
// At the time of submission:
//   Runtime 1 ms Beats 99.84%
//   Memory 42.42 MB Beats 77.75%

/****************************************
* 
* You are given an integer array `nums`.
* You are allowed to delete any number of elements from `nums` without making it
* _ empty. After performing the deletions, select a subarray of `nums` such that:
* 1. All elements in the subarray are unique.
* 2. The sum of the elements in the subarray is maximized.
* Return the maximum sum of such a subarray.
*
* Example 1:
* Input: nums = [1,2,3,4,5]
* Output: 15
* Explanation:
* Select the entire array without deleting any element to obtain the maximum sum.
*
* Example 2:
* Input: nums = [1,1,0,1,1]
* Output: 1
* Explanation:
* Delete the element nums[0] == 1, nums[1] == 1, nums[2] == 0, and nums[3] == 1.
* _ Select the entire array [1] to obtain the maximum sum.
*
* Example 3:
* Input: nums = [1,2,-1,-2,1,0,-1]
* Output: 3
* Explanation:
* Delete the elements nums[2] == -1 and nums[3] == -2, and select the subarray
* _ [2, 1] from [1, 2, 1, 0, -1] to obtain the maximum sum.
*
* Constraints:
* • 1 <= nums.length <= 100
* • -100 <= nums[i] <= 100
* 
****************************************/

class Solution {
    // Iterate through the array to sum unique positive integers only.  
    // Use a boolean array to track which values in [1,100] were seen.  
    // Track the overall maximum value to handle all-negative inputs.  
    // Time Complexity: O(n), where n is the number of elements in nums.  
    // Space Complexity: O(1), fixed size auxiliary array of size 101.
    public int maxSum(int[] nums) {
        boolean[] seen = new boolean[101];
        int sum_unique = 0;
        int non_empty = Integer.MIN_VALUE;
        for (int num : nums) {
            non_empty = Math.max(non_empty, num);
            if (num <= 0) continue;
            if (!seen[num]) {
                seen[num] = true;
                sum_unique += num;
            }
        }
        return non_empty < 0 ? non_empty : sum_unique;
    }
}