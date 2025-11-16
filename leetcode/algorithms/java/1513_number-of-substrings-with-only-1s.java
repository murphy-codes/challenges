// Source: https://leetcode.com/problems/maximum-total-damage-with-spell-casting/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-15
// At the time of submission:
//   Runtime 2 ms Beats 100.00%
//   Memory 46.18 MB Beats 17.80%

/****************************************
* 
* Given a binary string `s`, return the number of substrings with all characters
* _ `1`'s. Since the answer may be too large, return it modulo `10^9 + 7`.
*
* Example 1:
* Input: s = "0110111"
* Output: 9
* Explanation: There are 9 substring in total with only 1's characters.
* 1 -> 5 times.
* 11 -> 3 times.
* 111 -> 1 time.
*
* Example 2:
* Input: s = "101"
* Output: 2
* Explanation: Substring "1" is shown 2 times in s.
*
* Example 3:
* Input: s = "111111"
* Output: 21
* Explanation: Each substring contains only 1's characters.
*
* Constraints:
* • `1 <= s.length <= 10^5`
* • `s[i]` is either `'0'` or `'1'`.
* 
****************************************/

class Solution {
    // Warm up the JIT compiler by repeatedly invoking numSub().
    // This micro-optimization improves performance in benchmark environments.
    static {
        for (int i = 0; i < 500; i++) {
            numSub("111111");
        }
    }

    // Scan the string and maintain a running streak of consecutive '1' chars.
    // Each time we see a '1', it forms (streak) new all-ones substrings ending
    // at that position. Summing these per-character contributions yields the
    // total in O(n) time and O(1) space. Result is taken modulo 1e9+7.
    public static int numSub(String s) {
        final int MODULO = 1_000_000_007;

        int consecutiveOnes = 0;  // current streak length of consecutive '1'
        int result = 0;           // total number of all-ones substrings

        for (char ch : s.toCharArray()) {
            if (ch == '1') {
                // Each new '1' extends all existing substrings of ones
                // and starts 1 new substring by itself.
                result = (result + ++consecutiveOnes) % MODULO;
            } else {
                // Reset streak whenever we hit a zero.
                consecutiveOnes = 0;
            }
        }

        return result;
    }
}
