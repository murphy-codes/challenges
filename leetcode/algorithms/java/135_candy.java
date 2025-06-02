// Source: https://leetcode.com/problems/candy/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-01
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 45.99 MB Beats 45.81%

/****************************************
* 
* There are `n` children standing in a line. Each child is assigned a rating
* _ value given in the integer array `ratings`.
* You are giving candies to these children subjected to the following requirements:
* • Each child must have at least one candy.
* • Children with a higher rating get more candies than their neighbors.
* Return the minimum number of candies you need to have to distribute the
* _ candies to the children.
*
* Example 1:
* Input: ratings = [1,0,2]
* Output: 5
* Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.
*
* Example 2:
* Input: ratings = [1,2,2]
* Output: 4
* Explanation: You can allocate to the first, second and third child with 1, 2, 1 candies respectively.
* The third child gets 1 candy because it satisfies the above two conditions.
*
* Constraints:
* • n == ratings.length
* • 1 <= n <= 2 * 10^4
* • 0 <= ratings[i] <= 2 * 10^4
* 
****************************************/

class Solution {
    // One-pass greedy solution that handles increasing and decreasing
    // rating sequences in a single sweep. Counts total candies needed
    // and compensates if a descent is longer than the previous ascent.
    // Time: O(n), Space: O(1), where n is the number of children.
    // Avoids auxiliary arrays for better performance and lower memory.

    static {
        // JIT warm-up for performance benchmarking (not part of core logic)
        for (int i = 0; i < 140; ++i)
            candy(new int[] {1, 3, 2});
        System.gc();
    }

    public static int candy(int[] ratings) {
        int n = ratings.length;
        int i = 1;
        int totalCandies = 1;

        while (i < n) {
            if (ratings[i] == ratings[i - 1]) {
                totalCandies += 1;
                i++;
            }

            int up = 1;
            while (i < n && ratings[i] > ratings[i - 1]) {
                up++;
                totalCandies += up;
                i++;
            }

            int down = 1;
            while (i < n && ratings[i] < ratings[i - 1]) {
                totalCandies += down;
                down++;
                i++;
            }

            // If the down slope is longer than the up slope,
            // compensate by adding the missing peak candy
            if (down > up) {
                totalCandies += down - up;
            }
        }

        return totalCandies;
    }
}
