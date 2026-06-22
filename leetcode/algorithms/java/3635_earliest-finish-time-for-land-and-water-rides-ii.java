// Source: https://leetcode.com/problems/earliest-finish-time-for-land-and-water-rides-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-06-02
// At the time of submission:
//   Runtime 2 ms Beats 100.00%
//   Memory 100.99 MB Beats 31.72%

/****************************************
* 
* You are given two categories of theme park attractions: land rides and water rides.
* • Land rides
* _ • `landStartTime[i]` – the earliest time the `i^th` land ride can be boarded.
* _ • `landDuration[i]` – how long the `i^th` land ride lasts.
* • Water rides
* _ • `waterStartTime[j]` – the earliest time the `j^th` water ride can be boarded.
* _ • `waterDuration[j]` – how long the `j^th` water ride lasts.
* A tourist must experience exactly one ride from each category, in either order.
* • A ride may be started at its opening time or any later moment.
* • If a ride is started at time `t`, it finishes at time `t + duration`.
* • Immediately after finishing one ride the tourist may board the other
* __ (if it is already open) or wait until it opens.
* Return the earliest possible time at which the tourist can finish both rides.
*
* Example 1:
* Input: landStartTime = [2,8], landDuration = [4,1], waterStartTime = [6], waterDuration = [3]
* Output: 9
* Explanation:​​​​​​​
* • Plan A (land ride 0 → water ride 0):
* _ • Start land ride 0 at time landStartTime[0] = 2. Finish at 2 + landDuration[0] = 6.
* _ • Water ride 0 opens at time waterStartTime[0] = 6. Start immediately at 6, finish at 6 + waterDuration[0] = 9.
* • Plan B (water ride 0 → land ride 1):
* _ • Start water ride 0 at time waterStartTime[0] = 6. Finish at 6 + waterDuration[0] = 9.
* _ • Land ride 1 opens at landStartTime[1] = 8. Start at time 9, finish at 9 + landDuration[1] = 10.
* • Plan C (land ride 1 → water ride 0):
* _ • Start land ride 1 at time landStartTime[1] = 8. Finish at 8 + landDuration[1] = 9.
* _ • Water ride 0 opened at waterStartTime[0] = 6. Start at time 9, finish at 9 + waterDuration[0] = 12.
* • Plan D (water ride 0 → land ride 0):
* _ • Start water ride 0 at time waterStartTime[0] = 6. Finish at 6 + waterDuration[0] = 9.
* _ • Land ride 0 opened at landStartTime[0] = 2. Start at time 9, finish at 9 + landDuration[0] = 13.
* Plan A gives the earliest finish time of 9.
*
* Example 2:
* Input: landStartTime = [5], landDuration = [3], waterStartTime = [1], waterDuration = [10]
* Output: 14
* Explanation:​​​​​​​
* • Plan A (water ride 0 → land ride 0):
* _ • Start water ride 0 at time waterStartTime[0] = 1. Finish at 1 + waterDuration[0] = 11.
* _ • Land ride 0 opened at landStartTime[0] = 5. Start immediately at 11 and finish at 11 + landDuration[0] = 14.
* • Plan B (land ride 0 → water ride 0):
* _ • Start land ride 0 at time landStartTime[0] = 5. Finish at 5 + landDuration[0] = 8.
* _ • Water ride 0 opened at waterStartTime[0] = 1. Start immediately at 8 and finish at 8 + waterDuration[0] = 18.
* Plan A provides the earliest finish time of 14.​​​​​​​
*
* Constraints:
* • `1 <= n, m <= 5 * 10^4`
* • `landStartTime.length == landDuration.length == n`
* • `waterStartTime.length == waterDuration.length == m`
* • `1 <= landStartTime[i], landDuration[i], waterStartTime[j], waterDuration[j] <= 10^5`
* 
****************************************/

class Solution {
    // Let a1/a2 be the earliest completion times among land/water rides.
    // For a fixed order, replacing the first ride with the earliest-finishing
    // ride can never worsen the final completion time.
    // Evaluate Water→Land using a2 and Land→Water using a1.
    // Time: O(n + m), Space: O(1).

    public int earliestFinishTime(
        int[] landStartTime,
        int[] landDuration,
        int[] waterStartTime,
        int[] waterDuration
    ) {
        int landCount = landDuration.length;
        int waterCount = waterDuration.length;

        // Earliest completion among all land rides.
        int earliestLandFinish = Integer.MAX_VALUE;
        for (int i = 0; i < landCount; i++) {
            earliestLandFinish = Math.min(
                earliestLandFinish,
                landStartTime[i] + landDuration[i]
            );
        }

        // Earliest completion among all water rides.
        int earliestWaterFinish = Integer.MAX_VALUE;
        for (int i = 0; i < waterCount; i++) {
            earliestWaterFinish = Math.min(
                earliestWaterFinish,
                waterStartTime[i] + waterDuration[i]
            );
        }

        int answer = Integer.MAX_VALUE;

        // Water → Land
        for (int i = 0; i < landCount; i++) {
            answer = Math.min(
                answer,
                Math.max(earliestWaterFinish, landStartTime[i])
                    + landDuration[i]
            );
        }

        // Land → Water
        for (int i = 0; i < waterCount; i++) {
            answer = Math.min(
                answer,
                Math.max(earliestLandFinish, waterStartTime[i])
                    + waterDuration[i]
            );
        }

        return answer;
    }
}