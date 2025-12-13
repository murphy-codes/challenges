// Source: https://leetcode.com/problems/count-mentions-per-user/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-11
// At the time of submission:
//   Runtime 19 ms Beats 85.60%
//   Memory 47.49 MB Beats 45.60%

/****************************************
* 
* You are given an integer `numberOfUsers` representing the total number of
* _ users and an array `events` of size `n x 3`.
* Each `events[i]` can be either of the following two types:
* _ 1. Message Event: `["MESSAGE", "timestamp_i", "mentions_string_i"]`
* __ This event indicates that a set of users was mentioned in a message
* __ at `timestamp_i`.
* __ • The `mentions_string_i` string can contain one of the following tokens:
* __ - • `id<number>`: where `<number>` is an integer in range
* __ `[0,numberOfUsers - 1]`. There can be multiple ids separated by a single
* __ whitespace and may contain duplicates. This can mention even the offline users.
* __ - • `ALL`: mentions all users.
* __ - • `HERE`: mentions all online users.
* _ 2. Offline Event: `["OFFLINE", "timestamp_i", "id_i"]`
* __ • This event indicates that the user `id_i` had become offline at
* __ `timestamp_i` for 60 time units. The user will automatically be online
* __ again at time `timestamp_i + 60`.
* Return an array `mentions` where `mentions[i]` represents the number of
* _ mentions the user with id `i` has across all `MESSAGE` events.
* All users are initially online, and if a user goes offline or comes back
* _ online, their status change is processed before handling any message event
* _ that occurs at the same timestamp.
* Note that a user can be mentioned multiple times in a single message event,
* _ and each mention should be counted separately.
*
* Example 1:
* Input: numberOfUsers = 2, events = [["MESSAGE","10","id1 id0"],["OFFLINE","11","0"],["MESSAGE","71","HERE"]]
* Output: [2,2]
* Explanation:
* Initially, all users are online.
* At timestamp 10, id1 and id0 are mentioned. mentions = [1,1]
* At timestamp 11, id0 goes offline.
* At timestamp 71, id0 comes back online and "HERE" is mentioned. mentions = [2,2]
*
* Example 2:
* Input: numberOfUsers = 2, events = [["MESSAGE","10","id1 id0"],["OFFLINE","11","0"],["MESSAGE","12","ALL"]]
* Output: [2,2]
* Explanation:
* Initially, all users are online.
* At timestamp 10, id1 and id0 are mentioned. mentions = [1,1]
* At timestamp 11, id0 goes offline.
* At timestamp 12, "ALL" is mentioned. This includes offline users, so both id0 and id1 are mentioned. mentions = [2,2]
*
* Example 3:
* Input: numberOfUsers = 2, events = [["OFFLINE","10","0"],["MESSAGE","12","HERE"]]
* Output: [0,1]
* Explanation:
* Initially, all users are online.
* At timestamp 10, id0 goes offline.
* At timestamp 12, "HERE" is mentioned. Because id0 is still offline, they will not be mentioned. mentions = [0,1]
*
* Constraints:
* • `1 <= numberOfUsers <= 100`
* • `1 <= events.length <= 100`
* • `events[i].length == 3`
* • `events[i][0]` will be one of `MESSAGE` or `OFFLINE`.
* • `1 <= int(events[i][1]) <= 10^5`
* • The number of `id<number>` mentions in any `"MESSAGE"` event is between `1` and `100`.
* • `0 <= <number> <= numberOfUsers - 1`
* • It is guaranteed that the user id referenced in the `OFFLINE` event is online at the time the event occurs.
* 
****************************************/

// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.List;

class Solution {
    // Process all events in timestamp order, ensuring OFFLINE events are handled
    // before MESSAGE events at the same time. Track each user's online status and
    // when they will come back online. For each message, count mentions according
    // to rules: ALL targets everyone, HERE targets online users, and id<number>
    // tokens target specific users. Complexity is O(E * U) time and O(U) space.

    public int[] countMentions(int numberOfUsers, List<List<String>> events) {
        // Convert event list into sortable objects
        List<Event> ev = new ArrayList<>();
        for (List<String> e : events) {
            String type = e.get(0);
            int time = Integer.parseInt(e.get(1));
            String val = e.get(2);
            ev.add(new Event(type, time, val));
        }

        // Sort events by timestamp, offline-before-message at same timestamp
        Collections.sort(ev, (a, b) -> {
            if (a.time != b.time) return a.time - b.time;
            if (a.type.equals("OFFLINE") && b.type.equals("MESSAGE")) return -1;
            if (a.type.equals("MESSAGE") && b.type.equals("OFFLINE")) return 1;
            return 0;
        });

        int[] mentions = new int[numberOfUsers];
        boolean[] isOnline = new boolean[numberOfUsers];
        int[] offlineUntil = new int[numberOfUsers];

        // Initially everyone is online
        for (int i = 0; i < numberOfUsers; i++) isOnline[i] = true;

        for (Event e : ev) {

            // First update any users who come back online
            for (int i = 0; i < numberOfUsers; i++) {
                if (!isOnline[i] && e.time >= offlineUntil[i]) {
                    isOnline[i] = true;
                }
            }

            if (e.type.equals("OFFLINE")) {
                int user = Integer.parseInt(e.value);
                isOnline[user] = false;
                offlineUntil[user] = e.time + 60;
                continue;
            }

            // MESSAGE event
            String msg = e.value;

            if (msg.equals("ALL")) {
                for (int i = 0; i < numberOfUsers; i++) mentions[i]++;
                continue;
            }

            if (msg.equals("HERE")) {
                for (int i = 0; i < numberOfUsers; i++) {
                    if (isOnline[i]) mentions[i]++;
                }
                continue;
            }

            // Handle explicit id<number> mentions
            String[] tokens = msg.split(" ");
            for (String t : tokens) {
                if (t.startsWith("id")) {
                    int id = Integer.parseInt(t.substring(2));
                    mentions[id]++;  // count duplicates normally
                }
            }
        }

        return mentions;
    }

    // Simple event record
    static class Event {
        String type;
        int time;
        String value;
        Event(String t, int ti, String v) {
            type = t;
            time = ti;
            value = v;
        }
    }
}
