// Source: https://leetcode.com/problems/meeting-rooms-iii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-11
// At the time of submission:
//   Runtime 50 ms Beats 98.30%
//   Memory 125.74 MB Beats 85.27%

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

class Solution {
    // Sort meetings by start time, then for each meeting scan rooms to find
    // the first available room. If one is free by the meeting start, assign it.
    // Otherwise, delay the meeting and schedule it when the earliest room frees.
    // Track how many meetings each room hosts, returning the most used room.
    // Time: O(m * n) where m = #meetings, n = #rooms; Space: O(n)
    public int mostBooked(int roomCount, int[][] meetings) {
        int[] meetingUsedCount = new int[roomCount];    // # of meetings per room
        long[] roomFreeTime = new long[roomCount];      // next available time per room

        Arrays.sort(meetings, (a, b) -> a[0] - b[0]);   // sort by start time

        for (int[] meet : meetings) {
            int start = meet[0], end = meet[1];
            long earliestFree = Long.MAX_VALUE;
            int earliestRoom = -1;
            boolean scheduled = false;

            // try assign room; track earliest busy room fallback
            for (int room = 0; room < roomCount; room++) {
                if (roomFreeTime[room] < earliestFree) {
                    earliestFree = roomFreeTime[room];
                    earliestRoom = room;
                }
                if (roomFreeTime[room] <= start) {
                    roomFreeTime[room] = end;
                    meetingUsedCount[room]++;
                    scheduled = true;
                    break;
                }
            }

            // if none were free, delay meeting in earliest available room
            if (!scheduled) {
                roomFreeTime[earliestRoom] += (end - start);
                meetingUsedCount[earliestRoom]++;
            }
        }

        // find room with highest usage (lowest index wins ties)
        int bestRoom = 0, maxMeetings = 0;
        for (int room = 0; room < roomCount; room++) {
            if (meetingUsedCount[room] > maxMeetings) {
                maxMeetings = meetingUsedCount[room];
                bestRoom = room;
            }
        }
        return bestRoom;
    }
}
