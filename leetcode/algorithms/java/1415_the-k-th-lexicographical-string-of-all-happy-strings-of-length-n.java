// Source: https://leetcode.com/problems/the-k-th-lexicographical-string-of-all-happy-strings-of-length-n/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-14
// At the time of submission:
//   Runtime 1 ms Beats 99.96%
//   Memory 42.76 MB Beats 96.44%

/****************************************
* 
* A happy string is a string that:
* • consists only of letters of the set `['a', 'b', 'c']`.
* • `s[i] != s[i + 1]` for all values of `i` from `1` to `s.length - 1` (string is 1-indexed).
* For example, strings "abc", "ac", "b" and "abcbabcbcb" are all happy strings and strings "aa", "baa" and "ababbc" are not happy strings.
* Given two integers `n` and `k`, consider a list of all happy strings of length `n` sorted in lexicographical order.
* Return the kth string of this list or return an empty string if there are less than `k` happy strings of length `n`.
* 
* Example 1:
* Input: n = 1, k = 3
* Output: "c"
* Explanation: The list ["a", "b", "c"] contains all happy strings of length 1. The third string is "c".
* 
* Example 2:
* Input: n = 1, k = 4
* Output: ""
* Explanation: There are only 3 happy strings of length 1.
* 
* Example 3:
* Input: n = 3, k = 9
* Output: "cab"
* Explanation: There are 12 different happy string of length 3 ["aba", "abc", "aca", "acb", "bab", "bac", "bca", "bcb", "cab", "cac", "cba", "cbc"]. You will find the 9th string = "cab"
* 
* Constraints:
* • `1 <= n <= 10`
* • `1 <= k <= 100`
* 
****************************************/

class Solution {
    // Generate happy strings using backtracking in lexicographical order.
    // A StringBuilder is reused to avoid repeated string allocations.
    // Each valid string increments a counter and once the k-th string is
    // reached the search stops early. This avoids generating all strings.
    // Time: O(2^n) worst case, Space: O(n) recursion depth.

    // Allowed characters for happy strings
    private static final char[] LETTERS = { 'a', 'b', 'c' };

    // Counts how many happy strings we have generated so far
    private int generatedCount;

    // Stores the k-th happy string once found
    private String result;

    public String getHappyString(int n, int k) {

        generatedCount = 0;
        result = "";

        // Start DFS/backtracking
        buildHappyString(new StringBuilder(), n, k);

        return generatedCount >= k ? result : "";
    }

    private void buildHappyString(StringBuilder current, int n, int k) {

        int length = current.length();

        // If we built a full string, count it
        if (length == n) {

            generatedCount++;

            if (generatedCount == k) {
                result = current.toString();
            }

            return;
        }

        // Try adding each allowed character
        for (char ch : LETTERS) {

            // Skip if same as previous character
            if (length > 0 && ch == current.charAt(length - 1))
                continue;

            current.append(ch);

            buildHappyString(current, n, k);

            // Backtrack
            current.deleteCharAt(length);

            // Stop early once k-th string found
            if (generatedCount == k)
                return;
        }
    }
}