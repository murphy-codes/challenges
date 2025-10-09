// Source: https://leetcode.com/problems/successful-pairs-of-spells-and-potions/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-07
// At the time of submission:
//   Runtime 5 ms Beats 100.00%
//   Memory 65.47 MB Beats 29.13%

/****************************************
* 
* You are given two positive integer arrays `spells` and `potions`, of length
* _ `n` and `m` respectively, where `spells[i]` represents the strength of the
* _ `i^th` spell and `potions[j]` represents the strength of the `j^th` potion.
* You are also given an integer `success`. A spell and potion pair is considered
* successful if the product of their strengths is at least `success`.
* Return an integer array `pairs` of length `n` where `pairs[i]` is the number
* _ of potions that will form a successful pair with the `i^th` spell.
*
* Example 1:
* Input: spells = [5,1,3], potions = [1,2,3,4,5], success = 7
* Output: [4,0,3]
* Explanation:
* - 0th spell: 5 * [1,2,3,4,5] = [5,10,15,20,25]. 4 pairs are successful.
* - 1st spell: 1 * [1,2,3,4,5] = [1,2,3,4,5]. 0 pairs are successful.
* - 2nd spell: 3 * [1,2,3,4,5] = [3,6,9,12,15]. 3 pairs are successful.
* Thus, [4,0,3] is returned.
*
* Example 2:
* Input: spells = [3,1,2], potions = [8,5,8], success = 16
* Output: [2,0,2]
* Explanation:
* - 0th spell: 3 * [8,5,8] = [24,15,24]. 2 pairs are successful.
* - 1st spell: 1 * [8,5,8] = [8,5,8]. 0 pairs are successful.
* - 2nd spell: 2 * [8,5,8] = [16,10,16]. 2 pairs are successful.
* Thus, [2,0,2] is returned.
*
* Constraints:
* • `n == spells.length`
* • `m == potions.length`
* • `1 <= n, m <= 10^5`
* • `1 <= spells[i], potions[i] <= 10^5`
* • `1 <= success <= 10^10`
* 
****************************************/

class Solution {
    // Uses counting + prefix-sum instead of sorting & binary search.
    // Builds freq[i] = number of potions with strength >= i.
    // For each spell, computes the minimum required potion value
    // via ceil(success / spell) and looks it up in O(1) time.
    // Time: O(n + M), Space: O(M), where M = max potion value.
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        int m = spells.length;
        int n = potions.length;

        // Step 1: Find maximum potion strength to size our frequency map
        int maxPotion = 0;
        for (int p : potions) {
            maxPotion = Math.max(maxPotion, p);
        }

        // Step 2: Build frequency map for potion strengths
        int[] freq = new int[maxPotion + 1];
        for (int p : potions) {
            freq[p]++;
        }

        // Step 3: Convert frequency map to suffix sum:
        // freq[i] = number of potions with strength >= i
        int cumulative = 0;
        for (int i = maxPotion; i >= 0; i--) {
            cumulative += freq[i];
            freq[i] = cumulative;
        }

        // Step 4: For each spell, compute minimum potion needed and look up result
        int[] result = new int[m];
        for (int i = 0; i < m; i++) {
            int spellPower = spells[i];
            long requiredPotion = (success + spellPower - 1) / spellPower; // ceil(success / spell)

            if (requiredPotion <= maxPotion) {
                result[i] = freq[(int) requiredPotion];
            }
            // else result[i] stays 0 (no potion strong enough)
        }

        return result;
    }
}