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

import java.util.ArrayList;

class Solution {
    // This solution partitions `nums` into two lists: one for elements < pivot
    // and one for elements > pivot. The count of pivot elements is stored as an
    // integer. The result array is then reconstructed in O(n) while maintaining
    // relative order. The algorithm runs in O(n) time and uses O(n) extra space, 
    // making it efficient for large inputs.
    public int[] pivotArray(int[] nums, int pivot) {
        ArrayList<Integer> less = new ArrayList<>();
        ArrayList<Integer> greater = new ArrayList<>();
        int pivotCount = 0;

        // Step 1: Classify elements
        for (int num : nums) {
            if (num < pivot) {
                less.add(num);
            } else if (num == pivot) {
                pivotCount++;
            } else {
                greater.add(num);
            }
        }

        // Step 2: Construct the result array
        int[] result = new int[nums.length];
        int index = 0;

        for (int num : less) result[index++] = num;  // Less-than pivot
        while (pivotCount-- > 0) result[index++] = pivot;  // Pivot values
        for (int num : greater) result[index++] = num;  // Greater-than pivot

        return result;
    }
}
