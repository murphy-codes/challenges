// Source: https://leetcode.com/problems/find-all-possible-stable-binary-arrays-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-09
// At the time of submission:
//   Runtime 49 ms Beats 96.55%
//   Memory 73.86 MB Beats 85.06%

/****************************************
* 
* You are given 3 positive integers `zero`, `one`, and `limit`.
* A binary array `arr` is called stable if:
* • The number of occurrences of 0 in `arr` is exactly `zero`.
* • The number of occurrences of 1 in `arr` is exactly `one`.
* • Each subarray of arr with a size greater than `limit` must contain both 0 and 1.
* Return the total number of stable binary arrays.
* Since the answer may be very large, return it modulo `10^9 + 7`.
*
* Example 1:
* Input: zero = 1, one = 1, limit = 2
* Output: 2
* Explanation:
* The two possible stable binary arrays are [1,0] and [0,1].
*
* Example 2:
* Input: zero = 1, one = 2, limit = 1
* Output: 1
* Explanation:
* The only possible stable binary array is [1,0,1].
*
* Example 3:
* Input: zero = 3, one = 3, limit = 2
* Output: 14
* Explanation:
* All the possible stable binary arrays are [0,0,1,0,1,1], [0,0,1,1,0,1], [0,1,0,0,1,1], [0,1,0,1,0,1], [0,1,0,1,1,0], [0,1,1,0,0,1], [0,1,1,0,1,0], [1,0,0,1,0,1], [1,0,0,1,1,0], [1,0,1,0,0,1], [1,0,1,0,1,0], [1,0,1,1,0,0], [1,1,0,0,1,0], and [1,1,0,1,0,0].
*
* Constraints:
* • `1 <= zero, one, limit <= 1000`
* 
****************************************/

class Solution {
    // Let dp0[i][j] and dp1[i][j] represent arrays with i zeros and j ones
    // ending in 0 or 1. We extend sequences by appending a value, but runs
    // longer than 'limit' are invalid. Instead of tracking run length, we
    // subtract sequences that would create runs of length limit+1 using an
    // inclusion–exclusion step. Time: O(zero * one), Space: O(zero * one).

    public int numberOfStableArrays(int zero, int one, int limit) {

        final int MOD = 1_000_000_007;

        int[][] dpEndZero = new int[zero + 1][one + 1];
        int[][] dpEndOne  = new int[zero + 1][one + 1];

        int L = limit + 1;

        // Base cases: arrays consisting only of zeros
        for (int z = 1; z <= Math.min(zero, limit); z++)
            dpEndZero[z][0] = 1;

        // Base cases: arrays consisting only of ones
        for (int o = 1; o <= Math.min(one, limit); o++)
            dpEndOne[0][o] = 1;

        for (int z = 1; z <= zero; z++) {
            for (int o = 1; o <= one; o++) {

                dpEndZero[z][o] =
                        (dpEndZero[z-1][o] + dpEndOne[z-1][o]
                        - (z >= L ? dpEndOne[z-L][o] : 0)) % MOD;

                dpEndOne[z][o] =
                        (dpEndOne[z][o-1] + dpEndZero[z][o-1]
                        - (o >= L ? dpEndZero[z][o-L] : 0)) % MOD;

                if (dpEndZero[z][o] < 0) dpEndZero[z][o] += MOD;
                if (dpEndOne[z][o] < 0) dpEndOne[z][o] += MOD;
            }
        }

        return (dpEndZero[zero][one] + dpEndOne[zero][one]) % MOD;
    }
}