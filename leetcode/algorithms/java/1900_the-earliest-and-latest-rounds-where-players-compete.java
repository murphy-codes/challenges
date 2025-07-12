// Source: https://leetcode.com/problems/the-earliest-and-latest-rounds-where-players-compete/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-11
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 41.38 MB Beats 84.38%

/****************************************
* 
* There is a tournament where `n` players are participating. The players are
* _ standing in a single row and are numbered from `1` to `n` based on their
* _ initial standing position (player `1` is the first player in the row,
* _ player `2` is the second player in the row, etc.).
* The tournament consists of multiple rounds (starting from round number `1`).
* _ In each round, the `i^th` player from the front of the row competes against
* _ the `i^th` player from the end of the row, and the winner advances to the next
* _ round. When the number of players is odd for the current round, the player in
* _ the middle automatically advances to the next round.
* • For example, if the row consists of players `1, 2, 4, 6, 7`
* _ • Player `1` competes against player `7`.
* _ • Player `2` competes against player `6`.
* _ • Player `4` automatically advances to the next round.
* After each round is over, the winners are lined back up in the row based on the
* _ original ordering assigned to them initially (ascending order).
* The players numbered `firstPlayer` and `secondPlayer` are the best in the
* _ tournament. They can win against any other player before they compete against
* _ each other. If any two other players compete against each other, either of them
* _ might win, and thus you may choose the outcome of this round.
* Given the integers `n`, `firstPlayer`, and `secondPlayer`, return an integer array
* _ containing two values, the earliest possible round number and the latest possible
* _ round number in which these two players will compete against each other, respectively.
*
* Example 1:
* Input: n = 11, firstPlayer = 2, secondPlayer = 4
* Output: [3,4]
* Explanation:
* One possible scenario which leads to the earliest round number:
* First round: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11
* Second round: 2, 3, 4, 5, 6, 11
* Third round: 2, 3, 4
* One possible scenario which leads to the latest round number:
* First round: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11
* Second round: 1, 2, 3, 4, 5, 6
* Third round: 1, 2, 4
* Fourth round: 2, 4
*
* Example 2:
* Input: n = 5, firstPlayer = 1, secondPlayer = 5
* Output: [1,1]
* Explanation: The players numbered 1 and 5 compete in the first round.
* There is no way to make them compete in any other round.
*
* Constraints:
* • 2 <= n <= 28
* • 1 <= firstPlayer < secondPlayer <= n
* 
****************************************/

class Solution {
    // Recursively compute the earliest and latest round when firstPlayer
    // and secondPlayer can meet. Exploits tournament symmetry by reducing
    // to equivalent configurations, and uses combinatorics instead of full
    // simulation. Time complexity is O(n^2) due to pairwise comparisons;
    // space complexity is O(n) stack depth from recursion.
    public int[] earliestAndLatest(int n, int firstPlayer, int secondPlayer) {
        int p1 = Math.min(firstPlayer, secondPlayer);
        int p2 = Math.max(firstPlayer, secondPlayer);

        // Case 1: p1 and p2 are exact mirror → they meet in Round 1
        if (p1 + p2 == n + 1) return new int[]{1, 1};

        // Case 2: When only 3 or 4 players → guaranteed meeting in Round 2
        if (n == 3 || n == 4) return new int[]{2, 2};

        // Symmetry reduction: flip to make p1 closer to left side than p2 to right
        if (p1 - 1 > n - p2) {
            int temp = n + 1 - p1;
            p1 = n + 1 - p2;
            p2 = temp;
        }

        int half = (n + 1) / 2;
        int minRound = n, maxRound = 1;

        if (p2 * 2 <= n + 1) {
            // Case: p2 is in the first half
            int groupA = p1 - 1;          // players before p1
            int groupB = p2 - p1 - 1;     // players between p1 and p2

            for (int i = 0; i <= groupA; i++) {
                for (int j = 0; j <= groupB; j++) {
                    int[] next = earliestAndLatest(half, i + 1, i + j + 2);
                    minRound = Math.min(minRound, 1 + next[0]);
                    maxRound = Math.max(maxRound, 1 + next[1]);
                }
            }
        } else {
            // Case: p2 is in the second half
            int p2Mirrored = n + 1 - p2;
            int groupA = p1 - 1;
            int groupB = p2Mirrored - p1 - 1;
            int groupC = p2 - p2Mirrored - 1;

            for (int i = 0; i <= groupA; i++) {
                for (int j = 0; j <= groupB; j++) {
                    int[] next = earliestAndLatest(
                        half,
                        i + 1,
                        i + j + 1 + (groupC + 1) / 2 + 1
                    );
                    minRound = Math.min(minRound, 1 + next[0]);
                    maxRound = Math.max(maxRound, 1 + next[1]);
                }
            }
        }

        return new int[]{minRound, maxRound};
    }
}
