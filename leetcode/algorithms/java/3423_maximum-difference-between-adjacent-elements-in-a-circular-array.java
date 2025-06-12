// Source: https://leetcode.com/problems/maximum-difference-between-adjacent-elements-in-a-circular-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-11
// At the time of submission:
//   Runtime 1 ms Beats 98.36%
//   Memory 43.25 MB Beats 83.61%

/****************************************
* 
* Given a circular array `nums`, find the
* _ maximum absolute difference between adjacent elements.
* Note: In a circular array, the first and last elements are adjacent.
*
* Example 1:
* Input: nums = [1,2,4]
* Output: 3
* Explanation:
* Because nums is circular, nums[0] and nums[2] are adjacent. They have the maximum absolute difference of |4 - 1| = 3.
*
* Example 2:
* Input: nums = [-5,-10,-5]
* Output: 5
* Explanation:
* The adjacent elements nums[0] and nums[1] have the maximum absolute difference of |-5 - (-10)| = 5.
*
* Constraints:
* • 2 <= nums.length <= 100
* • -100 <= nums[i] <= 100
* 
****************************************/

class Solution {
    // Iterate through the array once to compute the maximum absolute
    // difference between all adjacent elements, including the circular
    // pair formed by the last and first elements. This ensures we check
    // all adjacent positions in a circular array. Time complexity is O(n),
    // and space complexity is O(1) since no extra space is used.
    public int maxAdjacentDistance(int[] nums) {
        int max = Math.abs(nums[nums.length-1]-nums[0]);
        for (int i = 0; i < nums.length-1; i++) {
            max = Math.max(max,Math.abs(nums[i]-nums[i+1]));
        }
        return max;
    }
}