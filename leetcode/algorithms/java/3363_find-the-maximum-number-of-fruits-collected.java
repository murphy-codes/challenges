// Source: https://leetcode.com/problems/find-the-maximum-number-of-fruits-collected/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-05
// At the time of submission:
//   Runtime 8 ms Beats 95.00%
//   Memory 142.08 MB Beats 95.00%

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
    // This solution uses a diagonal-based DP strategy with in-place updates.
    // Each of the 3 children follows distinct, non-overlapping paths: 
    // diagonal, top-right down, and bottom-left right. Fruits[][] is reused
    // as a DP table to store max fruit collection from each position.
    // Time: O(n^2), Space: O(1) extra (in-place DP using input grid).
    public int maxCollectedFruits(int[][] fruits) {
        int n = fruits.length;

        // Accumulate diagonal (child 1)
        for (int i = 1; i < n; i++) {
            fruits[i][i] += fruits[i - 1][i - 1];

            for (int j = i + 1; j < n; j++) {
                // Skip positions not reachable by child 2 or 3
                if (i + j < n - 1) continue;

                // Child 2: top-right to bottom-right (row < col)
                int fromDown = (j == n - 1) ? 0 : fruits[i - 1][j + 1];
                int fromLeft = (i + j == n - 1) ? 0 : fruits[i - 1][j];
                int fromDiagonal = (j == 0 || i + j <= n) ? 0 : fruits[i - 1][j - 1];
                fruits[i][j] += Math.max(fromDown, Math.max(fromLeft, fromDiagonal));

                // Child 3: bottom-left to bottom-right (row > col)
                int fromUp = (j == n - 1) ? 0 : fruits[j + 1][i - 1];
                int fromRight = (i + j == n - 1) ? 0 : fruits[j][i - 1];
                int fromDiag2 = (j == 0 || i + j <= n) ? 0 : fruits[j - 1][i - 1];
                fruits[j][i] += Math.max(fromUp, Math.max(fromRight, fromDiag2));
            }
        }

        // Final result, removing two extra counts of the bottom-right fruit
        return fruits[n - 1][n - 2] + fruits[n - 2][n - 1] + fruits[n - 1][n - 1];
    }
}
