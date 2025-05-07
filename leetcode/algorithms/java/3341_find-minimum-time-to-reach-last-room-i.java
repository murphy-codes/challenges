// Source: https://leetcode.com/problems/find-minimum-time-to-reach-last-room-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-06
// At the time of submission:
//   Runtime 152 ms Beats 5.61%
//   Memory 44.49 MB Beats 93.77%

/****************************************
* 
* There is a dungeon with `n x m` rooms arranged as a grid.
* You are given a 2D array `moveTime` of size `n x m`, where
* _ `moveTime[i][j]` represents the minimum time in seconds
* _ when you can start moving to that room. You start from the
* _ room `(0, 0)` at time `t = 0` and can move to an adjacent
* _ room. Moving between adjacent rooms takes exactly one second.
* Return the minimum time to reach the room `(n - 1, m - 1)`.
* Two rooms are adjacent if they share a common wall, either
* _ horizontally or vertically.
*
* Example 1:
* Input: moveTime = [[0,4],[4,4]]
* Output: 6
* Explanation:
* The minimum time required is 6 seconds.
* At time t == 4, move from room (0, 0) to room (1, 0) in one second.
* At time t == 5, move from room (1, 0) to room (1, 1) in one second.
*
* Example 2:
* Input: moveTime = [[0,0,0],[0,0,0]]
* Output: 3
* Explanation:
* The minimum time required is 3 seconds.
* At time t == 0, move from room (0, 0) to room (1, 0) in one second.
* At time t == 1, move from room (1, 0) to room (1, 1) in one second.
* At time t == 2, move from room (1, 1) to room (1, 2) in one second.
*
* Example 3:
* Input: moveTime = [[0,1],[1,2]]
* Output: 3
*
* Constraints:
* • 2 <= n == moveTime.length <= 50
* • 2 <= m == moveTime[i].length <= 50
* • 0 <= moveTime[i][j] <= 10^9
* 
****************************************/

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Solution {
    // Uses Dijkstra's algorithm to find the shortest time from (0,0)
    // to (n-1,m-1) in a grid where each cell enforces an earliest
    // entry time. Movement to a neighbor takes 1s and must satisfy
    // that the destination cell's moveTime is met or exceeded.
    // Time complexity: O(n * m * log(n * m)), Space: O(n * m)

    private static final int INF = Integer.MAX_VALUE;

    public int minTimeToReach(int[][] moveTime) {
        int n = moveTime.length, m = moveTime[0].length;
        int[][] dist = new int[n][m];
        for (int[] row : dist) Arrays.fill(row, INF);

        // Min-heap to prioritize cells with the shortest arrival time
        PriorityQueue<Cell> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.time));
        dist[0][0] = 0;
        pq.offer(new Cell(0, 0, 0));

        int[][] directions = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };

        while (!pq.isEmpty()) {
            Cell cur = pq.poll();
            int r = cur.row, c = cur.col, time = cur.time;

            if (r == n - 1 && c == m - 1) return time;

            if (time > dist[r][c]) continue; // Skip if already found better path

            for (int[] dir : directions) {
                int nr = r + dir[0], nc = c + dir[1];
                if (nr < 0 || nr >= n || nc < 0 || nc >= m) continue;

                int arrival = Math.max(time, moveTime[nr][nc]) + 1;
                if (arrival < dist[nr][nc]) {
                    dist[nr][nc] = arrival;
                    pq.offer(new Cell(nr, nc, arrival));
                }
            }
        }

        return -1; // Destination unreachable
    }

    private static class Cell {
        int row, col, time;
        Cell(int row, int col, int time) {
            this.row = row;
            this.col = col;
            this.time = time;
        }
    }
}