// Source: https://leetcode.com/problems/minimum-absolute-distance-between-mirror-pairs/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-17
// At the time of submission:
//   Runtime 57 ms Beats 34.17%
//   Memory 95.00 MB Beats 76.38%

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

class Solution {
    // We scan the array while storing the latest index of each reversed
    // number in a map. For each element, we check if it exists in the map,
    // meaning a previous number's reverse matches the current value.
    // This ensures we always compare with the closest prior occurrence.
    // Time is O(n * d), where d is digit count (~10), space is O(n).

    public int minMirrorPairDistance(int[] nums) {

        HashMap<Integer, Integer> map = new HashMap<>();
        int minDist = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length; i++) {

            int curr = nums[i];

            // Check if a matching reverse was seen before
            if (map.containsKey(curr)) {
                minDist = Math.min(minDist, i - map.get(curr));
            }

            // Store reverse of current number
            int reversed = reverse(curr);
            map.put(reversed, i);
        }

        return minDist == Integer.MAX_VALUE ? -1 : minDist;
    }

    private int reverse(int x) {
        int rev = 0;
        while (x > 0) {
            rev = rev * 10 + (x % 10);
            x /= 10;
        }
        return rev;
    }
}