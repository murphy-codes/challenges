// Source: https://leetcode.com/problems/reverse-bits/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-16
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 42.49 MB Beats 71.37%

/****************************************
* 
* Reverse bits of a given 32 bits signed integer.
*
* Example 1:
* Input: n = 43261596
* Output: 964176192
* Explanation:
* Integer        Binary
* 43261596        00000010100101000001111010011100
* 964176192        00111001011110000010100101000000
*
* Example 2:
* Input: n = 2147483644
* Output: 1073741822
* Explanation:
* Integer        Binary
* 2147483644        01111111111111111111111111111100
* 1073741822        00111111111111111111111111111110
*
* Constraints:
* • `0 <= n <= 2^31 - 2`
* • `n` is even.
* 
****************************************/

class Solution {
    // Reverse the bits by iterating exactly 32 times and rebuilding the number.
    // Each step shifts the result left, copies the current LSB from n, then
    // shifts n right to expose the next bit. This avoids strings and extra
    // memory. Time complexity is O(1) and space complexity is O(1).
    public int reverseBits(int n) {
        int result = 0;

        for (int i = 0; i < 32; i++) {
            result <<= 1;        // Make room for the next bit
            result |= (n & 1);   // Copy LSB from n into result
            n >>= 1;             // Shift n to process next bit
        }

        return result;
    }
}
