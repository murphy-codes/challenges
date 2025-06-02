// Source: https://leetcode.com/problems/snakes-and-ladders/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-01
// At the time of submission:
//   Runtime 10 ms Beats 17.59%
//   Memory 44.99 MB Beats 33.38%

/****************************************
* 
* You are given an `n x n` integer matrix `board` where the cells are labeled from
* _ `1` to `n^2` in a Boustrophedon style starting from the bottom left of the
* _ board (i.e. `board[n - 1][0]`) and alternating direction each row.
* You start on square `1` of the board. In each move, starting from square `curr`,
* _ do the following:
* • Choose a destination square `next` with a label in the range
* _ `[curr + 1, min(curr + 6, n^2)]`.
* __ • This choice simulates the result of a standard 6-sided die roll: i.e.,
* __ there are always at most 6 destinations, regardless of the size of the board.
* • If `next` has a snake or ladder, you must move to the destination of that
* _ snake or ladder. Otherwise, you move to `next`.
* • The game ends when you reach the square `n^2`.
* A board square on row `r` and column `c` has a snake or ladder if
* _ `board[r][c] != -1`. The destination of that snake or ladder is `board[r][c]`.
* _ Squares `1` and `n^2` are not the starting points of any snake or ladder.
* Note that you only take a snake or ladder at most once per dice roll. If the
* _ destination to a snake or ladder is the start of another snake or ladder,
* _ you do not follow the subsequent snake or ladder.
* • For example, suppose the board is `[[-1,4],[-1,3]]`, and on the first move,
* _ your destination square is `2`. You follow the ladder to square `3`, but do
* _ not follow the subsequent ladder to `4`.
* Return the least number of dice rolls required to reach the square `n^2`. If
* _ it is not possible to reach the square, return `-1`.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2018/09/23/snakes.png]
* Input: board = [[-1,-1,-1,-1,-1,-1],[-1,-1,-1,-1,-1,-1],[-1,-1,-1,-1,-1,-1],[-1,35,-1,-1,13,-1],[-1,-1,-1,-1,-1,-1],[-1,15,-1,-1,-1,-1]]
* Output: 4
* Explanation:
* In the beginning, you start at square 1 (at row 5, column 0).
* You decide to move to square 2 and must take the ladder to square 15.
* You then decide to move to square 17 and must take the snake to square 13.
* You then decide to move to square 14 and must take the ladder to square 35.
* You then decide to move to square 36, ending the game.
* This is the lowest possible number of moves to reach the last square, so return 4.
*
* Example 2:
* Input: board = [[-1,-1],[-1,3]]
* Output: 1
*
* Constraints:
* • n == board.length == board[i].length
* • 2 <= n <= 20
* • `board[i][j]` is either `-1` or in the range `[1, n^2]`.
* • The squares labeled `1` and `n^2` are not the starting points of any snake or ladder.
* 
****************************************/

import java.util.Queue;
import java.util.LinkedList;
import java.util.HashSet;

class Solution {
    // Use BFS to simulate moves from square 1, tracking visited squares.  
    // From each square, try moving to the next 1–6 positions, following  
    // ladders or snakes when present. A helper maps square numbers to  
    // board (row, col) positions considering the zigzag layout.  
    // Time: O(n^2), Space: O(n^2) for visited and queue structures.
    public int snakesAndLadders(int[][] board) {
        int n = board.length;
        Queue<Integer> queue = new LinkedList<>();
        HashSet<Integer> visited = new HashSet<>();

        queue.offer(1);  // start from square 1
        visited.add(1);
        int moves = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            // process all positions reachable in this number of moves
            for (int i = 0; i < size; i++) {
                int curr = queue.poll();
                if (curr == n * n) return moves;

                for (int next = curr + 1; next <= Math.min(curr + 6, n * n); next++) {
                    int[] pos = getCoordinates(next, n);
                    int row = pos[0], col = pos[1];
                    int dest = board[row][col] == -1 ? next : board[row][col];

                    if (!visited.contains(dest)) {
                        visited.add(dest);
                        queue.offer(dest);
                    }
                }
            }

            moves++;
        }

        return -1; // cannot reach the end
    }

    // Convert 1D square number to (row, col) coordinates on the board
    private int[] getCoordinates(int square, int n) {
        int row = n - 1 - (square - 1) / n;
        int col = (square - 1) % n;
        if (((n - row) % 2) == 0) {
            col = n - 1 - col; // reverse direction for even rows (from bottom)
        }
        return new int[]{row, col};
    }
}