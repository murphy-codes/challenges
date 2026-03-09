// Source: https://leetcode.com/problems/find-all-possible-stable-binary-arrays-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-08
// At the time of submission:
//   Runtime 1217 ms Beats 5.26%
//   Memory 287.20 MB Beats 5.26%

/****************************************
* 
* Given an array of strings `nums` containing `n` unique binary strings each of length `n`, return a binary string of length `n` that does not appear in `nums`. If there are multiple answers, you may return any of them.
* 
* Example 1:
* Input: nums = ["01","10"]
* Output: "11"
* Explanation: "11" does not appear in nums. "00" would also be correct.
* 
* Example 2:
* Input: nums = ["00","01"]
* Output: "11"
* Explanation: "11" does not appear in nums. "10" would also be correct.
* 
* Example 3:
* Input: nums = ["111","011","001"]
* Output: "101"
* Explanation: "101" does not appear in nums. "000", "010", "100", and "110" would also be correct.
* 
* Constraints:
* • n == nums.length
* • 1 <= n <= 16
* • nums[i].length == n
* • `nums[i]` is either `'0'` or `'1'`.
* • All the strings of `nums` are unique.
* 
****************************************/

class Solution {
    // Use DP with memoization where the state tracks remaining zeros, ones,
    // the last placed value, and the length of the current run of that value.
    // When appending a new element, ensure the run length does not exceed the
    // given limit. Recursively build valid arrays while caching results.
    // Time: O(zero * one * limit), Space: O(zero * one * limit).

    private static final int MOD = 1_000_000_007;
    private Integer[][][][] memo;
    private int limit;

    public int numberOfStableArrays(int zero, int one, int limit) {
        this.limit = limit;

        memo = new Integer[zero + 1][one + 1][2][limit + 1];

        long result = 0;

        if (zero > 0)
            result = (result + dfs(zero - 1, one, 0, 1)) % MOD;

        if (one > 0)
            result = (result + dfs(zero, one - 1, 1, 1)) % MOD;

        return (int) result;
    }

    private int dfs(int z, int o, int last, int run) {

        if (z == 0 && o == 0)
            return 1;

        if (memo[z][o][last][run] != null)
            return memo[z][o][last][run];

        long ways = 0;

        // Try placing 0
        if (z > 0) {
            if (last == 0) {
                if (run < limit)
                    ways += dfs(z - 1, o, 0, run + 1);
            } else {
                ways += dfs(z - 1, o, 0, 1);
            }
        }

        // Try placing 1
        if (o > 0) {
            if (last == 1) {
                if (run < limit)
                    ways += dfs(z, o - 1, 1, run + 1);
            } else {
                ways += dfs(z, o - 1, 1, 1);
            }
        }

        ways %= MOD;

        return memo[z][o][last][run] = (int) ways;
    }
}