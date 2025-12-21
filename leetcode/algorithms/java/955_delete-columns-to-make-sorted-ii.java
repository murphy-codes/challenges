// Source: https://leetcode.com/problems/delete-columns-to-make-sorted-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-20
// At the time of submission:
//   Runtime 1 ms Beats 78.08%
//   Memory 43.77 MB Beats 27.40%

/****************************************
* 
* You are given an array of `n` strings `strs`, all of the same length.
* We may choose any deletion indices, and we delete all the characters in those
* _ indices for each string.
* For example, if we have `strs = ["abcdef","uvwxyz"]` and deletion indices
* _ `{0, 2, 3}`, then the final array after deletions is `["bef", "vyz"]`.
* Suppose we chose a set of deletion indices answer such that after deletions,
* _ the final array has its elements in lexicographic order
* _ (i.e., `strs[0] <= strs[1] <= strs[2] <= ... <= strs[n - 1]`).
* _ Return the minimum possible value of `answer.length`.
*
* Example 1:
* Input: strs = ["ca","bb","ac"]
* Output: 1
* Explanation:
* After deleting the first column, strs = ["a", "b", "c"].
* Now strs is in lexicographic order (ie. strs[0] <= strs[1] <= strs[2]).
* We require at least 1 deletion since initially strs was not in lexicographic order, so the answer is 1.
*
* Example 2:
* Input: strs = ["xc","yb","za"]
* Output: 0
* Explanation:
* strs is already in lexicographic order, so we do not need to delete anything.
* Note that the rows of strs are not necessarily in lexicographic order:
* i.e., it is NOT necessarily true that (strs[0][0] <= strs[0][1] <= ...)
*
* Example 3:
* Input: strs = ["zyx","wvu","tsr"]
* Output: 3
* Explanation: We have to delete every column.
*
* Constraints:
* • `n == strs.length`
* • `1 <= n <= 100`
* • `1 <= strs[i].length <= 100`
* • `strs[i]` consists of lowercase English letters.
* 
****************************************/

class Solution {
    // Greedily scan columns left to right, deleting any column that would
    // violate lexicographic order for unresolved adjacent row pairs.
    // Track which row pairs are already strictly ordered to avoid rechecking.
    // Time: O(n * m), Space: O(n), where n = rows and m = columns.
    public int minDeletionSize(String[] strs) {
        int n = strs.length;
        int m = strs[0].length();
        boolean[] sorted = new boolean[n - 1];
        int deletions = 0;

        for (int col = 0; col < m; col++) {
            boolean bad = false;

            // Check if this column breaks ordering
            for (int row = 0; row < n - 1; row++) {
                if (!sorted[row] &&
                    strs[row].charAt(col) > strs[row + 1].charAt(col)) {
                    bad = true;
                    break;
                }
            }

            if (bad) {
                deletions++;
                continue;
            }

            // Update which row pairs are now confirmed sorted
            for (int row = 0; row < n - 1; row++) {
                if (!sorted[row] &&
                    strs[row].charAt(col) < strs[row + 1].charAt(col)) {
                    sorted[row] = true;
                }
            }
        }

        return deletions;
    }
}
