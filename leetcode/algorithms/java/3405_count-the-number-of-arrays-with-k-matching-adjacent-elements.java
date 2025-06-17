// Source: https://leetcode.com/problems/count-the-number-of-arrays-with-k-matching-adjacent-elements/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-16
// At the time of submission:
//   Runtime 29 ms Beats 65.85%
//   Memory 42.73 MB Beats 46.34%

/****************************************
* 
* You are given three integers `n`, `m`, `k`.
* _ A good array `arr` of size `n` is defined as follows:
* • Each element in `arr` is in the inclusive range `[1, m]`.
* • Exactly `k` indices `I` (where `1 <= i < n`) satisfy the
* __ condition `arr[i - 1] == arr[i]`.
* Return the number of good arrays that can be formed.
* Since the answer may be very large, return it modulo `10^9 + 7`.
*
* Example 1:
* Input: n = 3, m = 2, k = 1
* Output: 4
* Explanation:
* There are 4 good arrays. They are [1, 1, 2], [1, 2, 2], [2, 1, 1] and [2, 2, 1].
* Hence, the answer is 4.
*
* Example 2:
* Input: n = 4, m = 2, k = 2
* Output: 6
* Explanation:
* The good arrays are [1, 1, 1, 2], [1, 1, 2, 2], [1, 2, 2, 2], [2, 1, 1, 1], [2, 2, 1, 1] and [2, 2, 2, 1].
* Hence, the answer is 6.
*
* Example 3:
* Input: n = 5, m = 2, k = 0
* Output: 2
* Explanation:
* The good arrays are [1, 2, 1, 2, 1] and [2, 1, 2, 1, 2]. Hence, the answer is 2.
*
* Constraints:
* • 1 <= n <= 10^5
* • 1 <= m <= 10^5
* • 0 <= k <= n - 1
* 
****************************************/

import static java.lang.Math.min;

class Solution {
    // This solution uses combinatorics to count arrays of length `n` with `k`
    // adjacent equal elements. The array is divided into `n - k` segments, each
    // with constant values. The total count is:
    //    m * C(n - 1, k) * (m - 1)^(n - k - 1)
    // where C(n - 1, k) is a binomial coefficient, and powers are computed
    // with modular exponentiation.
    // Time: O(log(n - k)), Space: O(1) (ignoring factorial precomputation).
    static final int MOD = 1_000_000_007;
    static final int MAX = 100_005;

    static long[] fact = new long[MAX];
    static long[] invFact = new long[MAX];

    // Binary exponentiation: computes x^n % MOD
    static long powmod(long x, int n) {
        long res = 1;
        while (n > 0) {
            if ((n & 1) == 1)
                res = (res * x) % MOD;
            x = (x * x) % MOD;
            n >>= 1;
        }
        return res;
    }

    // Precompute factorials and inverse factorials
    static {
        fact[0] = 1;
        for (int i = 1; i < MAX; i++) {
            fact[i] = (fact[i - 1] * i) % MOD;
        }
        invFact[MAX - 1] = powmod(fact[MAX - 1], MOD - 2);
        for (int i = MAX - 2; i >= 0; i--) {
            invFact[i] = (invFact[i + 1] * (i + 1)) % MOD;
        }
    }

    // Compute C(n, k) % MOD
    static long comb(int n, int k) {
        if (k < 0 || k > n) return 0;
        return (((fact[n] * invFact[k]) % MOD) * invFact[n - k]) % MOD;
    }

    public int countGoodArrays(int n, int m, int k) {
        // Total count = m * C(n-1, k) * (m - 1)^(n - k - 1)
        long res = comb(n - 1, k);
        res = (res * m) % MOD;
        res = (res * powmod(m - 1, n - k - 1)) % MOD;
        return (int) res;
    }
}
