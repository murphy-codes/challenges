// Source: https://leetcode.com/count-good-numbers/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-13
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 40.41 MB Beats 65.84%

/****************************************
* 
* A digit string is good if the digits (0-indexed) at even indices are
* _ even and the digits at odd indices are prime (`2`, `3`, `5`, or `7`).
* For example, `"2582"` is good because the digits (`2` and `8`) at even positions are
* _ even and the digits (`5` and `2`) at odd positions are prime.
* _ However, `"3245"` is not good because `3` is at an even index but is not even.
* Given an integer `n`, return the total number of good digit strings of length `n`.
* _ Since the answer may be large, return it modulo `10^9 + 7`.
* A digit string is a string consisting of digits `0` through `9` that may contain leading zeros.
*
* Example 1:
* Input: n = 1
* Output: 5
* Explanation: The good numbers of length 1 are "0", "2", "4", "6", "8".
*
* Example 2:
* Input: n = 4
* Output: 400
*
* Example 3:
* Input: n = 50
* Output: 564908303
*
* Constraints:
* • 1 <= n <= 10^15
* 
****************************************/

import java.math.BigInteger;

class Solution {
    // Each even index (0-based) can be one of 5 even digits.
    // Each odd index can be one of 4 prime digits.
    // Total good strings = 5^(even count) * 4^(odd count), modulo 10^9+7.
    // We use fast modular exponentiation to compute this efficiently.
    // Time: O(log n), Space: O(1)
    private static final int MOD = 1_000_000_007;

    public int countGoodNumbers(long n) {
        long evenCount = (n + 1) / 2;
        long oddCount = n / 2;
        long goodEvens = modPow(5, evenCount, MOD);
        long goodOdds = modPow(4, oddCount, MOD);
        return (int)(goodEvens * goodOdds % MOD);
    }

    // Modular exponentiation: (base^exp) % mod
    private long modPow(long base, long exp, int mod) {
        long result = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1)
                result = (result * base) % mod;
            base = (base * base) % mod;
            exp >>= 1;
        }
        return result;
    }
}
