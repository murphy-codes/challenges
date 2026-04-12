// Source: https://leetcode.com/problems/minimum-distance-to-type-a-word-using-two-fingers/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-11
// At the time of submission:
//   Runtime 7 ms Beats 86.92%
//   Memory 43.14 MB Beats 87.69%

/****************************************
* 
* [Image: https://assets.leetcode.com/uploads/2020/01/02/leetcode_keyboard.png]
* You have a keyboard layout as shown above in the X-Y plane, where each
* _ English uppercase letter is located at some coordinate.
* • For example, the letter `'A'` is located at coordinate `(0, 0)`, the
* __ letter `'B'` is located at coordinate `(0, 1)`, the letter `'P'` is
* __ located at coordinate `(2, 3)` and the letter `'Z'` is located at
* __ coordinate `(4, 1)`.
* Given the string `word`, return the minimum total distance to type such
* _ string using only two fingers.
* The distance between coordinates `(x_1, y_1)` and `(x_2, y_2) is
* _ `|x_1 - x_2| + |y_1 - y_2|`.
* Note that the initial positions of your two fingers are considered free
* _ so do not count towards your total distance, also your two fingers do
* _ not have to start at the first letter or the first two letters.
*
* Example 1:
* Input: word = "CAKE"
* Output: 3
* Explanation: Using two fingers, one optimal way to type "CAKE" is:
* Finger 1 on letter 'C' -> cost = 0
* Finger 1 on letter 'A' -> cost = Distance from letter 'C' to letter 'A' = 2
* Finger 2 on letter 'K' -> cost = 0
* Finger 2 on letter 'E' -> cost = Distance from letter 'K' to letter 'E' = 1
* Total distance = 3
*
* Example 2:
* Input: word = "HAPPY"
* Output: 6
* Explanation: Using two fingers, one optimal way to type "HAPPY" is:
* Finger 1 on letter 'H' -> cost = 0
* Finger 1 on letter 'A' -> cost = Distance from letter 'H' to letter 'A' = 2
* Finger 2 on letter 'P' -> cost = 0
* Finger 2 on letter 'P' -> cost = Distance from letter 'P' to letter 'P' = 0
* Finger 1 on letter 'Y' -> cost = Distance from letter 'A' to letter 'Y' = 4
* Total distance = 6
*
* Constraints:
* • `2 <= word.length <= 300`
* • `word` consists of uppercase English letters.
* 
****************************************/

class Solution {
    // We use dynamic programming where dp[j] represents the minimum cost
    // when one finger is at the previous character and the other is at j.
    // At each step, we either move the same finger or switch fingers,
    // updating costs accordingly using Manhattan distance on the keyboard.
    // This reduces the state space to O(26) per step, yielding O(26 * n)
    // time and O(26) space.
    private int dist(int a, int b) {
        if (a == -1) return 0; // free initial placement
        int x1 = a / 6, y1 = a % 6;
        int x2 = b / 6, y2 = b % 6;
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public int minimumDistance(String word) {

        int n = word.length();

        // dp[j] = min cost where j is position of the "other" finger
        int[] dp = new int[26];

        // initialize as 0 (both fingers unused)
        for (int i = 0; i < 26; i++) {
            dp[i] = 0;
        }

        int prev = word.charAt(0) - 'A';

        for (int i = 1; i < n; i++) {
            int curr = word.charAt(i) - 'A';

            int[] newDp = new int[26];

            // initialize large
            for (int j = 0; j < 26; j++) {
                newDp[j] = Integer.MAX_VALUE;
            }

            for (int other = 0; other < 26; other++) {
                if (dp[other] == Integer.MAX_VALUE) continue;

                // Option 1: same finger types curr
                int cost1 = dp[other] + dist(prev, curr);
                newDp[other] = Math.min(newDp[other], cost1);

                // Option 2: switch fingers
                int cost2 = dp[other] + dist(other, curr);
                newDp[prev] = Math.min(newDp[prev], cost2);
            }

            dp = newDp;
            prev = curr;
        }

        int ans = Integer.MAX_VALUE;
        for (int val : dp) {
            ans = Math.min(ans, val);
        }

        return ans;
    }
}