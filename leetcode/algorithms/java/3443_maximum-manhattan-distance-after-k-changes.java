// Source: https://leetcode.com/problems/maximum-manhattan-distance-after-k-changes/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-19
// At the time of submission:
//   Runtime 55 ms Beats 86.36%
//   Memory 45.92 MB Beats 31.82%

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

public class Solution {
    // Traverse the string step-by-step, tracking net vertical and horizontal
    // movement. At each step, compute the Manhattan distance and consider
    // up to k changes, each adding 2 units of distance. Cap the result by
    // the number of steps taken so far (i + 1). This ensures we don’t exceed
    // the number of actual moves. Time: O(n), Space: O(1).
    public int maxDistance(String s, int k) {
        int vertical = 0;   // North-South axis
        int horizontal = 0; // East-West axis
        int maxDist = 0;

        for (int i = 0; i < s.length(); i++) {
            char move = s.charAt(i);
            switch (move) {
                case 'N': vertical++; break;
                case 'S': vertical--; break;
                case 'E': horizontal++; break;
                case 'W': horizontal--; break;
            }

            // Raw Manhattan distance from (0,0)
            int current = Math.abs(vertical) + Math.abs(horizontal);

            // Add up to k modifications (each can increase distance by 2)
            int boosted = current + 2 * k;

            // Distance can't exceed the number of steps taken
            maxDist = Math.max(maxDist, Math.min(boosted, i + 1));
        }

        return maxDist;
    }
}
