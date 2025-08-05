// Source: https://leetcode.com/problems/fruits-into-baskets-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-05
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 44.34 MB Beats 79.27%

/****************************************
* 
* You are given two arrays of integers, `fruits` and `baskets`, each of length `n`,
* _ where `fruits[i]` represents the quantity of the `i^th` type of fruit, and
* _ `baskets[j]` represents the capacity of the `j^th` basket.
* From left to right, place the fruits according to these rules:
* • Each fruit type must be placed in the leftmost available basket with a
* _ capacity greater than or equal to the quantity of that fruit type.
* • Each basket can hold only one type of fruit.
* • If a fruit type cannot be placed in any basket, it remains unplaced.
* Return the number of fruit types that remain unplaced after all possible
* _ allocations are made.
*
* Example 1:
* Input: fruits = [4,2,5], baskets = [3,5,4]
* Output: 1
* Explanation:
* fruits[0] = 4 is placed in baskets[1] = 5.
* fruits[1] = 2 is placed in baskets[0] = 3.
* fruits[2] = 5 cannot be placed in baskets[2] = 4.
* Since one fruit type remains unplaced, we return 1.
*
* Example 2:
* Input: fruits = [3,6,1], baskets = [6,4,7]
* Output: 0
* Explanation:
* fruits[0] = 3 is placed in baskets[0] = 6.
* fruits[1] = 6 cannot be placed in baskets[1] = 4 (insufficient capacity) but
* _ can be placed in the next available basket, baskets[2] = 7.
* fruits[2] = 1 is placed in baskets[1] = 4.
* Since all fruits are successfully placed, we return 0.
*
* Constraints:
* • n == fruits.length == baskets.length
* • 1 <= n <= 100
* • 1 <= fruits[i], baskets[i] <= 1000
* 
****************************************/

class Solution {
    // For each fruit, find the first available basket that can hold it.
    // Mark the basket as used by setting its value to 0 after placement.
    // If no suitable basket is found, count the fruit as unplaced.
    // Time Complexity: O(n^2), where n = fruits.length = baskets.length
    // Space Complexity: O(1), as baskets are reused and modified in-place
    public int numOfUnplacedFruits(int[] fruits, int[] baskets) {
        int unplaced = 0;
        for (int fruit : fruits) {
            boolean placed = false;
            for (int i = 0; i < baskets.length; i++) {
                if (fruit <= baskets[i]) {
                    baskets[i] = 0; // Mark basket as used
                    placed = true;
                    break; // Exit early after placing the fruit
                }
            }
            if (!placed) unplaced++;
        }

        return unplaced;
    }
}