// Source: https://leetcode.com/problems/find-minimum-time-to-reach-last-room-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-06
// At the time of submission:
//   Runtime 6 ms Beats 98.75%
//   Memory 45.02 MB Beats 53.27%

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

import java.util.PriorityQueue;

class Solution {
    // This solution uses Dijkstra's algorithm to find the earliest time to
    // reach the bottom-right cell. Each cell tracks its minimum entry time.
    // A priority queue ensures we always process the next soonest cell.
    // The neighbor traversal is inlined for performance. The time complexity
    // is O(m * n * log(m * n)) and space complexity is O(m * n), where m and n
    // are the number of rows and columns in the grid.

    private static class Cell implements Comparable<Cell> {
        int row, col, time;
        Cell(int row, int col, int time) {
            this.row = row;
            this.col = col;
            this.time = time;
        }
        @Override
        public int compareTo(Cell other) {
            return this.time - other.time;
        }
    }

    public int minTimeToReach(int[][] moveTime) {
        int rows = moveTime.length, cols = moveTime[0].length;

        // Tracks the earliest time to reach each cell
        int[][] minTime = new int[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                minTime[i][j] = Integer.MAX_VALUE;

        PriorityQueue<Cell> pq = new PriorityQueue<>();
        pq.offer(new Cell(0, 0, 0));
        minTime[0][0] = 0;

        while (!pq.isEmpty()) {
            Cell current = pq.poll();
            int r = current.row, c = current.col, time = current.time;

            if (r == rows - 1 && c == cols - 1)
                return time;

            // Skip outdated entries
            if (time > minTime[r][c]) continue;

            // Inline neighbor traversal for performance
            if (r > 0) tryMove(r - 1, c, time, moveTime, minTime, pq);
            if (c > 0) tryMove(r, c - 1, time, moveTime, minTime, pq);
            if (r + 1 < rows) tryMove(r + 1, c, time, moveTime, minTime, pq);
            if (c + 1 < cols) tryMove(r, c + 1, time, moveTime, minTime, pq);
        }

        return -1; // Unreachable (shouldn't happen per problem description)
    }

    private void tryMove(int r, int c, int currTime,
                         int[][] moveTime, int[][] minTime,
                         PriorityQueue<Cell> pq) {
        int open = moveTime[r][c];
        int nextTime = Math.max(currTime, open) + 1;

        if (nextTime < minTime[r][c]) {
            minTime[r][c] = nextTime;
            pq.offer(new Cell(r, c, nextTime));
        }
    }
}