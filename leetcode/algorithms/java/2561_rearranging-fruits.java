// Source: https://leetcode.com/problems/rearranging-fruits/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-02
// At the time of submission:
//   Runtime 15 ms Beats 100.00%
//   Memory 57.68 MB Beats 100.00%

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    // Sort both baskets and identify unmatched items and their frequencies.
    // For each value, record how many must be swapped to balance the baskets.
    // Calculate the minimal swap cost using either direct value or double min.
    // Time Complexity: O(n log n) due to sorting; rest is O(n) for processing.
    // Space Complexity: O(n) for the swap list that tracks differences.
    public long minCost(int[] basket1, int[] basket2) {
        List<int[]> swapList = new ArrayList<>();
        Arrays.sort(basket1);
        Arrays.sort(basket2);

        int i = 0, j = 0;
        int totalSwapsNeeded = 0;

        // Find elements that are mismatched between baskets
        while (i < basket1.length || j < basket2.length) {
            if (i < basket1.length && j < basket2.length && basket1[i] == basket2[j]) {
                i++; j++;
                continue;
            }

            int val, count = 0;
            if ((i < basket1.length && j < basket2.length && basket1[i] < basket2[j]) || j >= basket2.length) {
                val = basket1[i];
                while (i < basket1.length && basket1[i] == val) {
                    count++;
                    i++;
                }
            } else {
                val = basket2[j];
                while (j < basket2.length && basket2[j] == val) {
                    count++;
                    j++;
                }
            }

            if (count % 2 != 0) return -1; // Odd count means impossible to pair up
            totalSwapsNeeded += count / 2;
            swapList.add(new int[]{val, count / 2});
        }

        // Calculate minimum cost using optimal strategy
        long totalCost = 0;
        int swapsDone = 0, swapCount = 0;
        int minElement = Math.min(basket1[0], basket2[0]);

        int index = 0;
        while (index < swapList.size()) {
            int[] entry = swapList.get(index);
            int val = entry[0];
            int count = entry[1];

            totalCost += Math.min(val, 2 * minElement);
            swapsDone += 2;
            swapCount++;

            if (swapsDone == totalSwapsNeeded) break;
            if (swapCount == count) {
                swapCount = 0;
                index++;
            }
        }

        return totalCost;
    }
}
