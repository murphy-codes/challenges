// Source: https://leetcode.com/problems/minimum-absolute-distance-between-mirror-pairs/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-17
// At the time of submission:
//   Runtime 40 ms Beats 99.50%
//   Memory 102.42 MB Beats 21.61%

/****************************************
* 
* You are given an integer array `nums`.
* A mirror pair is a pair of indices `(i, j)` such that:
* • `0 <= i < j < nums.length`, and
* • `reverse(nums[i]) == nums[j]`, where `reverse(x)` denotes the integer
* __ formed by reversing the digits of `x`. Leading zeros are omitted after
* __ reversing, for example `reverse(120) = 21`.
* Return the minimum absolute distance between the indices of any mirror pair.
* _ The absolute distance between indices `i` and `j` is `abs(i - j)`.
* If no mirror pair exists, return `-1`.
*
* Example 1:
* Input: nums = [12,21,45,33,54]
* Output: 1
* Explanation:
* The mirror pairs are:
* • (0, 1) since `reverse(nums[0]) = reverse(12) = 21 = nums[1]`, giving an absolute distance `abs(0 - 1) = 1`.
* • (2, 4) since `reverse(nums[2]) = reverse(45) = 54 = nums[4]`, giving an absolute distance `abs(2 - 4) = 2`.
* The minimum absolute distance among all pairs is 1.
*
* Example 2:
* Input: nums = [120,21]
* Output: 1
* Explanation:
* There is only one mirror pair (0, 1) since `reverse(nums[0]) = reverse(120) = 21 = nums[1]`.
* The minimum absolute distance is 1.
*
* Example 3:
* Input: nums = [21,120]
* Output: -1
* Explanation:
* There are no mirror pairs in the array.
*
* Constraints:
* • `1 <= nums.length <= 10^5
* • `1 <= nums[i] <= 10^9
* 
****************************************/

import java.util.HashMap;
import java.util.Map;

class Solution {
    // We scan the array while storing the latest index of each reversed
    // number in a hash map. For each element, we check if it already exists
    // as a key, indicating a previous number whose reverse matches the
    // current value. We update the minimum distance accordingly. Using the
    // latest index ensures the smallest distance. Time is O(n), space is O(n).
    public int minMirrorPairDistance(int[] nums) {

        int n = nums.length;
        int minDistance = n;

        // Map: reverse(value) -> latest index
        Map<Integer, Integer> lastIndex = new HashMap<>(n, 1.0f);

        for (int j = 0; j < n; j++) {

            int current = nums[j];

            // Check for mirror match
            Integer prevIndex = lastIndex.get(current);
            if (prevIndex != null) {
                int distance = j - prevIndex;
                minDistance = Math.min(minDistance, distance);
            }

            // Compute reverse(current)
            int temp = current;
            int reversed = 0;

            while (temp > 0) {
                int digit = temp % 10;
                reversed = reversed * 10 + digit;
                temp /= 10;
            }

            // Store latest index
            lastIndex.put(reversed, j);
        }

        return (minDistance < n) ? minDistance : -1;
    }
}