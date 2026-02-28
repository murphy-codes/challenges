// Source: https://leetcode.com/problems/concatenation-of-consecutive-binary-numbers/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-27
// At the time of submission:
//   Runtime 23 ms Beats 100.00%
//   Memory 42.40 MB Beats 84.88%

/****************************************
* 
* Given an integer `n`, return the decimal value of the binary string formed
* _ by concatenating the binary representations of `1` to `n` in order,
* _ modulo `10^9 + 7`.
*
* Example 1:
* Input: n = 1
* Output: 1
* Explanation: "1" in binary corresponds to the decimal value 1.
*
* Example 2:
* Input: n = 3
* Output: 27
* Explanation: In binary, 1, 2, and 3 corresponds to "1", "10", and "11".
* After concatenating them, we have "11011", which corresponds to the decimal value 27.
*
* Example 3:
* Input: n = 12
* Output: 505379714
* Explanation: The concatenation results in "1101110010111011110001001101010111100".
* The decimal value of that is 118505380540.
* After modulo 109 + 7, the result is 505379714.
*
* Constraints:
* • `1 <= n <= 10^5`
* 
****************************************/

class Solution {
    // We build the result by appending numbers 1 through n in binary form.
    // Appending a number means left-shifting by its bit length, then adding it.
    // The bit length increases only when the number is a power of two.
    // Modulo arithmetic prevents overflow during accumulation.
    // Time complexity is O(n); space complexity is O(1).
    public int concatenatedBinary(int n) {

        long result = 0;
        long modulo = 1_000_000_007;
        int bitLength = 0;

        for (int value = 1; value <= n; value++) {

            // Bit length increases when value is a power of two
            if ((value & (value - 1)) == 0) {
                bitLength++;
            }

            // Shift left to make room for value, then append it
            result = ((result << bitLength) + value) % modulo;
        }

        return (int) result;
    }
}