// Source: https://leetcode.com/problems/maximum-total-damage-with-spell-casting/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-15
// At the time of submission:
//   Runtime 5 ms Beats 57.07%
//   Memory 46.20 MB Beats 14.66%

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
    // Count lengths of consecutive '1' segments; a run of k ones contributes
    // k*(k+1)/2 all-one substrings. We scan the string once, summing each
    // completed run and applying modulo arithmetic. This yields O(n) time
    // and O(1) space, optimal for n up to 1e5 characters.
    private static final int MOD = 1_000_000_007;

    public int numSub(String s) {
        long result = 0;
        long consecutive = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                consecutive++;
            } else {
                result = (result + (consecutive * (consecutive + 1) / 2)) % MOD;
                consecutive = 0;
            }
        }

        // Add last group if it ends with '1'
        result = (result + (consecutive * (consecutive + 1) / 2)) % MOD;

        return (int) result;
    }
}
