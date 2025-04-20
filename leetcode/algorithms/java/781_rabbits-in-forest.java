// Source: https://leetcode.com/rabbits-in-forest/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-19
// At the time of submission:
//   Runtime 3 ms Beats 82.48%
//   Memory 42.93 MB Beats 7.12%

/****************************************
* 
* There is a forest with an unknown number of rabbits.
* _ We asked n rabbits "How many (other) rabbits have the same color as you?"
* _ and collected the answers in an integer array `answers`
* _ where `answers[i]` is the answer of the `i^th` rabbit.
* Given the array `answers`, return the minimum number of rabbits that could be in the forest.
*
* Example 1:
* Input: answers = [1,1,2]
* Output: 5
* Explanation:
* The two rabbits that answered "1" could both be the same color, say red.
* The rabbit that answered "2" can't be red or the answers would be inconsistent.
* Say the rabbit that answered "2" was blue.
* Then there should be 2 other blue rabbits in the forest that didn't answer into the array.
* The smallest possible number of rabbits in the forest is therefore 5: 3 that answered plus 2 that didn't.
*
* Example 2:
* Input: answers = [10,10,10]
* Output: 11
*
* Constraints:
* • 1 <= answers.length <= 1000
* • 0 <= answers[i] < 1000
* 
****************************************/

import java.util.HashMap;
import java.util.Map;

class Solution {
    // Each rabbit that answers x implies a group of (x + 1) same-colored rabbits.
    // We count how many such rabbits gave the same answer, then calculate how
    // many full (x + 1)-sized groups are needed to fit them all.
    // Time: O(n) for counting and grouping, where n is length of answers[]
    // Space: O(n) for the frequency map.
    public int numRabbits(int[] answers) {
        Map<Integer, Integer> countMap = new HashMap<>();
        int totalRabbits = 0;

        for (int answer : answers) {
            countMap.put(answer, countMap.getOrDefault(answer, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            int answer = entry.getKey();
            int freq = entry.getValue();
            int groupSize = answer + 1;

            // Divide freq into groups of size `groupSize`, rounding up
            int numGroups = (freq + groupSize - 1) / groupSize;

            totalRabbits += numGroups * groupSize;
        }

        return totalRabbits;
    }
}
