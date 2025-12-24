// Source: https://leetcode.com/problems/apple-redistribution-into-boxes/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-23
// At the time of submission:
//   Runtime 1 ms Beats 98.81%
//   Memory 44.15 MB Beats 79.53%

/****************************************
* 
* You are given an array `apple` of size `n` and an array `capacity` of size `m`.
* There are `n` packs where the `i^th` pack contains `apple[i]` apples. There are
* _ `m` boxes as well, and the `i^th` box has a capacity of `capacity[i]` apples.
* Return the minimum number of boxes you need to select to redistribute these `n`
* _ packs of apples into boxes.
* Note that, apples from the same pack can be distributed into different boxes.
*
* Example 1:
* Input: apple = [1,3,2], capacity = [4,3,1,5,2]
* Output: 2
* Explanation: We will use boxes with capacities 4 and 5.
* It is possible to distribute the apples as the total capacity is greater than or equal to the total number of apples.
*
* Example 2:
* Input: apple = [5,5,5], capacity = [2,4,2,7]
* Output: 4
* Explanation: We will need to use all the boxes.
*
* Constraints:
* • `1 <= n == apple.length <= 50`
* • `1 <= m == capacity.length <= 50`
* • `1 <= apple[i], capacity[i] <= 50`
* • The input is generated such that it's possible to redistribute packs of apples into boxes.
* 
****************************************/

class Solution {
    // First sum all apples to know total capacity needed.
    // Sort box capacities and greedily select the largest boxes.
    // Each selected box increases available capacity until apples fit.
    // Greedy is optimal since larger boxes minimize box count.
    // Time: O(m^2), Space: O(1) extra
    public int minimumBoxes(int[] apple, int[] capacity) {
        int totalApples = 0;
        for (int i = 0; i < apple.length; i++) {
            totalApples += apple[i];
        }

        // Bubble sort capacities in ascending order
        int tempSwap;
        for (int i = 0; i < capacity.length; i++) {
            for (int j = 0; j < capacity.length - i - 1; j++) {
                if (capacity[j] > capacity[j + 1]) {
                    tempSwap = capacity[j];
                    capacity[j] = capacity[j + 1];
                    capacity[j + 1] = tempSwap;
                }
            }
        }

        int usedCapacity = 0;
        int boxCount = 0;

        // Select boxes from largest capacity to smallest
        for (int i = capacity.length - 1; i >= 0; i--) {
            if (usedCapacity < totalApples) {
                usedCapacity += capacity[i];
                boxCount++;
            }
        }

        return boxCount;
    }
}
