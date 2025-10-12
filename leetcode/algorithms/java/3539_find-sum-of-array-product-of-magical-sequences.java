// Source: https://leetcode.com/problems/find-sum-of-array-product-of-magical-sequences/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-11
// At the time of submission:
//   Runtime 76 ms Beats 24.32%
//   Memory 56.80 MB Beats 48.65%

/****************************************
* 
* You are given two integers, `m` and `k`, and an integer array `nums`.
* A sequence of integers `seq` is called magical if:
* • `seq` has a size of `m`.
* • `0 <= seq[i] < nums.length`
* • The binary representation of `2^seq[0] + 2^seq[1] + ... + 2^seq[m - 1]`
* __ has `k` set bits.
* The array product of this sequence is defined as
* _ `prod(seq) = (nums[seq[0]] * nums[seq[1]] * ... * nums[seq[m - 1]])`.
* Return the sum of the array products for all valid magical sequences.
* Since the answer may be large, return it modulo `10^9 + 7`.
* A set bit refers to a bit in the binary representation of a number that
* _ has a value of 1.
*
* Example 1:
* Input: m = 5, k = 5, nums = [1,10,100,10000,1000000]
* Output: 991600007
* Explanation:
* All permutations of [0, 1, 2, 3, 4] are magical sequences, each with an array product of 1013.
*
* Example 2:
* Input: m = 2, k = 2, nums = [5,4,3,2,1]
* Output: 170
* Explanation:
* The magical sequences are [0, 1], [0, 2], [0, 3], [0, 4], [1, 0], [1, 2], [1, 3], [1, 4], [2, 0], [2, 1], [2, 3], [2, 4], [3, 0], [3, 1], [3, 2], [3, 4], [4, 0], [4, 1], [4, 2], and [4, 3].
*
* Example 3:
* Input: m = 1, k = 1, nums = [28]
* Output: 28
* Explanation:
* The only magical sequence is [0].
*
* Constraints:
* • `1 <= k <= m <= 30`
* • `1 <= nums.length <= 50`
* • `1 <= nums[i] <= 10^8`
* 
****************************************/

import java.util.Arrays;

class Solution {
    // We use a DP over indices, remaining picks, and carry bits. For each index i,
    // we choose cnt times to pick it; that increments the carry and may produce a
    // set bit in the current position. We propagate to next index with updated carry.
    // We multiply by combinatorial count and nums[i]^cnt. 
    // Time is roughly O(n * m^2 * carryStates).
    // Space: O(n * m * carryStates) for memoization.
    static final int MOD = 1_000_000_007;
    int m, k;
    int n;
    int[] nums;
    int[][] comb; // combinatorial binomial coefficients
    Integer[][][][] memo; // dp cache

    public int magicalSum(int m, int k, int[] nums) {
        this.m = m;
        this.k = k;
        this.n = nums.length;
        this.nums = nums;

        // Precompute binomial coefficients up to m
        comb = new int[m + 1][m + 1];
        for (int i = 0; i <= m; i++) {
            comb[i][0] = 1;
            for (int j = 1; j <= i; j++) {
                comb[i][j] = (comb[i - 1][j - 1] + comb[i - 1][j]) % MOD;
            }
        }

        // dp state: dp[remainingSlots][bitsNeeded][i][carryValue]
        memo = new Integer[m + 1][k + 1][n + 1][m + 1];

        return dp(m, k, 0, 0);
    }

    // returns sum of array products (mod MOD) for sequences using
    // remaining = rem, bits still to fill = bitsNeeded, starting at index i,
    // and with carry = carryValue (carry from lower bits).
    private int dp(int rem, int bitsNeeded, int i, int carry) {
        // base / pruning
        if (rem < 0 || bitsNeeded < 0) return 0;
        // If no more to pick
        if (rem == 0) {
            // Check if carry’s bit count equals bitsNeeded
            return (Integer.bitCount(carry) == bitsNeeded) ? 1 : 0;
        }
        if (i == n) {
            return 0;
        }
        if (memo[rem][bitsNeeded][i][carry] != null) {
            return memo[rem][bitsNeeded][i][carry];
        }

        long ans = 0;
        // Try picking count = 0..rem of nums[i]
        for (int cnt = 0; cnt <= rem; cnt++) {
            // contribution from nums[i]^cnt * comb[rem][cnt]
            long contribution = modPow(nums[i], cnt) * comb[rem][cnt] % MOD;

            int newSum = carry + cnt;  // partial sum at bit i
            int bitsHere = newSum & 1;  // least significant bit
            int nextCarry = newSum >>> 1;

            ans = (ans + contribution * dp(rem - cnt, bitsNeeded - bitsHere, i + 1, nextCarry)) % MOD;
        }
        memo[rem][bitsNeeded][i][carry] = (int) ans;
        return (int) ans;
    }

    private long modPow(long x, int e) {
        long res = 1;
        x %= MOD;
        while (e > 0) {
            if ((e & 1) == 1) res = (res * x) % MOD;
            x = (x * x) % MOD;
            e >>= 1;
        }
        return res;
    }
}
