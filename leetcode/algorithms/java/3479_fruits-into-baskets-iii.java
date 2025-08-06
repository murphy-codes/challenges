// Source: https://leetcode.com/problems/fruits-into-baskets-iii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-05
// At the time of submission:
//   Runtime 32 ms Beats 97.30%
//   Memory 57.88 MB Beats 85.59%

/****************************************
* 
* You are given two arrays of integers, `fruits` and `baskets`, each of length
* _ `n`, where `fruits[i]` represents the quantity of the `i^th` type of fruit,
* _ and `baskets[j]` represents the capacity of the `j^th` basket.
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
* • `fruits[0] = 4` is placed in `baskets[1] = 5`.
* • `fruits[1] = 2` is placed in `baskets[0] = 3`.
* • `fruits[2] = 5` cannot be placed in `baskets[2] = 4`.
* Since one fruit type remains unplaced, we return 1.
*
* Example 2:
* Input: fruits = [3,6,1], baskets = [6,4,7]
* Output: 0
* Explanation:
* • `fruits[0] = 3` is placed in `baskets[0] = 6`.
* • `fruits[1] = 6` cannot be placed in `baskets[1] = 4` (insufficient capacity)
* _ but can be placed in the next available basket, `baskets[2] = 7`.
* • `fruits[2] = 1` is placed in `baskets[1] = 4`.
* Since all fruits are successfully placed, we return 0.
*
* Constraints:
* • n == fruits.length == baskets.length
* • 1 <= n <= 10^5
* • 1 <= fruits[i], baskets[i] <= 10^9
* 
****************************************/

class Solution {
    // Uses a Segment Tree to efficiently find the first basket that can fit
    // each fruit. Each query and update takes O(log n), resulting in an
    // overall time complexity of O(n log n) where n = number of baskets.
    // Space complexity is O(n) due to the segment tree representation.
    // Significantly faster than brute-force for large input sizes.
    public int numOfUnplacedFruits(int[] fruits, int[] baskets) {
        int basketCount = baskets.length;
        int treeSize = 1;
        // Find the smallest power of two >= basketCount
        while (treeSize <= basketCount) treeSize <<= 1;

        // Initialize segment tree to track basket capacities
        int[] segTree = new int[treeSize << 1];
        for (int i = 0; i < basketCount; i++)
            segTree[treeSize + i] = baskets[i];

        // Build the segment tree by taking max from bottom up
        for (int i = treeSize - 1; i > 0; i--)
            segTree[i] = Math.max(segTree[2 * i], segTree[2 * i + 1]);

        int unplacedFruits = 0;

        for (int fruitSize : fruits) {
            int nodeIndex = 1; // Start at the root of segment tree

            // If no basket can hold this fruit, count as unplaced
            if (segTree[nodeIndex] < fruitSize) {
                unplacedFruits++;
                continue;
            }

            // Binary search in segment tree to find leftmost valid basket
            while (nodeIndex < treeSize) {
                if (segTree[2 * nodeIndex] >= fruitSize)
                    nodeIndex *= 2;
                else
                    nodeIndex = 2 * nodeIndex + 1;
            }

            // Mark basket as used and update tree upwards
            segTree[nodeIndex] = -1;
            while (nodeIndex > 1) {
                nodeIndex >>= 1;
                segTree[nodeIndex] = Math.max(segTree[2 * nodeIndex], segTree[2 * nodeIndex + 1]);
            }
        }

        return unplacedFruits;
    }
}
