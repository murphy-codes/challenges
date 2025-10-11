// Source: https://leetcode.com/problems/maximum-total-damage-with-spell-casting/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-11
// At the time of submission:
//   Runtime 53 ms Beats 100.00%
//   Memory 61.35 MB Beats 79.73%

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

class Solution {
    // Sort powers and use DP + two-pointer window to accumulate max damage.
    // dp[i] = best total using power[i], skipping any power within 2 units.
    // Track the running max (maxPrevDamage) of valid previous states.
    // This ensures O(n log n) time (from sorting) and O(n) space overall.
    // Efficiently finds the optimal sum of non-conflicting potion powers.
    public long maximumTotalDamage(int[] power) {
        Arrays.sort(power); // Sort powers for sequential comparison

        long[] dp = new long[power.length];
        dp[0] = power[0];
        long maxPrevDamage = 0; // Tracks max damage from non-conflicting powers
        int left = 0;           // Left pointer for the valid range window

        for (int i = 1; i < power.length; i++) {
            if (power[i] == power[i - 1]) {
                // Same power — add to cumulative damage
                dp[i] = dp[i - 1] + power[i];
            } else {
                // Move left pointer while ensuring power difference >= 3
                while (power[left] + 2 < power[i]) {
                    maxPrevDamage = Math.max(maxPrevDamage, dp[left]);
                    left++;
                }
                dp[i] = maxPrevDamage + power[i];
            }
        }

        long result = 0;
        for (long total : dp) {
            result = Math.max(result, total);
        }
        return result;
    }
}


