// Source: https://leetcode.com/problems/zigzag-conversion/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-02
// At the time of submission:
//   Runtime 4 ms Beats 84.19%
//   Memory 45.03 MB Beats 61.13%

/****************************************
* 
* 12345678901234567890123456789012345678901234567890123456789012345678901234567890
* The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of 
* _ rows like this: 
* P   A   H   N
* A P L S I I G
* Y   I   R
* (you may want to display this pattern in a fixed font for better legibility)
* And then read line by line: "PAHNAPLSIIGYIR"
* Write the code that will take a string and make this conversion given a number 
* _ of rows:
* _ > string convert(string s, int numRows);
* 
* Example 1:
* Input: s = "PAYPALISHIRING", numRows = 3
* Output: "PAHNAPLSIIGYIR"
* 
* Example 2:
* Input: s = "PAYPALISHIRING", numRows = 4
* Output: "PINALSIGYAHRPI"
* Explanation:
* P     I    N
* A   L S  I G
* Y A   H R
* P     I
* 
* Example 3:
* Input: s = "A", numRows = 1
* Output: "A"
* 
* Constraints:
* • 1 <= s.length <= 1000
* • `s` consists of English letters (lower-case and upper-case), `','` and `'.'`.
* • 1 <= numRows <= 1000
* 
****************************************/

class Solution {
    // To solve in O(n), we'll use an array of StringBuilders for the rows.
    // We iterate through the input string `s` once, appending per row.  
    // The direction of traversal will switch at the top or bottom row.
    // Lastly, we combine all rows into a single string for the zigzag result.
    public String convert(String s, int numRows) {
        if (numRows == 1 || numRows >= s.length()) {
            return s;
        }
        StringBuilder[] rows = new StringBuilder[numRows];
        for(int i=0; i<numRows; i++) {
            rows[i] = new StringBuilder();
        }
        boolean descending = true;
        int i = 0;
        for (char c : s.toCharArray()) {
            rows[i].append(c);
            i += descending ? 1 : -1;
            if (i == 0 || i == numRows - 1) {
                descending = !descending;
            }
        }
        StringBuilder result = new StringBuilder();
        for (StringBuilder sb : rows) {
            result.append(sb);
        }
        return result.toString();
    }
}