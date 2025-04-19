// Source: https://leetcode.com/count-the-number-of-fair-pairs/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-18
// At the time of submission:
//   Runtime 24 ms Beats 78.66%
//   Memory 57.24 MB Beats 71.77%

/****************************************
* 
* Given a 0-indexed integer array `nums` of size `n` and two integers
* _ `lower` and `upper`, return the number of fair pairs.
* A pair `(i, j)` is fair if:
* • `0 <= i < j < n`, and
* • `lower <= nums[i] + nums[j] <= upper`
*
* Example 1:
* Input: nums = [0,1,7,4,4,5], lower = 3, upper = 6
* Output: 6
* Explanation: There are 6 fair pairs: (0,3), (0,4), (0,5), (1,3), (1,4), and (1,5).
*
* Example 2:
* Input: nums = [1,7,9,2,5], lower = 11, upper = 11
* Output: 1
* Explanation: There is a single fair pair: (2,3).
*
* Constraints:
* • 1 <= nums.length <= 10^5
* • nums.length == n
* • -10^9 <= nums[i] <= 10^9
* • -10^9 <= lower <= upper <= 10^9
* 
****************************************/

import java.util.Arrays;

class Solution {
    // This solution sorts the array (O(n log n)) and then uses two calls to a 
    // linear two-pointer method to count valid pairs. First, it counts pairs 
    // with sum ≤ upper, then subtracts pairs with sum < lower (i.e. ≤ lower-1).
    // Time complexity: O(n log n) due to sorting. Space complexity: O(1) extra.
    // Efficient for large arrays thanks to avoiding repeated binary searches.

    public long countFairPairs(int[] nums, int lower, int upper) {
        Arrays.sort(nums);
        return countPairsWithSumAtMost(nums, upper) 
             - countPairsWithSumAtMost(nums, lower - 1);
    }

    private long countPairsWithSumAtMost(int[] nums, int target) {
        long pairCount = 0;
        int left = 0, right = nums.length - 1;
        while (left < right) {
            if (nums[left] + nums[right] > target) right--; // If the sum is too large, decrease right to reduce the sum
            else pairCount += right - left++; // All pairs from (left, left+1) to (left, right) are valid
        }
        return pairCount;
    }
}
