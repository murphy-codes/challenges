// Source: https://leetcode.com/problems/count-the-number-of-arrays-with-k-matching-adjacent-elements/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-16
// At the time of submission:
//   Runtime 3 ms Beats 100.00%
//   Memory 45.09 MB Beats 31.71%

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

class Solution {
    // This solution counts arrays of size `n` with `k` matching adjacent pairs
    // using combinatorics. It calculates:
    //    m * C(n-1, k) * (m-1)^(n-k-1)
    // Factorials are computed recursively and cached for performance, and
    // modular inverses are calculated using a recursive Euclidean identity.
    // Time: O(log(n-k)), Space: O(n) due to lazy caching of factorials.

    // Modulo constant
    int mod = 1_000_000_007;

    // Static cache for factorials and reverse values
    static long[] inverseCache = new long[100001];
    static int[] factorialCache = new int[100001];

    public int countGoodArrays(int n, int m, int k) {
        // Lazy init of factorial base case
        if (factorialCache[0] == 0)
            factorialCache[0] = 1;

        // Apply the formula:
        // m * C(n-1, n-1-k) * (m - 1)^(n - 1 - k)
        long result = m;
        result = result * pow(m - 1, n - 1 - k) % mod;
        result = result * combination(n - 1, n - 1 - k) % mod;
        return (int) result;
    }

    // Fast exponentiation: computes a^b % mod
    public long pow(int base, int exponent) {
        long result = 1, b = base;
        while (exponent > 0) {
            if ((exponent & 1) == 1)
                result = result * b % mod;
            b = b * b % mod;
            exponent >>= 1;
        }
        return result;
    }

    // Computes C(a, b) = a! / (b! * (a - b)!) % mod
    public long combination(int a, int b) {
        return (long) getFactorial(a) * modularInverse(getFactorial(a - b)) % mod
               * modularInverse(getFactorial(b)) % mod;
    }

    // Computes a! using memoization
    public long getFactorial(int a) {
        if (factorialCache[a] != 0)
            return factorialCache[a];
        return factorialCache[a] = (int) (getFactorial(a - 1) * a % mod);
    }

    // Modular inverse using recursive Euclidean identity
    public long modularInverse(long a) {
        if (a == 1)
            return 1;
        return mod - mod / a * modularInverse(mod % a) % mod;
    }
}
