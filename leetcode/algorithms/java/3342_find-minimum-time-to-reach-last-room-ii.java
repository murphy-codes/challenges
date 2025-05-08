// Source: https://leetcode.com/problems/find-minimum-time-to-reach-last-room-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-07
// At the time of submission:
//   Runtime 23 ms Beats 100.00%
//   Memory 128.41 MB Beats 10.13%

/****************************************
* 
* There is a dungeon with `n x m` rooms arranged as a grid.
* You are given a 2D array `moveTime` of size `n x m`, where `moveTime[i][j]`
* _ represents the minimum time in seconds when you can start moving to that room.
* _ You start from the room `(0, 0)` at time `t = 0` and can move to an adjacent room.
* _ Moving between adjacent rooms takes one second for one move and two seconds
* _ for the next, alternating between the two.
* Return the minimum time to reach the room `(n - 1, m - 1)`.
* Two rooms are adjacent if they share a common wall, either horizontally or vertically.
*
* Example 1:
* Input: moveTime = [[0,4],[4,4]]
* Output: 7
* Explanation:
* The minimum time required is 7 seconds.
* At time t == 4, move from room (0, 0) to room (1, 0) in one second.
* At time t == 5, move from room (1, 0) to room (1, 1) in two seconds.
*
* Example 2:
* Input: moveTime = [[0,0,0,0],[0,0,0,0]]
* Output: 6
* Explanation:
* The minimum time required is 6 seconds.
* At time t == 0, move from room (0, 0) to room (1, 0) in one second.
* At time t == 1, move from room (1, 0) to room (1, 1) in two seconds.
* At time t == 3, move from room (1, 1) to room (1, 2) in one second.
* At time t == 4, move from room (1, 2) to room (1, 3) in two seconds.
*
* Example 3:
* Input: moveTime = [[0,1],[1,2]]
* Output: 4
*
* Constraints:
* • 2 <= n == moveTime.length <= 750
* • 2 <= m == moveTime[i].length <= 750
* • 0 <= moveTime[i][j] <= 10^9
* 
****************************************/

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

class Solution {
    // This solution models each room as a node with timing constraints.
    // Movement alternates between 1 and 2 seconds. We track eligible rooms using two 
    // queues for short and long moves, and a priority queue for rooms not yet reachable. 
    // Time is simulated step-by-step to reach the bottom-right room with minimal delay.
    // Time Complexity: O(n * m * log(n * m))
    // Space Complexity: O(n * m)

    // Represents a room in the dungeon grid
    private static class Room implements Comparable<Room> {
        final int earliestEntryTime;
        final boolean nextMoveIsLong; // true if next move costs 2 sec, else 1 sec
        Room[] neighbors; // Adjacent rooms: left, top, right, bottom
        Room nextInQueue; // Used for linked list traversal of active rooms

        // Default dummy room constructor
        Room() {
            this.earliestEntryTime = Integer.MAX_VALUE;
            this.nextMoveIsLong = true;
        }

        // Real room constructor
        Room(int time, boolean longMoveNext) {
            this.earliestEntryTime = time;
            this.nextMoveIsLong = longMoveNext;
            this.nextInQueue = this; // Marks room as not yet visited
        }

        @Override
        public int compareTo(Room other) {
            return Integer.compare(this.earliestEntryTime, other.earliestEntryTime);
        }
    }

    private static final Room DUMMY_ROOM = new Room();

    // Initialize rooms and connect their neighbors
    private static Room initializeRooms(int[][] moveTime) {
        int rows = moveTime.length;
        int cols = moveTime[0].length;
        Room[][] grid = new Room[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boolean longMove = ((i + j) & 1) == 0;
                grid[i][j] = new Room(moveTime[i][j], longMove);
            }
        }

        Room[] dummyRow = new Room[cols];
        Arrays.fill(dummyRow, DUMMY_ROOM);
        Room[] prevRow = dummyRow;
        Room[] currRow = grid[0];

        for (int i = 0; i <= rows - 1; i++) {
            Room[] nextRow = i < rows - 1 ? grid[i + 1] : dummyRow;
            Room leftNeighbor = DUMMY_ROOM;
            Room current = currRow[0];
            for (int j = 0; j <= cols - 1; j++) {
                Room rightNeighbor = j < cols - 1 ? currRow[j + 1] : DUMMY_ROOM;
                current.neighbors = new Room[] {
                    leftNeighbor, prevRow[j], rightNeighbor, nextRow[j]
                };
                leftNeighbor = current;
                current = rightNeighbor;
            }
            prevRow = currRow;
            currRow = nextRow;
        }

        Room start = grid[0][0];
        start.nextInQueue = grid[rows - 1][cols - 1]; // store finish room reference
        return start;
    }

    public int minTimeToReach(int[][] moveTime) {
        Room start = initializeRooms(moveTime);
        Room finish = start.nextInQueue;

        Queue<Room> waitQueue = new PriorityQueue<>();
        waitQueue.add(DUMMY_ROOM); // Sentinel to avoid null peek

        start.nextInQueue = null; // Mark as processed
        Room shortMoveQueue = start;
        Room longMoveQueue = null;

        int currentTime = 0;

        while (true) {
            Room newLongMoveQueue = null;

            // Process all rooms that are ready for a short move
            while (shortMoveQueue != null) {
                for (Room neighbor : shortMoveQueue.neighbors) {
                    if (neighbor.nextInQueue == neighbor) { // unvisited
                        if (neighbor == finish) {
                            return Math.max(currentTime, finish.earliestEntryTime) +
                                   (finish.nextMoveIsLong ? 2 : 1);
                        }
                        if (neighbor.earliestEntryTime <= currentTime) {
                            if (neighbor.nextMoveIsLong) {
                                neighbor.nextInQueue = newLongMoveQueue;
                                newLongMoveQueue = neighbor;
                            } else {
                                neighbor.nextInQueue = longMoveQueue;
                                longMoveQueue = neighbor;
                            }
                        } else {
                            neighbor.nextInQueue = null; // Mark as seen
                            waitQueue.offer(neighbor);
                        }
                    }
                }
                shortMoveQueue = shortMoveQueue.nextInQueue;
            }

            shortMoveQueue = longMoveQueue;
            longMoveQueue = newLongMoveQueue;

            // Load rooms that have become accessible
            while (waitQueue.peek().earliestEntryTime <= currentTime) {
                Room room = waitQueue.poll();
                if (room.nextMoveIsLong) {
                    room.nextInQueue = longMoveQueue;
                    longMoveQueue = room;
                } else {
                    room.nextInQueue = shortMoveQueue;
                    shortMoveQueue = room;
                }
            }

            // If no rooms can be visited, fast-forward time
            int nextAvailableTime = waitQueue.peek().earliestEntryTime;
            if (++currentTime < nextAvailableTime && shortMoveQueue == null && longMoveQueue == null) {
                currentTime = nextAvailableTime;
            }
        }
    }
}
