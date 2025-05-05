// Source: https://leetcode.com/problems/count-days-without-meetings/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-03-23
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 100.36 MB Beats 59.22%

/****************************************
* 
* You are given a positive integer `days` representing the total number of days an
* employee is available for work (starting from day 1). You are also given a
* 2D array `meetings` of size `n` where, `meetings[i] = [start_i, end_i]` represents
* the starting and ending days of meeting `i` (inclusive).
* Return the count of days when the employee is available for work but no meetings are scheduled.
* Note: The meetings may overlap.
*
* Example 1:
* Input: days = 10, meetings = [[5,7],[1,3],[9,10]]
* Output: 2
* Explanation:
* There is no meeting scheduled on the 4th and 8th days.
*
* Example 2:
* Input: days = 5, meetings = [[2,4],[1,3]]
* Output: 1
* Explanation:
* There is no meeting scheduled on the 5th day.
*
* Example 3:
* Input: days = 6, meetings = [[1,6]]
* Output: 0
* Explanation:
* Meetings are scheduled for all working days.
*
* Constraints:
* • 1 <= days <= 10^9
* • 1 <= meetings.length <= 10^5
* • meetings[i].length == 2
* • 1 <= meetings[i][0] <= meetings[i][1] <= days
* 
****************************************/

class Solution {
    // Recursively calculates the number of free days by checking how each meeting  
    // overlaps with the current interval [startDay, endDay]. If a meeting overlaps,  
    // the interval is split and the function recurses on the non-overlapping parts.  
    // Assumes `meetings` is sorted by start day for correctness and performance.  
    // Time: O(n^2) in worst case due to recursion; Space: O(n) call stack depth.
    public int countDays(int totalDays, int[][] meetings) {
        return countFreeDays(1, totalDays, meetings, 0);
    }

    private int countFreeDays(int startDay, int endDay, int[][] meetings, int index) {
        int freeDays = 0;

        while (index < meetings.length) {
            int meetingStart = meetings[index][0];
            int meetingEnd = meetings[index][1];

            // Case 1: Meeting starts before or at current interval
            if (meetingStart <= startDay) {
                if (meetingEnd >= endDay) {
                    // Meeting fully covers the interval: no free days
                    return freeDays;
                } else if (meetingEnd < startDay) {
                    // Meeting is before current interval: skip it
                    index++;
                } else {
                    // Overlaps at the beginning: move startDay forward
                    startDay = meetingEnd + 1;
                    index++;
                }
            }

            // Case 2: Meeting ends after or at current interval
            else if (meetingEnd >= endDay) {
                if (meetingStart > endDay) {
                    // Meeting starts after current interval: skip it
                    index++;
                } else {
                    // Overlaps at the end: shrink endDay
                    endDay = meetingStart - 1;
                    index++;
                }
            }

            // Case 3: Meeting is strictly inside the current interval
            else {
                // Count free days before this meeting, recurse after
                freeDays += countFreeDays(startDay, meetingStart - 1, meetings, index + 1);
                startDay = meetingEnd + 1;
            }
        }

        // Remaining interval is free
        return freeDays + (endDay - startDay + 1);
    }
}