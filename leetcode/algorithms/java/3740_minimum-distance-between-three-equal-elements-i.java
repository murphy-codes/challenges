// Source: https://leetcode.com/problems/minimum-distance-between-three-equal-elements-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-10
// At the time of submission:
//   Runtime 5 ms Beats 25.17%
//   Memory 45.27 MB Beats 13.11%

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
* • `1 <= n == nums.length <= 100`
* • `1 <= nums[i] <= n`
* 
****************************************/

import java.util.ArrayList;
import java.util.List;

class Solution {
    // We map each value to a list of its indices in the array. For values with
    // at least three occurrences, we scan consecutive triples of indices and
    // compute the distance as 2 * (k - i), which minimizes the total distance.
    // Checking only consecutive triples ensures optimality. The algorithm runs
    // in O(n) time overall and uses O(n) space to store index lists.
    public int minimumDistance(int[] nums) {
        int minDist = 2*nums.length;
        Map<Integer, List<Integer>> freq = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if(freq.containsKey(nums[i])) { freq.get(nums[i]).add(i); }
            else { freq.put(nums[i],new ArrayList<>(List.of(i))); }
        }
        for (Map.Entry<Integer, List<Integer>> e : freq.entrySet()) {
            for (int i = 0; i + 2 < e.getValue().size(); i++) {
                minDist = Math.min(minDist, 2 * (e.getValue().get(i+2) - e.getValue().get(i)));
            }
        }
        if (minDist < 2*nums.length) return minDist;
        return -1;
    }
}