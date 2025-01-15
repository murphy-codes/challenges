// Source: https://leetcode.com/problems/minimize-xor/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-14

/****************************************
* 
* Given two positive integers `num1` and `num2`, find the positive integer `x` such that:
* • `x` has the same number of set bits as `num2`, and
* • The value `x XOR num1` is minimal.
* Note that `XOR` is the bitwise XOR operation.
* Return the integer `x`. The test cases are generated such that `x` is uniquely determined.
* The number of set bits of an integer is the number of `1`'s in its binary representation.
* 
* Example 1:
* Input: num1 = 3, num2 = 5
* Output: 3
* Explanation:
* The binary representations of num1 and num2 are 0011 and 0101, respectively.
* The integer 3 has the same number of set bits as num2, and the value 3 XOR 3 = 0 is minimal.
* 
* Example 2:
* Input: num1 = 1, num2 = 12
* Output: 3
* Explanation:
* The binary representations of num1 and num2 are 0001 and 1100, respectively.
* The integer 3 has the same number of set bits as num2, and the value 3 XOR 1 = 2 is minimal.
* 
* Constraints:
* • 1 <= num1, num2 <= 10^9
* 
****************************************/

class Solution {
    // Approach: Construct x such that it has the same number of set bits as num2 
    // and minimizes x XOR num1. First, match high bits of num1 to minimize mismatches. 
    // Then, if more bits are needed, set the lowest unset bits in x. 
    // Time: O(1), Space: O(1).
    public int minimizeXor(int num1, int num2) {
        int x = 0;
        // Count the number of set bits in num2
        int num2Bits = Integer.bitCount(num2);

        for (int i = 31; i >= 0 && num2Bits > 0; i--) {
            if ((num1 & (1 << i)) != 0) { // Check if the ith bit of num1 is set
                x |= (1 << i); // Set the ith bit in x
                num2Bits--;
            }
        }

        for (int i = 0; i < 32 && num2Bits > 0; i++) {
            if ((x & (1 << i)) == 0) { // Check if the ith bit of x is unset
                x |= (1 << i); // Set the ith bit in x
                num2Bits--;
            }
        }

        return x;
    }
}
