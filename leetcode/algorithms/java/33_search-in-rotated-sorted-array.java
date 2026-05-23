// Source: https://leetcode.com/problems/search-in-rotated-sorted-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-22
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 43.83 MB Beats 45.14%

/****************************************
* 
* There is an integer array `nums` sorted in ascending order
* _ (with distinct values).
* Prior to being passed to your function, `nums` is possibly left rotated at an
* _ unknown index `k` `(1 <= k < nums.length)` such that the resulting array is
* _ `[nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]`
* _ (0-indexed). For example, `[0,1,2,4,5,6,7]` might be left rotated by `3`
* _ indices and become `[4,5,6,7,0,1,2]`.
* Given the array `nums` after the possible rotation and an integer target,
* _ return the index of target if it is in `nums`, or -1 if it is not in `nums`.
* You must write an algorithm with `O(log n)` runtime complexity.
*
* Example 1:
* Input: nums = [4,5,6,7,0,1,2], target = 0
* Output: 4
*
* Example 2:
* Input: nums = [4,5,6,7,0,1,2], target = 3
* Output: -1
*
* Example 3:
* Input: nums = [1], target = 0
* Output: -1
*
* Constraints:
* • `1 <= nums.length <= 5000`
* • `-10^4 <= nums[i] <= 10^4`
* • All values of `nums` are unique.
* • `nums` is an ascending array that is possibly rotated.
* • `-10^4 <= target <= 10^4`
* 
****************************************/

class Solution {
    // Binary search identifies which half remains sorted each step.
    // Check whether target lies inside the sorted interval range.
    // Discard the impossible half while preserving logarithmic search.
    // Time: O(log n), Space: O(1) // Time: O(n) if duplicates present
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + ((right - left) / 2);

            if (nums[mid] == target) {
                return mid;
            }

            // Duplicate ambiguity case (needed for LC 81)
            if (nums[left] == nums[mid] &&
                nums[mid] == nums[right]) {

                left++;
                right--;
            }

            // Left half is sorted
            else if (nums[left] <= nums[mid]) {

                if (target >= nums[left] &&
                    target < nums[mid]) {

                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            // Right half is sorted
            else {

                if (target > nums[mid] &&
                    target <= nums[right]) {

                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }

        return -1;
    }
}