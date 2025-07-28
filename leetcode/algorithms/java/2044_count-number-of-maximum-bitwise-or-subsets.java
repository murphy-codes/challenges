// Source: https://leetcode.com/problems/count-number-of-maximum-bitwise-or-subsets/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-27
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 41.83 MB Beats 26.23%

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
    // Backtrack through all subsets, tracking the running bitwise OR.
    // If current OR matches the maximum OR, count all remaining combinations.
    // This avoids exploring redundant paths and speeds up recursion.
    // Time complexity: O(2^n), but optimized with pruning when OR == max.
    // Space complexity: O(n) for recursion stack (n = nums.length).

    private int count = 0;

    public int countMaxOrSubsets(int[] nums) {
        int maxOr = 0;

        // Step 1: Calculate the maximum OR possible from all elements
        for (int num : nums) {
            maxOr |= num;
        }

        // Step 2: Begin backtracking from index 0 with OR = 0
        backtrack(nums, maxOr, 0, 0);
        return count;
    }

    private void backtrack(int[] nums, int targetOr, int currentOr, int index) {
        // Optimization: if current OR reaches max, count all remaining subsets
        if (currentOr == targetOr) {
            count += 1 << (nums.length - index); // 2^(n - index)
            return;
        }

        // Explore further by including each number starting from current index
        for (int i = index; i < nums.length; i++) {
            backtrack(nums, targetOr, currentOr | nums[i], i + 1);
        }
    }
}
