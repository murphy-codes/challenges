// Source: https://leetcode.com/problems/maximum-total-damage-with-spell-casting/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-10
// At the time of submission:
//   Runtime 169 ms Beats 76.58%
//   Memory 65.06 MB Beats 45.49%

/****************************************
* 
* A magician has various spells.
* You are given an array `power`, where each element represents the damage of
* _ a spell. Multiple spells can have the same damage value.
* It is a known fact that if a magician decides to cast a spell with a damage
* _ of `power[i]`, they cannot cast any spell with a damage of `power[i] - 2`,
* _ `power[i] - 1`, `power[i] + 1`, or `power[i] + 2`.
* Each spell can be cast only once.
* Return the maximum possible total damage that a magician can cast.
*
* Example 1:
* Input: power = [1,1,3,4]
* Output: 6
* Explanation:
* The maximum possible damage of 6 is produced by casting spells 0, 1, 3 with damage 1, 1, 4.
*
* Example 2:
* Input: power = [7,1,6,6]
* Output: 13
* Explanation:
* The maximum possible damage of 13 is produced by casting spells 1, 2, 3 with damage 1, 6, 6.
*
* Constraints:
* • `1 <= power.length <= 10^5`
* • `1 <= power[i] <= 10^9`
* 
****************************************/

import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

class Solution {
    // Group spells by power and sum their total damage.
    // Use dynamic programming on sorted unique power values.
    // If powers are within 2 of each other, they conflict; otherwise, we can combine them.
    // Transition: dp[i] = max(dp[i-1], dp[i-2] + totalDamage[i]) if close.
    // Runs in O(n log n) time and O(n) space due to sorting and DP.
    public long maximumTotalDamage(int[] power) {
        // Aggregate total damage per unique power
        Map<Integer, Long> totalDamage = new HashMap<>();
        for (int p : power) {
            totalDamage.put(p, totalDamage.getOrDefault(p, 0L) + p);
        }

        int[] keys = totalDamage.keySet().stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(keys);

        int m = keys.length;
        if (m == 0) return 0L;

        long[] dp = new long[m];
        dp[0] = totalDamage.get(keys[0]);

        for (int i = 1; i < m; i++) {
            long cur = totalDamage.get(keys[i]);

            // find largest j < i with keys[i] - keys[j] > 2  (i.e. keys[j] <= keys[i]-3)
            int lo = 0, hi = i - 1, j = -1;
            int target = keys[i] - 3;
            while (lo <= hi) {
                int mid = (lo + hi) >>> 1;
                if (keys[mid] <= target) {
                    j = mid;
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }

            long take = cur + (j >= 0 ? dp[j] : 0L);
            long skip = dp[i - 1];
            dp[i] = Math.max(skip, take);
        }

        return dp[m - 1];
    }
}
