// Source: https://leetcode.com/problems/check-if-array-is-sorted-and-rotated/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-22
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 42.80 MB Beats 79.50%

/****************************************
* 
* Given an array `nums`, return `true` if the array was originally sorted in
* _ non-decreasing order, then rotated some number of positions
* _ (including zero). Otherwise, return `false`.
* There may be duplicates in the original array.
* Note: An array `A` rotated by `x` positions results in an array `B` of the
* _ same length such that `B[i] == A[(i+x) % A.length]` for every valid index `i`.
*
* Example 1:
* Input: nums = [3,4,5,1,2]
* Output: true
* Explanation: [1,2,3,4,5] is the original sorted array.
* You can rotate the array by x = 2 positions to begin on the element of value 3: [3,4,5,1,2].
*
* Example 2:
* Input: nums = [2,1,3,4]
* Output: false
* Explanation: There is no sorted array once rotated that can make nums.
*
* Example 3:
* Input: nums = [1,2,3]
* Output: true
* Explanation: [1,2,3] is the original sorted array.
* You can rotate the array by x = 0 positions (i.e. no rotation) to make nums.
*
* Constraints:
* • `1 <= nums.length <= 100`
* • `1 <= nums[i] <= 100`
* 
****************************************/

class Solution {
    // Iterate through the array checking for a drop
    // After 1 drop, confirm elements meet criteria
    // Finally, check last element against first (if drop)
    // Time Complexity: O(n). Space Complexity: O(1).
    public boolean check(int[] nums) {
        int n = nums.length;
        if (n == 1) return true;
        boolean drop = false;
        for (int i = 1; i < n; i++) {
            if (drop) {
                if (nums[i]<nums[i-1] || nums[i]>nums[0]) return false;
            } else {
                if (nums[i]<nums[i-1]) drop = true;
            }
        }
        if (drop && nums[n-1]>nums[0]) return false;
        return true;
    }
}