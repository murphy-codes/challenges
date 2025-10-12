// Source: https://leetcode.com/problems/find-sum-of-array-product-of-magical-sequences/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-12
// At the time of submission:
//   Runtime 19 ms Beats 100.00%
//   Memory 46.77 MB Beats 70.27%

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

class Solution {
    // DP + memoization over index, remaining picks, carry bits, and bits left.
    // For each num, choose how many times to include it, updating the binary sum
    // and carry propagation. Multiply by num^count and divide by count! to handle
    // duplicates; multiply final result by m! for all permutations. Runs in about
    // O(n * m^2 * k) time and O(n * m^2 * k) space; all mod 1e9+7.
    
    private static final int MOD = 1_000_000_007;
    private static final int MAX_M = 31;
    private static final long[] FACT = new long[MAX_M];
    private static final long[] INV_FACT = new long[MAX_M];

    // Precompute factorials and modular inverses
    static {
        FACT[0] = 1;
        for (int i = 1; i < MAX_M; i++) {
            FACT[i] = FACT[i - 1] * i % MOD;
        }
        INV_FACT[MAX_M - 1] = modPow(FACT[MAX_M - 1], MOD - 2);
        for (int i = MAX_M - 1; i > 0; i--) {
            INV_FACT[i - 1] = INV_FACT[i] * i % MOD;
        }
    }

    private static long modPow(long base, int exp) {
        long res = 1;
        while (exp > 0) {
            if ((exp & 1) == 1) res = res * base % MOD;
            base = base * base % MOD;
            exp >>= 1;
        }
        return res;
    }

    public int magicalSum(int m, int k, int[] nums) {
        int n = nums.length;
        int[][] powCache = new int[n][m + 1];
        for (int i = 0; i < n; i++) {
            powCache[i][0] = 1;
            for (int j = 1; j <= m; j++) {
                powCache[i][j] = (int) ((long) powCache[i][j - 1] * nums[i] % MOD);
            }
        }

        int[][][][] memo = new int[n][m + 1][m / 2 + 1][k + 1];
        for (int[][][] a : memo)
            for (int[][] b : a)
                for (int[] c : b)
                    Arrays.fill(c, -1);

        return (int) (dfs(0, m, 0, k, powCache, memo) * FACT[m] % MOD);
    }

    // DFS with memoization
    private long dfs(int idx, int remaining, int carry, int bitsLeft,
                     int[][] powCache, int[][][][] memo) {

        int currentBits = Integer.bitCount(carry);
        if (currentBits + remaining < bitsLeft) return 0; // prune impossible
        if (idx == powCache.length)
            return (remaining == 0 && currentBits == bitsLeft) ? 1 : 0;

        if (memo[idx][remaining][carry][bitsLeft] != -1)
            return memo[idx][remaining][carry][bitsLeft];

        long total = 0;
        for (int pick = 0; pick <= remaining; pick++) {
            int bit = (carry + pick) & 1;
            if (bit <= bitsLeft) {
                long sub = dfs(idx + 1, remaining - pick, (carry + pick) >> 1,
                               bitsLeft - bit, powCache, memo);
                total = (total + sub * powCache[idx][pick] % MOD * INV_FACT[pick]) % MOD;
            }
        }

        return memo[idx][remaining][carry][bitsLeft] = (int) total;
    }
}
