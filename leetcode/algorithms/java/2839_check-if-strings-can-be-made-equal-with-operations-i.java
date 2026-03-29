// Source: https://leetcode.com/problems/check-if-strings-can-be-made-equal-with-operations-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-29
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 44.47 MB Beats 35.94%

/****************************************
* 
* You are given two strings `s1` and `s2`, both of length `4`, consisting of
* _ lowercase English letters.
* You can apply the following operation on any of the two strings any
* _ number of times:
* • Choose any two indices `i` and `j` such that `j - i = 2`, then swap
* __ the two characters at those indices in the string.
* Return true if you can make the strings s1 and s2 equal, and false otherwise.
*
* Example 1:
* Input: s1 = "abcd", s2 = "cdab"
* Output: true
* Explanation: We can do the following operations on s1:
* - Choose the indices i = 0, j = 2. The resulting string is s1 = "cbad".
* - Choose the indices i = 1, j = 3. The resulting string is s1 = "cdab" = s2.
*
* Example 2:
* Input: s1 = "abcd", s2 = "dacb"
* Output: false
* Explanation: It is not possible to make the two strings equal.
*
* Constraints:
* • `s1.length == s2.length == 4`
* • `s1` and `s2` consist only of lowercase English letters.
* 
****************************************/

class Solution {
    // Only swaps allowed are (0↔2) and (1↔3), so even and odd indices
    // form independent groups. Characters can be freely permuted within
    // each group. Compare sorted even-index characters and sorted odd-
    // index characters between both strings.
    // Time: O(1); Space: O(1).
    public boolean canBeEqual(String s1, String s2) {

        char[] s1Even = new char[] { s1.charAt(0), s1.charAt(2) };
        char[] s1Odd  = new char[] { s1.charAt(1), s1.charAt(3) };

        char[] s2Even = new char[] { s2.charAt(0), s2.charAt(2) };
        char[] s2Odd  = new char[] { s2.charAt(1), s2.charAt(3) };

        java.util.Arrays.sort(s1Even);
        java.util.Arrays.sort(s1Odd);
        java.util.Arrays.sort(s2Even);
        java.util.Arrays.sort(s2Odd);

        return s1Even[0] == s2Even[0] &&
               s1Even[1] == s2Even[1] &&
               s1Odd[0]  == s2Odd[0]  &&
               s1Odd[1]  == s2Odd[1];
    }
}