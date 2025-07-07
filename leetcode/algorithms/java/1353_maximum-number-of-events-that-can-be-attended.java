// Source: https://leetcode.com/problems/maximum-number-of-events-that-can-be-attended/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-06
// At the time of submission:
//   Runtime 30 ms Beats 99.42%
//   Memory 74.41 MB Beats 98.37%

/****************************************
* 
* You are given an array of `events` where `events[i] = [startDay_i, endDay_i]`.
* _ Every event `i` starts at `startDay_i` and ends at `endDay_i`.
* You can attend an event `i` at any day `d` where `startTime_i <= d <= endTime_i`.
* _ You can only attend one event at any time `d`.
* Return the maximum number of events you can attend.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2020/02/05/e1.png]
* Input: events = [[1,2],[2,3],[3,4]]
* Output: 3
* Explanation: You can attend all the three events.
* One way to attend them all is as shown.
* Attend the first event on day 1.
* Attend the second event on day 2.
* Attend the third event on day 3.
*
* Example 2:
* Input: events= [[1,2],[2,3],[3,4],[1,2]]
* Output: 4
*
* Constraints:
* • 1 <= events.length <= 10^5
* • events[i].length == 2
* • 1 <= startDay_i <= endDay_i <= 10^5
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Greedy + Union-Find (DSU) with path compression approach.
    // Sort events by end day to prioritize earliest possible attendance.
    // Use DSU to find the earliest available day to attend each event.
    // Once a day is used, union it with the next available day.
    // Time: O(N log N) from sorting + near O(1) per DSU op → total O(N log N)
    // Space: O(D), where D = latest day among all events
    public int maxEvents(int[][] events) {
        // Sort events by end day (ascending)
        Arrays.sort(events, (a, b) -> a[1] - b[1]);

        // Parent array for union-find up to the latest end day
        int maxDay = events[events.length - 1][1];
        int[] parent = new int[maxDay + 2]; // 1-based days
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        int count = 0;
        for (int[] event : events) {
            int earliestDay = find(parent, event[0]);
            if (earliestDay <= event[1]) {
                count++;
                // Mark this day as used by unioning to next available
                parent[earliestDay] = find(parent, earliestDay + 1);
            }
        }
        return count;
    }

    // Union-Find with path compression
    private int find(int[] parent, int day) {
        if (parent[day] != day) {
            parent[day] = find(parent, parent[day]);
        }
        return parent[day];
    }
}
