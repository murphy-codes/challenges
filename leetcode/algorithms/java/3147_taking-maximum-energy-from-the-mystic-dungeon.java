// Source: https://leetcode.com/problems/taking-maximum-energy-from-the-mystic-dungeon/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-10
// At the time of submission:
//   Runtime 3 ms Beats 55.28%
//   Memory 63.28 MB Beats 19.88%

/****************************************
* 
* In a mystic dungeon, `n` magicians are standing in a line. Each magician has an
* _ attribute that gives you energy. Some magicians can give you negative energy,
* _ which means taking energy from you.
* You have been cursed in such a way that after absorbing energy from magician
* _ `i`, you will be instantly transported to magician `(i + k)`. This process
* _ will be repeated until you reach the magician where `(i + k)` does not exist.
* In other words, you will choose a starting point and then teleport with `k`
* _ jumps until you reach the end of the magicians' sequence, absorbing all the
* _ energy during the journey.
* You are given an array `energy` and an integer `k`. Return the maximum possible
* _ energy you can gain.
* Note that when you are reach a magician, you must take energy from them,
* _ whether it is negative or positive energy.
*
* Example 1:
* Input: energy = [5,2,-10,-5,1], k = 3
* Output: 3
* Explanation: We can gain a total energy of 3 by starting from magician 1 absorbing 2 + 1 = 3.
*
* Example 2:
* Input: energy = [-2,-3,-1], k = 2
* Output: -1
* Explanation: We can gain a total energy of -1 by starting from magician 2.
*
* Constraints:
* • `1 <= energy.length <= 10^5`
* • `-1000 <= energy[i] <= 1000`
* • `1 <= k <= energy.length - 1`
* 
****************************************/

class Solution {
    // Dynamic Programming approach.
    // dp[i] = energy[i] + dp[i + k], representing total energy gained starting
    // at index i and jumping by k steps. We compute this backwards to reuse
    // future results efficiently. Runs in O(n) time and O(1) extra space.
    public int maximumEnergy(int[] energy, int k) {
        int n = energy.length;
        int[] dp = new int[n];

        // Compute dp[i] backwards
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = energy[i];
            if (i + k < n) {
                dp[i] += dp[i + k];
            }
        }

        // Find maximum energy achievable from any starting index
        int maxEnergy = Integer.MIN_VALUE;
        for (int val : dp) {
            maxEnergy = Math.max(maxEnergy, val);
        }

        return maxEnergy;
    }
}
