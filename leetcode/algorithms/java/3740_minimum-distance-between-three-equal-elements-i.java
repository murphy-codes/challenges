// Source: https://leetcode.com/problems/minimum-distance-between-three-equal-elements-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-10
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 44.83 MB Beats 33.01%

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
    // For any triple i < j < k, the distance simplifies to 2 * (k - i).
    // We group indices by value and, for each value with at least three
    // occurrences, check consecutive triples to minimize (k - i).
    // This ensures the smallest possible distance. The approach runs in
    // O(n) time and uses O(n) space for storing indices.

    public int minimumDistance(int[] nums) {

        int n = nums.length;

        // Since nums[i] <= n, we can use array of lists
        List<Integer>[] positions = new ArrayList[n + 1];

        for (int i = 0; i <= n; i++) {
            positions[i] = new ArrayList<>();
        }

        // Store indices for each value
        for (int i = 0; i < n; i++) {
            positions[nums[i]].add(i);
        }

        int minDistance = Integer.MAX_VALUE;

        // Process each value
        for (int val = 1; val <= n; val++) {
            List<Integer> idxList = positions[val];

            if (idxList.size() < 3) continue;

            // Check consecutive triples
            for (int i = 0; i + 2 < idxList.size(); i++) {
                int left = idxList.get(i);
                int right = idxList.get(i + 2);

                int distance = 2 * (right - left);
                minDistance = Math.min(minDistance, distance);
            }
        }

        return minDistance == Integer.MAX_VALUE ? -1 : minDistance;
    }
}