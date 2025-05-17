// Source: https://leetcode.com/problems/sort-colors/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-16
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 42.19 MB Beats 30.03%

/****************************************
* 
* Given an array `nums` with `n` objects colored red, white, or blue, sort them
* _ in-place so that objects of the same color are adjacent, with the colors in
* _ the order red, white, and blue.
* We will use the integers `0`, `1`, and `2` to represent the colors
* _ red, white, and blue, respectively.
* You must solve this problem without using the library's sort function.
*
* Example 1:
* Input: nums = [2,0,2,1,1,0]
* Output: [0,0,1,1,2,2]
*
* Example 2:
* Input: nums = [2,0,1]
* Output: [0,1,2]
*
* Constraints:
* • n == nums.length
* • 1 <= n <= 300
* • nums[i] is either 0, 1, or 2.
*
* Follow up: Could you come up with a one-pass algorithm using
* _ only constant extra space?
* 
****************************************/

class Solution {
    // One-pass in-place sorting using Dutch National Flag algorithm
    // low: boundary for 0s; mid: current element; high: boundary for 2s
    // Time complexity: O(n); Space complexity: O(1)
    // Guarantees in-place sorting with a single scan of the array
    public void sortColors(int[] nums) {
        int low = 0, mid = 0, high = nums.length - 1;
        while (mid <= high) {
            if (nums[mid] == 0) {
                swap(nums, low++, mid++);
            } else if (nums[mid] == 1) {
                mid++;
            } else { // nums[mid] == 2
                swap(nums, mid, high--);
            }
        }
    }

    // Helper method to swap two elements in the array
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
