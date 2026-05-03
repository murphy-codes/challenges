// Source: https://leetcode.com/problems/rotated-digits/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-03
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 42.20 MB Beats 47.82%

/****************************************
* 
* An integer `x` is a good if after rotating each digit individually by 180
* _ degrees, we get a valid number that is different from `x`. Each digit must
* _ be rotated - we cannot choose to leave it alone.
* A number is valid if each digit remains a digit after rotation. For example:
* • `0`, `1`, and `8` rotate to themselves,
* • `2` and `5` rotate to each other (in this case they are rotated in a
* __ different direction, in other words, `2` or `5` gets mirrored),
* • `6` and `9` rotate to each other, and
* • the rest of the numbers do not rotate to any other number and become invalid.
* Given an integer `n`, return the number of good integers in the range `[1, n]`.
*
* Example 1:
* Input: n = 10
* Output: 4
* Explanation: There are four good numbers in the range [1, 10] : 2, 5, 6, 9.
* Note that 1 and 10 are not good numbers, since they remain unchanged after rotating.
*
* Example 2:
* Input: n = 1
* Output: 0
*
* Example 3:
* Input: n = 2
* Output: 1
*
* Constraints:
* • `1 <= n <= 10^4`
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Use digit DP to count valid numbers ≤ n by building digits.
    // Track position, tight bound, and if any digit changes after rotation.
    // Only iterate valid digits and mark if a "good" digit is used.
    // Time: O(d * 2 * 2 * 7), Space: O(d * 2 * 2)

    // Valid digits after rotation
    int[] validDigits = {0, 1, 2, 5, 6, 8, 9};

    // dp[pos][tight][hasDiff]
    int[][][] memo;

    private int dfs(int pos, int isTight, int hasDiff, String numStr) {
        // Base case: valid number only if at least one digit changes
        if (pos == numStr.length()) {
            return hasDiff == 1 ? 1 : 0;
        }

        if (memo[pos][isTight][hasDiff] != -1) {
            return memo[pos][isTight][hasDiff];
        }

        int limit = (isTight == 1) ? numStr.charAt(pos) - '0' : 9;
        int total = 0;

        for (int digit : validDigits) {
            if (digit > limit) continue;

            int nextTight = (isTight == 1 && digit == limit) ? 1 : 0;

            // Digits that do NOT change after rotation
            if (digit == 0 || digit == 1 || digit == 8) {
                total += dfs(pos + 1, nextTight, hasDiff, numStr);
            } 
            // Digits that DO change
            else {
                total += dfs(pos + 1, nextTight, 1, numStr);
            }
        }

        return memo[pos][isTight][hasDiff] = total;
    }

    public int rotatedDigits(int n) {
        String numStr = String.valueOf(n);

        memo = new int[numStr.length() + 1][2][2];
        for (int i = 0; i <= numStr.length(); i++) {
            Arrays.fill(memo[i][0], -1);
            Arrays.fill(memo[i][1], -1);
        }

        return dfs(0, 1, 0, numStr);
    }
}