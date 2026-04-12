// Source: https://leetcode.com/problems/minimum-distance-to-type-a-word-using-two-fingers/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-11
// At the time of submission:
//   Runtime 4 ms Beats 100.00%
//   Memory 42.66 MB Beats 93.08%

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
    // We use a compressed DP where dp[x] represents the minimum cost when
    // one finger is at the previous character and the other is at letter x
    // (or free at index 26). For each step, we add movement cost for using
    // the same finger and compute the optimal switch cost. This avoids extra
    // arrays and runs in O(26 * n) time with O(1) space.

    public int minimumDistance(String word) {
        int n = word.length();
        if (n < 3) return 0;

        char[] chars = word.toCharArray();

        // dp[0..25] = other finger at 'A'..'Z'
        // dp[26] = "free" finger (not placed yet)
        int[] dp = new int[27];

        for (int i = 0; i < 27; i++) {
            dp[i] = Integer.MAX_VALUE;
        }

        // Initial states
        dp[26] = dist(chars[1], chars[0]); // same finger used twice
        dp[chars[0] - 'A'] = 0;            // other finger unused

        for (int i = 2; i < n; i++) {

            int moveCost = dist(chars[i], chars[i - 1]);
            int bestSwitchCost = dp[26];

            // Update all states
            for (int other = 0; other < 27; other++) {

                if (dp[other] < bestSwitchCost) {
                    int switchCost = dp[other] +
                        dist(chars[i], (char) (other + 'A'));
                    bestSwitchCost = Math.min(bestSwitchCost, switchCost);
                }

                if (dp[other] < Integer.MAX_VALUE) {
                    dp[other] += moveCost;
                }
            }

            // Update state where we switch fingers
            int prevChar = chars[i - 1] - 'A';
            dp[prevChar] = Math.min(dp[prevChar], bestSwitchCost);
        }

        int result = Integer.MAX_VALUE;
        for (int val : dp) {
            result = Math.min(result, val);
        }

        return result;
    }

    private int dist(char a, char b) {
        int i = a - 'A', j = b - 'A';
        return Math.abs(i / 6 - j / 6) + Math.abs(i % 6 - j % 6);
    }
}