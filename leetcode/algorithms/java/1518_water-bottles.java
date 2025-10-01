// Source: https://leetcode.com/problems/water-bottles/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-30
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 39.82 MB Beats 99.62%

/****************************************
* 
* There are `numBottles` water bottles that are initially full of water. You
* _ can exchange `numExchange` empty water bottles from the market with one full
* _ water bottle.
* The operation of drinking a full water bottle turns it into an empty bottle.
* Given the two integers `numBottles` and `numExchange`, return the maximum
* _ number of water bottles you can drink.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2020/07/01/sample_1_1875.png]
* Input: numBottles = 9, numExchange = 3
* Output: 13
* Explanation: You can exchange 3 empty bottles to get 1 full water bottle.
* Number of water bottles you can drink: 9 + 3 + 1 = 13.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2020/07/01/sample_2_1875.png]
* Input: numBottles = 15, numExchange = 4
* Output: 19
* Explanation: You can exchange 4 empty bottles to get 1 full water bottle.
* Number of water bottles you can drink: 15 + 3 + 1 = 19.
*
* Constraints:
* • `1 <= numBottles <= 100`
* • `2 <= numExchange <= 100`
* 
****************************************/

class Solution {
    // Simulate the drinking/exchange process. Start by drinking all bottles
    // and counting them as empties. While enough empties exist for an
    // exchange, trade them for new bottles, drink those, and update empties.
    // Loop runs until no more exchanges are possible. Time complexity is O(n)
    // in the number of exchanges (at most numBottles), and space is O(1).
    public int numWaterBottles(int numBottles, int numExchange) {
        int drank = numBottles, empties = numBottles;
        while (empties >= numExchange) {
            numBottles = empties / numExchange;
            drank += numBottles;
            empties = (empties % numExchange) + numBottles;
        }
        return drank;
    }
}