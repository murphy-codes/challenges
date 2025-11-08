// Source: https://leetcode.com/problems/minimum-one-bit-operations-to-make-integers-zero/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-07
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 42.12 MB Beats 12.00%

/****************************************
* 
* Given an integer `n`, you must transform it into `0` using the following
* _ operations any number of times:
* • Change the rightmost (`0^th`) bit in the binary representation of `n`.
* • Change the `i^th` bit in the binary representation of `n` if the `(i-1)^th`
* __ bit is set to `1` and the `(i-2)^th` through `0^th` bits are set to `0`.
* Return the minimum number of operations to transform `n` into `0`.
*
* Example 1:
* Input: n = 3
* Output: 2
* Explanation: The binary representation of 3 is "11".
* 11 -> "01" with the 2nd operation since the 0th bit is 1.
* 01 -> "00" with the 1st operation.
*
* Example 2:
* Input: n = 6
* Output: 4
* Explanation: The binary representation of 6 is "110".
* 110 -> "010" with the 2nd operation since the 1st bit is 1 and 0th through 0th bits are 0.
* 010 -> "011" with the 1st operation.
* 011 -> "001" with the 2nd operation since the 0th bit is 1.
* 001 -> "000" with the 1st operation.
*
* Constraints:
* • `0 <= n <= 10^9`
* 
****************************************/

class Solution {
    // Recursively computes the minimum bit operations to turn n into 0.
    // Uses Gray-code pattern: f(n) = (2^(k+1)-1) - f(n ^ (1<<k)),
    // where k is the index of the highest set bit in n.
    // Each step clears the most significant bit while inverting lower bits.
    // Runs in O(log n) time and O(log n) recursion depth, using O(1) space.
    public int minimumOneBitOperations(int n) {
        if (n == 0) return 0; // Base case: already zero
        int k = 0;
        while ((1 << (k + 1)) <= n) k++; // find highest set bit
        // Use recurrence: f(n) = (2^(k+1) - 1) - f(n ^ (1<<k))
        // This mirrors Gray-code distance properties.
        return (1 << (k + 1)) - 1 - minimumOneBitOperations(n ^ (1 << k));
    }
}
