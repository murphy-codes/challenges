// Source: https://leetcode.com/problems/number-of-equivalent-domino-pairs/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-03
// At the time of submission:
//   Runtime 2 ms Beats 98.22%
//   Memory 52.80 MB Beats 64.63%

/****************************************
* 
* Given a list of `dominoes`, `dominoes[i] = [a, b]` is equivalent
* _ to `dominoes[j] = [c, d]` if and only if either (`a == c` and `b == d`),
* _ or (`a == d` and `b == c`) — that is, one domino can be rotated to be equal
* _ to another domino.
* Return the number of pairs `(i, j)` for which `0 <= i < j < dominoes.length`,
* _ and `dominoes[i]` is equivalent to `dominoes[j]`.
*
* Example 1:
* Input: dominoes = [[1,2],[2,1],[3,4],[5,6]]
* Output: 1
*
* Example 2:
* Input: dominoes = [[1,2],[1,2],[1,1],[1,2],[2,2]]
* Output: 3
*
* Constraints:
* • 1 <= dominoes.length <= 4 * 10^4
* • dominoes[i].length == 2
* • 1 <= dominoes[i][j] <= 9
* 
****************************************/

class Solution {
    // Count each domino as a pair [a][b] in a 10x10 matrix, since values range
    // from 1 to 9. Then for each unique unordered pair [i][j], compute the number
    // of equivalent pairs using n * (n - 1) / 2, summing symmetric pairs [i][j]
    // and [j][i] when i != j. This avoids extra normalization or hashing overhead.
    // Time: O(n), Space: O(1) due to fixed-size 2D array.
    public int numEquivDominoPairs(int[][] dominoes) {
        int[][] pairCounts = new int[10][10]; // Tracks counts of domino [a][b]

        // Count each domino directly into the matrix
        for (int[] domino : dominoes) {
            int a = domino[0];
            int b = domino[1];
            pairCounts[a][b]++;
        }

        int result = 0;

        // For each unique unordered domino pair [i][j]
        for (int i = 1; i <= 9; i++) {
            for (int j = i; j <= 9; j++) {
                int count = pairCounts[i][j];

                // If i != j, also add count of symmetric [j][i]
                if (i != j) {
                    count += pairCounts[j][i];
                }

                // Add number of unique pairs from count (n choose 2)
                result += count * (count - 1) / 2;
            }
        }

        return result;
    }
}
