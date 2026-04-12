// Source: https://leetcode.com/problems/minimum-distance-between-three-equal-elements-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-11
// At the time of submission:
//   Runtime 5 ms Beats 100.00%
//   Memory 161.10 MB Beats 96.17%

/****************************************
* 
* You are given an integer array `nums`.
* A tuple (`i, j, k`) of 3 distinct indices is good if `nums[i] == nums[j] == nums[k]`.
* The distance of a good tuple is `abs(i - j) + abs(j - k) + abs(k - i)`, where
* _ `abs(x)` denotes the absolute value of `x`.
* Return an integer denoting the minimum possible distance of a good tuple.
* _ If no good tuples exist, return `-1`.
*
* Example 1:
* Input: nums = [1,2,1,1,3]
* Output: 6
* Explanation:
* The minimum distance is achieved by the good tuple (0, 2, 3).
* (0, 2, 3) is a good tuple because nums[0] == nums[2] == nums[3] == 1. Its distance is abs(0 - 2) + abs(2 - 3) + abs(3 - 0) = 2 + 1 + 3 = 6.
*
* Example 2:
* Input: nums = [1,1,2,3,2,1,2]
* Output: 8
* Explanation:
* The minimum distance is achieved by the good tuple (2, 4, 6).
* (2, 4, 6) is a good tuple because nums[2] == nums[4] == nums[6] == 2. Its distance is abs(2 - 4) + abs(4 - 6) + abs(6 - 2) = 2 + 2 + 4 = 8.
*
* Example 3:
* Input: nums = [1]
* Output: -1
* Explanation:
* There are no good tuples. Therefore, the answer is -1.
*
* Constraints:
* • `1 <= n == nums.length <= 10^5`
* • `1 <= nums[i] <= n`
* 
****************************************/

class Solution {
    // We track the last two occurrences of each value while iterating once.
    // When encountering a value for the third time, we compute the distance
    // using the current index and the second previous occurrence, which
    // minimizes 2 * (k - i). This avoids storing full index lists and
    // achieves O(n) time with O(n) space using constant work per element.
    public int minimumDistance(int[] nums) {

        // lastTwo[0][v] = most recent occurrence of v (1-based index)
        // lastTwo[1][v] = second most recent occurrence of v (1-based index)
        int[][] lastTwo = new int[2][nums.length + 1];

        int minDistance = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length; i++) {
            int value = nums[i];

            // If we have seen this value at least twice before
            if (lastTwo[1][value] != 0) {
                int distance = (i - lastTwo[1][value] + 1) * 2;
                minDistance = Math.min(minDistance, distance);
            }

            // Shift occurrences:
            // second most recent ← most recent
            lastTwo[1][value] = lastTwo[0][value];

            // most recent ← current index (1-based)
            lastTwo[0][value] = i + 1;
        }

        return minDistance == Integer.MAX_VALUE ? -1 : minDistance;
    }
}