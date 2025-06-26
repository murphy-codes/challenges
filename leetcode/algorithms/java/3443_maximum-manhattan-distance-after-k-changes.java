// Source: https://leetcode.com/problems/maximum-manhattan-distance-after-k-changes/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-19
// At the time of submission:
//   Runtime 23 ms Beats 99.98%
//   Memory 46.24 MB Beats 8.11%

/****************************************
* 
* You are given a string `s` consisting of the characters `'N'`, `'S'`, `'E'`,
* _ and `'W'`, where `s[i]` indicates movements in an infinite grid:
* • `'N'` : Move north by 1 unit.
* • `'S'` : Move south by 1 unit.
* • `'E'` : Move east by 1 unit.
* • `'W'` : Move west by 1 unit.
* Initially, you are at the origin `(0, 0)`.
* _ You can change at most `k` characters to any of the four directions.
* Find the maximum Manhattan distance from the origin that can be achieved
* _ at any time while performing the movements in order.
* The Manhattan Distance between two cells
* _ `(x_i, y_i)` and `(x_j, y_j`) is `|x_i - x_j| + |y_i - y_j|`.
*
* Example 1:
* Input: s = "NWSE", k = 1
* Output: 3
* Explanation:
* Change `s[2]` from `'S'` to `'N'`. The string `s` becomes `"NWNE"`.
* Movement	Position (x, y)	Manhattan Distance	Maximum
* s[0] == 'N'	(0, 1)	0 + 1 = 1	1
* s[1] == 'W'	(-1, 1)	1 + 1 = 2	2
* s[2] == 'N'	(-1, 2)	1 + 2 = 3	3
* s[3] == 'E'	(0, 2)	0 + 2 = 2	3
* The maximum Manhattan distance from the origin that can be achieved is 3. Hence, 3 is the output.
*
* Example 2:
* Input: s = "NSWWEW", k = 3
* Output: 6
* Explanation:
* Change `s[1]` from `'S'` to `'N'`, and `s[4]` from `'E'` to `'W'`. The string `s` becomes `"NNWWWW"`.
* The maximum Manhattan distance from the origin that can be achieved is 6. Hence, 6 is the output.
*
* Constraints:
* • 1 <= s.length <= 10^5
* • 0 <= k <= s.length
* • `s` consists of only `'N'`, `'S'`, `'E'`, and `'W'`.
* 
****************************************/

class Solution {
    // Tracks position as we follow the movement string step-by-step.
    // Computes Manhattan distance from origin at each step, allowing up
    // to k direction changes (each worth up to +2 distance). Caps result
    // at current step count to ensure we don’t exceed actual moves taken.
    // Time: O(n), Space: O(1) — only fixed-size arrays used.
    public int maxDistance(String s, int k) {
        // Predefined direction vectors: [dx, dy] for 'N', 'S', 'E', 'W'
        int[][] direction = new int[26][2];
        direction['N' - 'A'] = new int[]{0, 1};
        direction['S' - 'A'] = new int[]{0, -1};
        direction['E' - 'A'] = new int[]{1, 0};
        direction['W' - 'A'] = new int[]{-1, 0};

        int result = 0;
        int[] location = new int[2]; // [x, y]
        int steps = 0;

        for (char c : s.toCharArray()) {
            int[] move = direction[c - 'A'];
            location[0] += move[0];
            location[1] += move[1];

            int manhattan = Math.abs(location[0]) + Math.abs(location[1]);
            int boosted = manhattan + (k << 1); // Up to k moves can each add 2 distance
            result = Math.max(result, Math.min(boosted, steps + 1));
            steps++;
        }

        return result;
    }
}
