// Source: https://leetcode.com/problems/smallest-integer-divisible-by-k/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-24
// At the time of submission:
//   Runtime 2 ms Beats 97.52%
//   Memory 41.86 MB Beats 71.45%

/****************************************
* 
* Given a positive integer `k`, you need to find the length of the smallest
* _ positive integer `n` such that `n` is divisible by `k`, and `n` only
* _ contains the digit `1`.
* Return the length of `n`. If there is no such `n`, return `-1`.
* Note: `n` may not fit in a 64-bit signed integer.
*
* Example 1:
* Input: k = 1
* Output: 1
* Explanation: The smallest answer is n = 1, which has length 1.
*
* Example 2:
* Input: k = 2
* Output: -1
* Explanation: There is no such positive integer n divisible by 2.
*
* Example 3:
* Input: k = 3
* Output: 3
* Explanation: The smallest answer is n = 111, which has length 3.
*
* Constraints:
* • `1 <= k <= 10^5`
* 
****************************************/

class Solution {
    // Build repunits indirectly by tracking their remainder modulo k.
    // The next remainder is (prev * 10 + 1) % k, avoiding large integers.
    // If we reach remainder 0, the current length is the smallest result.
    // If k steps pass without hitting 0, remainders repeat and no answer exists.
    // Runs in O(k) time and O(1) space.
    public int smallestRepunitDivByK(int k) {
        if (k % 2 == 0 || k % 5 == 0) return -1; // impossible if divisible by 2 or 5
        int remainder = 0;
        for (int length = 1; length <= k; length++) {
            remainder = (remainder * 10 + 1) % k;
            if (remainder == 0) return length;
        }
        return -1;
    }
}
