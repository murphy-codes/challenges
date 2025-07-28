// Source: https://leetcode.com/problems/count-number-of-maximum-bitwise-or-subsets/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-27
// At the time of submission:
//   Runtime 8 ms Beats 80.38%
//   Memory 41.26 MB Beats 55.22%

/****************************************
* 
* Given an integer array `nums`, find the maximum possible bitwise OR of a subset
* _ of `nums` and return the number of different non-empty subsets with the
* _ maximum bitwise OR.
* An array `a` is a subset of an array `b` if `a` can be obtained from `b` by
* _ deleting some (possibly zero) elements of `b`. Two subsets are considered
* _ different if the indices of the elements chosen are different.
* The bitwise OR of an array `a` is equal to
* _ `a[0] OR a[1] OR ... OR a[a.length - 1]` (0-indexed).
*
* Example 1:
* Input: nums = [3,1]
* Output: 2
* Explanation: The maximum possible bitwise OR of a subset is 3. There are 2 subsets with a bitwise OR of 3:
* - [3]
* - [3,1]
*
* Example 2:
* Input: nums = [2,2,2]
* Output: 7
* Explanation: All non-empty subsets of [2,2,2] have a bitwise OR of 2. There are 23 - 1 = 7 total subsets.
*
* Example 3:
* Input: nums = [3,2,1,5]
* Output: 6
* Explanation: The maximum possible bitwise OR of a subset is 7. There are 6 subsets with a bitwise OR of 7:
* - [3,5]
* - [3,1,5]
* - [3,2,5]
* - [3,2,1,5]
* - [2,5]
* - [2,1,5]
*
* Constraints:
* • 1 <= nums.length <= 16
* • 1 <= nums[i] <= 10^5
* 
****************************************/

class Solution {
    // Use backtracking to explore all 2^n subsets of nums.
    // Compute OR value for each subset and count those matching max OR.
    // max OR is the OR of all elements in nums. Time: O(2^n), Space: O(n).
    // Efficient since nums.length ≤ 16, allowing full subset traversal.
    // No extra space used except for recursion stack (depth ≤ n).

    private int count = 0;
    private int maxOr = 0;

    public int countMaxOrSubsets(int[] nums) {
        // Step 1: Compute the maximum OR possible
        for (int num : nums) {
            maxOr |= num;
        }

        // Step 2: Use backtracking to explore all subsets
        backtrack(nums, 0, 0);
        return count;
    }

    private void backtrack(int[] nums, int index, int currentOr) {
        if (index == nums.length) {
            if (currentOr == maxOr) {
                count++;
            }
            return;
        }

        // Include nums[index]
        backtrack(nums, index + 1, currentOr | nums[index]);

        // Exclude nums[index]
        backtrack(nums, index + 1, currentOr);
    }
}
