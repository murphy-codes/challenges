// Source: https://leetcode.com/count-the-number-of-ideal-arrays/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-22
// At the time of submission:
//   Runtime 7 ms Beats 97.73%
//   Memory 41.22 MB Beats 97.73%

/****************************************
* 
* You are given two integers `n` and `maxValue`,
* _ which are used to describe an ideal array.
* A 0-indexed integer array `arr` of length `n` is
* _ considered ideal if the following conditions hold:
* • Every `arr[i]` is a value from `1` to `maxValue`, for `0 <= i < n`.
* • Every `arr[i]` is divisible by `arr[i - 1]`, for `0 < i < n`.
* Return the number of distinct ideal arrays of length `n`.
* _ Since the answer may be very large, return it modulo `10^9 + 7`.
*
* Example 1:
* Input: n = 2, maxValue = 5
* Output: 10
* Explanation: The following are the possible ideal arrays:
* - Arrays starting with the value 1 (5 arrays): [1,1], [1,2], [1,3], [1,4], [1,5]
* - Arrays starting with the value 2 (2 arrays): [2,2], [2,4]
* - Arrays starting with the value 3 (1 array): [3,3]
* - Arrays starting with the value 4 (1 array): [4,4]
* - Arrays starting with the value 5 (1 array): [5,5]
* There are a total of 5 + 2 + 1 + 1 + 1 = 10 distinct ideal arrays.
*
* Example 2:
* Input: n = 5, maxValue = 3
* Output: 11
* Explanation: The following are the possible ideal arrays:
* - Arrays starting with the value 1 (9 arrays):
* - With no other distinct values (1 array): [1,1,1,1,1]
* - With 2nd distinct value 2 (4 arrays): [1,1,1,1,2], [1,1,1,2,2], [1,1,2,2,2], [1,2,2,2,2]
* - With 2nd distinct value 3 (4 arrays): [1,1,1,1,3], [1,1,1,3,3], [1,1,3,3,3], [1,3,3,3,3]
* - Arrays starting with the value 2 (1 array): [2,2,2,2,2]
* - Arrays starting with the value 3 (1 array): [3,3,3,3,3]
* There are a total of 9 + 1 + 1 = 11 distinct ideal arrays.
*
* Constraints:
* • 2 <= n <= 10^4
* • 1 <= maxValue <= 10^4
* 
****************************************/

import java.math.BigInteger;

class Solution {
    // Computes the number of ideal arrays of length n with elements ≤ maxValue.
    // Uses prime factorization and combinatorics to count valid sequences.
    // Precomputes C(n + k - 1, k) for k up to log₂(maxValue) using BigInteger.
    // Time: O(m log log m), Space: O(m + log n), where m = maxValue.
    // Efficient and readable, with fast sieve and minimal binomial calculations.
    private static final int MOD = (int) 1e9 + 7;

    public int idealArrays(int n, int maxValue) {
        // Sieve of Eratosthenes storing smallest prime divisor for each number
        int[] smallestPrime = new int[maxValue + 1];
        for (int i = 2; i <= maxValue; i++) {
            if (smallestPrime[i] == 0) {
                for (int j = i; j <= maxValue; j += i) {
                    if (smallestPrime[j] == 0) {
                        smallestPrime[j] = i;
                    }
                }
            }
        }

        // max exponent of any prime is at most log2(maxValue)
        int maxExponent = (int) (Math.log(maxValue) / Math.log(2)) + 1;

        // Precompute binomial coefficients C(n + k - 1, k) for k = 1..maxExponent
        int[] binomials = new int[maxExponent + 1];
        BigInteger coeff = BigInteger.ONE;
        BigInteger bigMod = BigInteger.valueOf(MOD);

        for (int k = 1; k <= maxExponent; k++) {
            coeff = coeff.multiply(BigInteger.valueOf(n + k - 1));
            coeff = coeff.divide(BigInteger.valueOf(k));
            binomials[k] = coeff.mod(bigMod).intValue();
        }

        int result = 0;

        // For each number from 1 to maxValue
        for (int num = 1; num <= maxValue; num++) {
            int x = num;
            long product = 1;

            // Factorize x using smallestPrime[]
            while (x > 1) {
                int prime = smallestPrime[x];
                int count = 0;

                while (x % prime == 0) {
                    x /= prime;
                    count++;
                }

                product = (product * binomials[count]) % MOD;
            }

            result = (int)((result + product) % MOD);
        }

        return result;
    }
}
