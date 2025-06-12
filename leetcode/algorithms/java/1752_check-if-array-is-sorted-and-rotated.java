// Source: https://leetcode.com/problems/check-if-array-is-sorted-and-rotated/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-02-02

/****************************************
* 
* Given an array `nums`, return `true` if the array was originally sorted in non-decreasing order, then rotated some number of positions (including zero). Otherwise, return `false`.
* 
* There may be duplicates in the original array.
* 
* Note: An array `A` rotated by `x` positions results in an array `B` of the same length such that `A[i] == B[(i+x) % A.length]`, where `%` is the modulo operation.
* 
* Example 1:
* Input: nums = [3,4,5,1,2]
* Output: true
* Explanation: [1,2,3,4,5] is the original sorted array.
* You can rotate the array by x = 3 positions to begin on the the element of value 3: [3,4,5,1,2].
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
* • 1 <= nums.length <= 100
* • 1 <= nums[i] <= 100
* 
****************************************/

class Solution {
    // Iterate through the array to count drops (where nums[i] < nums[i-1]).
    // A valid rotated sorted array can have at most one drop in order.
    // Also check the wrap-around case (nums[n-1] > nums[0]) as a second drop.
    // If more than one drop exists, the array is not a rotated sorted array.
    // Time Complexity: O(n), as we traverse the array once. Space Complexity: O(1).
    public boolean check(int[] nums) {
        int countDrop = 0;
        int n = nums.length;

        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[i - 1]) {
                countDrop++;
            }
        }

        if (nums[n - 1] > nums[0]) {
            countDrop++;
        }

        return countDrop <= 1;
    }
}

