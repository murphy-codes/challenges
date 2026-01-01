// Source: https://leetcode.com/problems/maximize-happiness-of-selected-children/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-31
// At the time of submission:
//   Runtime 43 ms Beats 45.79%
//   Memory 109.17 MB Beats 68.56%

/****************************************
* 
* You are given an array `happiness` of length `n`, and a positive integer `k`.
* There are `n` children standing in a queue, where the `i^th` child has
* _ happiness value `happiness[i]`. You want to select `k` children from these
* _ `n` children in `k` turns.
* In each turn, when you select a child, the happiness value of all the children
* _ that have not been selected till now decreases by `1`. Note that the happiness
* _ value cannot become negative and gets decremented only if it is positive.
* Return the maximum sum of the happiness values of the selected children you can
* _ achieve by selecting `k` children.
*
* Example 1:
* Input: happiness = [1,2,3], k = 2
* Output: 4
* Explanation: We can pick 2 children in the following way:
* - Pick the child with the happiness value == 3. The happiness value of the remaining children becomes [0,1].
* - Pick the child with the happiness value == 1. The happiness value of the remaining child becomes [0]. Note that the happiness value cannot become less than 0.
* The sum of the happiness values of the selected children is 3 + 1 = 4.
*
* Example 2:
* Input: happiness = [1,1,1,1], k = 2
* Output: 1
* Explanation: We can pick 2 children in the following way:
* - Pick any child with the happiness value == 1. The happiness value of the remaining children becomes [0,0,0].
* - Pick the child with the happiness value == 0. The happiness value of the remaining child becomes [0,0].
* The sum of the happiness values of the selected children is 1 + 0 = 1.
*
* Example 3:
* Input: happiness = [2,3,4,5], k = 1
* Output: 5
* Explanation: We can pick 1 child in the following way:
* - Pick the child with the happiness value == 5. The happiness value of the remaining children becomes [1,2,3].
* The sum of the happiness values of the selected children is 5.
*
* Constraints:
* • `1 <= n == happiness.length <= 2 * 10^5`
* • `1 <= happiness[i] <= 10^8`
* • `1 <= k <= n`
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Sort happiness descending and pick k highest values. Each pick reduces all
    // remaining values by 1, so the i-th selected child effectively loses (i-1).
    // Add max(happiness[i] - (i-1), 0) for each pick to avoid negative values.
    // Sorting dominates runtime: O(n log n). Space: O(1) extra beyond input.
    public long maximumHappinessSum(int[] happiness, int k) {
        Arrays.sort(happiness);

        long total = 0;
        int decrease = 0;

        for (int i = happiness.length - 1; i >= 0 && k > 0; i--, k--) {
            int value = happiness[i] - decrease;
            if (value > 0) {
                total += value;
            }
            decrease++;  // every subsequent pick suffers one more global decay
        }

        return total;
    }
}
