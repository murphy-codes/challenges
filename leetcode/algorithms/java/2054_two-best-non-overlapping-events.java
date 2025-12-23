// Source: https://leetcode.com/problems/two-best-non-overlapping-events/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-22
// At the time of submission:
//   Runtime 53 ms Beats 40.00%
//   Memory 171.61 MB Beats 60.50%

/****************************************
* 
* 
* 
****************************************/

import java.util.Arrays;
import java.util.Comparator;

class Solution {
    // Sort events by start time and separately by end time.
    // Sweep through events, maintaining the max value of events
    // that have already ended before the current start time.
    // For each event, combine its value with the best prior event.
    // Time: O(n log n), Space: O(n)
    public int maxTwoEvents(int[][] events) {
        int n = events.length;

        // Sort by start time
        Arrays.sort(events, Comparator.comparingInt(a -> a[0]));

        // Separate array sorted by end time
        int[][] byEnd = events.clone();
        Arrays.sort(byEnd, Comparator.comparingInt(a -> a[1]));

        int bestEndedValue = 0;
        int answer = 0;
        int endPtr = 0;

        for (int i = 0; i < n; i++) {
            int start = events[i][0];
            int value = events[i][2];

            // Process all events that end before this event starts
            while (endPtr < n && byEnd[endPtr][1] < start) {
                bestEndedValue = Math.max(bestEndedValue, byEnd[endPtr][2]);
                endPtr++;
            }

            // Combine current event with best previous non-overlapping event
            answer = Math.max(answer, bestEndedValue + value);

            // Also consider taking only this event
            answer = Math.max(answer, value);
        }

        return answer;
    }
}
