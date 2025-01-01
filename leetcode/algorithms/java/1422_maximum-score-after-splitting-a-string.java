// Source: https://leetcode.com/problems/maximum-score-after-splitting-a-string/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2024-12-31

/****************************************
* 
* Given a string s of zeros and ones, return the maximum score after splitting the string into two non-empty substrings (i.e. left substring and right substring).
* 
* The score after splitting a string is the number of zeros in the left substring plus the number of ones in the right substring.
* 
* Example 1:
* Input: s = "011101"
* Output: 5 
* Explanation: 
* All possible ways of splitting s into two non-empty substrings are:
* left = "0" and right = "11101", score = 1 + 4 = 5 
* left = "01" and right = "1101", score = 1 + 3 = 4 
* left = "011" and right = "101", score = 1 + 2 = 3 
* left = "0111" and right = "01", score = 1 + 1 = 2 
* left = "01110" and right = "1", score = 2 + 1 = 3
* 
* Example 2:
* Input: s = "00111"
* Output: 5
* Explanation: When left = "00" and right = "111", we get the maximum score = 2 + 3 = 5
* 
* Example 3:
* Input: s = "1111"
* Output: 3
* 
* Constraints:
* • 2 <= s.length <= 500
* • The string s consists of characters '0' and '1' only.
* 
****************************************/

class Solution {
    // We use a two-pass approach to solve this in O(n):
    // First, precompute the total number of '1's in the string.
    // Then, iterate through the string to calculate the score dynamically by:
    // incrementing the count of '0's on the left and subtracting from '1's on the right.
    // Track the maximum score encountered during this process.
    public int maxScore(String s) {
        int totalOnes = 0;
        // Precompute the total number of ones in the string
        for (char c : s.toCharArray()) {
            if (c == '1') {
                totalOnes++;
            }
        }

        int maxScore = 0;
        int zerosSeenSoFar = 0;
        int onesSeenSoFar = 0;

        // Iterate through the string and calculate scores, 
        // stop before last char since both substr must be non-empty
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == '0') {
                zerosSeenSoFar++;
            } else {
                onesSeenSoFar++;
            }
            // Calculate the score: zeros in left + ones in right
            int score = zerosSeenSoFar + (totalOnes - onesSeenSoFar);
            maxScore = Math.max(maxScore, score);
        }

        return maxScore;
    }
}
