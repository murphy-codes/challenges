// Source: https://leetcode.com/problems/find-the-count-of-good-integers/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-12
// At the time of submission:
//   Runtime 58 ms Beats 96.15%
//   Memory 40.70 MB Beats 96.15%

/****************************************
* 
* You are given two positive integers `n` and `k`.
* An integer x is called k-palindromic if:
* • `x` is a palindrome.
* • `x` is divisible by `k`.
* An integer is called good if its digits can be rearranged to form a
* _ k-palindromic integer. For example, for `k = 2`, 2020 can be rearranged
* _ to form the k-palindromic integer 2002, whereas 1010 cannot be rearranged
* _ to form a k-palindromic integer.
* Return the count of good integers containing `n` digits.
* Note that any integer must not have leading zeros, neither before nor
* _ after rearrangement. For example, 1010 cannot be rearranged to form 101.
*
* Example 1:
* Input: n = 3, k = 5
* Output: 27
* Explanation:
* Some of the good integers are:
* 551 because it can be rearranged to form 515.
* 525 because it is already k-palindromic.
*
* Example 2:
* Input: n = 1, k = 4
* Output: 2
* Explanation:
* The two good integers are 4 and 8.
*
* Example 3:
* Input: n = 5, k = 6
* Output: 2468
*
* Constraints:
* • 1 <= n <= 10
* • 1 <= k <= 9
* 
****************************************/

class Solution {
    // Generates palindromic digit combinations, then checks for divisibility by k.
    // Uses bitmasking and precomputed powers/mods to speed up residue checks.
    // Avoids permutations with leading zeros and counts only valid arrangements.
    // Time: O(10^(n/2) * n), Space: O(n) for recursion and digit state.

    // Precomputed mod values for digit × power-of-10 mod k cases
    private static final int[][] modPow10 = new int[][] {
        {0}, 
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 
        {1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
        {1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
        {1, 3, 2, 6, 4, 5, 1, 3, 2, 6, 4}, 
        {1, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0}, 
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    // Precomputed factorials up to 10!
    private static final int[] factorial = {
        1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800
    };

    // Entry point: Count all palindromic numbers of length `n` divisible by `k`
    public long countGoodIntegers(int n, int k) {
        if (n == 1) return 9 / k;  // Special case: Single digit numbers
        return generateHalfPalindromes(0, 0, n, k, new int[(n + 1) / 2]);
    }

    // Backtracking to generate non-decreasing digit combinations for half-palindromes
    private long generateHalfPalindromes(int idx, int minDigit, int n, int k, int[] halfDigits) {
        if (idx == halfDigits.length) {
            if (halfDigits[idx - 1] == 0) return 0;

            if (n % 2 == 1) {
                // Odd length: need to try all possible center digits
                if (halfDigits[idx - 2] == 0) {
                    return isValidPalindrome(halfDigits, n, k, 0, 1 << (idx - 2), 0)
                        ? countValidPermutations(halfDigits, n, 0)
                        : 0;
                }

                long total = 0;
                for (int i = 0; i < halfDigits.length; i++) {
                    if (i > 0 && halfDigits[i] == halfDigits[i - 1]) continue; // Avoid duplicates

                    int centerDigit = halfDigits[i];
                    int residue = (centerDigit * modPow10[k][n / 2]) % k;

                    if (isValidPalindrome(halfDigits, n, k, residue, 1 << i, 0)) {
                        total += countValidPermutations(halfDigits, n, centerDigit);
                    }
                }
                return total;
            }

            // Even length palindrome
            return isValidPalindrome(halfDigits, n, k, 0, 0, 0)
                ? countValidPermutations(halfDigits, n, -1)
                : 0;
        }

        long total = 0;
        for (int digit = minDigit; digit <= 9; digit++) {
            halfDigits[idx] = digit;
            total += generateHalfPalindromes(idx + 1, digit, n, k, halfDigits);
        }
        return total;
    }

    // Recursive check: Is there a valid palindrome permutation divisible by k?
    private boolean isValidPalindrome(int[] halfDigits, int n, int k, int residue, int usedMask, int count) {
        if (count == n / 2) return residue == 0;

        for (int i = halfDigits.length - 1; i >= 0; i--) {
            if (count == 0 && halfDigits[i] == 0) break; // Leading 0 not allowed
            if ((usedMask & (1 << i)) != 0) continue;    // Skip used digits

            int digit = halfDigits[i];
            int power = modPow10[k][n - count - 1] + modPow10[k][count];
            int newResidue = (residue + digit * power) % k;

            if (isValidPalindrome(halfDigits, n, k, newResidue, usedMask | (1 << i), count + 1)) {
                return true;
            }
        }
        return false;
    }

    // Count valid palindromic permutations of digits with an optional center digit
    private int countValidPermutations(int[] halfDigits, int n, int centerDigit) {
        int nonZeroStart = 0;
        while (halfDigits[nonZeroStart] == 0) nonZeroStart++;

        int zeroCount = nonZeroStart * 2 - (centerDigit == 0 ? 1 : 0);
        long totalPerms = factorial[n] / factorial[zeroCount];

        // Count repetitions in the digit list
        int streak = 1;
        for (int i = nonZeroStart + 1; i < halfDigits.length; i++) {
            if (halfDigits[i] == halfDigits[i - 1]) {
                streak++;
            } else {
                totalPerms /= factorial[2 * streak - (halfDigits[i - 1] == centerDigit ? 1 : 0)];
                streak = 1;
            }
        }
        totalPerms /= factorial[2 * streak - (halfDigits[halfDigits.length - 1] == centerDigit ? 1 : 0)];

        return (int)(totalPerms * (n - zeroCount) / n); // Adjust for leading zeros
    }
}
