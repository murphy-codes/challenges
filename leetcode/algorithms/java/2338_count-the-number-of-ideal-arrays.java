// Source: https://leetcode.com/count-the-number-of-ideal-arrays/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-22
// At the time of submission:
//   Runtime 11 ms Beats 95.45%
//   Memory 45.70 MB Beats 50.00%

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

import java.util.ArrayList;
import java.util.List;

class Solution {
    // Uses combinatorics to count valid arrays where each arr[i]
    // divides arr[i+1]. For each x in [1, maxValue], we factor x
    // into primes and count ways to distribute its exponents over n slots.
    // Combinations C(n + p - 1, p) model prime factor distributions.
    // Time: O((n + log(m)) * log(m) + m * loglog(m))
    // Space: O((n + log(m)) * log(m)) for precomputed factors/combinations

    static final int MOD = 1_000_000_007;
    static final int MAX_N = 10010;
    static final int MAX_P = 15; // Max number of prime factors
    static int[][] c = new int[MAX_N + MAX_P][MAX_P + 1];
    static int[] sieve = new int[MAX_N]; // Stores smallest prime factor
    static List<Integer>[] ps = new List[MAX_N]; // Prime factor counts for each number

    public Solution() {
        // Only initialize once to avoid redundant work
        if (c[0][0] == 1) return;

        // Initialize prime factor list
        for (int i = 0; i < MAX_N; i++) {
            ps[i] = new ArrayList<>();
        }

        // Sieve of Eratosthenes to find smallest prime factor
        for (int i = 2; i < MAX_N; i++) {
            if (sieve[i] == 0) {
                for (int j = i; j < MAX_N; j += i) {
                    if (sieve[j] == 0) {
                        sieve[j] = i;
                    }
                }
            }
        }

        // Precompute prime factor multiplicities for all numbers
        for (int i = 2; i < MAX_N; i++) {
            int x = i;
            while (x > 1) {
                int p = sieve[x], cnt = 0;
                while (x % p == 0) {
                    x /= p;
                    cnt++;
                }
                ps[i].add(cnt);
            }
        }

        // Precompute combinations: c[i][j] = C(i, j)
        c[0][0] = 1;
        for (int i = 1; i < MAX_N + MAX_P; i++) {
            c[i][0] = 1;
            for (int j = 1; j <= Math.min(i, MAX_P); j++) {
                c[i][j] = (c[i - 1][j] + c[i - 1][j - 1]) % MOD;
            }
        }
    }

    public int idealArrays(int n, int maxValue) {
        long ans = 0;

        for (int x = 1; x <= maxValue; x++) {
            long mul = 1;
            for (int count : ps[x]) {
                mul = (mul * c[n + count - 1][count]) % MOD;
            }
            ans = (ans + mul) % MOD;
        }

        return (int) ans;
    }
}
