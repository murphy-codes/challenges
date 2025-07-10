// Source: https://leetcode.com/problems/reschedule-meetings-for-maximum-free-time-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-09
// At the time of submission:
//   Runtime 4 ms Beats 78.21%
//   Memory 61.78 MB Beats 42.31%

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
    // For each meeting, check if it can be rescheduled earlier (from the left)
    // or later (from the right) based on surrounding gaps. Store reschedulability
    // in boolean array q[]. Then compute the maximum continuous free window that
    // could be created by shifting exactly one meeting, using the available gaps.
    // Time Complexity: O(n), Space Complexity: O(n)
    public int maxFreeTime(int eventTime, int[] startTime, int[] endTime) {
        int n = startTime.length;
        boolean[] canMove = new boolean[n];

        int leftGap = 0;
        int rightGap = 0;

        // Forward and backward pass to determine if a meeting can be moved
        for (int i = 0; i < n; i++) {
            // Forward check: Can this meeting fit entirely in the gap before it?
            if (endTime[i] - startTime[i] <= leftGap) {
                canMove[i] = true;
            }
            leftGap = Math.max(leftGap, startTime[i] - (i == 0 ? 0 : endTime[i - 1]));

            // Backward check: Can this meeting fit in the gap after it?
            int j = n - i - 1;
            if (endTime[j] - startTime[j] <= rightGap) {
                canMove[j] = true;
            }
            rightGap = Math.max(rightGap, (i == 0 ? eventTime : startTime[j + 1]) - endTime[j]);
        }

        int maxFree = 0;

        // Evaluate max free time if this meeting is moved or left alone
        for (int i = 0; i < n; i++) {
            int gapStart = (i == 0) ? 0 : endTime[i - 1];
            int gapEnd = (i == n - 1) ? eventTime : startTime[i + 1];
            int gap = gapEnd - gapStart;

            if (canMove[i]) {
                // Meeting can be shifted — entire gap becomes free
                maxFree = Math.max(maxFree, gap);
            } else {
                // Meeting is fixed — subtract its duration from the gap
                int occupied = endTime[i] - startTime[i];
                maxFree = Math.max(maxFree, gap - occupied);
            }
        }

        return maxFree;
    }
}
