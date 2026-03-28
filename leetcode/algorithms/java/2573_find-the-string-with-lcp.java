// Source: https://leetcode.com/problems/find-the-string-with-lcp/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-27
// At the time of submission:
//   Runtime 5 ms Beats 100.00%
//   Memory 173.13 MB Beats 91.43%

/****************************************
* 
* We define the `lcp` matrix of any 0-indexed string `word` of `n` lowercase
* _ English letters as an `n x n` grid such that:
* • `lcp[i][j]` is equal to the length of the longest common prefix between
* __ the substrings `word[i,n-1]` and `word[j,n-1]`.
* Given an `n x n` matrix `lcp`, return the alphabetically smallest string
* _ `word` that corresponds to `lcp`. If there is no such string, return an
* _ empty string.
* A string `a` is lexicographically smaller than a string `b` (of the same
* _ length) if in the first position where `a` and `b` differ, string `a` has
* _ a letter that appears earlier in the alphabet than the corresponding letter
* _ in `b`. For example, `"aabd"` is lexicographically smaller than `"aaca"`
* _ because the first position they differ is at the third letter, and
* _ `'b'` comes before `'c'`.
*
* Example 1:
* Input: lcp = [[4,0,2,0],[0,3,0,1],[2,0,2,0],[0,1,0,1]]
* Output: "abab"
* Explanation: lcp corresponds to any 4 letter string with two alternating letters. The lexicographically smallest of them is "abab".
*
* Example 2:
* Input: lcp = [[4,3,2,1],[3,3,2,1],[2,2,2,1],[1,1,1,1]]
* Output: "aaaa"
* Explanation: lcp corresponds to any 4 letter string with a single distinct letter. The lexicographically smallest of them is "aaaa".
*
* Example 3:
* Input: lcp = [[4,3,2,1],[3,3,2,1],[2,2,2,1],[1,1,1,3]]
* Output: ""
* Explanation: lcp[3][3] cannot be equal to 3 since word[3,...,3] consists of only a single letter; Thus, no answer exists.
*
* Constraints:
* • `1 <= n == lcp.length == lcp[i].length <= 1000`
* • `0 <= lcp[i][j] <= n`
* 
****************************************/

class Solution {
    // Greedily assign smallest characters and propagate equality using
    // lcp[i][j] > 0 constraints to build the string without DSU.
    // Then validate using LCP recurrence: if chars match, lcp[i][j]
    // must equal lcp[i+1][j+1] + 1, else must be 0.
    // Bottom-up traversal ensures dependencies are already validated.
    // Time: O(n^2); Space: O(1) extra (excluding input).

    public String findTheString(int[][] lcp) {
        int n = lcp.length;
        char[] word = new char[n];
        char nextChar = 'a';

        // Step 1: Construct string using greedy assignment
        for (int i = 0; i < n; i++) {

            // If not assigned yet, assign smallest available char
            if (word[i] == 0) {

                if (nextChar > 'z') {
                    return ""; // Too many distinct groups
                }

                word[i] = nextChar;

                // Propagate equality: lcp[i][j] > 0 ⇒ same char
                for (int j = i + 1; j < n; j++) {
                    if (lcp[i][j] > 0) {
                        word[j] = word[i];
                    }
                }

                nextChar++;
            }
        }

        // Step 2: Validate using LCP recurrence
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {

                if (word[i] != word[j]) {
                    // Different chars ⇒ LCP must be 0
                    if (lcp[i][j] != 0) {
                        return "";
                    }
                } else {
                    // Same chars ⇒ follow recurrence
                    if (i == n - 1 || j == n - 1) {
                        if (lcp[i][j] != 1) {
                            return "";
                        }
                    } else {
                        if (lcp[i][j] != lcp[i + 1][j + 1] + 1) {
                            return "";
                        }
                    }
                }
            }
        }

        return new String(word);
    }
}