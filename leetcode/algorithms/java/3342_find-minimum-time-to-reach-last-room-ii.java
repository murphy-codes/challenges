// Source: https://leetcode.com/problems/find-minimum-time-to-reach-last-room-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-07
// At the time of submission:
//   Runtime 301 ms Beats 42.73%
//   Memory 114.16 MB Beats 30.84%

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

import java.util.PriorityQueue;
import java.util.Comparator;

class Solution {
    // Uses Dijkstra's algorithm with state tracking for move parity.
    // Alternates movement cost (1 or 2s), tracked via boolean moveIsOneSec.
    // For each cell, we maintain two shortest arrival times depending on move state.
    // Priority queue ensures always expanding the shortest-time path first.
    // Time Complexity: O(n * m * log(n * m)), Space: O(n * m)
    private static class State {
        int row, col, time;
        boolean moveIsOneSec;

        State(int row, int col, int time, boolean moveIsOneSec) {
            this.row = row;
            this.col = col;
            this.time = time;
            this.moveIsOneSec = moveIsOneSec;
        }
    }

    public int minTimeToReach(int[][] moveTime) {
        int rows = moveTime.length, cols = moveTime[0].length;
        int[][][] minTime = new int[rows][cols][2]; // 0: next move is 1s, 1: next move is 2s

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++) {
                minTime[i][j][0] = Integer.MAX_VALUE;
                minTime[i][j][1] = Integer.MAX_VALUE;
            }

        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.time));
        pq.offer(new State(0, 0, 0, true));
        minTime[0][0][1] = 0; // next move will be 1 second

        int[][] directions = {{1,0},{-1,0},{0,1},{0,-1}};

        while (!pq.isEmpty()) {
            State curr = pq.poll();
            int r = curr.row, c = curr.col, time = curr.time;
            boolean isOne = curr.moveIsOneSec;
            int parity = isOne ? 1 : 0;

            if (r == rows - 1 && c == cols - 1)
                return time;

            if (time > minTime[r][c][parity])
                continue;

            for (int[] dir : directions) {
                int nr = r + dir[0], nc = c + dir[1];
                if (nr < 0 || nc < 0 || nr >= rows || nc >= cols) continue;

                int waitUntil = moveTime[nr][nc];
                int moveCost = isOne ? 1 : 2;
                int arriveTime = Math.max(time, waitUntil) + moveCost;
                int nextParity = isOne ? 0 : 1;

                if (arriveTime < minTime[nr][nc][nextParity]) {
                    minTime[nr][nc][nextParity] = arriveTime;
                    pq.offer(new State(nr, nc, arriveTime, !isOne));
                }
            }
        }

        return -1;
    }
}
