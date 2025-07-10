// Source: https://leetcode.com/problems/reschedule-meetings-for-maximum-free-time-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-09
// At the time of submission:
//   Runtime 3 ms Beats 100.00%
//   Memory 61.07 MB Beats 55.13%

/****************************************
* 
* You are given an integer `eventTime` denoting the duration of an event. You are
* _ also given two integer arrays `startTime` and `endTime`, each of length `n`.
* These represent the start and end times of `n` non-overlapping meetings that
* _ occur during the event between time `t = 0` and time `t = eventTime`, where
* _ the `i^th` meeting occurs during the time `[startTime[i], endTime[i]]`.
* You can reschedule at most one meeting by moving its start time while
* _ maintaining the same duration, such that the meetings remain non-overlapping,
* _ to maximize the longest continuous period of free time during the event.
* Return the maximum amount of free time possible after rearranging the meetings.
* Note that the meetings can not be rescheduled to a time outside the event and
* _ they should remain non-overlapping.
* Note: In this version, it is valid for the relative ordering of the meetings
* _ to change after rescheduling one meeting.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2024/12/22/example0_rescheduled.png]
* Input: eventTime = 5, startTime = [1,3], endTime = [2,5]
* Output: 2
* Explanation:
* Reschedule the meeting at [1, 2] to [2, 3], leaving no meetings during the time [0, 2].
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2024/12/22/rescheduled_example0.png]
* Input: eventTime = 10, startTime = [0,7,9], endTime = [1,8,10]
* Output: 7
* Explanation:
* Reschedule the meeting at [0, 1] to [8, 9], leaving no meetings during the time [0, 7].
*
* Example 3:
* [Image: https://assets.leetcode.com/uploads/2025/01/28/image3.png]
* Input: eventTime = 10, startTime = [0,3,7,9], endTime = [1,4,8,10]
* Output: 6
* Explanation:
* Reschedule the meeting at [3, 4] to [8, 9], leaving no meetings during the time [1, 7].
*
* Example 4:
* Input: eventTime = 5, startTime = [0,1,2,3,4], endTime = [1,2,3,4,5]
* Output: 0
* Explanation:
* There is no time during the event not occupied by meetings.
*
* Constraints:
* • 1 <= eventTime <= 10^9
* • n == startTime.length == endTime.length
* • 2 <= n <= 10^5
* • 0 <= startTime[i] < endTime[i] <= eventTime
* • `endTime[i] <= startTime[i + 1]` where `i` lies in the range `[0, n - 2]`.
* 
****************************************/

class Solution {
    // This solution computes the max continuous free time by calculating
    // all gaps between meetings and checking if any meeting can be moved
    // into adjacent free slots. It precomputes prefix/suffix max gaps in
    // O(n), allowing efficient checking of move feasibility.
    // Time: O(n), Space: O(n)
    public int maxFreeTime(int eventTime, int[] startTime, int[] endTime) {
        int n = startTime.length;

        // Calculate gaps between meetings and store them in gaps[]
        int[] gaps = new int[n + 1];
        int lastEnd = 0;
        for (int i = 0; i < n; i++) {
            gaps[i] = startTime[i] - lastEnd;
            lastEnd = endTime[i];
        }
        gaps[n] = eventTime - endTime[n - 1];

        // Build prefix max gap array
        int[] prefixMax = new int[n];
        prefixMax[0] = gaps[0];
        for (int i = 1; i < n; i++) {
            prefixMax[i] = Math.max(prefixMax[i - 1], gaps[i]);
        }

        // Build suffix max gap array
        int[] suffixMax = new int[n];
        suffixMax[n - 1] = gaps[n];
        for (int i = n - 2; i >= 0; i--) {
            suffixMax[i] = Math.max(suffixMax[i + 1], gaps[i + 1]);
        }

        int maxFreeTime = 0;

        // Check for each meeting if it can be moved into either adjacent gap
        for (int i = 0; i < n; i++) {
            int combinedGaps = gaps[i] + gaps[i + 1];
            int duration = endTime[i] - startTime[i];
            boolean canFit = false;

            if (i > 0 && prefixMax[i - 1] >= duration) canFit = true;
            if (i + 1 < n && suffixMax[i + 1] >= duration) canFit = true;

            if (canFit) {
                combinedGaps += duration;
            }

            maxFreeTime = Math.max(maxFreeTime, combinedGaps);
        }

        return maxFreeTime;
    }
}

