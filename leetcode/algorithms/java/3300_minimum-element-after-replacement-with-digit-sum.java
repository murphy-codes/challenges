// Source: https://leetcode.com/problems/longest-common-suffix-queries/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-27
// At the time of submission:
//   Runtime 1 ms Beats 99.99%
//   Memory 44.42 MB Beats 95.90%

/****************************************
* 
* You are given an integer array `nums`.
* You replace each element in `nums` with the sum of its digits.
* Return the minimum element in `nums` after all replacements.
*
* Example 1:
* Input: nums = [10,12,13,14]
* Output: 1
* Explanation:
* nums becomes [1, 3, 4, 5] after all replacements, with minimum element 1.
*
* Example 2:
* Input: nums = [1,2,3,4]
* Output: 1
* Explanation:
* nums becomes [1, 2, 3, 4] after all replacements, with minimum element 1.
*
* Example 3:
* Input: nums = [999,19,199]
* Output: 10
* Explanation:
* nums becomes [27, 10, 19] after all replacements, with minimum element 10.
*
* Constraints:
* • `1 <= nums.length <= 100`
* • `1 <= nums[i] <= 10^4`
* 
****************************************/

class Solution {
    // Compute digit sums using n - 9 * Σ(n / 10^k), which equals the
    // sum of digits for numbers up to 5 digits (per constraints).
    // Track the minimum transformed value while scanning the array.
    // Time: O(n), Space: O(1)
    public int minElement(int[] nums) {
        // Maximum possible digit sum for nums[i] <= 10000 is: 36
        // 9 + 9 + 9 + 9 = 36 … // 1 + 0 + 0 + 0 + 0 = 1
        int mDS = 36;
        for (int i : nums)
            mDS = Math.min(mDS, i - 9 * ((i/10) + (i/100) + (i/1000) + (i/10000)));
        return mDS;
    }
}