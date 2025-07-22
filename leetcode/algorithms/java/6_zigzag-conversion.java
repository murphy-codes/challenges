// Source: https://leetcode.com/problems/zigzag-conversion/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-02
// At the time of submission:
//   Runtime 2 ms Beats 99.60%
//   Memory 44.60 MB Beats 97.47%

/****************************************
* 
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
    // This solution calculates positions of characters directly in zigzag order.
    // It fills a result char array by traversing each row and jumping by step size.
    // Middle rows also insert diagonal chars between vertical columns.
    // Time: O(n), Space: O(n), where n is the input string length.
    public String convert(String s, int numRows) {
        if (numRows == 1) return s;

        int len = s.length();
        char[] result = new char[len];
        int step = (numRows - 1) * 2;
        int resultIndex = 0;

        for (int row = 0; row < numRows; row++) {
            int j = row;
            while (j < len) {
                // Always add the character in the current vertical position
                result[resultIndex++] = s.charAt(j);

                // For middle rows, add the diagonal character if within bounds
                if (row > 0 && row < numRows - 1) {
                    int diagonalIndex = j + (numRows - row - 1) * 2;
                    if (diagonalIndex < len) {
                        result[resultIndex++] = s.charAt(diagonalIndex);
                    }
                }

                // Move to the next vertical column in this zigzag cycle
                j += step;
            }
        }

        return new String(result);
    }
}
