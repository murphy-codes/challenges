// Source: https://leetcode.com/problems/decode-the-slanted-ciphertext/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-04
// At the time of submission:
//   Runtime 17 ms Beats 99.38%
//   Memory 55.15 MB Beats 99.38%

/****************************************
* 
* A string `originalText` is encoded using a slanted transposition cipher to a
* _ string `encodedText` with the help of a matrix having a fixed number of
* _ rows `rows`.
* `originalText` is placed first in a top-left to bottom-right manner.
* [Image: https://assets.leetcode.com/uploads/2021/11/07/exa11.png]
* The blue cells are filled first, followed by the red cells, then the
* _ yellow cells, and so on, until we reach the end of `originalText`.
* _ The arrow indicates the order in which the cells are filled. All empty
* _ cells are filled with `' '`. The number of columns is chosen such that
* _ the rightmost column will not be empty after filling in `originalText`.
* `encodedText` is then formed by appending all characters of the matrix
* _ in a row-wise fashion.
* [Image: https://assets.leetcode.com/uploads/2021/11/07/exa12.png]
* The characters in the blue cells are appended first to `encodedText`,
* _ then the red cells, and so on, and finally the yellow cells. The arrow
* _ indicates the order in which the cells are accessed.
* For example, if `originalText = "cipher"` and `rows = 3`, then we
* _ encode it in the following manner:
* [Image: https://assets.leetcode.com/uploads/2021/10/25/desc2.png]
* The blue arrows depict how originalText is placed in the matrix, and
* _ the red arrows denote the order in which encodedText is formed. In
* _ the above example, encodedText = "ch ie pr".
* Given the encoded string `encodedText` and number of rows `rows`, return
* _ the original string `originalText`.
* Note: `originalText` does not have any trailing spaces `' '`. The test
* _ cases are generated such that there is only one possible `originalText`.
*
* Example 1:
* Input: encodedText = "ch   ie   pr", rows = 3
* Output: "cipher"
* Explanation: This is the same example described in the problem description.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2021/10/26/exam1.png]
* Input: encodedText = "iveo    eed   l te   olc", rows = 4
* Output: "i love leetcode"
* Explanation: The figure above denotes the matrix that was used to encode originalText.
* The blue arrows show how we can find originalText from encodedText.
*
* Example 3:
* [Image: https://assets.leetcode.com/uploads/2021/10/26/eg2.png]
* Input: encodedText = "coding", rows = 1
* Output: "coding"
* Explanation: Since there is only 1 row, both originalText and encodedText are the same.
*
* Constraints:
* • `0 <= encodedText.length <= 10^6`
* • `encodedText` consists of lowercase English letters and `' '` only.
* • `encodedText` is a valid encoding of some `originalText` that does not have trailing spaces.
* • `1 <= rows <= 1000`
* • The testcases are generated such that there is only one possible `originalText`.
* 
****************************************/

class Solution {
    // Treat encodedText as a row-wise filled matrix with rows and cols.
    // Original text is formed by reading diagonals starting from top row.
    // For each column, traverse down-right and collect characters.
    // Avoid building the matrix by computing indices directly.
    // Time: O(n), Space: O(n) for result string.
    public String decodeCiphertext(String encodedText, int rows) {
        int n = encodedText.length();
        if (rows == 1) return encodedText;

        int cols = n / rows;
        StringBuilder result = new StringBuilder();

        // Traverse diagonals starting from each column in row 0
        for (int startCol = 0; startCol < cols; startCol++) {
            int r = 0;
            int c = startCol;

            while (r < rows && c < cols) {
                result.append(encodedText.charAt(r * cols + c));
                r++;
                c++;
            }
        }

        // Remove trailing spaces
        int end = result.length() - 1;
        while (end >= 0 && result.charAt(end) == ' ') {
            end--;
        }

        return result.substring(0, end + 1);
    }
}