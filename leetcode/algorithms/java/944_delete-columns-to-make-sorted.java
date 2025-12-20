// Source: https://leetcode.com/problems/delete-columns-to-make-sorted/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-19
// At the time of submission:
//   Runtime 5 ms Beats 99.36%
//   Memory 47.05 MB Beats 39.84%

/****************************************
* 
* You are given an array of `n` strings `strs`, all of the same length.
* The strings can be arranged such that there is one on each line, making a grid.
* • For example, `strs = ["abc", "bce", "cae"]` can be arranged as follows:
* abc
* bce
* cae
* You want to delete the columns that are not sorted lexicographically. In the
* _ above example (0-indexed), columns 0 (`'a'`, `'b'`, `'c'`) and
* _ 2 (`'c'`, `'e'`, `'e'`) are sorted, while column 1 (`'b'`, `'c'`, `'a'`) is
* _ not, so you would delete column 1.
* Return the number of columns that you will delete.
*
* Example 1:
* Input: strs = ["cba","daf","ghi"]
* Output: 1
* Explanation: The grid looks as follows:
* cba
* daf
* ghi
* Columns 0 and 2 are sorted, but column 1 is not, so you only need to delete 1 column.
*
* Example 2:
* Input: strs = ["a","b"]
* Output: 0
* Explanation: The grid looks as follows:
* a
* b
* Column 0 is the only column and is sorted, so you will not delete any columns.
*
* Example 3:
* Input: strs = ["zyx","wvu","tsr"]
* Output: 3
* Explanation: The grid looks as follows:
* zyx
* wvu
* tsr
* All 3 columns are not sorted, so you will delete all 3.
*
* Constraints:
* • `n == strs.length`
* • `1 <= n <= 100`
* • `1 <= strs[i].length <= 1000`
* • `strs[i]` consists of lowercase English letters.
* 
****************************************/

class Solution {
    // Iterate column by column and check if characters are non-decreasing
    // from top to bottom. Track the last seen character per column.
    // If any character is smaller than the previous one, the column
    // is unsorted and must be deleted. Time: O(n * m), Space: O(1), 
    // where n = rows and m = columns.
    public int minDeletionSize(String[] strs) {
        int n = strs.length;
        int m = strs[0].length();
        int del = 0;
        for (int i = 0; i < m; i++) {
            int last = 0;
            for (int j = 0; j < n; j++) {
                int curr = strs[j].charAt(i)-'a';
                if (curr<last) { 
                    del++;
                    break; 
                } else { last = curr; }
            }
        }
        return del;
    }
}