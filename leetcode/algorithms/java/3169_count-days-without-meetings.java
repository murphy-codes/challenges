// Source: https://leetcode.com/problems/count-days-without-meetings/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-03-23

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

import java.util.Arrays;

class Solution {
    // Sort the meetings by start time (O(n log n)).
    // Iterate through meetings, merging overlapping intervals (O(n)).
    // Track the total occupied days and subtract from `days` (O(1)).
    // Overall complexity: O(n log n), optimal for large inputs.
    public int countDays(int days, int[][] meetings) {
        // Sort meetings by start day
        Arrays.sort(meetings, (a, b) -> Integer.compare(a[0], b[0]));
        
        int occupiedDays = 0, prevEnd = 0;

        for (int[] meeting : meetings) {
            int start = meeting[0], end = meeting[1];
            if (start > prevEnd) {
                occupiedDays += end - start + 1;
            } else if (end > prevEnd) {
                occupiedDays += end - prevEnd;
            }
            prevEnd = Math.max(prevEnd, end);
        }

        return days - occupiedDays;
    }
}
