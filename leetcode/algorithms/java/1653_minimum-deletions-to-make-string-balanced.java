// Source: https://leetcode.com/problems/minimum-deletions-to-make-string-balanced/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-06
// At the time of submission:
//   Runtime 37 ms Beats 42.76%
//   Memory 47.82 MB Beats 45.39%

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
    // We want all 'a's before all 'b's. At each split, delete 'b's on the left
    // and 'a's on the right. Track b's seen so far and a's remaining ahead.
    // Sweep once from left to right and minimize (bSeen + aRemaining).
    // Time: O(n), Space: O(1)
    public int minimumDeletions(String s) {
        int aRemaining = 0;
        for (char c : s.toCharArray()) {
            if (c == 'a') aRemaining++;
        }

        int bSeen = 0;
        int minDeletions = aRemaining;

        for (char c : s.toCharArray()) {
            if (c == 'a') {
                aRemaining--;
            } else { // c == 'b'
                bSeen++;
            }
            minDeletions = Math.min(minDeletions, bSeen + aRemaining);
        }

        return minDeletions;
    }
}
