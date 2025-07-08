// Source: https://leetcode.com/problems/maximum-number-of-events-that-can-be-attended-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-07
// At the time of submission:
//   Runtime 135 ms Beats 20.67%
//   Memory 128.92 MB Beats 56.25%

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

class Solution {
    // DP + Binary Search solution for max value of up to k non-overlapping events
    // Sort events by startDay, and at each step, choose to skip or take an event
    // Taking uses binary search to find the next non-conflicting event efficiently
    // Time: O(n log n + n * k) where n = events.length (log n from binary search)
    // Space: O(n * k) for memoization table + O(log n) call stack in worst case
    public int maxValue(int[][] events, int k) {
        // Sort events by start time
        Arrays.sort(events, (a, b) -> Integer.compare(a[0], b[0]));

        int n = events.length;
        // Memoization cache: [event index][events left to pick]
        Integer[][] dp = new Integer[n][k + 1];

        return dfs(events, 0, k, dp);
    }

    // Recursive DP with memoization
    private int dfs(int[][] events, int i, int k, Integer[][] dp) {
        if (i == events.length || k == 0) return 0;
        if (dp[i][k] != null) return dp[i][k];

        // Option 1: Skip current event
        int res = dfs(events, i + 1, k, dp);

        // Option 2: Take current event
        int nextIndex = findNext(events, events[i][1]);
        int take = events[i][2] + dfs(events, nextIndex, k - 1, dp);

        res = Math.max(res, take);
        return dp[i][k] = res;
    }

    // Find next event that starts AFTER endTime
    private int findNext(int[][] events, int endTime) {
        int low = 0, high = events.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (events[mid][0] > endTime) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }
}
