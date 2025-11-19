// Source: https://leetcode.com/problems/keep-multiplying-found-values-by-two/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-18
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 45.57 MB Beats 21.57%

/****************************************
* 
* You are given an array of integers `nums`. You are also given an integer
* _ `original` which is the first number that needs to be searched for in `nums`.
* You then do the following steps:
* 1. If `original` is found in `nums`, multiply it by two (i.e., set `original = 2 * original`).
* 2. Otherwise, stop the process.
* 3. Repeat this process with the new number as long as you keep finding the number.
* Return the final value of `original`.
*
* Example 1:
* Input: nums = [5,3,6,1,12], original = 3
* Output: 24
* Explanation:
* - 3 is found in nums. 3 is multiplied by 2 to obtain 6.
* - 6 is found in nums. 6 is multiplied by 2 to obtain 12.
* - 12 is found in nums. 12 is multiplied by 2 to obtain 24.
* - 24 is not found in nums. Thus, 24 is returned.
*
* Example 2:
* Input: nums = [2,7,9], original = 4
* Output: 4
* Explanation:
* - 4 is not found in nums. Thus, 4 is returned.
*
* Constraints:
* • `1 <= nums.length <= 1000`
* • `1 <= nums[i], original <= 1000`
* 
****************************************/

class Solution {
    // Scans through all of nums on each iteration, doubling when found.
    // Due to small constraints, this linear scan is efficient in practice.
    // Time complexity is O(n * k), Space complexity is O(1).
    // Where k is the number of doublings (at most ~10). 
    public int findFinalValue(int[] nums, int original) {
        boolean keepSearching = true;
        while (keepSearching) {            
            keepSearching = existsInArray(nums, original); // Check if cur val exists
            original *= 2; // If so, double it for next iteration
        }
        return original / 2; // We undo last, extra doubling
    }
    // Returns true if target value is found in nums
    public boolean existsInArray(int[] nums, int target) {
        for (int value : nums) {
            if (value == target) {
                return true;
            }
        }
        return false;
    }
}