// Source: https://leetcode.com/find-the-count-of-good-integers/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-12
// At the time of submission:
//   Runtime 57 ms Beats 100.00%
//   Memory 40.34 MB Beats 96.15%

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
    private static final int[][] modPow10 = new int[][]{
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

    private static final int[] factorial = new int[]{1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800};
    public long countGoodIntegers(int n, int k) {
        if(n == 1) return 9/k;
        return backtrackDigitCombos(0, 0, n, k,  new int[(n+1)/2]);
    }

    private long backtrackDigitCombos(int idx, int lo, int n, int k, int[] digits) {
        if(idx == digits.length) {
            if(digits[idx-1] == 0) return 0;
            if(n % 2 == 1) {
                if(digits[idx-2] == 0) return hasKPalPerm(digits, n, k, 0, 1 << (idx-2), 0) ? validPermCnt(digits, n, 0) : 0;
                long res = 0;
                for(int c = 0; c < digits.length; c++) { 
                    if(c > 0 && digits[c] == digits[c-1]) continue;
                    if(hasKPalPerm(digits, n, k, (digits[c]*modPow10[k][n/2]) % k, 1 << c, 0)) {
                        res += validPermCnt(digits, n, digits[c]);
                    }
                }
                return res;
            }
            
            return hasKPalPerm(digits, n, k, 0, 0, 0) ? validPermCnt(digits, n, -1) : 0;
        }
        long res = 0;
        for(int d = lo; d <= 9; d++) {
            digits[idx] = d;
            res += backtrackDigitCombos(idx+1, d, n, k, digits);
        }
        return res;
    }

    private boolean hasKPalPerm(int[] digits, int n, int k, int residue, int mask, int chosenCnt) {
        if(chosenCnt == n/2) {
            return residue == 0;
        }

        for(int i = digits.length-1; i >= 0; i--) {
            if(chosenCnt == 0 && digits[i] == 0) break;
            if((mask & (1 << i)) != 0) continue;
            if(hasKPalPerm(digits, n, k, (residue + digits[i]*(modPow10[k][n-chosenCnt-1] + modPow10[k][chosenCnt])) % k, mask | (1 << i), chosenCnt+1)) return true;
        }

        return false;
    }

    private int validPermCnt(int[] digits, int n, int center) {

        int nonZeroInd = 0;
        while(digits[nonZeroInd] == 0) nonZeroInd++;
        int zeroCnt = nonZeroInd * 2 - (center == 0 ? 1 : 0);

        long perms = factorial[n]/factorial[zeroCnt];
        int streak = 1;
        for(int i = nonZeroInd+1; i < digits.length; i++) {
            if(digits[i] == digits[i-1]) {
                streak++;
            } else {
                perms /= factorial[2*streak - (digits[i-1] == center ? 1 : 0)];
                streak = 1;
            }
        }
        perms /= factorial[2*streak - (digits[digits.length-1] == center ? 1 : 0)];

        return (int)(perms*(n-zeroCnt)/n);
    }
}