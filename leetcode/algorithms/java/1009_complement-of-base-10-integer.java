// Source: https://leetcode.com/problems/complement-of-base-10-integer/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-11
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 41.96 MB Beats 84.64%

/****************************************
* 
* The complement of an integer is the integer you get when you flip all the
* _ `0`'s to `1`'s and all the `1`'s to `0`'s in its binary representation.
* For example, The integer `5` is `"101"` in binary and its complement is
* _ `"010"` which is the integer `2`.
* Given an integer `n`, return its complement.
*
* Example 1:
* Input: n = 5
* Output: 2
* Explanation: 5 is "101" in binary, with complement "010" in binary, which is 2 in base-10.
*
* Example 2:
* Input: n = 7
* Output: 0
* Explanation: 7 is "111" in binary, with complement "000" in binary, which is 0 in base-10.
*
* Example 3:
* Input: n = 10
* Output: 5
* Explanation: 10 is "1010" in binary, with complement "0101" in binary, which is 5 in base-10.
*
* Constraints:
* • `0 <= n < 10^9`
* 
****************************************/

class Solution {
    // Build a mask of all 1s that matches the bit-length of n. For example,
    // if n = 5 (101), the mask becomes 111. XORing n with this mask flips
    // each bit only within the used bit range, producing the complement.
    // We construct the mask by shifting and adding 1 until all bits of n
    // are covered. Time: O(log n), Space: O(1).

    public int bitwiseComplement(int n) {

        if (n == 0) return 1;

        int mask = 0;
        int temp = n;

        // Build mask of all 1s covering the bit length of n
        while (temp > 0) {
            mask = (mask << 1) | 1;
            temp >>= 1;
        }

        return n ^ mask;
    }
}