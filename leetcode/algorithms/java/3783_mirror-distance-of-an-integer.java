// Source: https://leetcode.com/problems/mirror-distance-of-an-integer/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-17
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 42.59 MB Beats 65.56%

/****************************************
* 
* You are given an integer `n`.
* Define its mirror distance as: `abs(n - reverse(n))`​​​​​​​ where `reverse(n)` is
* _ the integer formed by reversing the digits of `n`.
* Return an integer denoting the mirror distance of `n`​.
* `abs(x)` denotes the absolute value of `x`.
*
* Example 1:
* Input: n = 25
* Output: 27
* Explanation:
* • reverse(25) = 52.
* • Thus, the answer is `abs(25 - 52) = 27`.
*
* Example 2:
* Input: n = 10
* Output: 9
* Explanation:
* • `reverse(10) = 01` which is 1.
* • Thus, the answer is `abs(10 - 1) = 9`.
*
* Example 3:
* Input: n = 7
* Output: 0
* Explanation:
* • `reverse(7) = 7`.
* • Thus, the answer is `abs(7 - 7) = 0`.
*
* Constraints:
* • `1 <= n <= 10^9`
* 
****************************************/

class Solution {
    // Compute the mirror distance by reversing the digits of n
    // and taking the absolute difference between n and reverse(n).
    // The reverse is built digit-by-digit using modulo and division.
    // Time: O(d), where d is number of digits (~log10 n).
    // Space: O(1), since only a few variables are used.

    public int mirrorDistance(int n) {
        return Math.abs(n - reverse(n));
    }

    public static int reverse(int n) {
        int rev = 0;
        while (n != 0) {
            int digit = n % 10;
            rev = rev * 10 + digit;
            n /= 10;
        }
        return rev;
    }
}