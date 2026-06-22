// Source: https://leetcode.com/problems/earliest-finish-time-for-land-and-water-rides-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-06-02
// At the time of submission:
//   Runtime 201 ms Beats 10.35%
//   Memory 98.06 MB Beats 25.86%

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

import java.util.Arrays;

class Solution {
    // Sort second rides by start time; build prefix min durations and
    // suffix min(start + duration). For each first ride completion time,
    // binary-search the split between already-open and future rides.
    // Evaluate both ride orders and take the minimum finish time.
    // Time: O((n + m) log m), Space: O(m).
    public int earliestFinishTime(
        int[] landStartTime,
        int[] landDuration,
        int[] waterStartTime,
        int[] waterDuration
    ) {
        return Math.min(
            solve(
                landStartTime,
                landDuration,
                waterStartTime,
                waterDuration
            ),
            solve(
                waterStartTime,
                waterDuration,
                landStartTime,
                landDuration
            )
        );
    }

    private int solve(
        int[] firstStart,
        int[] firstDuration,
        int[] secondStart,
        int[] secondDuration
    ) {
        int m = secondStart.length;

        int[][] secondRides = new int[m][2];
        for (int i = 0; i < m; i++) {
            secondRides[i][0] = secondStart[i];
            secondRides[i][1] = secondDuration[i];
        }

        Arrays.sort(secondRides, (a, b) -> Integer.compare(a[0], b[0]));

        int[] starts = new int[m];
        int[] prefixMinDuration = new int[m];
        int[] suffixMinFinish = new int[m];

        for (int i = 0; i < m; i++) {
            starts[i] = secondRides[i][0];
        }

        prefixMinDuration[0] = secondRides[0][1];
        for (int i = 1; i < m; i++) {
            prefixMinDuration[i] = Math.min(
                prefixMinDuration[i - 1],
                secondRides[i][1]
            );
        }

        suffixMinFinish[m - 1] =
            secondRides[m - 1][0] + secondRides[m - 1][1];

        for (int i = m - 2; i >= 0; i--) {
            suffixMinFinish[i] = Math.min(
                suffixMinFinish[i + 1],
                secondRides[i][0] + secondRides[i][1]
            );
        }

        int answer = Integer.MAX_VALUE;

        for (int i = 0; i < firstStart.length; i++) {
            int finishFirst = firstStart[i] + firstDuration[i];

            int split = upperBound(starts, finishFirst);

            if (split > 0) {
                answer = Math.min(
                    answer,
                    finishFirst + prefixMinDuration[split - 1]
                );
            }

            if (split < m) {
                answer = Math.min(
                    answer,
                    suffixMinFinish[split]
                );
            }
        }

        return answer;
    }

    private int upperBound(int[] arr, int target) {
        int left = 0;
        int right = arr.length;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }
}