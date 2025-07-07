// Source: https://leetcode.com/problems/maximum-number-of-events-that-can-be-attended/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-06
// At the time of submission:
//   Runtime 66 ms Beats 60.61%
//   Memory 75.39 MB Beats 86.83%

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
import java.util.PriorityQueue;

class Solution {
    // Greedy + Min-Heap approach to attend max events.
    // Sort events by start day; use min-heap to track soonest-ending events.
    // On each day, add newly available events, remove expired ones,
    // and attend the event that ends earliest.
    // Time: O(N log N), Space: O(N), where N = events.length
    public int maxEvents(int[][] events) {
        Arrays.sort(events, (a, b) -> Integer.compare(a[0], b[0]));
        
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int i = 0, day = 1, count = 0;
        int n = events.length;
        
        while (i < n || !minHeap.isEmpty()) {
            // Add all events starting today
            while (i < n && events[i][0] == day) {
                minHeap.offer(events[i][1]);
                i++;
            }
            // Remove expired events
            while (!minHeap.isEmpty() && minHeap.peek() < day) {
                minHeap.poll();
            }
            // Attend the event that ends earliest
            if (!minHeap.isEmpty()) {
                minHeap.poll();
                count++;
            }
            day++;
        }
        return count;
    }
}
