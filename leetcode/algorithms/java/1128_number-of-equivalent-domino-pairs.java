// Source: https://leetcode.com/problems/number-of-equivalent-domino-pairs/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-03
// At the time of submission:
//   Runtime 10 ms Beats 62.60%
//   Memory 53.05 MB Beats 51.15%

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

import java.util.HashMap;
import java.util.Map;

class Solution {
    // Normalize each domino by sorting the two numbers,
    // and map it to a unique key in range [11, 99].
    // Count frequency of each key and for each duplicate,
    // add to result the number of existing pairs formed.
    // Time: O(n), Space: O(1) due to limited unique keys.
    public int numEquivDominoPairs(int[][] dominoes) {
        Map<Integer, Integer> countMap = new HashMap<>();
        int result = 0;

        for (int[] domino : dominoes) {
            int a = domino[0];
            int b = domino[1];
            int key = a < b ? a * 10 + b : b * 10 + a;
            int currentCount = countMap.getOrDefault(key, 0);
            result += currentCount; // Add current count to result
            countMap.put(key, currentCount + 1);
        }

        return result;
    }
}
