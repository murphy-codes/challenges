// Source: https://leetcode.com/problems/build-array-from-permutation/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-05
// At the time of submission:
//   Runtime 1 ms Beats 98.72%
//   Memory 45.36 MB Beats 63.62%

/****************************************
* 
* Given a zero-based permutation `nums` (0-indexed), build an array
* _ `ans` of the same length where `ans[i] = nums[nums[i]]` for each
* _ `0 <= i < nums.length` and return it.
* A zero-based permutation `nums` is an array of distinct integers
* _ from `0` to `nums.length - 1` (inclusive).
*
* Example 1:
* Input: nums = [0,2,1,5,3,4]
* Output: [0,1,2,4,5,3]
* Explanation: The array ans is built as follows:
* ans = [nums[nums[0]], nums[nums[1]], nums[nums[2]], nums[nums[3]], nums[nums[4]], nums[nums[5]]]
* _    = [nums[0], nums[2], nums[1], nums[5], nums[3], nums[4]]
* _    = [0,1,2,4,5,3]
*
* Example 2:
* Input: nums = [5,0,1,2,3,4]
* Output: [4,5,0,1,2,3]
* Explanation: The array ans is built as follows:
* ans = [nums[nums[0]], nums[nums[1]], nums[nums[2]], nums[nums[3]], nums[nums[4]], nums[nums[5]]]
* _    = [nums[5], nums[0], nums[1], nums[2], nums[3], nums[4]]
* _    = [4,5,0,1,2,3]
*
* Constraints:
* • 1 <= nums.length <= 1000
* • 0 <= nums[i] < nums.length
* • The elements in `nums` are distinct.
*
* Follow-up: Can you solve it without using an extra space (i.e., O(1) memory)?
* 
****************************************/

class Solution {
    // Build a new array where each element at index i is set to nums[nums[i]].
    // Since nums is a valid permutation (distinct values from 0 to n-1),
    // all indices are within bounds. This uses O(n) time and O(n) space.
    // Time Complexity: O(n) — one pass through the array.
    // Space Complexity: O(n) — for the output array `ans`.
    public int[] buildArray(int[] nums) {
        int[] ans = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            ans[i]=nums[nums[i]];
        }
        return ans;
    }
}