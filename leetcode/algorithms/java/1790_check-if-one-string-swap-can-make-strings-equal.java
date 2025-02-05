// Source: https://leetcode.com/problems/check-if-one-string-swap-can-make-strings-equal/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-02-04

/****************************************
* 
* You are given two strings `s1` and `s2` of equal length. A string swap is an operation where you choose two indices in a string (not necessarily different) and swap the characters at these indices.
* 
* Return `true` if it is possible to make both strings equal by performing at most one string swap on exactly one of the strings. Otherwise, return `false`.
* 
* Example 1:
* Input: s1 = "bank", s2 = "kanb"
* Output: true
* Explanation: For example, swap the first character with the last character of s2 to make "bank".
* 
* Example 2:
* Input: s1 = "attack", s2 = "defend"
* Output: false
* Explanation: It is impossible to make them equal with one string swap.
* 
* Example 3:
* Input: s1 = "kelb", s2 = "kelb"
* Output: true
* Explanation: The two strings are already equal, so no string swap operation is required.
* 
* Constraints:
* • 1 <= s1.length, s2.length <= 100
* • s1.length == s2.length
* • `s1` and `s2` consist of only lowercase English letters.
* 
****************************************/

class Solution {
    // This solution finds the indices where s1 and s2 differ, tracking at most 2.  
    // If more than 2 differences exist, we return false immediately. If exactly 2  
    // differences exist, we check if swapping those characters in one string makes  
    // them equal. The time complexity is O(n) since we traverse s1 and s2 once,  
    // and the space complexity is O(1) as we use only a few integer variables.  
    public boolean areAlmostEqual(String s1, String s2) {
        if (s1.equals(s2)) return true; // Already equal, no swap needed.

        int first = -1, second = -1; // Indices of differing characters.
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                if (first == -1) {
                    first = i; // First mismatch.
                } else if (second == -1) {
                    second = i; // Second mismatch.
                } else {
                    return false; // More than two mismatches → impossible to swap.
                }
            }
        }

        // Exactly two mismatches → check if swapping makes them equal.
        return (second != -1 && 
                s1.charAt(first) == s2.charAt(second) &&
                s1.charAt(second) == s2.charAt(first));
    }
}
