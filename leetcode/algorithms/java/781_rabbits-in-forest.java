// Source: https://leetcode.com/problems/rabbits-in-forest/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-19
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 42.46 MB Beats 41.97%

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

class Solution {
    // Each rabbit says how many *others* have their color. So x → group of x+1.
    // We use an array to track how many rabbits gave the same answer (x).
    // Each new group adds x+1 to the total, and resets once filled.
    // Time: O(n) - single pass through the answers array.
    // Space: O(1) - fixed-size array for 0 <= x < 1000.
    public int numRabbits(int[] answers) {
        int[] count = new int[1000];  // Track number of rabbits for each answer value
        int total = 0;

        for (int answer : answers) {
            // First rabbit of this group → start a new group
            if (++count[answer] == 1)
                total += answer + 1;

            // Group is full → reset count for a potential new group
            if (count[answer] == answer + 1)
                count[answer] = 0;
        }

        return total;
    }
}
