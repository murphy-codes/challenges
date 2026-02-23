// Source: https://leetcode.com/problems/check-if-a-string-contains-all-binary-codes-of-size-k/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-22
// At the time of submission:
//   Runtime 6 ms Beats 100.00%
//   Memory 47.83 MB Beats 95.80%

/****************************************
* 
* Given a binary string s and an integer `k`, return `true` if every binary
* _ code of length `k` is a substring of `s`. Otherwise, return `false`.
*
* Example 1:
* Input: s = "00110110", k = 2
* Output: true
* Explanation: The binary codes of length 2 are "00", "01", "10" and "11". They can be all found as substrings at indices 0, 1, 3 and 2 respectively.
*
* Example 2:
* Input: s = "0110", k = 1
* Output: true
* Explanation: The binary codes of length 1 are "0" and "1", it is clear that both exist as a substring.
*
* Example 3:
* Input: s = "0110", k = 2
* Output: false
* Explanation: The binary code "00" is of length 2 and does not exist in the array.
*
* Constraints:
* • `1 <= s.length <= 5 * 10^5`
* • `s[i]` is either `'0'` or `'1'`.
* • `1 <= k <= 20`
* 
****************************************/

class Solution {
    // Uses a rolling k-bit hash to represent each substring in O(1) time.
    // Each binary substring maps directly to an integer in [0, 2^k).
    // A boolean array tracks which codes have been seen so far.
    // Time complexity is O(n), space complexity is O(2^k).
    public boolean hasAllCodes(String s, int k) {
        int totalCodes = 1 << k;        // Number of binary codes of length k
        int n = s.length();

        // Not enough characters or substrings to cover all codes
        if (n < k || n - k + 1 < totalCodes) {
            return false;
        }

        boolean[] seen = new boolean[totalCodes];
        int rollingHash = 0;
        int mask = totalCodes - 1;      // Keeps only last k bits
        int foundCount = 0;

        for (int i = 0; i < n; i++) {
            // Shift left, drop old bit, add new bit
            rollingHash = ((rollingHash << 1) & mask)
                        | (s.charAt(i) - '0');

            // Only start checking once we have k bits
            if (i >= k - 1) {
                if (!seen[rollingHash]) {
                    seen[rollingHash] = true;
                    foundCount++;

                    if (foundCount == totalCodes) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}