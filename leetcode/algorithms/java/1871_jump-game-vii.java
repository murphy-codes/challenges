// Source: https://leetcode.com/problems/search-in-rotated-sorted-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-25
// At the time of submission:
//   Runtime 9 ms Beats 78.15%
//   Memory 47.88 MB Beats 59.33%

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
    // Sliding window tracks reachable indices within jump range.
    // Index i is reachable if s[i] == '0' and window count > 0.
    // Add entering indices and remove exiting indices in O(1) time.
    // Time: O(n), Space: O(n)
    public boolean canReach(String s, int minJump, int maxJump) {

        int n = s.length();

        boolean[] reachable = new boolean[n];
        reachable[0] = true;

        int reachableCount = 0;

        for (int i = 1; i < n; i++) {

            // Add newly reachable index entering window
            if (i - minJump >= 0 && reachable[i - minJump]) {
                reachableCount++;
            }

            // Remove index leaving window
            if (i - maxJump - 1 >= 0 &&
                reachable[i - maxJump - 1]) {

                reachableCount--;
            }

            // Current index reachable if:
            // 1. current char is '0'
            // 2. at least one valid previous position reachable
            if (s.charAt(i) == '0' && reachableCount > 0) {
                reachable[i] = true;
            }
        }

        return reachable[n - 1];
    }
}