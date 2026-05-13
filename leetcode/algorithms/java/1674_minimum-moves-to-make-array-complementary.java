// Source: https://leetcode.com/problems/minimum-moves-to-make-array-complementary/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-12
// At the time of submission:
//   Runtime 6 ms Beats 100.00%
//   Memory 76.62 MB Beats 24.66%

/****************************************
* 
* You are given an integer array `nums` of even length `n` and an integer
* _ `limit`. In one move, you can replace any integer from `nums` with another
* _ integer between `1` and `limit`, inclusive.
* The array `nums` is complementary if for all indices `i` (0-indexed),
* _ `nums[i] + nums[n - 1 - i]` equals the same number. For example, the
* _ array `[1,2,3,4]` is complementary because for all indices `i`,
* _ `nums[i] + nums[n - 1 - i] = 5`.
* Return the minimum number of moves required to make `nums` complementary.
*
* Example 1:
* Input: nums = [1,2,4,3], limit = 4
* Output: 1
* Explanation: In 1 move, you can change nums to [1,2,2,3] (underlined elements are changed).
* nums[0] + nums[3] = 1 + 3 = 4.
* nums[1] + nums[2] = 2 + 2 = 4.
* nums[2] + nums[1] = 2 + 2 = 4.
* nums[3] + nums[0] = 3 + 1 = 4.
* Therefore, nums[i] + nums[n-1-i] = 4 for every i, so nums is complementary.
*
* Example 2:
* Input: nums = [1,2,2,1], limit = 2
* Output: 2
* Explanation: In 2 moves, you can change nums to [2,2,2,2]. You cannot change any number to 3 since 3 > limit.
*
* Example 3:
* Input: nums = [1,2,1,2], limit = 2
* Output: 0
* Explanation: nums is already complementary.
*
* Constraints:
* • `n == nums.length`
* • `2 <= n <= 10^5`
* • `1 <= nums[i] <= limit <= 10^5`
* • `n` is even.
* 
****************************************/

class Solution {
    // Use a diff array to track move cost changes for each target sum.
    // Each pair contributes ranges requiring 2, 1, or 0 modifications.
    // Prefix summing the diff array gives total moves for every target.
    // Time: O(n + limit), Space: O(limit)
    public int minMoves(int[] nums, int limit) {

        int n = nums.length;
        int[] diff = new int[(2 * limit) + 2];

        for (int i = 0; i < n / 2; i++) {

            int a = Math.min(nums[i], nums[n - 1 - i]);
            int b = Math.max(nums[i], nums[n - 1 - i]);

            int oneMoveLow = a + 1;
            int oneMoveHigh = b + limit;

            int exactSum = a + b;

            // Default cost = 2 moves
            diff[2] += 2;

            // Reduce to 1 move range
            diff[oneMoveLow] -= 1;
            diff[oneMoveHigh + 1] += 1;

            // Reduce to 0 moves at exact sum
            diff[exactSum] -= 1;
            diff[exactSum + 1] += 1;
        }

        int minMoves = Integer.MAX_VALUE;
        int currentMoves = 0;

        for (int sum = 2; sum <= 2 * limit; sum++) {
            currentMoves += diff[sum];
            minMoves = Math.min(minMoves, currentMoves);
        }

        return minMoves;
    }
}