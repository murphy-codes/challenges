// Source: https://leetcode.com/problems/add-binary/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-15
// At the time of submission:
//   Runtime 1 ms Beats 99.65%
//   Memory 43.59 MB Beats 70.73%

/****************************************
* 
* Given two binary strings `a` and `b`, return their sum as a binary string.
*
* Example 1:
* Input: a = "11", b = "1"
* Output: "100"
*
* Example 2:
* Input: a = "1010", b = "1011"
* Output: "10101"
*
* Constraints:
* • `1 <= a.length, b.length <= 10^4`
* • `a` and `b` consist only of '0' or '1' characters.
* • Each string does not contain leading zeros except for the zero itself.
* 
****************************************/

class Solution {
    // Perform binary addition from right to left using two pointers and a carry.
    // Each step adds the current bits and carry, appending sum % 2 to the result.
    // Carry is updated as sum / 2. The result is built in reverse and flipped at
    // the end. Time complexity is O(n) and space complexity is O(n), where n is
    // the length of the longer input string.
    public String addBinary(String a, String b) {
        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;

        StringBuilder result = new StringBuilder();

        while (i >= 0 || j >= 0 || carry == 1) {
            int sum = carry;

            if (i >= 0) {
                sum += a.charAt(i) - '0';
                i--;
            }
            if (j >= 0) {
                sum += b.charAt(j) - '0';
                j--;
            }

            result.append(sum % 2);
            carry = sum / 2;
        }

        return result.reverse().toString();
    }
}