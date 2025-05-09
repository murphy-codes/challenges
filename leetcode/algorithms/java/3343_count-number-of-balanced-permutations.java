// Source: https://leetcode.com/problems/count-number-of-balanced-permutations/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-09
// At the time of submission:
//   Runtime 55 ms Beats 88.37%
//   Memory 45.88 MB Beats 41.86%

/****************************************
* 
* You are given a string num. A string of digits is called balanced
* _ if the sum of the digits at even indices is equal to the sum of
* _ the digits at odd indices.
* Return the number of distinct permutations of `num` that are balanced.
* Since the answer may be very large, return it modulo `10^9 + 7`.
* A permutation is a rearrangement of all the characters of a string.
*
* Example 1:
* Input: num = "123"
* Output: 2
* Explanation:
* The distinct permutations of num are "123", "132", "213", "231", "312" and "321".
* Among them, "132" and "231" are balanced. Thus, the answer is 2.
*
* Example 2:
* Input: num = "112"
* Output: 1
* Explanation:
* The distinct permutations of num are "112", "121", and "211".
* Only "121" is balanced. Thus, the answer is 1.
*
* Example 3:
* Input: num = "12345"
* Output: 0
* Explanation:
* None of the permutations of num are balanced, so the answer is 0.
*
* Constraints:
* • 2 <= num.length <= 80
* • num consists of digits '0' to '9' only.
* 
****************************************/

import java.util.Arrays;

class Solution {

    // This solution uses DFS + memoization to count the valid digit
    // permutations where the sum of digits at odd positions equals
    // that at even positions. It precomputes combinations and uses
    // pruning to skip invalid configurations. 
    // Time: O(n^2 * S), Space: O(n^2 + nS)

    private static final long MOD = 1_000_000_007;
    private long[][][] memo;
    private int[] cnt;
    private int[] psum;
    private int target;
    private long[][] comb;

    public int countBalancedPermutations(String num) {
        int tot = 0, n = num.length();
        cnt = new int[10];
        for (char ch : num.toCharArray()) {
            int d = ch - '0';
            cnt[d]++;
            tot += d;
        }
        if (tot % 2 != 0) return 0;

        target = tot / 2;
        int maxOdd = (n + 1) / 2;

        // Precompute combinations
        comb = new long[maxOdd + 1][maxOdd + 1];
        for (int i = 0; i <= maxOdd; i++) {
            comb[i][i] = comb[i][0] = 1;
            for (int j = 1; j < i; j++) {
                comb[i][j] = (comb[i - 1][j] + comb[i - 1][j - 1]) % MOD;
            }
        }

        psum = new int[11];
        for (int i = 9; i >= 0; i--) {
            psum[i] = psum[i + 1] + cnt[i];
        }

        memo = new long[10][target + 1][maxOdd + 1];
        for (long[][] arr2 : memo) {
            for (long[] arr1 : arr2) {
                Arrays.fill(arr1, -1);
            }
        }

        return (int) dfs(0, 0, maxOdd);
    }

    private long dfs(int pos, int curr, int oddCnt) {
        if (oddCnt < 0 || psum[pos] < oddCnt || curr > target) {
            return 0;
        }
        if (pos > 9) {
            return (curr == target && oddCnt == 0) ? 1 : 0;
        }
        if (memo[pos][curr][oddCnt] != -1) {
            return memo[pos][curr][oddCnt];
        }

        int evenCnt = psum[pos] - oddCnt;
        long res = 0;
        for (
            int i = Math.max(0, cnt[pos] - evenCnt);
            i <= Math.min(cnt[pos], oddCnt);
            i++
        ) {
            int j = cnt[pos] - i; // Even-position count
            long ways = (comb[oddCnt][i] * comb[evenCnt][j]) % MOD;
            res =
                (res +
                 (ways * dfs(pos + 1, curr + i * pos, oddCnt - i)) % MOD) %
                MOD;
        }

        memo[pos][curr][oddCnt] = res;
        return res;
    }
}