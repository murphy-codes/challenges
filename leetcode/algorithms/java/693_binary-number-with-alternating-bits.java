// Source: https://leetcode.com/problems/binary-number-with-alternating-bits/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-17
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 42.40 MB Beats 34.01%

/****************************************
* 
* Given a positive integer, check whether it has alternating bits: namely,
* _ if two adjacent bits will always have different values.
*
* Example 1:
* Input: n = 5
* Output: true
* Explanation: The binary representation of 5 is: 101
*
* Example 2:
* Input: n = 7
* Output: false
* Explanation: The binary representation of 7 is: 111.
*
* Example 3:
* Input: n = 11
* Output: false
* Explanation: The binary representation of 11 is: 1011.
*
* Constraints:
* • `1 <= n <= 231 - 1`
* 
****************************************/

class Solution {
    // Shift the number right and XOR it with the original.
    // If bits alternate, the XOR result becomes all 1s.
    // A number with all 1s satisfies x & (x + 1) == 0.
    // Time complexity is O(1) and space complexity is O(1).
    public boolean hasAlternatingBits(int n) {
        int xor = n ^ (n >> 1);
        return (xor & (xor + 1)) == 0;
    }
}