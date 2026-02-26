// Source: https://leetcode.com/problems/number-of-steps-to-reduce-a-number-in-binary-representation-to-one/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-24
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 42.79 MB Beats 76.11%

/****************************************
* 
* Given the binary representation of an integer as a string s, return the
* _ number of steps to reduce it to 1 under the following rules:
* • If the current number is even, you have to divide it by `2`.
* • If the current number is odd, you have to add `1` to it.
* It is guaranteed that you can always reach one for all test cases.
*
* Example 1:
* Input: s = "1101"
* Output: 6
* Explanation: "1101" corressponds to number 13 in their decimal representation.
* Step 1) 13 is odd, add 1 and obtain 14.
* Step 2) 14 is even, divide by 2 and obtain 7.
* Step 3) 7 is odd, add 1 and obtain 8.
* Step 4) 8 is even, divide by 2 and obtain 4.
* Step 5) 4 is even, divide by 2 and obtain 2.
* Step 6) 2 is even, divide by 2 and obtain 1.
*
* Example 2:
* Input: s = "10"
* Output: 1
* Explanation: "10" corresponds to number 2 in their decimal representation.
* Step 1) 2 is even, divide by 2 and obtain 1.
*
* Example 3:
* Input: s = "1"
* Output: 0
*
* Constraints:
* • `1 <= s.length <= 500`
* • `s` consists of characters '0' or '1'
* • `s[0] == '1'`
* 
****************************************/

class Solution {
    // We simulate reducing the binary number directly from right to left.
    // A carry tracks whether a previous "+1" affected the current bit.
    // Even bits require one step (divide by 2); odd bits require two steps
    // (add 1, then divide). The most significant bit is handled at the end.
    // Time complexity is O(n); space complexity is O(1).
    public int numSteps(String s) {
        int steps = 0;
        int carry = 0;

        // Traverse from least significant bit to the second most significant bit
        for (int i = s.length() - 1; i > 0; i--) {
            int bit = (s.charAt(i) - '0') + carry;

            if (bit % 2 == 0) {
                // Even → divide by 2
                steps += 1;
            } else {
                // Odd → add 1, then divide by 2
                steps += 2;
                carry = 1;
            }
        }

        // If there's a carry left at the MSB, it adds one final step
        return steps + carry;
    }
}