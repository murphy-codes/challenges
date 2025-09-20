// Source: https://leetcode.com/problems/implement-router/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-19
// At the time of submission:
//   Runtime 150 ms Beats 100.00%
//   Memory 114.20 MB Beats 84.55%

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
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

class Router {
    // Router uses a queue for FIFO forwarding and a map of destination -> packets.
    // Each destination keeps a sorted list of [source, timestamp] to allow O(log n)
    // binary search for range queries and duplicates. Adding/removing packets is O(1)
    // amortized (queue ops), getCount is O(log n). Memory is O(N), bounded by limit.

    Deque<int[]> packetQueue;  // FIFO queue: stores packets in [source, dest, time]
    HashMap<Integer, List<int[]>> destToPackets;  // Maps destination -> list of [source, time]
    int capacity;

    public Router(int memoryLimit) {
        packetQueue = new LinkedList<>();
        destToPackets = new HashMap<>();
        this.capacity = memoryLimit;
    }
    
    public boolean addPacket(int source, int destination, int timestamp) {
        // Ensure a list exists for this destination
        destToPackets.putIfAbsent(destination, new ArrayList<>());
        List<int[]> packetList = destToPackets.get(destination);

        // Binary search for insertion index of this timestamp
        int left = lowerBound(packetList, timestamp);

        // Check if duplicate (same source, same timestamp)
        for (int i = left; i < packetList.size() && packetList.get(i)[1] == timestamp; i++) {
            if (packetList.get(i)[0] == source) return false;
        }

        // Add packet to both global queue and destination list
        packetList.add(new int[]{source, timestamp});
        packetQueue.addLast(new int[]{source, destination, timestamp});

        // If capacity exceeded, evict oldest packet
        if (packetQueue.size() > capacity) {
            forwardPacket();
        }
        return true;
    }
    
    public int[] forwardPacket() {
        if (packetQueue.isEmpty()) return new int[0];

        int[] packet = packetQueue.pollFirst();
        // Remove from destination-specific list as well
        destToPackets.get(packet[1]).remove(0);
        return packet;
    }
    
    public int getCount(int destination, int startTime, int endTime) {
        if (!destToPackets.containsKey(destination)) return 0;
        List<int[]> packetList = destToPackets.get(destination);

        int left = lowerBound(packetList, startTime);
        int right = upperBound(packetList, endTime);

        if (left > right) return 0;
        return right - left + 1;
    }

    // Binary search: first index where timestamp >= start
    private int lowerBound(List<int[]> list, int start) {
        int left = 0, right = list.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid)[1] >= start) right = mid - 1;
            else left = mid + 1;
        }
        return left;
    }

    // Binary search: last index where timestamp <= end
    private int upperBound(List<int[]> list, int end) {
        int left = 0, right = list.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid)[1] > end) right = mid - 1;
            else left = mid + 1;
        }
        return right;
    }
}
