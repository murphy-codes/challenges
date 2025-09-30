// Source: https://leetcode.com/problems/find-triangular-sum-of-an-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-29
// At the time of submission:
//   Runtime 42 ms Beats 86.32%
//   Memory 44.55 MB Beats 70.44%

/****************************************
* 
* You are given a 0-indexed integer array `nums`, where `nums[i]` is a digit
* _ between `0` and `9` (inclusive).
* The triangular sum of `nums` is the value of the only element present in
* _ `nums` after the following process terminates:
* 1. Let `nums` comprise of `n` elements. If `n == 1`, end the process.
* __ Otherwise, create a new 0-indexed integer array `newNums` of length `n - 1`.
* 2. For each index `i`, where `0 <= i < n - 1`, assign the value of
* __ `newNums[i]` as `(nums[i] + nums[i+1]) % 10`, where `%` denotes modulo operator.
* 3. Replace the array `nums` with `newNums`.
* 4. Repeat the entire process starting from step 1.
* Return the triangular sum of `nums`.
*
* Example 1:
* [image: https://assets.leetcode.com/uploads/2022/02/22/ex1drawio.png]
* Input: nums = [1,2,3,4,5]
* Output: 8
* Explanation:
* The above diagram depicts the process from which we obtain the triangular sum of the array.
*
* Example 2:
* Input: nums = [5]
* Output: 5
* Explanation:
* Since there is only one element in nums, the triangular sum is the value of that element itself.
*
* Constraints:
* • 1 <= nums.length <= 1000
* • 0 <= nums[i] <= 9
* 
****************************************/

class Solution {
    // Iteratively simulate the triangular sum by updating nums in place.
    // After each pass, nums[0..size-2] becomes the new row of reduced size.
    // Continue until only one element remains, which is the triangular sum.
    // Time complexity: O(n^2) due to nested loops; Space complexity: O(1).
    public int triangularSum(int[] nums) {
        for (int size = nums.length; size > 1; size--) {
            for (int i = 0; i < size - 1; i++) {
                nums[i] = (nums[i] + nums[i + 1]) % 10;
            }
        }
        return nums[0];
    }
}