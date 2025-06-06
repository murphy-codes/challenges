// Source: https://leetcode.com/problems/using-a-robot-to-print-the-lexicographically-smallest-string/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-06
// At the time of submission:
//   Runtime 169 ms Beats 40.51%
//   Memory 55.29 MB Beats 77.22%

/****************************************
* 
* You are given a string `s` and a robot that currently holds an empty string `t`.
* _ Apply one of the following operations until `s` and `t` are both empty:
* • Remove the first character of a string `s` and give it to the robot.
* __ The robot will append this character to the string `t`.
* • Remove the last character of a string `t` and give it to the robot.
* __ The robot will write this character on paper.
* Return the lexicographically smallest string that can be written on the paper.
*
* Example 1:
* Input: s = "zza"
* Output: "azz"
* Explanation: Let p denote the written string.
* Initially p="", s="zza", t="".
* Perform first operation three times p="", s="", t="zza".
* Perform second operation three times p="azz", s="", t="".
*
* Example 2:
* Input: s = "bac"
* Output: "abc"
* Explanation: Let p denote the written string.
* Perform first operation twice p="", s="c", t="ba".
* Perform second operation twice p="ab", s="c", t="".
* Perform first operation p="ab", s="", t="c".
* Perform second operation p="abc", s="", t="".
*
* Example 3:
* Input: s = "bdda"
* Output: "addb"
* Explanation: Let p denote the written string.
* Initially p="", s="bdda", t="".
* Perform first operation four times p="", s="", t="bdda".
* Perform second operation four times p="addb", s="", t="".
*
* Constraints:
* • 1 <= s.length <= 105
* • `s` consists of only English lowercase letters.
* 
****************************************/

import java.util.Stack;

class Solution {
    // Greedy approach using a stack to simulate robot's buffer (t string).
    // Only pop from the stack when top <= the smallest character left in s.
    // Time Complexity: O(n + 26), where n = length of s.
    // Space Complexity: O(n) for the stack and frequency count array.
    public String robotWithString(String s) {
        int[] remaining = new int[26];  // Frequency count of each character

        // Count character frequencies in s
        for (char c : s.toCharArray()) {
            remaining[c - 'a']++;
        }

        Stack<Character> buffer = new Stack<>();  // Stack simulates robot's t string
        StringBuilder result = new StringBuilder();  // Final output (paper)
        char minChar = 'a';  // Track the smallest character remaining in s

        for (char c : s.toCharArray()) {
            buffer.push(c);  // Give character to robot (append to t)
            remaining[c - 'a']--;  // Mark this character as used

            // Move minChar pointer to the next smallest unused character
            while (minChar <= 'z' && remaining[minChar - 'a'] == 0) {
                minChar++;
            }

            // If the top of the stack is <= smallest remaining, write it to paper
            while (!buffer.isEmpty() && buffer.peek() <= minChar) {
                result.append(buffer.pop());
            }
        }

        return result.toString();
    }
}
