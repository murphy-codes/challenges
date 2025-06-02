// Source: https://leetcode.com/problems/candy/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-01
// At the time of submission:
//   Runtime 3 ms Beats 84.79%
//   Memory 45.96 MB Beats 45.81%

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
    // Assign each child at least one candy, and ensure higher-rated children
    // get more candies than neighbors. Do two greedy passes:
    // one left-to-right, and another right-to-left to resolve both sides.
    // Time: O(n), Space: O(n), where n is the number of children.
    public int candy(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];
        Arrays.fill(candies, 1); // Every child gets at least 1 candy

        // First pass: left to right
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }

        // Second pass: right to left
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
        }

        int total = 0;
        for (int c : candies) {
            total += c;
        }
        return total;
    }
}
