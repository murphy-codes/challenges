// Source: https://leetcode.com/problems/minimum-deletions-to-make-string-balanced/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-06
// At the time of submission:
//   Runtime 18 ms Beats 100.00%
//   Memory 47.91 MB Beats 30.43%

/****************************************
* 
* You are given a string `s` consisting only of characters `'a'` and `'b'​​​​`.
* You can delete any number of characters in `s` to make `s` balanced. `s` is
* _ balanced if there is no pair of indices `(i,j)` such that `i < j` and
* _ `s[i] = 'b'` and `s[j]= 'a'`.
* Return the minimum number of deletions needed to make `s` balanced.
*
* Example 1:
* Input: s = "aababbab"
* Output: 2
* Explanation: You can either:
* Delete the characters at 0-indexed positions 2 and 6 ("aababbab" -> "aaabbb"), or
* Delete the characters at 0-indexed positions 3 and 6 ("aababbab" -> "aabbbb").
*
* Example 2:
* Input: s = "bbaaaaabb"
* Output: 2
* Explanation: The only solution is to delete the first two characters.
*
* Constraints:
* • `1 <= s.length <= 10^5`
* • `s[i]` is `'a'` or `'b'​​`.
* 
****************************************/

class Solution {
    // We scan left to right, tracking unmatched 'b's seen so far.
    // When an 'a' appears after a 'b', it forms a "ba" violation.
    // Greedily delete the 'a' and cancel one prior 'b'.
    // Each deletion fixes exactly one violation.
    // Time: O(n), Space: O(1)
    public int minimumDeletions(String s) {
        int deletions = 0;   // total deletions needed
        int bCount = 0;      // number of unmatched 'b's seen so far

        for (char ch : s.toCharArray()) {
            if (ch == 'b') {
                // This 'b' may violate balance with a future 'a'
                bCount++;
            } else if (bCount > 0) {
                // Found a 'ba' pattern: delete this 'a' and cancel one 'b'
                deletions++;
                bCount--;
            }
        }

        return deletions;
    }
}
