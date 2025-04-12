// Source: https://leetcode.com/find-the-count-of-good-integers/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-12
// At the time of submission:
//   Runtime 425 ms Beats 23.08%
//   Memory 47.90 MB Beats 30.77%

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

import java.util.HashSet;
import java.util.Set;

class Solution {
    // Enumerate all palindromic numbers of length n by constructing the first half
    // and appending its reverse (excluding the middle digit if n is odd).
    // For each palindrome, check if it's divisible by k.
    // To avoid counting duplicates, use a set to track sorted digit strings.
    // For each unique digit combination, calculate the number of valid permutations
    // that don't start with zero using combinatorial mathematics.
    public long countGoodIntegers(int n, int k) {
        int halfLen = (n + 1) / 2;
        int start = (int) Math.pow(10, halfLen - 1);
        int end = (int) Math.pow(10, halfLen);
        Set<String> seen = new HashSet<>();
        long result = 0;

        for (int i = start; i < end; i++) {
            String firstHalf = Integer.toString(i);
            String secondHalf = new StringBuilder(firstHalf).reverse().toString();
            String palindrome = firstHalf + secondHalf.substring(n % 2);

            if (Long.parseLong(palindrome) % k != 0) continue;

            char[] chars = palindrome.toCharArray();
            java.util.Arrays.sort(chars);
            String sorted = new String(chars);
            if (seen.contains(sorted)) continue;
            seen.add(sorted);

            int[] count = new int[10];
            for (char c : palindrome.toCharArray()) {
                count[c - '0']++;
            }

            long permutations = 0;
            for (int d = 1; d <= 9; d++) {
                if (count[d] == 0) continue;
                count[d]--;
                long perm = factorial(n - 1);
                for (int cnt : count) {
                    perm /= factorial(cnt);
                }
                permutations += perm;
                count[d]++;
            }
            result += permutations;
        }
        return result;
    }

    private long factorial(int n) {
        long res = 1;
        for (int i = 2; i <= n; i++) res *= i;
        return res;
    }
}
