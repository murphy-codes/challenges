// Source: https://leetcode.com/problems/binary-gap/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-21
// At the time of submission:
//   Runtime 1 ms Beats 40.87%
//   Memory 42.20 MB Beats 84.49%

/****************************************
* 
* Given a positive integer `n`, find and return the longest distance between
* _ any two adjacent `1`'s in the binary representation of `n`. If there are
* _ no two adjacent `1`'s, return `0`.
* Two `1`'s are adjacent if there are only `0`'s separating them (possibly
* _ no `0`'s). The distance between two `1`'s is the absolute difference
* _ between their bit positions. For example, the two `1`'s in `"1001"`
* _ have a distance of `3`.
*
* Example 1:
* Input: n = 22
* Output: 2
* Explanation: 22 in binary is "10110".
* The first adjacent pair of 1's is "10110" with a distance of 2.
* The second adjacent pair of 1's is "10110" with a distance of 1.
* The answer is the largest of these two distances, which is 2.
* Note that "10110" is not a valid pair since there is a 1 separating the two 1's underlined.
*
* Example 2:
* Input: n = 8
* Output: 0
* Explanation: 8 in binary is "1000".
* There are not any adjacent pairs of 1's in the binary representation of 8, so we return 0.
*
* Example 3:
* Input: n = 5
* Output: 2
* Explanation: 5 in binary is "101".
*
* Constraints:
* • `1 <= n <= 10^9`
* 
****************************************/

class Solution {
    // Count how many numbers ≤ N have a prime number of set bits
    // by iterating through each bit position and counting valid combinations
    // using binomial coefficients. Instead of scanning the range, it computes
    // count(right) − count(left − 1). Time complexity is O(32 × primes), and
    // space complexity is O(1).

    // All prime values <= 19 (max possible set bits for numbers <= 1e6)
    private static int[] PRIMES = {2, 3, 5, 7, 11, 13, 17, 19};

    // Computes C(n, k) using factorial division
    private int comb(int n, int k) {
        if (n <= 0 || k <= 0 || k > n) return 0;

        long numerator = 1, denominator = 1;

        for (int i = k + 1; i <= n; i++) {
            numerator *= i;
        }

        for (int i = 2; i <= n - k; i++) {
            denominator *= i;
        }

        return (int) (numerator / denominator);
    }

    // Counts valid combinations given:
    // av = available remaining bit positions
    // al = already selected set bits
    private int countValid(int available, int alreadySet) {
        int total = 0;

        for (int prime : PRIMES) {
            int needed = prime - alreadySet;
            if (needed < 0 || needed > available) continue;
            total += comb(available, needed);
        }

        return total;
    }

    // Counts how many primes are <= num
    private int countPrimes(int num) {
        for (int i = PRIMES.length - 1; i >= 0; i--) {
            if (num >= PRIMES[i]) {
                return i + 1;
            }
        }
        return 0;
    }

    // Counts numbers <= num with a prime number of set bits
    private int countNum(int num) {
        if (num == 0) return 0;

        int bitMask = 0x40000000; // highest 1-bit for 32-bit int
        int remainingBits = 31;
        int setBitsSoFar = 0;
        int total = 0;

        while (bitMask != 0) {
            if ((num & bitMask) != 0) {
                total += countValid(remainingBits - 1, setBitsSoFar);
                setBitsSoFar++;
            }
            bitMask >>>= 1;
            remainingBits--;
        }

        total += countPrimes(setBitsSoFar);
        return total;
    }

    public int countPrimeSetBits(int left, int right) {
        return countNum(right) - countNum(left - 1);
    }
}
