// Source: https://leetcode.com/problems/lexicographically-minimum-string-after-removing-stars/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-06
// At the time of submission:
//   Runtime 22 ms Beats 98.79%
//   Memory 46.00 MB Beats 93.94%

/****************************************
* 
* You are given a string `s`. It may contain any number of `'*'` characters.
* _ Your task is to remove all `'*'` characters.
* While there is a `'*'`, do the following operation:
* • Delete the leftmost `'*'` and the smallest non-`'*'` character to its left.
* __ If there are several smallest characters, you can delete any of them.
* Return the lexicographically smallest resulting string after removing
* _ all `'*'` characters.
*
* Example 1:
* Input: s = "aaba*"
* Output: "aab"
* Explanation:
* We should delete one of the 'a' characters with '*'. If we choose s[3], s becomes the lexicographically smallest.
*
* Example 2:
* Input: s = "abc"
* Output: "abc"
* Explanation:
* There is no '*' in the string.
*
* Constraints:
* • 1 <= s.length <= 105
* • `s` consists only of lowercase English letters and `'*'`.
* • The input is generated such that it is possible to delete all `'*'` characters.
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Efficient solution using a byte array and custom linked lists.
    // Deletes the leftmost '*' and smallest char to its left by tracking
    // recent char positions in heads[]. Marks deleted chars as zero.
    // Time: O(n) — each character is processed at most once.
    // Space: O(n) for the byte array and tracking arrays.
    public String clearStars(String s) {
        int n = s.length();
        byte[] str = new byte[n + 1];
        s.getBytes(0, n, str, 0);
        str[n] = '*'; // Add sentinel '*' to simplify loop logic.

        int start = 0;
        int starCount = 0;

        // Find first useful index after skipping characters we know will be removed.
        for (int i = 0; i < n; i++) {
            if (str[i] == '*') {
                starCount++;
                if (starCount == ((i + 2) >> 1)) start = i + 1;
            }
        }

        if (starCount == 0) return s;
        if (start == n) return "";

        int[] links = new int[n];
        int[] heads = new int[128]; // ASCII letters
        Arrays.fill(heads, -1);

        for (int i = start;; i++) {
            int c = str[i];
            if (c == '*') {
                if (i >= n) break;
                str[i] = 0;
                for (int j = 'a'; j <= 'z'; j++) {
                    if (heads[j] >= 0) {
                        str[heads[j]] = 0;
                        heads[j] = links[heads[j]];
                        break;
                    }
                }
            } else {
                links[i] = heads[c];
                heads[c] = i;
            }
        }

        int write = 0;
        for (int i = start; i < n; i++) {
            if (str[i] != 0) str[write++] = str[i];
        }

        return new String(str, 0, write);
    }
}
