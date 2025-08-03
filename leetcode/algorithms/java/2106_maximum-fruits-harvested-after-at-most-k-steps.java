// Source: https://leetcode.com/problems/maximum-fruits-harvested-after-at-most-k-steps/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-03
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 97.61 MB Beats 58.54%

/****************************************
* 
* Fruits are available at some positions on an infinite x-axis. You are given a
* _ 2D integer array `fruits` where `fruits[i] = [position_i, amount_i]` depicts
* _ `amount_i` fruits at the position `position_i`. fruits is already sorted by
* _ `position_i` in ascending order, and each `position_i` is unique.
* You are also given an integer `startPos` and an integer `k`. Initially, you are
* _ at the position `startPos`. From any position, you can either walk to the
* _ left or right. It takes one step to move one unit on the x-axis, and you can
* _ walk at most `k` steps in total. For every position you reach, you harvest all
* _ the fruits at that position, and the fruits will disappear from that position.
* Return the maximum total number of fruits you can harvest.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/11/21/1.png]
* Input: fruits = [[2,8],[6,3],[8,6]], startPos = 5, k = 4
* Output: 9
* Explanation:
* The optimal way is to:
* - Move right to position 6 and harvest 3 fruits
* - Move right to position 8 and harvest 6 fruits
* You moved 3 steps and harvested 3 + 6 = 9 fruits in total.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2021/11/21/2.png]
* Input: fruits = [[0,9],[4,1],[5,7],[6,2],[7,4],[10,9]], startPos = 5, k = 4
* Output: 14
* Explanation:
* You can move at most k = 4 steps, so you cannot reach position 0 nor 10.
* The optimal way is to:
* - Harvest the 7 fruits at the starting position 5
* - Move left to position 4 and harvest 1 fruit
* - Move right to position 6 and harvest 2 fruits
* - Move right to position 7 and harvest 4 fruits
* You moved 1 + 3 = 4 steps and harvested 7 + 1 + 2 + 4 = 14 fruits in total.
*
* Example 3:
* [Image: https://assets.leetcode.com/uploads/2021/11/21/3.png]
* Input: fruits = [[0,3],[6,4],[8,5]], startPos = 3, k = 2
* Output: 0
* Explanation:
* You can move at most k = 2 steps and cannot reach any position with fruits.
*
* Constraints:
* • 1 <= fruits.length <= 10^5
* • fruits[i].length == 2
* • 0 <= startPos, position_i <= 2 * 10^5
* • `position_i-1 < position_i` for any `i > 0` (0-indexed)
* • 1 <= amount_i <= 10^4
* • 0 <= k <= 2 * 10^5
* 
****************************************/

class Solution {
    // Uses a sliding window to track the max fruits collectable while moving
    // at most k steps. First locates the leftmost fruit in range. Then slides
    // rightward, shrinking the window when the path exceeds k. Binary search
    // finds the start point, then we scan once to the right.
    // Time: O(N), Space: O(1) extra (excluding input array)
    public int maxTotalFruits(int[][] fruits, int startPos, int k) {
        int n = fruits.length;

        // Start from the first fruit within reach to the left
        int left = lowerBound(fruits, startPos - k);
        int right = left;
        int currentSum = 0;

        // Collect fruits from left side up to startPos
        while (right < n && fruits[right][0] <= startPos) {
            currentSum += fruits[right][1];
            right++;
        }

        int maxFruits = currentSum;

        // Expand to the right, adjust left when path cost exceeds k
        while (right < n && fruits[right][0] <= startPos + k) {
            currentSum += fruits[right][1];

            // If the total path to collect is > k, shrink the window from left
            while (
                (fruits[right][0] - startPos + fruits[right][0] - fruits[left][0] > k) &&
                (startPos - fruits[left][0] + fruits[right][0] - fruits[left][0] > k)
            ) {
                currentSum -= fruits[left][1];
                left++;
            }

            maxFruits = Math.max(maxFruits, currentSum);
            right++;
        }

        return maxFruits;
    }

    // Binary search for first fruit position >= target
    private int lowerBound(int[][] arr, int target) {
        int low = 0, high = arr.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr[mid][0] < target) low = mid + 1;
            else high = mid;
        }
        return low;
    }
}
