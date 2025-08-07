// Source: https://leetcode.com/problems/find-the-maximum-number-of-fruits-collected/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-05
// At the time of submission:
//   Runtime 13 ms Beats 75.00%
//   Memory 215.24 MB Beats 10.00%

/****************************************
* 
* There is a game dungeon comprised of `n x n` rooms arranged in a grid.
*
* You are given a 2D array `fruits` of size `n x n`, where `fruits[i][j]`
* _ represents the number of fruits in the room `(i, j)`. Three children will
* _ play in the game dungeon, with initial positions at the corner rooms
* _ `(0, 0)`, `(0, n - 1)`, and `(n - 1, 0)`.
* The children will make exactly `n - 1` moves according to the following rules
* _ to reach the room `(n - 1, n - 1)`:
* • The child starting from `(0, 0)` must move from their current room `(i, j)`
* __ to one of the rooms `(i + 1, j + 1)`, `(i + 1, j)`, and `(i, j + 1)` if
* __ the target room exists.
* • The child starting from `(0, n - 1)` must move from their current room
* __ `(i, j)` to one of the rooms `(i + 1, j - 1)`, `(i + 1, j)`, and
* __ `(i + 1, j + 1)` if the target room exists.
* • The child starting from `(n - 1, 0)` must move from their current room
* __ `(i, j)` to one of the rooms `(i - 1, j + 1)`, `(i, j + 1)`, and
* __ `(i + 1, j + 1)` if the target room exists.
* When a child enters a room, they will collect all the fruits there. If two
* _ or more children enter the same room, only one child will collect the fruits,
* _ and the room will be emptied after they leave.
* Return the maximum number of fruits the children can collect from the dungeon.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2024/10/15/example_1.gif]
* Input: fruits = [[1,2,3,4],[5,6,8,7],[9,10,11,12],[13,14,15,16]]
* Output: 100
* Explanation:
* In this example:
* The 1st child (green) moves on the path (0,0) -> (1,1) -> (2,2) -> (3, 3).
* The 2nd child (red) moves on the path (0,3) -> (1,2) -> (2,3) -> (3, 3).
* The 3rd child (blue) moves on the path (3,0) -> (3,1) -> (3,2) -> (3, 3).
* In total they collect 1 + 6 + 11 + 16 + 4 + 8 + 12 + 13 + 14 + 15 = 100 fruits.
*
* Example 2:
* Input: fruits = [[1,1],[1,1]]
* Output: 4
* Explanation:
* In this example:
* The 1st child moves on the path (0,0) -> (1,1).
* The 2nd child moves on the path (0,1) -> (1,1).
* The 3rd child moves on the path (1,0) -> (1,1).
* In total they collect 1 + 1 + 1 + 1 = 4 fruits.
*
* Constraints:
* • 2 <= n == fruits.length == fruits[i].length <= 1000
* • 0 <= fruits[i][j] <= 1000
* 
****************************************/

class Solution {
    // This solution collects diagonal fruits and uses dynamic programming (DP)
    // twice to find the optimal non-diagonal paths from both top-right and
    // bottom-left corners. It uses O(n^2) time for each DP run, and O(n) space
    // per run via 1D rolling arrays. Final complexity is O(n^2) time and O(n) space.

    public int maxCollectedFruits(int[][] fruits) {
        int n = fruits.length;
        int totalFruits = 0;

        // Step 1: Collect diagonal fruits (always collected by default)
        for (int i = 0; i < n; ++i) {
            totalFruits += fruits[i][i];
        }

        // Step 2: DP function to calculate best path from top-right or bottom-left
        java.util.function.Supplier<Integer> dp = () -> {
            int[] prev = new int[n]; // Previous row of DP
            int[] curr = new int[n]; // Current row of DP

            // Initialize: starting from (0, n-1) or (n-1, 0), depending on matrix orientation
            java.util.Arrays.fill(prev, Integer.MIN_VALUE);
            prev[n - 1] = fruits[0][n - 1];

            // Traverse from row 1 to n-2 (excluding diagonals)
            for (int i = 1; i < n - 1; ++i) {
                java.util.Arrays.fill(curr, Integer.MIN_VALUE);

                // j is the column index
                for (int j = Math.max(n - 1 - i, i + 1); j < n; ++j) {
                    int bestPrev = prev[j];
                    if (j - 1 >= 0) bestPrev = Math.max(bestPrev, prev[j - 1]);
                    if (j + 1 < n) bestPrev = Math.max(bestPrev, prev[j + 1]);

                    curr[j] = bestPrev + fruits[i][j];
                }

                // Swap buffers
                int[] temp = prev;
                prev = curr;
                curr = temp;
            }

            return prev[n - 1]; // Final result from this path
        };

        // Step 3: Add best path from top-right to bottom-right (excluding diagonals)
        totalFruits += dp.get();

        // Step 4: Flip matrix along the main diagonal to simulate bottom-left to top-left
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < i; ++j) {
                int temp = fruits[i][j];
                fruits[i][j] = fruits[j][i];
                fruits[j][i] = temp;
            }
        }

        // Step 5: Add best path again (this time simulating the second path)
        totalFruits += dp.get();

        return totalFruits;
    }
}
