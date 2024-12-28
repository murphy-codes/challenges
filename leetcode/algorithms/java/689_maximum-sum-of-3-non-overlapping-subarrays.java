// Source: https://leetcode.com/problems/maximum-sum-of-3-non-overlapping-subarrays/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2024-12-27

/****************************************
* 
* Given an integer array nums and an integer k, find three non-overlapping subarrays of length k with maximum sum and return them.
* 
* Return the result as a list of indices representing the starting position of each interval (0-indexed). If there are multiple answers, return the lexicographically smallest one.
* 
* Example 1:
* Input: nums = [1,2,1,2,6,7,5,1], k = 2
* Output: [0,3,5]
* Explanation: Subarrays [1, 2], [2, 6], [7, 5] correspond to the starting indices [0, 3, 5].
* We could have also taken [2, 1], but an answer of [1, 3, 5] would be lexicographically larger.
* 
* Example 2:
* Input: nums = [1,2,1,2,1,2,1,2,1], k = 2
* Output: [0,2,4]
* 
* Constraints:
* 
* 1 <= nums.length <= 2 * 104
* 1 <= nums[i] < 216
* 1 <= k <= floor(nums.length / 3)
* 
****************************************/

class Solution {
    // Use a prefix sum array to precompute cumulative sums. 
    // This allows efficient subarray sum calculations during subsequent iterations, 
    // reducing redundant computations and keeping time complexity manageable.
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int n = nums.length;
        int[] left = new int[n];
        int[] right = new int[n];

        // Calculate prefix sums
        int[] prefix = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        // Fill left array (maximum sum for subarrays from the left up to index i)
        int bestLeftIndex = 0;
        for (int i = k - 1; i < n; i++) {
            int sum = prefix[i + 1] - prefix[i + 1 - k];
            if (i == k - 1 || sum > prefix[bestLeftIndex + k] - prefix[bestLeftIndex]) {
                bestLeftIndex = i + 1 - k;
            }
            left[i] = bestLeftIndex;
        }

        // Fill right array (maximum sum for subarrays from index i to the end)
        int bestRightIndex = n - k;
        for (int i = n - k; i >= 0; i--) {
            int sum = prefix[i + k] - prefix[i];
            if (i == n - k || sum >= prefix[bestRightIndex + k] - prefix[bestRightIndex]) {
                bestRightIndex = i;
            }
            right[i] = bestRightIndex;
        }

        // Find the best middle subarray to maximize total sum
        int[] result = new int[3];
        int maxSum = 0;

        for (int midStart = k; midStart <= n - 2 * k; midStart++) {
            int leftStart = left[midStart - 1];
            int rightStart = right[midStart + k];
            int totalSum = (prefix[leftStart + k] - prefix[leftStart]) +
                           (prefix[midStart + k] - prefix[midStart]) +
                           (prefix[rightStart + k] - prefix[rightStart]);

            if (totalSum > maxSum) {
                maxSum = totalSum;
                result[0] = leftStart;
                result[1] = midStart;
                result[2] = rightStart;
            }
        }

        return result;
    }
}
