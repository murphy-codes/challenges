// Source: https://leetcode.com/problems/find-the-string-with-lcp/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-27
// At the time of submission:
//   Runtime 10 ms Beats 42.86%
//   Memory 178.86 MB Beats 8.57%

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
    // Use DSU to group indices that must share the same character
    // based on lcp[i][j] > 0 constraints. Assign smallest letters
    // to each group to ensure lexicographically minimal result.
    // Rebuild LCP using DP and validate against input matrix.
    // Time: O(n^2); Space: O(n^2) for DP validation.
    public String findTheString(int[][] lcp) {
        int n = lcp.length;

        // Step 1: Validate diagonal
        for (int i = 0; i < n; i++) {
            if (lcp[i][i] != n - i) return "";
        }

        // Step 2: DSU to group equal indices
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (lcp[i][j] > 0) {
                    union(parent, i, j);
                }
            }
        }

        // Step 3: Assign smallest lexicographic characters
        char[] word = new char[n];
        int[] groupChar = new int[n];
        for (int i = 0; i < n; i++) groupChar[i] = -1;

        int nextChar = 0;

        for (int i = 0; i < n; i++) {
            int root = find(parent, i);

            if (groupChar[root] == -1) {
                if (nextChar >= 26) return "";
                groupChar[root] = nextChar++;
            }

            word[i] = (char) ('a' + groupChar[root]);
        }

        // Step 4: Recompute LCP and validate
        int[][] dp = new int[n + 1][n + 1];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (word[i] == word[j]) {
                    dp[i][j] = dp[i + 1][j + 1] + 1;
                } else {
                    dp[i][j] = 0;
                }

                if (dp[i][j] != lcp[i][j]) return "";
            }
        }

        return new String(word);
    }

    private int find(int[] parent, int x) {
        if (parent[x] != x) {
            parent[x] = find(parent, parent[x]);
        }
        return parent[x];
    }

    private void union(int[] parent, int a, int b) {
        int pa = find(parent, a);
        int pb = find(parent, b);
        if (pa != pb) {
            parent[pa] = pb;
        }
    }
}