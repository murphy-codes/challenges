// Source: https://leetcode.com/problems/decode-the-slanted-ciphertext/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-04
// At the time of submission:
//   Runtime 4 ms Beats 100.00%
//   Memory 55.96 MB Beats 93.85%

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
    // Treat encodedText as a row-wise matrix with known rows and cols.
    // Decode by traversing diagonals starting from each column in row 0.
    // Stop early when diagonals exceed matrix bounds to save work.
    // Remove trailing spaces since original text has none.
    // Time: O(n), Space: O(n) for result storage.
    public String decodeCiphertext(String encodedText, int rows) {
        // Edge cases: empty string or single row → no encoding
        if (encodedText.equals("") || rows == 1) {
            return encodedText;
        }

        int totalLength = encodedText.length();
        int cols = totalLength / rows;

        StringBuilder result = new StringBuilder();

        // Traverse diagonals starting from each column
        for (int startCol = 0; startCol < cols; ++startCol) {
            boolean reachedBoundary = false;

            for (int row = 0; row < rows; ++row) {
                int col = startCol + row;

                // Stop if diagonal goes out of bounds
                if (col == cols) {
                    reachedBoundary = true;
                    break;
                }

                // Convert (row, col) → index in encoded string
                result.append(encodedText.charAt(row * cols + col));
            }

            // No further diagonals will be valid
            if (reachedBoundary) {
                break;
            }
        }

        // Trim trailing spaces
        int end = result.length() - 1;
        while (result.charAt(end) == ' ') {
            --end;
        }

        result.setLength(end + 1);
        return result.toString();
    }
}