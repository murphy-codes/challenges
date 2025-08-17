// Source: https://leetcode.com/problems/new-21-game/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-17
// At the time of submission:
//   Runtime 4 ms Beats 96.10%
//   Memory 44.00 MB Beats 57.56%

/****************************************
* 
* Alice plays the following game, loosely based on the card game "21".
*
* Alice starts with 0 points and draws numbers while she has less than `k` points.
* _ During each draw, she gains an integer number of points randomly from the
* _ range `[1, maxPts]`, where `maxPts` is an integer. Each draw is independent
* _ and the outcomes have equal probabilities.
* Alice stops drawing numbers when she gets `k` or more points.
* Return the probability that Alice has `n` or fewer points.
* Answers within `10^-5` of the actual answer are considered accepted.
*
* Example 1:
* Input: n = 10, k = 1, maxPts = 10
* Output: 1.00000
* Explanation: Alice gets a single card, then stops.
*
* Example 2:
* Input: n = 6, k = 1, maxPts = 10
* Output: 0.60000
* Explanation: Alice gets a single card, then stops.
* In 6 out of 10 possibilities, she is at or below 6 points.
*
* Example 3:
* Input: n = 21, k = 17, maxPts = 10
* Output: 0.73278
*
* Constraints:
* • 0 <= k <= n <= 10^4
* • 1 <= maxPts <= 10^4
* 
****************************************/

class Solution {
    // We use DP where dp[i] = probability of reaching score i. Alice only draws
    // while her score < k. To compute efficiently, we keep a sliding window sum
    // of the last maxPts dp values. For i < k, add dp[i] to the window; for i >= k,
    // add dp[i] to the final result. This runs in O(n) time and O(n) space.
    public double new21Game(int n, int k, int maxPts) {
        if (k == 0 || n >= k - 1 + maxPts) return 1.0;
        
        double[] dp = new double[n + 1];
        dp[0] = 1.0;
        double windowSum = 1.0, result = 0.0;
        
        for (int i = 1; i <= n; i++) {
            dp[i] = windowSum / maxPts;
            
            if (i < k) {
                windowSum += dp[i]; // can still draw more
            } else {
                result += dp[i]; // game ends if score >= k
            }
            
            if (i - maxPts >= 0) {
                windowSum -= dp[i - maxPts]; // slide window
            }
        }
        
        return result;
    }
}
