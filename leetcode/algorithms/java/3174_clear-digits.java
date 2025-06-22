// Source: https://leetcode.com/problems/clear-digits/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-02-09
// At the time of submission:
//   Runtime 2 ms Beats 73.57%
//   Memory 42.38 MB Beats 94.08%

/****************************************
* 
* You are given a string `s`.
* Your task is to remove all digits by doing this operation repeatedly:
* • Delete the first digit and the closest non-digit character to its left.
* Return the resulting string after removing all digits.
* 
* Example 1:
* Input: s = "abc"
* Output: "abc"
* Explanation:
* There is no digit in the string.
* 
* Example 2:
* Input: s = "cb34"
* Output: ""
* Explanation:
* First, we apply the operation on s[2], and s becomes "c4".
* Then we apply the operation on s[1], and s becomes "".
* 
* Constraints:
* • 1 <= s.length <= 100
* • s consists only of lowercase English letters and digits.
* • The input is generated such that it is possible to delete all digits.
* 
****************************************/

import java.util.Stack;

class Solution {
    // This solution removes digits and their closest left non-digit using a stack.
    // Letters are pushed onto the stack, while digits trigger a pop operation.
    // The final stack contents form the remaining string.
    // Time Complexity: O(n), as each character is processed at most once 'per pass'.
    // Space Complexity: O(n), storing up to n non-digit characters.
    public String clearDigits(String s) {
        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                if (!stack.isEmpty()) {
                    stack.pop(); // Remove closest left non-digit
                }
            } else {
                stack.push(c);
            }
        }

        StringBuilder result = new StringBuilder();
        for (char c : stack) {
            result.append(c);
        }

        return result.toString();
    }
}

