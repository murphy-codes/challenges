// Source: https://leetcode.com/problems/water-bottles-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-01
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 41.21 MB Beats 25.81%

/****************************************
* 
* You are given two integers numBottles and numExchange.
* `numBottles` represents the number of full water bottles that you initially have.
* _ In one operation, you can perform one of the following operations:
* • Drink any number of full water bottles turning them into empty bottles.
* • Exchange `numExchange` empty bottles with one full water bottle.
* __ Then, increase `numExchange` by one.
* Note that you cannot exchange multiple batches of empty bottles for the same
* _ value of `numExchange`. For example, if `numBottles == 3` and `numExchange == 1`,
* _ you cannot exchange 3 empty water bottles for 3 full bottles.
* Return the maximum number of water bottles you can drink.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2024/01/28/exampleone1.png]
* Input: numBottles = 13, numExchange = 6
* Output: 15
* Explanation: The table above shows the number of full water bottles, empty
* _ water bottles, the value of numExchange, and the number of bottles drunk.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2024/01/28/example231.png]
* Input: numBottles = 10, numExchange = 3
* Output: 13
* Explanation: The table above shows the number of full water bottles, empty
* _ water bottles, the value of numExchange, and the number of bottles drunk.
*
* Constraints:
* • `1 <= numBottles <= 100`
* • `1 <= numExchange <= 100`
* 
****************************************/

class Solution {
    // Simulate drinking and exchanging bottles with a dynamic exchange cost.
    // Start with all initial bottles drunk, tracking empties. While enough
    // empties exist to trade, exchange them for one full bottle, increment
    // the exchange cost, and drink that bottle. Loop ends when exchanges are
    // no longer possible. Time complexity is O(n) & space complexity is O(1).
    public int maxBottlesDrunk(int numBottles, int numExchange) {
        int drank = numBottles, empties = numBottles;
        while (empties >= numExchange) {
            empties -= numExchange-1;
            numExchange ++;
            drank ++;
        }
        return drank;
    }
}