// Source: https://leetcode.com/problems/reschedule-meetings-for-maximum-free-time-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-08
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 63.31 MB Beats 40.00%

/****************************************
* 
* You are given an integer `eventTime` denoting the duration of an event, where
* _ the event occurs from time `t = 0` to time `t = eventTime`.
* You are also given two integer arrays `startTime` and `endTime`, each of length
* _ `n`. These represent the start and end time of n non-overlapping meetings,
* _ where the `i^th` meeting occurs during the time `[startTime[i], endTime[i]]`.
* You can reschedule at most `k` meetings by moving their start time while
* _ maintaining the same duration, to maximize the longest continuous period of
* _ free time during the event.
* The relative order of all the meetings should stay the same and they should
* _ remain non-overlapping.
* Return the maximum amount of free time possible after rearranging the meetings.
* Note that the meetings can not be rescheduled to a time outside the event.
*
* Example 1:
* Input: eventTime = 5, k = 1, startTime = [1,3], endTime = [2,5]
* Output: 2
* Explanation:
* Reschedule the meeting at `[1, 2]` to `[2, 3]`, leaving no meetings during the time `[0, 2]`.
*
* Example 2:
* Input: eventTime = 10, k = 1, startTime = [0,2,9], endTime = [1,4,10]
* Output: 6
* Explanation:
* Reschedule the meeting at `[2, 4]` to `[1, 3]`, leaving no meetings during the time `[3, 9]`.
*
* Example 3:
* Input: eventTime = 5, k = 2, startTime = [0,1,2,3,4], endTime = [1,2,3,4,5]
* Output: 0
* Explanation:
* There is no time during the event not occupied by meetings.
*
* Constraints:
* • 1 <= eventTime <= 10^9
* • n == startTime.length == endTime.length
* • 2 <= n <= 10^5
* • 1 <= k <= n
* • 0 <= startTime[i] < endTime[i] <= eventTime
* • `endTime[i] <= startTime[i + 1]` where `i` lies in the range `[0, n - 2]`.
* 
****************************************/

import java.lang.Math;

class Solution {
    // Compute all free-time gaps between meetings (n+1 gaps total)
    // Use sliding window of size (k+1) to find the max sum of consecutive gaps
    // Time: O(n) for computing gaps and max window sum
    // Space: O(n) for storing the gap values
    public int maxFreeTime(int eventTime, int k, int[] startTime, int[] endTime) {
        int n = startTime.length;

        // There are n + 1 total free-time gaps
        int[] gaps = new int[n + 1];
        gaps[0] = startTime[0];  // Free time before first meeting
        for (int i = 1; i < n; i++) {
            gaps[i] = startTime[i] - endTime[i - 1];  // Gaps between meetings
        }
        gaps[n] = eventTime - endTime[n - 1];  // Free time after last meeting

        // Sliding window of size (k + 1) to find maximum free time
        int windowSize = k + 1;
        int currentSum = 0;
        int maxFreeTime = 0;

        for (int i = 0; i < gaps.length; i++) {
            currentSum += gaps[i];
            if (i >= windowSize) {
                currentSum -= gaps[i - windowSize];
            }
            if (i >= windowSize - 1) {
                maxFreeTime = Math.max(maxFreeTime, currentSum);
            }
        }

        return maxFreeTime;
    }
}
