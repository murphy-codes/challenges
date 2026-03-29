// Source: https://leetcode.com/problems/check-if-strings-can-be-made-equal-with-operations-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-29
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 44.49 MB Beats 35.94%

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
    // Only swaps allowed are (0↔2) and (1↔3), forming two independent
    // index groups. Each group can only permute its two characters.
    // So we check whether each pair from s1 matches the corresponding
    // pair in s2 (in any order) using constant-time comparisons.
    // Time: O(1); Space: O(1).
    public boolean canBeEqual(String s1, String s2) {
        return samePair(s1.charAt(0), s1.charAt(2),
                        s2.charAt(0), s2.charAt(2)) &&
               samePair(s1.charAt(1), s1.charAt(3),
                        s2.charAt(1), s2.charAt(3));
    }

    private boolean samePair(char a, char b, char c, char d) {
        return (a == c && b == d) || (a == d && b == c);
    }
}