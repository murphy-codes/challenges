// Source: https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-17
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 44.94 MB Beats 35.66%

/****************************************
* 
* Suppose an array of length `n` sorted in ascending order is rotated between `1`
* _ and `n` times. For example, the array` nums = [0,1,4,4,5,6,7]` might become:
* • `[4,5,6,7,0,1,4] if it was rotated 4 times.
* • `[0,1,4,4,5,6,7] if it was rotated 7 times.
* Notice that rotating an array `[a[0], a[1], a[2], ..., a[n-1]]` 1 time
* _ results in the array `[a[n-1], a[0], a[1], a[2], ..., a[n-2]]`.
* Given the sorted rotated array `nums` that may contain duplicates, return
* _ the minimum element of this array.
* You must decrease the overall operation steps as much as possible.
*
* Example 1:
* Input: nums = [1,3,5]
* Output: 1
*
* Example 2:
* Input: nums = [2,2,2,0,1]
* Output: 0
*
* Constraints:
* • `n == nums.length`
* • `1 <= n <= 5000`
* • `-5000 <= nums[i] <= 5000`
* • `nums` is sorted and rotated between `1` and `n` times.
*
* Follow up: This problem is similar to [153. Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/description/), but `nums` may contain duplicates. Would this affect the runtime complexity? How and why?
* 
****************************************/

class Solution {
    // Binary search on rotated sorted array with duplicates allowed.
    // Compare mid vs right to identify the unsorted half containing min.
    // If nums[mid] == nums[right], decrement right to remove ambiguity.
    // Time: O(log n) avg, O(n) worst-case; Space: O(1).
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else if (nums[mid] < nums[right]) {
                right = mid;
            } else {
                right--;
            }
        }

        return nums[left];
    }
}