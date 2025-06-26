// Source: https://leetcode.com/problems/partition-array-such-that-maximum-difference-is-k/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-18
// At the time of submission:
//   Runtime 4 ms Beats 97.05%
//   Memory 58.24 MB Beats 13.84%

/****************************************
* 
* You are given an integer array `nums` and an integer `k`. You may partition
* _ `nums` into one or more subsequences such that each element in `nums` appears
* _ in exactly one of the subsequences.
* Return the minimum number of subsequences needed such that the difference between
* _ the maximum and minimum values in each subsequence is at most k.
* A subsequence is a sequence that can be derived from another sequence by deleting
* _ some or no elements without changing the order of the remaining elements.
*
* Example 1:
* Input: nums = [3,6,1,2,5], k = 2
* Output: 2
* Explanation:
* We can partition nums into the two subsequences [3,1,2] and [6,5].
* The difference between the maximum and minimum value in the first subsequence is 3 - 1 = 2.
* The difference between the maximum and minimum value in the second subsequence is 6 - 5 = 1.
* Since two subsequences were created, we return 2. It can be shown that 2 is the minimum number of subsequences needed.
*
* Example 2:
* Input: nums = [1,2,3], k = 1
* Output: 2
* Explanation:
* We can partition nums into the two subsequences [1,2] and [3].
* The difference between the maximum and minimum value in the first subsequence is 2 - 1 = 1.
* The difference between the maximum and minimum value in the second subsequence is 3 - 3 = 0.
* Since two subsequences were created, we return 2. Note that another optimal solution is to partition nums into the two subsequences [1] and [2,3].
*
* Example 3:
* Input: nums = [2,2,4,5], k = 0
* Output: 3
* Explanation:
* We can partition nums into the three subsequences [2,2], [4], and [5].
* The difference between the maximum and minimum value in the first subsequences is 2 - 2 = 0.
* The difference between the maximum and minimum value in the second subsequences is 4 - 4 = 0.
* The difference between the maximum and minimum value in the third subsequences is 5 - 5 = 0.
* Since three subsequences were created, we return 3. It can be shown that 3 is the minimum number of subsequences needed.
*
* Constraints:
* • 1 <= nums.length <= 10^5
* • 0 <= nums[i] <= 10^5
* • 0 <= k <= 10^5
* 
****************************************/

class Solution {
    // Group values into the minimum number of subsequences such that the
    // difference between any two elements in a group is ≤ k. Uses a boolean
    // presence array instead of sorting to allow linear-time grouping.
    // Time: O(n + range) where range = max(nums) - min(nums)
    // Space: O(max(nums)) for the presence array
    public int partitionArray(int[] nums, int k) {
        int n = nums.length;
        int max = -1;
        int min = Integer.MAX_VALUE;

        // Find global min and max to determine array range
        for (int value : nums) {
            max = Math.max(max, value);
            min = Math.min(min, value);
        }

        // If all numbers fit within a single k-range, only one group is needed
        if (max - min <= k) {
            return 1;
        }

        // Create a presence array (bitmap) to quickly check number existence
        boolean[] present = new boolean[max + 1];
        for (int value : nums) {
            present[value] = true;
        }

        int groups = 1;
        int i = min + k + 1;

        // Greedily skip ahead by k steps after each group is formed
        while (i <= max) {
            // Find the next value that exists
            while (i <= max && !present[i]) {
                i++;
            }
            if (i <= max) {
                groups++;
                i += k + 1; // Skip ahead to avoid overlap
            }
        }

        return groups;
    }
}

