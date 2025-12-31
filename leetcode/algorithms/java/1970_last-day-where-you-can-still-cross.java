// Source: https://leetcode.com/problems/last-day-where-you-can-still-cross/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-30
// At the time of submission:
//   Runtime 69 ms Beats 58.38%
//   Memory 75.30 MB Beats 86.80%

/****************************************
* 
* There is a 1-based binary matrix where `0` represents land and `1` represents
* _ water. You are given integers `row` and `col` representing the number of
* _ rows and columns in the matrix, respectively.
* Initially on day `0`, the entire matrix is land. However, each day a new cell
* _ becomes flooded with water. You are given a 1-based 2D array `cells`, where
* _ `cells[i] = [r_i, c_i]` represents that on the `i^th` day, the cell on the
* _ `r_i^th` row and `c_i^th` column (1-based coordinates) will be covered with
* _ water (i.e., changed to `1`).
* You want to find the last day that it is possible to walk from the top to the
* _ bottom by only walking on land cells. You can start from any cell in the top
* _ row and end at any cell in the bottom row. You can only travel in the four
* _ cardinal directions (left, right, up, and down).
* Return the last day where it is possible to walk from the top to the bottom
* _ by only walking on land cells.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/07/27/1.png]
* Input: row = 2, col = 2, cells = [[1,1],[2,1],[1,2],[2,2]]
* Output: 2
* Explanation: The above image depicts how the matrix changes each day starting from day 0.
* The last day where it is possible to cross from top to bottom is on day 2.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2021/07/27/2.png]
* Input: row = 2, col = 2, cells = [[1,1],[1,2],[2,1],[2,2]]
* Output: 1
* Explanation: The above image depicts how the matrix changes each day starting from day 0.
* The last day where it is possible to cross from top to bottom is on day 1.
*
* Example 3:
* [Image: https://assets.leetcode.com/uploads/2021/07/27/3.png]
* Input: row = 3, col = 3, cells = [[1,2],[2,1],[3,3],[2,2],[1,1],[1,3],[2,3],[3,2],[3,1]]
* Output: 3
* Explanation: The above image depicts how the matrix changes each day starting from day 0.
* The last day where it is possible to cross from top to bottom is on day 3.
*
* Constraints:
* • `2 <= row, col <= 2 * 10^4`
* • `4 <= row * col <= 2 * 10^4`
* • `cells.length == row * col`
* • `1 <= r_i <= row`
* • `1 <= c_i <= col`
* • All the values of `cells` are unique.
* 
****************************************/

import java.util.ArrayDeque;
import java.util.Queue;

class Solution {
    // Binary search the latest day where a path from top to bottom still exists.
    // For each mid day, flood all cells up to that day and run BFS starting from
    // the unflooded cells in the top row. If BFS reaches the bottom row, crossing
    // is still possible on that day. Time: O((row*col) * log(row*col)). Space: O(row*col).
    public int latestDayToCross(int row, int col, int[][] cells) {
        int left = 0, right = cells.length - 1, answer = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (canCross(row, col, cells, mid)) {
                answer = mid + 1;   // +1 because days are 1-based
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return answer;
    }

    private boolean canCross(int row, int col, int[][] cells, int day) {
        int[][] grid = new int[row][col];

        // Mark flooded cells up to the given day
        for (int i = 0; i <= day; i++) {
            int r = cells[i][0] - 1;
            int c = cells[i][1] - 1;
            grid[r][c] = 1;
        }

        Queue<int[]> q = new ArrayDeque<>();

        // Push all non-flooded cells in top row
        for (int c = 0; c < col; c++) {
            if (grid[0][c] == 0) {
                q.offer(new int[]{0, c});
                grid[0][c] = 1; // mark visited by turning into water
            }
        }

        int[][] directions = {{1,0},{-1,0},{0,1},{0,-1}};

        // BFS until we reach bottom
        while (!q.isEmpty()) {
            int[] cell = q.poll();
            int r = cell[0], c = cell[1];

            if (r == row - 1) return true; // reached bottom

            for (int[] d : directions) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < row && nc >= 0 && nc < col && grid[nr][nc] == 0) {
                    grid[nr][nc] = 1; // visited
                    q.offer(new int[]{nr, nc});
                }
            }
        }

        return false;
    }
}
