// Source: https://leetcode.com/problems/two-best-non-overlapping-events/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-22
// At the time of submission:
//   Runtime 32 ms Beats 99.50%
//   Memory 171.60 MB Beats 60.50%

/****************************************
* 
* You are given a 0-indexed 2D integer array of `events` where
* _ `events[i] = [startTime_i, endTime_i, value_i]`. The `i^th` event starts at
* _ `startTime_i` and ends at `endTime_i`, and if you attend this event, you will
* _ receive a value of `value_i`. You can choose at most two non-overlapping
* _ events to attend such that the sum of their values is maximized.
* Return this maximum sum.
* Note that the start time and end time is inclusive: that is, you cannot attend
* _ two events where one of them starts and the other ends at the same time.
* _ More specifically, if you attend an event with end time `t`, the next event
* _ must start at or after `t + 1`.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/09/21/picture5.png]
* Input: events = [[1,3,2],[4,5,2],[2,4,3]]
* Output: 4
* Explanation: Choose the green events, 0 and 1 for a sum of 2 + 2 = 4.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2021/09/21/picture1.png]
* Example 1 Diagram
* Input: events = [[1,3,2],[4,5,2],[1,5,5]]
* Output: 5
* Explanation: Choose event 2 for a sum of 5.
*
* Example 3:
* [Image: https://assets.leetcode.com/uploads/2021/09/21/picture3.png]
* Input: events = [[1,5,3],[1,5,1],[6,6,5]]
* Output: 8
* Explanation: Choose events 0 and 2 for a sum of 3 + 5 = 8.
*
* Constraints:
* • `2 <= events.length <= 10^5`
* • `events[i].length == 3`
* • `1 <= startTime_i <= endTime_i <= 10^9`
* • `1 <= valuei <= 10^6`
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Sort events by start time and precompute suffix max values.
    // For each event, binary search the first event that starts
    // strictly after the current one ends.
    // Combine current value with the best valid future event.
    // Time: O(n log n), Space: O(n)
    public int maxTwoEvents(int[][] events) {
        // Sort events by start time
        Arrays.sort(events, (a, b) -> a[0] - b[0]);

        int n = events.length;

        // suffixMax[i] = max value among events[i..n-1]
        int[] suffixMax = new int[n + 1];

        for (int i = n - 1; i >= 0; i--) {
            suffixMax[i] = Math.max(suffixMax[i + 1], events[i][2]);
        }

        int maxValue = 0;

        for (int[] event : events) {
            int currValue = event[2];
            int left = 0, right = n;

            // Binary search for first event starting after current ends
            while (left < right) {
                int mid = (left + right) >> 1;
                if (events[mid][0] > event[1]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }

            // Combine with best non-overlapping future event
            if (left < n) {
                currValue += suffixMax[left];
            }

            maxValue = Math.max(maxValue, currValue);
        }

        return maxValue;
    }
}
