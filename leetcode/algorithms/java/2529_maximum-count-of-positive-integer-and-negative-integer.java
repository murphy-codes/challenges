// Source: https://leetcode.com/problems/maximum-count-of-positive-integer-and-negative-integer/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-03-11

/****************************************
* 
* Given an array nums sorted in non-decreasing order, return the maximum between the number of positive integers and the number of negative integers.
* • In other words, if the number of positive integers in nums is pos and the number of negative integers is neg, then return the maximum of pos and neg.
* Note that 0 is neither positive nor negative.
*
* Example 1:
* Input: nums = [-2,-1,-1,1,2,3]
* Output: 3
* Explanation: There are 3 positive integers and 3 negative integers. The maximum count among them is 3.
*
* Example 2:
* Input: nums = [-3,-2,-1,0,0,1,2]
* Output: 3
* Explanation: There are 2 positive integers and 3 negative integers. The maximum count among them is 3.
*
* Example 3:
* Input: nums = [5,20,66,1314]
* Output: 4
* Explanation: There are 4 positive integers and 0 negative integers. The maximum count among them is 4.
*
* Constraints:
* • 1 <= nums.length <= 2000
* • -2000 <= nums[i] <= 2000
* • `nums` is sorted in a non-decreasing order.
*
* Follow up: Can you solve the problem in O(log(n)) time complexity?
* 
****************************************/
class Solution {
    // Time Complexity: O(log n) -> Two binary searches, each O(log n).
    // Space Complexity: O(1) -> Only a few extra variables are used.
    public int maximumCount(int[] nums) {
        int negCount = findLastNegativeIndex(nums) + 1; // Count of negative numbers
        int posCount = nums.length - findFirstPositiveIndex(nums); // Count of positives
        return Math.max(negCount, posCount);
    }

    // Binary search for the first index of a positive number
    private int findFirstPositiveIndex(int[] nums) {
        int left = 0, right = nums.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= 1) right = mid; // Look left for the first positive
            else left = mid + 1;
        }
        return left; // Index of first positive element (or length if none exist)
    }

    // Binary search for the last negative number's index
    private int findLastNegativeIndex(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < 0) left = mid + 1; // Move right to find the last negative
            else right = mid - 1;
        }
        return right; // Index of last negative number
    }
}
