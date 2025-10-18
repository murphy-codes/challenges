// Source: https://leetcode.com/problems/maximum-number-of-distinct-elements-after-operations/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-17
// At the time of submission:
//   Runtime 20 ms Beats 47.59%
//   Memory 58.61 MB Beats 31.72%

/****************************************
* 
* You are given an integer array `nums` and an integer `k`.
* You are allowed to perform the following operation on each element of the
* _ array at most once:
* • Add an integer in the range `[-k, k]` to the element.
* Return the maximum possible number of distinct elements in `nums` after
* _ performing the operations.
*
* Example 1:
* Input: nums = [1,2,2,3,3,4], k = 2
* Output: 6
* Explanation:
* nums changes to [-1, 0, 1, 2, 3, 4] after performing operations on the first four elements.
*
* Example 2:
* Input: nums = [4,4,4,4], k = 1
* Output: 3
* Explanation:
* By adding -1 to nums[0] and 1 to nums[1], nums changes to [3, 5, 4, 4].
*
* Constraints:
* • `1 <= nums.length <= 10^5`
* • `1 <= nums[i] <= 10^9`
* • `0 <= k <= 10^9`
* 
****************************************/

class Solution {
    // Greedy + sorting approach: sort nums and assign each element the smallest
    // available integer within its allowed range [num - k, num + k]. Track the
    // current smallest unused value and increment when used. Ensures all chosen
    // values are distinct. Time complexity O(n log n) from sorting; space O(1).
    public int maxDistinctElements(int[] nums, int k) {
        Arrays.sort(nums);
        long nextAvailable = Long.MIN_VALUE;
        int distinct = 0;

        for (int x : nums) {
            long low = (long)x - k;
            long high = (long)x + k;

            // Choose the smallest valid number ≥ nextAvailable
            long chosen = Math.max(nextAvailable, low);
            if (chosen <= high) {
                distinct++;
                nextAvailable = chosen + 1;
            }
        }

        return distinct;
    }
}