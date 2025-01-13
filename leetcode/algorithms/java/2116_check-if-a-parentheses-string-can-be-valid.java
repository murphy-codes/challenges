// Source: https://leetcode.com/problems/check-if-a-parentheses-string-can-be-valid/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-12

/****************************************
* 
* A parentheses string is a non-empty string consisting only of `'('` and `')'`. It is valid if any of the * following conditions is true:
* • It is `()`.
* • It can be written as `AB` (`A` concatenated with `B`), where `A` and `B` are valid parentheses strings.
* • It can be written as `(A)`, where `A` is a valid parentheses string.
* 
* You are given a parentheses string `s` and a string `locked`, both of length `n`. `locked` is a binary * string consisting only of `'0'`s and `'1'`s. For each index `i` of `locked`,
* • If `locked[i]` is `'1'`, you cannot change `s[i]`.
* • But if `locked[i]` is `'0'`, you can change `s[i]` to either `'('` or `')'`.
* 
* Return `true` if you can make `s` a valid parentheses string. Otherwise, return `false`.
* 
*   (Image for Example 1: https://assets.leetcode.com/uploads/2021/11/06/eg1.png)
* Example 1:
* Input: s = "))()))", locked = "010100"
* Output: true
* Explanation: locked[1] == '1' and locked[3] == '1', so we cannot change s[1] or s[3].
* We change s[0] and s[4] to '(' while leaving s[2] and s[5] unchanged to make s valid.
* 
* Example 2:
* Input: s = "()()", locked = "0000"
* Output: true
* Explanation: We do not need to make any changes because s is already valid.
* 
* Example 3:
* Input: s = ")", locked = "0"
* Output: false
* Explanation: locked permits us to change s[0]. 
* Changing s[0] to either '(' or ')' will not make s valid.
* 
* Constraints:
* • n == s.length == locked.length
* • 1 <= n <= 10^5
* • s[i] is either '(' or ')'.
* • locked[i] is either '0' or '1'.
* 
****************************************/

class Solution {
    // Check edge case: if length of s is odd, return false
    // Traverse left-to-right, counting open slots ('(' or wildcards)
    // If ')' exceeds available open slots at any point, return false
    // Traverse right-to-left, counting close slots (')' or wildcards)
    // If '(' exceeds available close slots at any point, return false
    // If both passes succeed, return true
    // Time: O(n) & Memory: O(1)
    public boolean canBeValid(String s, String locked) {
        // If length is odd, it's impossible to have valid parentheses
        if (s.length() % 2 != 0) return false;

        // Left-to-right pass
        int openSlots = 0; // Tracks potential open positions
        for (int i = 0; i < s.length(); i++) {
            // Increment for '(' or a wildcard
            if (s.charAt(i) == '(' || locked.charAt(i) == '0') {
                openSlots++;
            } else { // It's a locked ')'
                openSlots--;
            }
            // If at any point ')' exceeds open slots, it's invalid
            if (openSlots < 0) return false;
        }

        // Right-to-left pass
        int closeSlots = 0; // Tracks potential close positions
        for (int i = s.length() - 1; i >= 0; i--) {
            // Increment for ')' or a wildcard
            if (s.charAt(i) == ')' || locked.charAt(i) == '0') {
                closeSlots++;
            } else { // It's a locked '('
                closeSlots--;
            }
            // If at any point '(' exceeds close slots, it's invalid
            if (closeSlots < 0) return false;
        }

        // Both passes succeeded, return true
        return true;
    }
}
