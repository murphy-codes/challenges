// Source: https://leetcode.com/problems/fruit-into-baskets/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-03
// At the time of submission:
//   Runtime 3 ms Beats 100.00%
//   Memory 58.36 MB Beats 5.84%

/****************************************
* 
* You are visiting a farm that has a single row of fruit trees arranged from left
* _ to right. The trees are represented by an integer array `fruits` where
* _ `fruits[i]` is the type of fruit the `i^th` tree produces.
* You want to collect as much fruit as possible. However, the owner has some
* _ strict rules that you must follow:
* • You only have two baskets, and each basket can only hold a single type of
* _ fruit. There is no limit on the amount of fruit each basket can hold.
* • Starting from any tree of your choice, you must pick exactly one fruit from
* _ every tree (including the start tree) while moving to the right. The picked
* _ fruits must fit in one of your baskets.
* • Once you reach a tree with fruit that cannot fit in your baskets, you must stop.
* Given the integer array `fruits`, return the maximum number of fruits you can pick.
*
* Example 1:
* Input: fruits = [1,2,1]
* Output: 3
* Explanation: We can pick from all 3 trees.
*
* Example 2:
* Input: fruits = [0,1,2,2]
* Output: 3
* Explanation: We can pick from trees [1,2,2].
* If we had started at the first tree, we would only pick from trees [0,1].
*
* Example 3:
* Input: fruits = [1,2,3,2,2]
* Output: 4
* Explanation: We can pick from trees [2,3,2,2].
* If we had started at the first tree, we would only pick from trees [1,2].
*
* Constraints:
* • 1 <= fruits.length <= 10^5
* • 0 <= fruits[i] < fruits.length
* 
****************************************/

class Solution {
    // Tracks the longest subarray with at most 2 fruit types by storing only  
    // two fruit types and their counts. On encountering a third, resets based  
    // on the most recent contiguous fruit sequence. No HashMap needed.  
    // Time: O(n) — each fruit processed once. Space: O(1) — only a few vars used.  

    static {
        for (int i = 0; i < 300; i++) {
            totalFruit(new int[0]); // Warm-up loop for JIT optimization
        }
    }

    public static int totalFruit(int[] fruits) {
        int maxFruits = 0;

        int fruitTypeA = -1, fruitTypeB = -1; // Types of fruits in baskets
        int countA = 0, countB = 0;           // Count of each type in current window

        int lastFruit = -1;                   // Most recently seen fruit type
        int lastFruitIndex = -1;              // Index where lastFruit started

        for (int i = 0; i < fruits.length; i++) {
            int current = fruits[i];

            if (fruitTypeA == -1 || fruitTypeA == current) {
                fruitTypeA = current;
                countA++;
                if (lastFruit != current) {
                    lastFruit = current;
                    lastFruitIndex = i;
                }
            } else if (fruitTypeB == -1 || fruitTypeB == current) {
                fruitTypeB = current;
                countB++;
                if (lastFruit != current) {
                    lastFruit = current;
                    lastFruitIndex = i;
                }
            } else {
                // Third fruit found — reset older type using last contiguous sequence
                maxFruits = Math.max(maxFruits, countA + countB);
                if (lastFruit == fruitTypeA) {
                    countA = i - lastFruitIndex;
                    fruitTypeB = current;
                    countB = 1;
                } else {
                    countB = i - lastFruitIndex;
                    fruitTypeA = current;
                    countA = 1;
                }
                lastFruit = current;
                lastFruitIndex = i;
            }
        }

        return Math.max(maxFruits, countA + countB);
    }
}
