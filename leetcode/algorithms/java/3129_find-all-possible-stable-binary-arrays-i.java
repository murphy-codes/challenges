// Source: https://leetcode.com/problems/find-all-possible-stable-binary-arrays-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-08
// At the time of submission:
//   Runtime 6 ms Beats 100.00%
//   Memory 46.21 MB Beats 93.42%

/****************************************
* 
* Given an array of strings `nums` containing `n` unique binary strings each of length `n`, return a binary string of length `n` that does not appear in `nums`. If there are multiple answers, you may return any of them.
* 
* Example 1:
* Input: nums = ["01","10"]
* Output: "11"
* Explanation: "11" does not appear in nums. "00" would also be correct.
* 
* Example 2:
* Input: nums = ["00","01"]
* Output: "11"
* Explanation: "11" does not appear in nums. "10" would also be correct.
* 
* Example 3:
* Input: nums = ["111","011","001"]
* Output: "101"
* Explanation: "101" does not appear in nums. "000", "010", "100", and "110" would also be correct.
* 
* Constraints:
* • n == nums.length
* • 1 <= n <= 16
* • nums[i].length == n
* • `nums[i]` is either `'0'` or `'1'`.
* • All the strings of `nums` are unique.
* 
****************************************/

class Solution {
    // Use DP where dp0[i][j] and dp1[i][j] represent arrays with i zeros and j
    // ones ending in 0 or 1. Normally we extend sequences by appending a value,
    // but runs longer than 'limit' are invalid. Instead of tracking run length,
    // we subtract sequences that would create runs of length limit+1 using an
    // inclusion–exclusion step. Time: O(zero * one), Space: O(zero * one).
    public int numberOfStableArrays(int zero, int one, int limit) {

        final int MOD = 1_000_000_007;

        int window = limit + 1;

        // dpEndWithZero[i][j] = number of arrays using i zeros and j ones ending in 0
        int[][] dpEndWithZero = new int[zero + 1][one + 1];

        // dpEndWithOne[i][j] = number of arrays using i zeros and j ones ending in 1
        int[][] dpEndWithOne = new int[zero + 1][one + 1];

        // Base cases: arrays made of only zeros (length must not exceed limit)
        for (int z = 1; z <= Math.min(zero, limit); ++z)
            dpEndWithZero[z][0] = 1;

        // Base cases: arrays made of only ones
        for (int o = 1; o <= Math.min(one, limit); ++o)
            dpEndWithOne[0][o] = 1;

        // Build DP table
        for (int z = 1; z <= zero; ++z) {
            for (int o = 1; o <= one; ++o) {

                // Append 0
                dpEndWithZero[z][o] =
                        (dpEndWithZero[z - 1][o] + dpEndWithOne[z - 1][o]
                        - (z >= window ? dpEndWithOne[z - window][o] : 0)) % MOD;

                // Append 1
                dpEndWithOne[z][o] =
                        (dpEndWithOne[z][o - 1] + dpEndWithZero[z][o - 1]
                        - (o >= window ? dpEndWithZero[z][o - window] : 0)) % MOD;

                // Normalize negative values after subtraction
                dpEndWithZero[z][o] = (dpEndWithZero[z][o] + MOD) % MOD;
                dpEndWithOne[z][o] = (dpEndWithOne[z][o] + MOD) % MOD;
            }
        }

        return (dpEndWithZero[zero][one] + dpEndWithOne[zero][one]) % MOD;
    }
}