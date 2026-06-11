// Source: https://leetcode.com/problems/longest-common-suffix-queries/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-27
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 44.68 MB Beats 86.90%

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
    public int minElement(int[] nums) {
        int minDE = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++)
            minDE = Math.min(minDE, getDigitSum(nums[i]));
        return minDE;
    }
    
    private int getDigitSum(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
}