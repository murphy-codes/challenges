// Source: https://leetcode.com/problems/maximum-difference-between-even-and-odd-frequency-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-10
// At the time of submission:
//   Runtime 76 ms Beats 73.33%
//   Memory 44.98 MB Beats 70.00%

/****************************************
* 
* You are given a string `s` and an integer `k`. Your task is to find the maximum
* _ difference between the frequency of two characters, `freq[a] - freq[b]`, in a
* _ substring `subs` of `s`, such that:
* • `subs` has a size of at least `k`.
* • Character `a` has an odd frequency in `subs`.
* • Character `b` has an even frequency in `subs`.
* Return the maximum difference.
* Note that `subs` can contain more than 2 distinct characters.
*
* Example 1:
* Input: s = "12233", k = 4
* Output: -1
* Explanation:
* For the substring "12233", the frequency of '1' is 1 and the frequency of '3' is 2. The difference is 1 - 2 = -1.
*
* Example 2:
* Input: s = "1122211", k = 3
* Output: 1
* Explanation:
* For the substring "11222", the frequency of '2' is 3 and the frequency of '1' is 2. The difference is 3 - 2 = 1.
*
* Example 3:
* Input: s = "110", k = 3
* Output: -1
*
* Constraints:
* • 3 <= s.length <= 3 * 10^4
* • `s` consists only of digits `'0'` to `'4'`.
* • The input is generated that at least one substring has a character with an even frequency and a character with an odd frequency.
* • 1 <= k <= s.length
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Enumerate all 25 (oddChar,evenChar) pairs.  For each pair, slide a [left+1 … right] 
    // window and keep the minimal (aCnt-bCnt) seen for each parity state
    // in any prefix of length ≥ k that already contains an even b. Combining the 
    // current window with the best compatible prefix gives a candidate max difference.
    // Time: O(n) (25·n) … Space: O(1)

    public int maxDifference(String s, int k) {
        final int n = s.length();
        int bestAns = Integer.MIN_VALUE;                         // global max

        /*  Σ = { '0' … '4' },  five choices for a and b  */
        for (char oddChar = '0'; oddChar <= '4'; oddChar++) {
            for (char evenChar = '0'; evenChar <= '4'; evenChar++) {
                if (oddChar == evenChar) continue;               // must differ

                /* best[parity-state] = minimal (aCnt – bCnt) seen so far for
                   that state inside a valid prefix of length ≥ k.
                   parity-state encoding: (aCnt%2)<<1 | (bCnt%2)  -> 0..3       */
                int[] best = new int[4];
                Arrays.fill(best, Integer.MAX_VALUE);

                int aCnt = 0, bCnt = 0;                          // counts in [0…right]
                int leftA = 0, leftB = 0;                        // counts in [0…left]
                int left = -1;                                   // window’s left-1

                for (int right = 0; right < n; right++) {
                    /* extend window to the right */
                    if (s.charAt(right) == oddChar)  aCnt++;
                    if (s.charAt(right) == evenChar) bCnt++;

                    /* shrink from the left until window length ≥ k
                       **and** at least one even char present          */
                    while (right - left >= k && (bCnt - leftB) >= 2) {
                        int state = parity(leftA, leftB);
                        best[state] = Math.min(best[state], leftA - leftB);

                        left++;                                  // drop s[left]
                        if (s.charAt(left) == oddChar)  leftA++;
                        if (s.charAt(left) == evenChar) leftB++;
                    }

                    /* current state of the right end */
                    int rightState = parity(aCnt, bCnt);

                    /* opposite parity for ‘a’ (odd) and ‘b’ (even):
                       flip the a-bit (XOR 0b10)                     */
                    int needed = rightState ^ 0b10;

                    if (best[needed] != Integer.MAX_VALUE) {
                        bestAns = Math.max(
                                bestAns,
                                (aCnt - bCnt) - best[needed]);
                    }
                }
            }
        }
        return bestAns;
    }

    /** Encode parities into 2-bit state: (aCnt%2)<<1 | (bCnt%2) */
    private static int parity(int aCnt, int bCnt) {
        return ((aCnt & 1) << 1) | (bCnt & 1);
    }
}
