// Source: https://leetcode.com/problems/sum-of-distances/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-24
// At the time of submission:
//   Runtime 21 ms Beats 84.07%
//   Memory 119.65 MB Beats 65.49%

/****************************************
* 
* You are given a 0-indexed integer array `nums`. There exists an array `arr`
* _ of length `nums.length`, where `arr[i]` is the sum of `|i - j|` over all
* _ `j` such that `nums[j] == nums[i]` and `j != i`. If there is no such `j`,
* _ set `arr[i]` to be `0`.
* Return the array `arr`.
*
* Example 1:
* Input: nums = [1,3,1,1,2]
* Output: [5,0,3,4,0]
* Explanation:
* When i = 0, nums[0] == nums[2] and nums[0] == nums[3]. Therefore, arr[0] = |0 - 2| + |0 - 3| = 5.
* When i = 1, arr[1] = 0 because there is no other index with value 3.
* When i = 2, nums[2] == nums[0] and nums[2] == nums[3]. Therefore, arr[2] = |2 - 0| + |2 - 3| = 3.
* When i = 3, nums[3] == nums[0] and nums[3] == nums[2]. Therefore, arr[3] = |3 - 0| + |3 - 2| = 4.
* When i = 4, arr[4] = 0 because there is no other index with value 2.
*
* Example 2:
* Input: nums = [0,5,3]
* Output: [0,0,0]
* Explanation: Since each element in nums is distinct, arr[i] = 0 for all i.
*
* Constraints:
* • `1 <= nums.length <= 10^5`
* • `0 <= nums[i] <= 10^9`
* 
****************************************/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    // Group indices by value and process each group independently.
    // Use prefix sums to compute distances to left and right indices
    // in O(1) per position, avoiding quadratic comparisons.
    // For each index, distance = left contribution + right contribution.
    // Time: O(n), Space: O(n).
    public long[] distance(int[] nums) {
        int n = nums.length;
        long[] result = new long[n];

        // Group indices by value
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }

        // Process each group independently
        for (List<Integer> indices : map.values()) {
            int size = indices.size();

            long prefixSum = 0; // sum of previous indices
            long totalSum = 0;

            // total sum of all indices in this group
            for (int idx : indices) {
                totalSum += idx;
            }

            for (int i = 0; i < size; i++) {
                int currentIndex = indices.get(i);

                // Left contribution
                long left = (long) currentIndex * i - prefixSum;

                // Right contribution
                long right = (totalSum - prefixSum - currentIndex)
                           - (long) currentIndex * (size - i - 1);

                result[currentIndex] = left + right;

                // update prefix sum
                prefixSum += currentIndex;
            }
        }

        return result;
    }
}