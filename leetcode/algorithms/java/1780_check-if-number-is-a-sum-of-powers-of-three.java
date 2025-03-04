// Source: https://leetcode.com/problems/check-if-number-is-a-sum-of-powers-of-three.java/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-03-03

/****************************************
* 
* Given an integer `n`, return `true` if it is possible to represent `n` as the sum of distinct powers of three. Otherwise, return `false`.
* An integer `y` is a power of three if there exists an integer `x` such that `y == 3^x`.
*
* Example 1:
* Input: n = 12
* Output: true
* Explanation: 12 = 3^1 + 3^2
* … = 3 + 9
*
* Example 2:
* Input: n = 91
* Output: true
* Explanation: 91 = 3^0 + 3^2 + 3^4
* … = 1 + 9 + 81
*
* Example 3:
* Input: n = 21
* Output: false
*
* Constraints:
* • 1 <= n <= 10^7
* 
****************************************/

class Solution {
    // This method checks if `n` can be expressed as a sum of distinct powers of 3.
    // We repeatedly divide `n` by 3, ensuring each remainder is either 0 or 1.
    // If a remainder of 2 is found, return false, as we can't use multiple of any power.
    // Time Complexity: O(log_3(n)), at most ~15 iterations for n ≤ 10^7.
    // Space Complexity: O(1), using only a few integer variables.
    public boolean checkPowersOfThree(int n) {
        while (n > 0) {
            if (n % 3 == 2) return false; // If remainder is 2, it's not possible
            n /= 3; // Reduce n by dividing by 3
        }
        return true; // If we reach 0, it's possible
    }
}
