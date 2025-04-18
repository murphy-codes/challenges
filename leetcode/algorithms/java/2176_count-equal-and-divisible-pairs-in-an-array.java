// Source: https://leetcode.com/count-equal-and-divisible-pairs-in-an-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-17
// At the time of submission:
//   Runtime 5 ms Beats 11.63%
//   Memory 43.13 MB Beats 8.25%

/****************************************
* 
* Given a 0-indexed integer array `nums` of length `n` and an integer `k`,
* _ return the number of pairs `(i, j)` where `0 <= i < j < n`, such that
* _ `nums[i] == nums[j]` and `(i * j)` is divisible by `k`.
*
* Example 1:
* Input: nums = [3,1,2,2,2,1,3], k = 2
* Output: 4
* Explanation:
* There are 4 pairs that meet all the requirements:
* - nums[0] == nums[6], and 0 * 6 == 0, which is divisible by 2.
* - nums[2] == nums[3], and 2 * 3 == 6, which is divisible by 2.
* - nums[2] == nums[4], and 2 * 4 == 8, which is divisible by 2.
* - nums[3] == nums[4], and 3 * 4 == 12, which is divisible by 2.
*
* Example 2:
* Input: nums = [1,2,3,4], k = 1
* Output: 0
* Explanation: Since no value in nums is repeated, there are no pairs (i,j) that meet all the requirements.
*
* Constraints:
* • 1 <= nums.length <= 100
* • 1 <= nums[i], k <= 100
* 
****************************************/

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

class Solution {
    // Group indices of equal values using a map to avoid unnecessary checks.
    // Only consider index pairs where nums[i] == nums[j] by iterating grouped indices.
    // For each such pair (i, j), check if i * j is divisible by k.
    // Time: O(n^2) in worst case (if all values are equal), but pruned significantly.
    // Space: O(n) for storing indices grouped by value.
    public int countPairs(int[] nums, int k) {
        HashMap<Integer, List<Integer>> valueToIndices = new HashMap<>();
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            valueToIndices
                .computeIfAbsent(nums[i], v -> new ArrayList<>())
                .add(i);
        }

        for (List<Integer> indices : valueToIndices.values()) {
            int size = indices.size();
            for (int i = 0; i < size; i++) {
                for (int j = i + 1; j < size; j++) {
                    int idx1 = indices.get(i);
                    int idx2 = indices.get(j);
                    if ((idx1 * idx2) % k == 0) {
                        count++;
                    }
                }
            }
        }

        return count;
    }
}
