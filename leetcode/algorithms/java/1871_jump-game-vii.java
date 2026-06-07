// Source: https://leetcode.com/problems/search-in-rotated-sorted-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-25
// At the time of submission:
//   Runtime 6 ms Beats 99.11%
//   Memory 47.44 MB Beats 93.96%

/****************************************
* 
* You are given a 0-indexed binary string `s` and two integers `minJump` and
* _ `maxJump`. In the beginning, you are standing at index `0`, which is equal
* _ to `'0'`. You can move from index `i` to index `j` if the following
* _ conditions are fulfilled:
* • `i + minJump <= j <= min(i + maxJump, s.length - 1)`, and
* • `s[j] == '0'`.
* Return `true` if you can reach index `s.length - 1` in `s`, or `false` otherwise.
*
* Example 1:
* Input: s = "011010", minJump = 2, maxJump = 3
* Output: true
* Explanation:
* In the first step, move from index 0 to index 3.
* In the second step, move from index 3 to index 5.
*
* Example 2:
* Input: s = "01101110", minJump = 2, maxJump = 3
* Output: false
*
* Constraints:
* • `2 <= s.length <= 10^5`
* • `s[i]` is either `'0'` or `'1'`.
* • `s[0] == '0'`
* • `1 <= minJump <= maxJump < s.length`
* 
****************************************/

class Solution {
    // Expand reachable intervals from each valid index using DP.
    // Track the farthest processed boundary to avoid rescanning ranges.
    // Each index is processed at most once across all interval expansions.
    // Time: O(n), Space: O(n)
    public boolean canReach(String s, int minJump, int maxJump) {

        int n = s.length();

        // Invalid start/end positions
        if (n == 0 ||
            s.charAt(0) == '1' ||
            s.charAt(n - 1) == '1') {

            return false;
        }

        boolean[] reachable = new boolean[n];
        reachable[0] = true;

        // Tracks farthest index already processed
        int farthestProcessed = 0;

        for (int i = 0; i < n; i++) {

            // Skip unreachable positions
            if (!reachable[i]) {
                continue;
            }

            int start =
                Math.max(farthestProcessed + 1,
                         i + minJump);

            int end =
                Math.min(n - 1,
                         i + maxJump);

            for (int j = start; j <= end; j++) {

                if (s.charAt(j) == '0') {
                    reachable[j] = true;
                }
            }

            farthestProcessed = end;

            // Early exit once destination reachable
            if (reachable[n - 1]) {
                return true;
            }
        }

        return reachable[n - 1];
    }
}