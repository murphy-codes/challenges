// Source: https://leetcode.com/problems/minimum-cost-of-buying-candies-with-discount/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-31
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 43.82 MB Beats 98.77%

/****************************************
* 
* A shop is selling candies at a discount. For every two candies sold, the
* _ shop gives a third candy for free.
* The customer can choose any candy to take away for free as long as the
* _ cost of the chosen candy is less than or equal to the minimum cost of
* _ the two candies bought.
* • For example, if there are `4` candies with costs `1`, `2`, `3`, and `4`,
* __ and the customer buys candies with costs `2` and `3`, they can take the
* __ candy with cost `1` for free, but not the candy with cost `4`.
* Given a 0-indexed integer array `cost`, where `cost[i]` denotes the cost of
* _ the `i^th` candy, return the minimum cost of buying all the candies.
*
* Example 1:
* Input: cost = [1,2,3]
* Output: 5
* Explanation: We buy the candies with costs 2 and 3, and take the candy with cost 1 for free.
* The total cost of buying all candies is 2 + 3 = 5. This is the only way we can buy the candies.
* Note that we cannot buy candies with costs 1 and 3, and then take the candy with cost 2 for free.
* The cost of the free candy has to be less than or equal to the minimum cost of the purchased candies.
*
* Example 2:
* Input: cost = [6,5,7,9,2,2]
* Output: 23
* Explanation: The way in which we can get the minimum cost is described below:
* - Buy candies with costs 9 and 7
* - Take the candy with cost 6 for free
* - We buy candies with costs 5 and 2
* - Take the last remaining candy with cost 2 for free
* Hence, the minimum cost to buy all candies is 9 + 7 + 5 + 2 = 23.
*
* Example 3:
* Input: cost = [5,5]
* Output: 10
* Explanation: Since there are only 2 candies, we buy both of them. There is not a third candy we can take for free.
* Hence, the minimum cost to buy all candies is 5 + 5 = 10.
*
* Constraints:
* • `1 <= cost.length <= 100`
* • `1 <= cost[i] <= 100`
* 
****************************************/

class Solution {
    // Use counting sort since candy costs are bounded to [1,100].
    // Process prices from high to low and apply the buy-2-get-1-free rule.
    // Batch identical-cost candies to avoid explicit sorting or simulation.
    // Time: O(n + 100) = O(n), Space: O(100) = O(1).
    public int minimumCost(int[] costs) {
        int[] frq = new int[101];
        for (int c : costs) {
            frq[c]++;
        }
        int paid = 0;
        int total = 0;
        for (int price = 100; price > 0; price--) {
            if (frq[price] > 2 - paid) {
                frq[price] -= (3 - paid);
                // Finish current buy-buy-free group
                total += (2 - paid) * price;
                // Process complete groups of 3 identical candies
                total += 2 * price * (frq[price] / 3);
                paid = frq[price] % 3;
                // Remaining paid candies
                total += price * paid;
            } else {
                paid += frq[price];
                total += price * frq[price];
            }
        }
        return total;
    }
}