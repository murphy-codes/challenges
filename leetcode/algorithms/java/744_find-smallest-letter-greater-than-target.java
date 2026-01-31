// Source: https://leetcode.com/problems/find-smallest-letter-greater-than-target/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-30
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 46.28 MB Beats 83.25%

/****************************************
* 
* You are given an array of characters `letters` that is sorted in
* _ non-decreasing order, and a character `target`. There are at least two
* _ different characters in `letters`.
* Return the smallest character in `letters` that is lexicographically greater
* _ than `target`. If such a character does not exist, return the first character
* _ in `letters`.
*
* Example 1:
* Input: letters = ["c","f","j"], target = "a"
* Output: "c"
* Explanation: The smallest character that is lexicographically greater than 'a' in letters is 'c'.
*
* Example 2:
* Input: letters = ["c","f","j"], target = "c"
* Output: "f"
* Explanation: The smallest character that is lexicographically greater than 'c' in letters is 'f'.
*
* Example 3:
* Input: letters = ["x","x","y","y"], target = "z"
* Output: "x"
* Explanation: There are no characters in letters that is lexicographically greater than 'z' so we return letters[0].
*
* Constraints:
* • `2 <= letters.length <= 10^4`
* • `letters[i]` is a lowercase English letter.
* • `letters` is sorted in non-decreasing order.
* • `letters` contains at least two different characters.
* • `target` is a lowercase English letter.
* 
****************************************/

class Solution {
    // Since the array is sorted, the first character greater than target
    // must appear when scanning from left to right.
    // We iterate through the array and return immediately if found
    // Otherwise, we return letters[0]
    // Time complexity: O(n). Space complexity: O(1).
    public char nextGreatestLetter(char[] letters, char target) {
        for (char c : letters) {
            if (c > target) {
                return c;
            }
        }
        return letters[0];
    }
}
