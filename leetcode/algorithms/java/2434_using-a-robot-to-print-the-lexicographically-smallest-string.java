// Source: https://leetcode.com/problems/using-a-robot-to-print-the-lexicographically-smallest-string/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-06
// At the time of submission:
//   Runtime 26 ms Beats 100.00%
//   Memory 45.64 MB Beats 99.37%

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

class Solution {
    // Greedy stack simulation using int array for optimal performance.
    // Push each character to the stack, and pop when it's <= the smallest
    // character still remaining in the input. This guarantees a lex smallest
    // output. Time: O(n + 26), Space: O(n), where n = length of input string.
    public String robotWithString(String s) {
        int[] freq = new int[26]; // Frequency count of each character in s
        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        int smallestCharIndex = 0; // Pointer to the smallest available character

        int[] stack = new int[s.length()]; // Simulated stack using int array
        int stackTop = -1;

        StringBuilder result = new StringBuilder();

        for (char ch : s.toCharArray()) {
            // Move smallestCharIndex to next available smallest character
            while (freq[smallestCharIndex] == 0) {
                smallestCharIndex++;
            }

            // Pop from stack if top character <= smallest available remaining char
            while (stackTop != -1 && stack[stackTop] <= smallestCharIndex) {
                result.append((char) (stack[stackTop--] + 'a'));
            }

            freq[ch - 'a']--;               // Use the current character
            stack[++stackTop] = ch - 'a';   // Push to stack (as int)
        }

        // Empty the remaining stack
        while (stackTop != -1) {
            result.append((char) (stack[stackTop--] + 'a'));
        }

        return result.toString();
    }
}
