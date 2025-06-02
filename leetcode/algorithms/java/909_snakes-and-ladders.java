// Source: https://leetcode.com/problems/snakes-and-ladders/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-01
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 44.59 MB Beats 71.18%

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

class Solution {
    // Performs BFS on a flattened snakes-and-ladders board using an array-based
    // circular queue for optimal performance. Ladders/snakes are applied directly
    // using a flattened lookup. Uses early exits and pruning to reduce runtime.
    // Time: O(n^2), Space: O(n^2), where n is the board length.
    public int snakesAndLadders(int[][] board) {
        int n = board.length;
        int goal = n * n;

        // Flatten the board into a 1D array for easy index access
        short[] squareToDest = new short[goal + 1];
        int index = 1;

        // Fill in the flattened board in zigzag order
        for (int row = n - 1; row >= 0; row--) {
            for (int col = 0; col < n; col++) {
                squareToDest[index++] = (short) board[row][col];
            }
            if (--row < 0) break;
            for (int col = n - 1; col >= 0; col--) {
                squareToDest[index++] = (short) board[row][col];
            }
        }

        // Use a circular queue to perform BFS efficiently
        short[] queue = new short[goal];
        int head = 0, tail = 0;
        queue[tail++] = 1;

        // Track number of moves to reach each square (0 means unvisited)
        int[] movesToReach = new int[goal + 1];
        movesToReach[1] = 1;

        while (head != tail) {
            int curr = queue[head++];
            head %= goal;

            // If we can reach or overshoot the goal with a dice roll, return steps
            if (curr + 6 >= goal) {
                return movesToReach[curr];
            }

            int bestNeutral = 0;
            for (int roll = 6; roll >= 1; roll--) {
                int next = curr + roll;

                // If there's a ladder or snake, jump to the destination
                if (squareToDest[next] >= 0) {
                    next = squareToDest[next];
                    if (next == goal) return movesToReach[curr];
                } else {
                    // Skip worse neutral (no snake or ladder) positions
                    if (roll < bestNeutral) continue;
                    bestNeutral = roll;
                }

                // If this square hasn't been visited yet
                if (movesToReach[next] == 0) {
                    movesToReach[next] = movesToReach[curr] + 1;
                    queue[tail++] = (short) next;
                    tail %= goal;

                    // Detect circular queue overflow (shouldn't happen)
                    if (head == tail) return 0;
                }
            }
        }

        return -1; // If the goal is unreachable
    }
}
