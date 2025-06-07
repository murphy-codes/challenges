// Source: https://leetcode.com/problems/lexicographically-minimum-string-after-removing-stars/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-06
// At the time of submission:
//   Runtime 69 ms Beats 92.12%
//   Memory 47.14 MB Beats 72.12%

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

import java.util.Deque;
import java.util.ArrayDeque;

class Solution {
    // Greedy deletion using 26 stacks (one per lowercase letter).
    // For each '*', delete the smallest available char to its left.
    // Time: O(n * 26) worst-case (each star scans all 26 stacks), but O(n) in practice.
    // Space: O(n) for the stacks and input string reconstruction.
    public String clearStars(String s) {
        Deque<Integer>[] charIndices = new Deque[26];
        for (int i = 0; i < 26; i++) {
            charIndices[i] = new ArrayDeque<>();
        }

        char[] arr = s.toCharArray();

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != '*') {
                charIndices[arr[i] - 'a'].push(i);
            } else {
                // Find and delete the smallest available character to the left
                for (int j = 0; j < 26; j++) {
                    if (!charIndices[j].isEmpty()) {
                        arr[charIndices[j].pop()] = '*'; // mark char as deleted
                        break;
                    }
                }
            }
        }

        // Build the result without any '*'
        StringBuilder result = new StringBuilder();
        for (char c : arr) {
            if (c != '*') {
                result.append(c);
            }
        }

        return result.toString();
    }
}
