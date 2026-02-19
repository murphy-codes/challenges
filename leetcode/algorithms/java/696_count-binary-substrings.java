// Source: https://leetcode.com/problems/count-binary-substrings/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-18
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 42.40 MB Beats 34.01%

/****************************************
* 
* Given a positive integer, check whether it has alternating bits: namely,
* _ if two adjacent bits will always have different values.
*
* Example 1:
* Input: n = 5
* Output: true
* Explanation: The binary representation of 5 is: 101
*
* Example 2:
* Input: n = 7
* Output: false
* Explanation: The binary representation of 7 is: 111.
*
* Example 3:
* Input: n = 11
* Output: false
* Explanation: The binary representation of 11 is: 1011.
*
* Constraints:
* • `1 <= n <= 231 - 1`
* 
****************************************/

class Solution {
    // Count consecutive runs of identical characters.
    // For each adjacent run pair, the number of valid substrings
    // equals the smaller of the two run lengths.
    // Summing these over the string counts all valid cases.
    // Time complexity is O(n); space complexity is O(1).
    public int countBinarySubstrings(String s) {
        int result = 0;
        int prevRun = 0;
        int currRun = 1;

        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                currRun++;
            } else {
                result += Math.min(prevRun, currRun);
                prevRun = currRun;
                currRun = 1;
            }
        }

        return result + Math.min(prevRun, currRun);
    }
}
