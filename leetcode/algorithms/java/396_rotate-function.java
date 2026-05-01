// Source: https://leetcode.com/problems/rotate-function/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-01
// At the time of submission:
//   Runtime 4 ms Beats 90.14%
//   Memory 90.64 MB Beats 32.49%

/****************************************
* 
* You are given an integer array `nums` of length `n`.
* Assume `arr_k` to be an array obtained by rotating `nums` by `k` positions
* _ clock-wise. We define the rotation function `F` on `nums` as follow:
* • `F(k) = 0 * arr_k[0] + 1 * arr_k[1] + ... + (n - 1) * arr_k[n - 1]`.
* Return the maximum value of `F(0), F(1), ..., F(n-1)`.
* The test cases are generated so that the answer fits in a 32-bit integer.
*
* Example 1:
* Input: nums = [4,3,2,6]
* Output: 26
* Explanation:
* F(0) = (0 * 4) + (1 * 3) + (2 * 2) + (3 * 6) = 0 + 3 + 4 + 18 = 25
* F(1) = (0 * 6) + (1 * 4) + (2 * 3) + (3 * 2) = 0 + 4 + 6 + 6 = 16
* F(2) = (0 * 2) + (1 * 6) + (2 * 4) + (3 * 3) = 0 + 6 + 8 + 9 = 23
* F(3) = (0 * 3) + (1 * 2) + (2 * 6) + (3 * 4) = 0 + 2 + 12 + 12 = 26
* So the maximum value of F(0), F(1), F(2), F(3) is F(3) = 26.
*
* Example 2:
* Input: nums = [100]
* Output: 0
*
* Constraints:
* • `n == nums.length`
* • `1 <= n <= 10^5`
* • `-100 <= nums[i] <= 100`
* 
****************************************/

class Solution {
    // Compute F(0) and total sum, then derive F(k) from F(k-1).
    // Rotation adds sum to all weights and subtracts n * shifted value.
    // Iterate once to update F(k) in O(1) per step.
    // Time: O(n), Space: O(1)
    public int maxRotateFunction(int[] nums) {
        int n = nums.length;

        long sum = 0;
        long f = 0;

        // Compute total sum and F(0)
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            f += (long) i * nums[i];
        }

        long max = f;

        // Use recurrence to compute F(k)
        for (int k = 1; k < n; k++) {
            f = f + sum - (long) n * nums[n - k];
            max = Math.max(max, f);
        }

        return (int) max;
    }
}