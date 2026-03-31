// Source: https://leetcode.com/problems/check-if-strings-can-be-made-equal-with-operations-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-29
// At the time of submission:
//   Runtime 5 ms Beats 93.81%
//   Memory 47.99 MB Beats 72.94%

/****************************************
* 
* You are given two strings `s1` and `s2`, both of length `n`, consisting of
* _ lowercase English letters.
* You can apply the following operation on any of the two strings any number of times:
* • Choose any two indices `i` and `j` such that `i < j` and the difference
* __ `j - i` is even, then swap the two characters at those indices in the string.
* Return `true` if you can make the strings `s1` and `s2` equal, and `false` otherwise.
*
* Example 1:
* Input: s1 = "abcdba", s2 = "cabdab"
* Output: true
* Explanation: We can apply the following operations on s1:
* - Choose the indices i = 0, j = 2. The resulting string is s1 = "cbadba".
* - Choose the indices i = 2, j = 4. The resulting string is s1 = "cbbdaa".
* - Choose the indices i = 1, j = 5. The resulting string is s1 = "cabdab" = s2.
*
* Example 2:
* Input: s1 = "abe", s2 = "bea"
* Output: false
* Explanation: It is not possible to make the two strings equal.
*
* Constraints:
* • `n == s1.length == s2.length`
* • `1 <= n <= 10^5`
* • `s1` and `s2` consist only of lowercase English letters.
* 
****************************************/

class Solution {
    // Swaps are allowed only between indices with same parity,
    // so even and odd indices form independent groups.
    // Characters can be freely permuted within each group.
    // Compare frequency counts of even and odd positions separately.
    // Time: O(n); Space: O(1) using fixed-size frequency arrays.
    public boolean checkStrings(String s1, String s2) {
        int n = s1.length();

        int[] evenCount = new int[26];
        int[] oddCount = new int[26];

        for (int i = 0; i < n; i++) {
            int c1 = s1.charAt(i) - 'a';
            int c2 = s2.charAt(i) - 'a';

            if (i % 2 == 0) {
                evenCount[c1]++;
                evenCount[c2]--;
            } else {
                oddCount[c1]++;
                oddCount[c2]--;
            }
        }

        // Check if all counts balance out
        for (int i = 0; i < 26; i++) {
            if (evenCount[i] != 0 || oddCount[i] != 0) {
                return false;
            }
        }

        return true;
    }
}