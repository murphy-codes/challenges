// Source: https://leetcode.com/problems/prime-number-of-set-bits-in-binary-representation/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-21
// At the time of submission:
//   Runtime 1 ms Beats 99.39%
//   Memory 42.18 MB Beats 90.76%

/****************************************
* 
* Given two integers `left` and `right`, return the count of numbers in the
* _inclusive range `[left, right]` having a prime number of set bits in their
* _binary representation.
* Recall that the number of set bits an integer has is the number of `1`'s
* _present when written in binary.
* • For example, `21` written in binary is `10101`, which has `3` set bits.
*
* Example 1:
* Input: left = 6, right = 10
* Output: 4
* Explanation:
* 6  -> 110 (2 set bits, 2 is prime)
* 7  -> 111 (3 set bits, 3 is prime)
* 8  -> 1000 (1 set bit, 1 is not prime)
* 9  -> 1001 (2 set bits, 2 is prime)
* 10 -> 1010 (2 set bits, 2 is prime)
* 4 numbers have a prime number of set bits.
*
* Example 2:
* Input: left = 10, right = 15
* Output: 5
* Explanation:
* 10 -> 1010 (2 set bits, 2 is prime)
* 11 -> 1011 (3 set bits, 3 is prime)
* 12 -> 1100 (2 set bits, 2 is prime)
* 13 -> 1101 (3 set bits, 3 is prime)
* 14 -> 1110 (3 set bits, 3 is prime)
* 15 -> 1111 (4 set bits, 4 is not prime)
* 5 numbers have a prime number of set bits.
*
* Constraints:
* • `1 <= left <= right <= 10^6`
* • `0 <= right - left <= 10^4`
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
