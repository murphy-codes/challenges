// Source: https://leetcode.com/problems/maximum-distance-between-a-pair-of-values/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-19
// At the time of submission:
//   Runtime 3 ms Beats 98.64%
//   Memory 89.94 MB Beats 80.34%

/****************************************
* 
* You are given two non-increasing 0-indexed integer arrays `nums1`​​​​​​ and `nums2​​​​​​`.
* A pair of indices `(i, j)`, where `0 <= i < nums1.length` and
* _ `0 <= j < nums2.length`, is valid if both `I <= j` and `nums1[i] <= nums2[j]`.
* _ The distance of the pair is `j - i​​​​`.
* Return the maximum distance of any valid pair `(i, j)`. If there are no
* _ valid pairs, return `0`.
* An array `arr` is non-increasing if `arr[i-1] >= arr[i]` for every
* _ `1 <= i < arr.length`.
*
* Example 1:
* Input: nums1 = [55,30,5,4,2], nums2 = [100,20,10,10,5]
* Output: 2
* Explanation: The valid pairs are (0,0), (2,2), (2,3), (2,4), (3,3), (3,4), and (4,4).
* The maximum distance is 2 with pair (2,4).
*
* Example 2:
* Input: nums1 = [2,2,2], nums2 = [10,10,1]
* Output: 1
* Explanation: The valid pairs are (0,0), (0,1), and (1,1).
* The maximum distance is 1 with pair (0,1).
*
* Example 3:
* Input: nums1 = [30,29,19,5], nums2 = [25,25,25,25,25]
* Output: 2
* Explanation: The valid pairs are (2,2), (2,3), (2,4), (3,3), and (3,4).
* The maximum distance is 2 with pair (2,4).
*
* Constraints:
* • `1 <= nums1.length, nums2.length <= 10^5`
* • `1 <= nums1[i], nums2[j] <= 10^5`
* • Both `nums1` and `nums2` are non-increasing.
* 
****************************************/

class Solution {
    // Use two pointers on non-increasing arrays.
    // For each i, move j forward while nums2[j] >= nums1[i]
    // to maximize distance (j - i). If invalid, increment i.
    // Pointer j never moves backward, ensuring linear scan.
    // Time: O(n + m), Space: O(1).
    public int maxDistance(int[] nums1, int[] nums2) {
        int i = 0, j = 0;
        int maxDist = 0;

        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] <= nums2[j]) {
                maxDist = Math.max(maxDist, j - i);
                j++; // try to expand distance
            } else {
                i++; // need smaller nums1[i]
                if (i > j) j = i; // maintain i <= j
            }
        }

        return maxDist;
    }
}