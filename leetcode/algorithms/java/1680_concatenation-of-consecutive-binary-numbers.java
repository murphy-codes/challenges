// Source: https://leetcode.com/problems/concatenation-of-consecutive-binary-numbers/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-27
// At the time of submission:
//   Runtime 24 ms Beats 98.84%
//   Memory 42.33 MB Beats 84.88%

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
    // We build the result incrementally by appending each number in binary.
    // Appending i means shifting the current value left by bitLength(i).
    // The bit length increases only when i is a power of two.
    // Each step applies modulo arithmetic to avoid overflow.
    // Time complexity is O(n), space complexity is O(1).
    public int concatenatedBinary(int n) {
        final int MOD = 1_000_000_007;

        long result = 0;
        int bitLength = 0;

        for (int i = 1; i <= n; i++) {
            // If i is a power of two, its binary length increases by 1
            if ((i & (i - 1)) == 0) {
                bitLength++;
            }

            // Shift left to make space for i, then add i
            result = ((result << bitLength) + i) % MOD;
        }

        return (int) result;
    }
}