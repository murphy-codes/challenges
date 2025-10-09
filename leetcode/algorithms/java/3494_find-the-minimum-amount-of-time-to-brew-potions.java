// Source: https://leetcode.com/problems/find-the-minimum-amount-of-time-to-brew-potions/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-09
// At the time of submission:
//   Runtime 139 ms Beats 50.00%
//   Memory 44.98 MB Beats 33.33%

/****************************************
* 
* You are given two integer arrays, skill and mana, of length n and m, respectively.
* In a laboratory, `n` wizards must brew `m` potions in order. Each potion has a
* _ mana capacity `mana[j]` and must pass through all the wizards sequentially to
* _ be brewed properly. The time taken by the `ith` wizard on the `jth` potion is
* _ `time_ij = skill[i] * mana[j]`.
* Since the brewing process is delicate, a potion must be passed to the next
* _ wizard immediately after the current wizard completes their work. This means
* _ the timing must be synchronized so that each wizard begins working on a
* _ potion exactly when it arrives. ​
* Return the minimum amount of time required for the potions to be brewed properly.
*
* Example 1:
* Input: skill = [1,5,2,4], mana = [5,1,4,2]
* Output: 110
* Explanation:
* Shortened Headers Key:
* — PN = Potion Number
* — ST = Start time
* — W0db = Wizard 0 done by
* — W1db = Wizard 1 done by
* — W2db = Wizard 2 done by
* — W3db = Wizard 3 done by
* Table:
* PN	ST	W0db	W1db	W2db	W3db
* 0	0	5	30	40	60
* 1	52	53	58	60	64
* 2	54	58	78	86	102
* 3	86	88	98	102	110
* As an example for why wizard 0 cannot start working on the 1st potion before
* _ time `t = 52`, consider the case where the wizards started preparing the
* _ 1st potion at time `t = 50`. At time `t = 58`, wizard 2 is done with the
* _ 1st potion, but wizard 3 will still be working on the 0th potion till
* _ time `t = 60`.
*
* Example 2:
* Input: skill = [1,1,1], mana = [1,1,1]
* Output: 5
* Explanation:
* Preparation of the 0th potion begins at time t = 0, and is completed by time t = 3.
* Preparation of the 1st potion begins at time t = 1, and is completed by time t = 4.
* Preparation of the 2nd potion begins at time t = 2, and is completed by time t = 5.
*
* Example 3:
* Input: skill = [1,2,3,4], mana = [1,2]
* Output: 21
*
* Constraints:
* • `n == skill.length`
* • `m == mana.length`
* • `1 <= n, m <= 5000`
* • `1 <= mana[i], skill[i] <= 5000`
* 
****************************************/

class Solution {
    // Simulates sequential potion brewing with n wizards and m potions. Each potion
    // passes through all wizards in order, with no waiting between them. Tracks each
    // wizard's earliest free time (f[i]) and updates it per potion using two passes:
    // a forward pass for synchronization and a backward pass to realign start times.
    // Time complexity: O(n * m); Space complexity: O(n).
    public long minTime(int[] skill, int[] mana) {
        int n = skill.length;
        int m = mana.length;
        long[] f = new long[n]; // earliest free times per wizard (initialized as 0s)

        for (int j = 0; j < m; j++) {
            long x = mana[j];      // current potion mana (use long)
            long now = f[0];       // starting lower bound

            // left-to-right: compute earliest "now" that respects pipeline + availability
            for (int i = 1; i < n; i++) {
                now = Math.max(now + (long) skill[i-1] * x, f[i]);
            }

            // finish time of this potion on last wizard
            long finish = now + (long) skill[n-1] * x;
            f[n-1] = finish;

            // right-to-left: set earlier wizards' free times consistent with no-wait constraint
            for (int i = n - 2; i >= 0; i--) {
                f[i] = f[i+1] - (long) skill[i+1] * x;
            }
        }

        // answer is time when last wizard finishes last potion
        return f[n-1];
    }
}
