// Source: https://leetcode.com/problems/jump-game-iv/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-18
// At the time of submission:
//   Runtime 2 ms Beats 88.98%
//   Memory 80.12 MB Beats 91.06%

/****************************************
* 
* Given two integer arrays `nums1` and `nums2`, sorted in non-decreasing
* _ order, return the minimum integer common to both arrays. If there is no
* _ common integer amongst `nums1` and `nums2`, return `-1`.
* Note that an integer is said to be common to `nums1` and `nums2` if both
* _ arrays have at least one occurrence of that integer.
*
* Example 1:
* Input: nums1 = [1,2,3], nums2 = [2,4]
* Output: 2
* Explanation: The smallest element common to both arrays is 2, so we return 2.
*
* Example 2:
* Input: nums1 = [1,2,3,6], nums2 = [2,3,4,5]
* Output: 2
* Explanation: There are two common elements in the array 2 and 3 out of which 2 is the smallest, so 2 is returned.
*
* Constraints:
* • `1 <= nums1.length, nums2.length <= 10^5`
* • `1 <= nums1[i], nums2[j] <= 10^9`
* • Both `nums1` and `nums2` are sorted in non-decreasing order.
* 
****************************************/

class Solution {
    // Use two pointers to scan both sorted arrays simultaneously.
    // Advance the pointer with the smaller value since it cannot match later.
    // First equal pair is the minimum common value due to sorted order.
    // Time: O(n + m), Space: O(1)
    public int getCommon(int[] nums1, int[] nums2) {
        int i = 0;
        int j = 0;

        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] == nums2[j]) {
                return nums1[i];
            }

            if (nums1[i] < nums2[j]) {
                i++;
            } else {
                j++;
            }
        }

        return -1;
    }
}