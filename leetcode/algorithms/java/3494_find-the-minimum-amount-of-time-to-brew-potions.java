// Source: https://leetcode.com/problems/find-the-minimum-amount-of-time-to-brew-potions/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-09
// At the time of submission:
//   Runtime 79 ms Beats 100.00%
//   Memory 44.90 MB Beats 48.61%

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
    // Uses prefix sums and dynamic propagation of potion times to compute the
    // minimum total brewing time. For each potion, the algorithm aligns wizard
    // availability using accumulated skill*mana relationships, avoiding full
    // simulation. Runs in O(n * m) time and O(n) space overall.
    public long minTime(int[] skill, int[] mana) {
        int n = skill.length, m = mana.length;
        long[] prefixSkill = new long[n + 1]; // prefix sum of skill levels

        // Build prefix sum: prefixSkill[i] = total skill up to wizard i-1
        for (int i = 0; i < n; i++) {
            prefixSkill[i + 1] = prefixSkill[i] + skill[i];
        }

        long prevTime = 0;  // total brewing time up to previous potion
        long currTime = 0;  // current brewing time being computed

        // Process each potion in order (starting from 2nd one)
        for (int j = 1; j < m; j++) {
            currTime = 0;
            for (int i = 0; i < n; i++) {
                // Update the time to align with wizard availability and potion sequence
                currTime = Math.max(
                    currTime,
                    prevTime + mana[j - 1] * prefixSkill[i + 1] - mana[j] * prefixSkill[i]
                );
            }
            prevTime = currTime;
        }

        // Final total time after all potions are brewed
        return prevTime + mana[m - 1] * prefixSkill[n];
    }
}
