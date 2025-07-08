// Source: https://leetcode.com/problems/maximum-number-of-events-that-can-be-attended-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-07
// At the time of submission:
//   Runtime 20 ms Beats 98.08%
//   Memory 124.57 MB Beats 66.83%

/****************************************
* 
* You are given an array of `events` where `events[i] = [startDay_i, endDay_i, value_i].
* _ The `i^th` event starts at `startDay_i` and ends at `endDay_i`, and if you attend
* _ this event, you will receive a value of `value_i`. You are also given an integer
* _ `k` which represents the maximum number of events you can attend.
* You can only attend one event at a time. If you choose to attend an event, you must
* _ attend the entire event. Note that the end day is inclusive: that is, you cannot
* _ attend two events where one of them starts and the other ends on the same day.
* Return the maximum sum of values that you can receive by attending events.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/01/10/screenshot-2021-01-11-at-60048-pm.png]
* Input: events = [[1,2,4],[3,4,3],[2,3,1]], k = 2
* Output: 7
* Explanation: Choose the green events, 0 and 1 (0-indexed) for a total value of 4 + 3 = 7.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2021/01/10/screenshot-2021-01-11-at-60150-pm.png]
* Input: events = [[1,2,4],[3,4,3],[2,3,10]], k = 2
* Output: 10
* Explanation: Choose event 2 for a total value of 10.
* Notice that you cannot attend any other event as they overlap, and that you do not have to attend k events.
*
* Example 3:
* [Image: https://assets.leetcode.com/uploads/2021/01/10/screenshot-2021-01-11-at-60703-pm.png]
* Input: events = [[1,1,1],[2,2,2],[3,3,3],[4,4,4]], k = 3
* Output: 9
* Explanation: Although the events do not overlap, you can only attend 3 events. Pick the highest valued three.
*
* Constraints:
* • 1 <= k <= events.length
* • 1 <= k * events.length <= 10^6
* • 1 <= startDay_i <= endDay_i <= 10^9
* • 1 <= value_i <= 10^6
* 
****************************************/

import java.util.Arrays;

import java.util.Arrays;

class Solution {
    // Bottom-up DP with binary search to find max total value from at most k events
    // Sort events by start day; for each event, either skip or take it
    // Taking it adds its value and moves to the next non-overlapping event via binary search
    // Time: O(n * k + n log n), where n = number of events (log n from binary search)
    // Space: O(n * k) for the DP table storing subproblem results
    public int maxValue(int[][] events, int k) {
        if (k == 1) {
            int maxValue = 0;
            for (int[] event : events) {
                maxValue = Math.max(maxValue, event[2]);
            }
            return maxValue;
        }

        // Sort events by their start time
        Arrays.sort(events, (a, b) -> Integer.compare(a[0], b[0]));

        int n = events.length;
        int[][] dp = new int[n + 1][k + 1];  // dp[i][j] = max value from i-th event with j events remaining

        // Fill DP table from the end to the beginning
        for (int i = n - 1; i >= 0; i--) {
            int next = findNext(events, i + 1, n, events[i][1]);
            for (int count = k - 1; count >= 0; count--) {
                dp[i][count] = Math.max(dp[i + 1][count], events[i][2] + dp[next][count + 1]);
            }
        }

        return dp[0][0];
    }

    // Binary search to find the first event starting after 'target' end day
    private int findNext(int[][] events, int left, int right, int target) {
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (events[mid][0] > target) right = mid;
            else left = mid + 1;
        }
        return left;
    }
}
