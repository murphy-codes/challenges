// Source: https://leetcode.com/problems/merge-two-2d-arrays-by-summing-values/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-03-02

/****************************************
* 
* You are given two 2D integer arrays `nums1` and `nums2`.
* • `nums1[i] = [id_i, val_i]` indicate that the number with the id `id_i` has a value equal to `val_i`.
* • `nums2[i] = [id_i, val_i]` indicate that the number with the id `id_i` has a value equal to `val_i`.
* Each array contains unique ids and is sorted in ascending order by id.
* Merge the two arrays into one array that is sorted in ascending order by id, respecting the following conditions:
* • Only ids that appear in at least one of the two arrays should be included in the resulting array.
* • Each id should be included only once and its value should be the sum of the values of this id in the two arrays. If the id does not exist in one of the two arrays, then assume its value in that array to be `0`.
* Return the resulting array. The returned array must be sorted in ascending order by id.
*
* Example 1:
* Input: nums1 = [[1,2],[2,3],[4,5]], nums2 = [[1,4],[3,2],[4,1]]
* Output: [[1,6],[2,3],[3,2],[4,6]]
* Explanation: The resulting array contains the following:
* - id = 1, the value of this id is 2 + 4 = 6.
* - id = 2, the value of this id is 3.
* - id = 3, the value of this id is 2.
* - id = 4, the value of this id is 5 + 1 = 6.
*
* Example 2:
* Input: nums1 = [[2,4],[3,6],[5,5]], nums2 = [[1,3],[4,3]]
* Output: [[1,3],[2,4],[3,6],[4,3],[5,5]]
* Explanation: There are no common ids, so we just include each id with its value in the resulting list.
*
* Constraints:
* • 1 <= nums1.length, nums2.length <= 200
* • nums1[i].length == nums2[j].length == 2
* • 1 <= id_i, val_i <= 1000
* • Both arrays contain unique ids.
* • Both arrays are in strictly ascending order by id.
* 
****************************************/

import java.util.ArrayList;

class Solution {
    // This solution merges two sorted 2D arrays using a two-pointer approach.
    // It iterates through both arrays in O(n + m) time, where n and m are lengths
    // of nums1 and nums2. The merged list is stored dynamically in an ArrayList,
    // which is later converted to a 2D array. This ensures optimal time & space
    // complexity: O(n + m) time and O(n + m) space for the output list.
    public int[][] mergeArrays(int[][] nums1, int[][] nums2) {
        List<int[]> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < nums1.length && j < nums2.length) {
            if (nums1[i][0] == nums2[j][0]) {
                result.add(new int[]{nums1[i][0], nums1[i][1] + nums2[j][1]});
                i++;
                j++;
            } else if (nums1[i][0] < nums2[j][0]) {
                result.add(nums1[i]);
                i++;
            } else {
                result.add(nums2[j]);
                j++;
            }
        }

        // Add remaining elements from nums1 or nums2
        while (i < nums1.length) result.add(nums1[i++]);
        while (j < nums2.length) result.add(nums2[j++]);

        return result.toArray(new int[result.size()][]);
    }
}
