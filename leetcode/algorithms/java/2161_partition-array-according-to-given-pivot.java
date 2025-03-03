// Source: https://leetcode.com/problems/partition-array-according-to-given-pivot/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-03-02

/****************************************
* 
* You are given a 0-indexed integer array `nums` and an integer `pivot`. Rearrange `nums` such that the following conditions are satisfied:
* • Every element less than `pivot` appears before every element greater than `pivot`.
* • Every element equal to `pivot` appears in between the elements less than and greater than `pivot`.
* • The relative order of the elements less than `pivot` and the elements greater than `pivot` is maintained.
* • More formally, consider every `p_i`, `p_j` where `p_i` is the new position of the `ith` element and `p_j` is the new position of the `jth` element. If `i < j` and both elements are smaller (or larger) than pivot, then `pi < pj`.
* Return `nums` after the rearrangement.
*
* Example 1:
* Input: nums = [9,12,5,10,14,3,10], pivot = 10
* Output: [9,5,3,10,10,12,14]
* Explanation:
* The elements 9, 5, and 3 are less than the pivot so they are on the left side of the array.
* The elements 12 and 14 are greater than the pivot so they are on the right side of the array.
* The relative ordering of the elements less than and greater than pivot is also maintained. [9, 5, 3] and [12, 14] are the respective orderings.
*
* Example 2:
* Input: nums = [-3,4,3,2], pivot = 2
* Output: [-3,2,4,3]
* Explanation:
* The element -3 is less than the pivot so it is on the left side of the array.
* The elements 4 and 3 are greater than the pivot so they are on the right side of the array.
* The relative ordering of the elements less than and greater than pivot is also maintained. [-3] and [4, 3] are the respective orderings.
*
* Constraints:
* • 1 <= nums.length <= 10^5
* • -10^6 <= nums[i] <= 10^6
* • `pivot` equals to an element of `nums`.
* 
****************************************/

class Solution {
    // This solution partitions the array using a single pass and minimal space.
    // We use a result array and two pointers to fill elements < pivot from the start
    // and elements > pivot from the end, while counting occurrences of pivot.
    // Afterward, we place all pivot elements in the middle and reverse the right side.
    // Time Complexity: O(n) (single pass + O(n) reversal); Space Complexity: O(1) aux.
    public int[] pivotArray(int[] nums, int pivot) {
        int n = nums.length;
        int[] result = new int[n];
        int left = 0, right = n - 1, pivotCount = 0;

        // First pass: Count pivot occurrences & populate left/right in one pass
        for (int num : nums) {
            if (num < pivot) {
                result[left++] = num;  // Fill from start
            } else if (num > pivot) {
                result[right--] = num; // Fill from end
            } else {
                pivotCount++;  // Count pivot occurrences
            }
        }

        // Second pass: Place pivot elements in the middle
        while (pivotCount-- > 0) {
            result[left++] = pivot;
        }

        // Reverse the right section since we filled it backwards
        reverseSegment(result, right + 1, n - 1);

        return result;
    }

    private void reverseSegment(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start++] = arr[end];
            arr[end--] = temp;
        }
    }
}
