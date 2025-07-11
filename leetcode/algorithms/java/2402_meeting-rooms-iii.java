// Source: https://leetcode.com/problems/meeting-rooms-iii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-11
// At the time of submission:
//   Runtime 51 ms Beats 96.34%
//   Memory 101.29 MB Beats 16.64%

/****************************************
* 
* You are given an integer `n`. There are n rooms numbered from `0` to `n - 1`.
* You are given a 2D integer array `meetings` where `meetings[i] = [start_i, end_i]`
* _ means that a meeting will be held during the half-closed time interval
* _ `[start_i, end_i)`. All the values of `start_i` are unique.
* Meetings are allocated to rooms in the following manner:
* 1. Each meeting will take place in the unused room with the lowest number.
* 2. If there are no available rooms, the meeting will be delayed until a room
* __ becomes free. The delayed meeting should have the same duration as the
* __ original meeting. When a room becomes unused, meetings that have an earlier
* __ original start time should be given the room.
* 3. Return the number of the room that held the most meetings. If there are
* __ multiple rooms, return the room with the lowest number.
* A half-closed interval `[a, b)` is the interval between `a` and `b` including
* _ `a` and not including `b`.
*
* Example 1:
* Input: n = 2, meetings = [[0,10],[1,5],[2,7],[3,4]]
* Output: 0
* Explanation:
* - At time 0, both rooms are not being used. The first meeting starts in room 0.
* - At time 1, only room 1 is not being used. The second meeting starts in room 1.
* - At time 2, both rooms are being used. The third meeting is delayed.
* - At time 3, both rooms are being used. The fourth meeting is delayed.
* - At time 5, the meeting in room 1 finishes. The third meeting starts in room 1 for the time period [5,10).
* - At time 10, the meetings in both rooms finish. The fourth meeting starts in room 0 for the time period [10,11).
* Both rooms 0 and 1 held 2 meetings, so we return 0.
*
* Example 2:
* Input: n = 3, meetings = [[1,20],[2,10],[3,5],[4,9],[6,8]]
* Output: 1
* Explanation:
* - At time 1, all three rooms are not being used. The first meeting starts in room 0.
* - At time 2, rooms 1 and 2 are not being used. The second meeting starts in room 1.
* - At time 3, only room 2 is not being used. The third meeting starts in room 2.
* - At time 4, all three rooms are being used. The fourth meeting is delayed.
* - At time 5, the meeting in room 2 finishes. The fourth meeting starts in room 2 for the time period [5,10).
* - At time 6, all three rooms are being used. The fifth meeting is delayed.
* - At time 10, the meetings in rooms 1 and 2 finish. The fifth meeting starts in room 1 for the time period [10,12).
* Room 0 held 1 meeting while rooms 1 and 2 each held 2 meetings, so we return 1.
*
* Constraints:
* • 1 <= n <= 100
* • 1 <= meetings.length <= 10^5
* • meetings[i].length == 2
* • 0 <= start_i < end)i <= 5 * 10^5
* • All the values of `start_i` are unique.
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Sort meetings by start time, then allocate each meeting to a room.
    // For each meeting, try to assign it to a currently free room.
    // If none are free, delay the meeting until the earliest room is free.
    // Track the number of meetings per room and return the most used room.
    // Time: O(m * n), Space: O(n); where m = # of meetings, n = # of rooms.
    public int mostBooked(int n, int[][] meetings) {
        long[] roomAvailableAt = new long[n];
        int[] roomMeetingCount = new int[n];

        // Sort all meetings by start time
        Arrays.sort(meetings, (a, b) -> Integer.compare(a[0], b[0]));

        for (int[] meeting : meetings) {
            int start = meeting[0], end = meeting[1];
            int duration = end - start;

            int chosenRoom = -1;
            long earliestFreeTime = Long.MAX_VALUE;

            // Try to find a room available at or before the meeting start
            for (int i = 0; i < n; i++) {
                if (roomAvailableAt[i] <= start) {
                    chosenRoom = i;
                    break;
                } else if (roomAvailableAt[i] < earliestFreeTime) {
                    earliestFreeTime = roomAvailableAt[i];
                    chosenRoom = i;
                }
            }

            if (roomAvailableAt[chosenRoom] <= start) {
                // Room is free, schedule at actual start time
                roomAvailableAt[chosenRoom] = start + duration;
            } else {
                // Room is busy, delay meeting until it's free
                roomAvailableAt[chosenRoom] += duration;
            }

            roomMeetingCount[chosenRoom]++;
        }

        // Find the room with the most meetings
        int maxRoom = 0;
        for (int i = 1; i < n; i++) {
            if (roomMeetingCount[i] > roomMeetingCount[maxRoom]) {
                maxRoom = i;
            }
        }

        return maxRoom;
    }
}
