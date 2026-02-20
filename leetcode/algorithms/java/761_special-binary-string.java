// Source: https://leetcode.com/problems/special-binary-string/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-19
// At the time of submission:
//   Runtime 2 ms Beats 100.00%
//   Memory 43.52 MB Beats 19.67%

/****************************************
* 
* Special binary strings are binary strings with the following two properties:
* • The number of `0`'s is equal to the number of `1`'s.
* • Every prefix of the binary string has at least as many `1`'s as `0`'s.
* You are given a special binary string `s`.
* A move consists of choosing two consecutive, non-empty, special substrings
* _ of `s`, and swapping them. Two strings are consecutive if the last character
* _ of the first string is exactly one index before the first character of the
* _ second string.
* Return the lexicographically largest resulting string possible after applying
* _ the mentioned operations on the string.
*
* Example 1:
* Input: s = "11011000"
* Output: "11100100"
* Explanation: The strings "10" [occuring at s[1]] and "1100" [at s[3]] are swapped.
* This is the lexicographically largest string possible after some number of swaps.
*
* Example 2:
* Input: s = "10"
* Output: "10"
*
* Constraints:
* • `1 <= s.length <= 50`
* • `s[i]` is either `'0'` or `'1'`.
* • `s` is a special binary string.
* 
****************************************/

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Solution {
    // Decompose the string into top-level special substrings using balance count.
    // Recursively maximize each substring by processing its inner content.
    // Sort all resulting substrings in descending lexicographical order.
    // Concatenating them greedily produces the lexicographically largest result.
    // Time complexity is O(n log n); space complexity is O(n) due to recursion.

    public String makeLargestSpecial(String s) {
        List<String> parts = new ArrayList<>();
        int balance = 0;
        int start = 0;

        for (int i = 0; i < s.length(); i++) {
            balance += (s.charAt(i) == '1') ? 1 : -1;

            if (balance == 0) {
                // Recursively maximize the inner special substring
                String inner = s.substring(start + 1, i);
                String optimized =
                    "1" + makeLargestSpecial(inner) + "0";
                parts.add(optimized);
                start = i + 1;
            }
        }

        // Sort parts in descending lexicographical order
        Collections.sort(parts, Collections.reverseOrder());

        // Concatenate all parts
        StringBuilder result = new StringBuilder();
        for (String part : parts) {
            result.append(part);
        }

        return result.toString();
    }
}
