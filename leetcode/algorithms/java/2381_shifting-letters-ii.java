// Source: https://leetcode.com/problems/shifting-letters-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-04

/****************************************
* 
* You are given a string s of lowercase English letters and a 2D integer array shifts where shifts[i] = [start_i, end_i, direction_i]. For every i, shift the characters in s from the index start_i to the index end_i (inclusive) forward if direction_i = 1, or shift the characters backward if direction_i = 0.
* 
* Shifting a character forward means replacing it with the next letter in the alphabet (wrapping around so that 'z' becomes 'a'). Similarly, shifting a character backward means replacing it with the previous letter in the alphabet (wrapping around so that 'a' becomes 'z').
* 
* Return the final string after all such shifts to s are applied.
* 
* Example 1:
* Input: s = "abc", shifts = [[0,1,0],[1,2,1],[0,2,1]]
* Output: "ace"
* Explanation: Firstly, shift the characters from index 0 to index 1 backward. Now s = "zac".
* Secondly, shift the characters from index 1 to index 2 forward. Now s = "zbd".
* Finally, shift the characters from index 0 to index 2 forward. Now s = "ace".
* 
* Example 2:
* Input: s = "dztz", shifts = [[0,0,0],[1,1,1]]
* Output: "catz"
* Explanation: Firstly, shift the characters from index 0 to index 0 backward. Now s = "cztz".
* Finally, shift the characters from index 1 to index 1 forward. Now s = "catz".
* 
* Constraints:
* 
* • 1 <= s.length, shifts.length <= 5 * 10^4
* • shifts[i].length == 3
* • 0 <= start_i <= end_i < s.length
* • 0 <= direction_i <= 1
* • s consists of lowercase English letters.
* 
****************************************/

class Solution {
    // We use a two-pass approach to achieve O(m + n) time complexity:
    // 1. First pass: Precompute the net shift for each index using a difference array technique.
    //    - Accumulate shifts efficiently using start and end indices from the `shifts` array.
    // 2. Second pass: Apply the computed net shifts to each character in the string.
    //    - Use modulo 26 to handle alphabet wrap-arounds during character shifts.
    public String shiftingLetters(String s, int[][] shifts) {
        int n = s.length();
        int[] shiftEffect = new int[n + 1]; // Create an effect array of size n+1
        
        // First pass: Populate the shiftEffect array
        for (int[] shift : shifts) {
            int start = shift[0], end = shift[1], direction = shift[2];
            int effect = (direction == 1) ? 1 : -1;
            shiftEffect[start] += effect;
            shiftEffect[end + 1] -= effect;
        }
        
        // Compute prefix sum to determine net shifts at each index
        int cumulativeShift = 0;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) {
            cumulativeShift += shiftEffect[i];
            int netShift = (cumulativeShift % 26 + 26) % 26; // Ensure positive modulo
            char shiftedChar = (char) ('a' + (s.charAt(i) - 'a' + netShift) % 26);
            result.append(shiftedChar);
        }
        
        return result.toString();
    }
}
