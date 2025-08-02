// Source: https://leetcode.com/problems/rearranging-fruits/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-02
// At the time of submission:
//   Runtime 65 ms Beats 38.03%
//   Memory 66.56 MB Beats 40.84%

/****************************************
* 
* You have two fruit baskets containing `n` fruits each. You are given two
* _ 0-indexed integer arrays `basket1` and `basket2` representing the cost of fruit
* _ in each basket. You want to make both baskets equal. To do so, you can use the
* _ following operation as many times as you want:
* • Chose two indices `i` and `j`, and swap the `i_th` fruit of basket1 with the
* __ `j_th` fruit of `basket2`.
* • The cost of the swap is `min(basket1[i],basket2[j])`.
* Two baskets are considered equal if sorting them according to the fruit cost
* _ makes them exactly the same baskets.
* Return the minimum cost to make both the baskets equal or `-1` if impossible.
*
* Example 1:
* Input: basket1 = [4,2,2,2], basket2 = [1,4,1,2]
* Output: 1
* Explanation: Swap index 1 of basket1 with index 0 of basket2, which has cost 1.
* Now basket1 = [4,1,2,2] and basket2 = [2,4,1,2].
* Rearranging both the arrays makes them equal.
*
* Example 2:
* Input: basket1 = [2,3,4,1], basket2 = [3,2,5,1]
* Output: -1
* Explanation: It can be shown that it is impossible to make both the baskets equal.
*
* Constraints:
* • basket1.length == basket2.length
* • 1 <= basket1.length <= 10^5
* • 1 <= basket1[i],basket2[i] <= 10^9
* 
****************************************/

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;

class Solution {
    // Efficient solution that calculates the minimum cost to equalize two baskets.
    // Uses frequency maps to find surplus fruits, and greedy logic to minimize cost.
    // Either directly swaps or uses the global minimum fruit twice as proxy swap.
    // Time complexity: O(n log n) due to sorting of surplus items.
    // Space complexity: O(n) for frequency and surplus lists.
    public long minCost(int[] basket1, int[] basket2) {
        Map<Integer, Integer> freq = new HashMap<>();
        int n = basket1.length;
        int minVal = Integer.MAX_VALUE;

        // Count total frequency and track global min value
        for (int i = 0; i < n; i++) {
            freq.put(basket1[i], freq.getOrDefault(basket1[i], 0) + 1);
            freq.put(basket2[i], freq.getOrDefault(basket2[i], 0) + 1);
            minVal = Math.min(minVal, Math.min(basket1[i], basket2[i]));
        }

        // Check feasibility
        for (int val : freq.values()) {
            if (val % 2 != 0) return -1;
        }

        // Track surplus items to swap
        Map<Integer, Integer> count1 = new HashMap<>();
        for (int val : basket1) count1.put(val, count1.getOrDefault(val, 0) + 1);
        for (int val : basket2) count1.put(val, count1.getOrDefault(val, 0) - 1);

        ArrayList<Integer> excess = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : count1.entrySet()) {
            int val = entry.getKey();
            int diff = entry.getValue();
            for (int i = 0; i < Math.abs(diff) / 2; i++) {
                excess.add(val);
            }
        }

        Collections.sort(excess);
        long cost = 0;
        for (int i = 0; i < excess.size() / 2; i++) {
            cost += Math.min(excess.get(i), 2 * minVal);
        }

        return cost;
    }
}

}