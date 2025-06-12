// Source: https://leetcode.com/problems/maximum-difference-between-even-and-odd-frequency-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-10
// At the time of submission:
//   Runtime 25 ms Beats 96.67%
//   Memory 44.64 MB Beats 100.00%

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
    // Slide a window for every (a,b) digit pair.  Maintain min prefix
    // diffs (A−B) & (B−A) for each 2-bit parity state.  While moving the
    // window, update the answer using the compatible prefix parity
    // (state ^ 2 or state ^ 1).  Time O(Σ²·n), Space O(1).

    private static final int UNSET_DIFF = Integer.MAX_VALUE / 2;

    public int maxDifference(String s, int k) {
        char[] arr = s.toCharArray();
        int n = arr.length;

        int[] bestAB = new int[4];              // min (prefix A−B) per parity
        int[] bestBA = new int[4];              // min (prefix B−A) per parity
        boolean[] neverEvenOdd = new boolean[5];// digit never forms valid pair

        int answer = Integer.MIN_VALUE;

        A_LOOP:
        for (char a = '1'; a <= '4'; a++)              // a must be odd
        B_LOOP:
        for (char b = '0'; b < a; b++) {               // b even, avoid dup pairs
            if (neverEvenOdd[b - '0']) continue;

            /* expand right pointer until window length ≥ k and has ≥1 a + ≥1 b */
            int right = 0, aRight = 0, bRight = 0;
            while (right < k || aRight + bRight < 3 || aRight == 0 || bRight == 0) {
                if (right == n) {                      // ran out of string
                    if (bRight == 0) neverEvenOdd[b - '0'] = true;
                    if (aRight == 0) {
                        neverEvenOdd[a - '0'] = true;
                        continue A_LOOP;               // next a
                    }
                    continue B_LOOP;                   // next b
                }
                char c = arr[right++];
                if (c == a) aRight++;
                else if (c == b) bRight++;
            }

            /* normal sliding–window phase */
            int left = 0, aLeft = 0, bLeft = 0;
            Arrays.fill(bestAB, UNSET_DIFF);
            Arrays.fill(bestBA, UNSET_DIFF);
            bestAB[0] = bestBA[0] = 0;

            while (true) {
                /* ---------- shrink left ---------- */
                while (left < right - k) {
                    char c = arr[left++];
                    if (c == a) {
                        if (aRight == aLeft + 1) { left--; break; }
                        aLeft++;
                    } else if (c == b) {
                        if (bRight == bLeft + 1) { left--; break; }
                        bLeft++;
                    } else continue;

                    int state = parity(aLeft, bLeft);
                    int diff  = aLeft - bLeft;
                    bestAB[state] = Math.min(bestAB[state],  diff);
                    bestBA[state] = Math.min(bestBA[state], -diff);
                }

                /* ---------- update answer ---------- */
                int state = parity(aRight, bRight);
                int diff  = aRight - bRight;
                answer = Math.max(answer,  diff - bestAB[state ^ 2]); // a odd, b even
                answer = Math.max(answer, -diff - bestBA[state ^ 1]); // b odd, a even

                if (right == n) break;                 // finished string

                /* ---------- grow right ---------- */
                char c = arr[right++];
                if (c == a) aRight++;
                else if (c == b) bRight++;
                else while (right < n && (c = arr[right]) != a && c != b) right++;
            }
        }
        return answer;
    }

    private static int parity(int aCnt, int bCnt) {
        return ((aCnt & 1) << 1) | (bCnt & 1);         // 00,01,10,11 → 0..3
    }
}
