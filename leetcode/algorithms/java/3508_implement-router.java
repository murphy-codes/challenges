// Source: https://leetcode.com/problems/implement-router/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-19
// At the time of submission:
//   Runtime 189 ms Beats 49.09%
//   Memory 149.15 MB Beats 58.18%

/****************************************
* 
* Design a data structure that can efficiently manage data packets in a network
* _ router. Each data packet consists of the following attributes:
* • `source`: A unique identifier for the machine that generated the packet.
* • `destination`: A unique identifier for the target machine.
* • `timestamp`: The time at which the packet arrived at the router.
* Implement the `Router` class:
* • `Router(int memoryLimit)`: Initializes the Router object with a
* _ fixed memory limit.
* _ • `memoryLimit` is the maximum number of packets the router can store
* ___ at any given time.
* _ • If adding a new packet would exceed this limit, the oldest packet must
* ___ be removed to free up space.
* • `bool addPacket(int source, int destination, int timestamp)`: Adds a packet
* _ with the given attributes to the router.
* _ • A packet is considered a duplicate if another packet with the same
* ___ `source`, `destination`, and `timestamp` already exists in the router.
* • Return `true` if the packet is successfully added (i.e., it is not
* ___ a duplicate); otherwise return `false`.
* • `int[] forwardPacket()`: Forwards the next packet in
* _ FIFO (First In First Out) order.
* _ • Remove the packet from storage.
* _ • Return the packet as an array `[source, destination, timestamp]`.
* _ • If there are no packets to forward, return an empty array.
* • `int getCount(int destination, int startTime, int endTime)`:
* _ • Returns the number of packets currently stored in the router
* ___ (i.e., not yet forwarded) that have the specified destination and have
* ___ timestamps in the inclusive range `[startTime, endTime]`.
* Note that queries for `addPacket` will be made in increasing order of `timestamp`.
*
* Example 1:
* Input:
* ["Router", "addPacket", "addPacket", "addPacket", "addPacket", "addPacket", "forwardPacket", "addPacket", "getCount"]
* [[3], [1, 4, 90], [2, 5, 90], [1, 4, 90], [3, 5, 95], [4, 5, 105], [], [5, 2, 110], [5, 100, 110]]
* Output:
* [null, true, true, false, true, true, [2, 5, 90], true, 1]
* Explanation
* Router router = new Router(3); // Initialize Router with memoryLimit of 3.
* router.addPacket(1, 4, 90); // Packet is added. Return True.
* router.addPacket(2, 5, 90); // Packet is added. Return True.
* router.addPacket(1, 4, 90); // This is a duplicate packet. Return False.
* router.addPacket(3, 5, 95); // Packet is added. Return True
* router.addPacket(4, 5, 105); // Packet is added, [1, 4, 90] is removed as number of packets exceeds memoryLimit. Return True.
* router.forwardPacket(); // Return [2, 5, 90] and remove it from router.
* router.addPacket(5, 2, 110); // Packet is added. Return True.
* router.getCount(5, 100, 110); // The only packet with destination 5 and timestamp in the inclusive range [100, 110] is [4, 5, 105]. Return 1.
*
* Example 2:
* Input:
* ["Router", "addPacket", "forwardPacket", "forwardPacket"]
* [[2], [7, 4, 90], [], []]
* Output:
* [null, true, [7, 4, 90], []]
* Explanation
* Router router = new Router(2); // Initialize Router with memoryLimit of 2.
* router.addPacket(7, 4, 90); // Return True.
* router.forwardPacket(); // Return [7, 4, 90].
* router.forwardPacket(); // There are no packets left, return [].
*
* Constraints:
* • `2 <= memoryLimit <= 10^5`
* • `1 <= source, destination <= 2 * 10^5`
* • `1 <= timestamp <= 10^9`
* • `1 <= startTime <= endTime <= 10^9`
* • At most `10^5` calls will be made to `addPacket`, `forwardPacket`, and `getCount` methods altogether.
* • queries for `addPacket` will be made in increasing order of `timestamp`.
* 
****************************************/

import java.util.Deque;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

class Router {
    // This Router stores packets with FIFO order while supporting duplicate checks
    // and range queries by destination + timestamp. A deque manages packet order,
    // a hash set tracks duplicates, and a map of destination → timestamps supports
    // binary search for fast getCount queries. Each add/forward/edit is O(log n)
    // due to binary search/remove, and overall space is O(memoryLimit).
    
    private int memoryLimit;
    private Deque<int[]> packetQueue; // stores [source, destination, timestamp]
    private Set<String> packetSet;    // detects duplicates
    private Map<Integer, List<Integer>> destToTimestamps; // dest → sorted timestamps

    public Router(int memoryLimit) {
        this.memoryLimit = memoryLimit;
        this.packetQueue = new ArrayDeque<>();
        this.packetSet = new HashSet<>();
        this.destToTimestamps = new HashMap<>();
    }

    public boolean addPacket(int source, int destination, int timestamp) {
        String key = source + "|" + destination + "|" + timestamp;
        if (packetSet.contains(key)) return false; // duplicate

        // Evict oldest if over memory limit
        if (packetQueue.size() == memoryLimit) {
            int[] oldest = packetQueue.pollFirst();
            String oldKey = oldest[0] + "|" + oldest[1] + "|" + oldest[2];
            packetSet.remove(oldKey);

            // remove timestamp from destination list
            List<Integer> list = destToTimestamps.get(oldest[1]);
            if (list != null) {
                int idx = Collections.binarySearch(list, oldest[2]);
                if (idx >= 0) list.remove(idx);
            }
        }

        // Add new packet
        packetQueue.offerLast(new int[]{source, destination, timestamp});
        packetSet.add(key);
        destToTimestamps.computeIfAbsent(destination, k -> new ArrayList<>()).add(timestamp);
        return true;
    }

    public int[] forwardPacket() {
        if (packetQueue.isEmpty()) return new int[0];

        int[] packet = packetQueue.pollFirst();
        String key = packet[0] + "|" + packet[1] + "|" + packet[2];
        packetSet.remove(key);

        // remove timestamp from destination list
        List<Integer> list = destToTimestamps.get(packet[1]);
        if (list != null) {
            int idx = Collections.binarySearch(list, packet[2]);
            if (idx >= 0) list.remove(idx);
        }

        return packet;
    }

    public int getCount(int destination, int startTime, int endTime) {
        List<Integer> list = destToTimestamps.get(destination);
        if (list == null || list.isEmpty()) return 0;

        // binary search for range
        int left = lowerBound(list, startTime);
        int right = upperBound(list, endTime);
        return Math.max(0, right - left);
    }

    // first index >= target
    private int lowerBound(List<Integer> list, int target) {
        int lo = 0, hi = list.size();
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (list.get(mid) < target) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    // first index > target
    private int upperBound(List<Integer> list, int target) {
        int lo = 0, hi = list.size();
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (list.get(mid) <= target) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}

/**
 * Your Router object will be instantiated and called as such:
 * Router obj = new Router(memoryLimit);
 * boolean param_1 = obj.addPacket(source,destination,timestamp);
 * int[] param_2 = obj.forwardPacket();
 * int param_3 = obj.getCount(destination,startTime,endTime);
 */
