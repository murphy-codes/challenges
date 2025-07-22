// Source: https://leetcode.com/problems/maximum-sum-of-3-non-overlapping-subarrays/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2024-12-27
// At the time of submission:
//   Runtime 3 ms Beats 99.29%
//   Memory 46.90 MB Beats 27.14%

/****************************************
* 
* Given an integer array nums and an integer k, find three non-overlapping 
* _ subarrays of length k with maximum sum and return them.
* Return the result as a list of indices representing the starting position of each 
* _ interval (0-indexed). If there are multiple answers, return the lexicographically 
* _ smallest one.
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
* • 1 <= nums.length <= 2 * 10^4
* • 1 <= nums[i] < 2^16
* • 1 <= k <= floor(nums.length / 3)
* 
****************************************/

class Solution {
    // This solution first calculates the sums of all subarrays of length k to allow
    // O(1) access to window sums. Then, it precomputes the best (max sum) subarray
    // indices from the left and right sides for each possible middle subarray.
    // Finally, it iterates over all valid middle subarrays, combining with the best
    // left and right subarrays to find the max total sum of three non-overlapping
    // subarrays. The time complexity is O(n), and space complexity is O(n) for the 
    // auxiliary arrays used.
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int n = nums.length;
        int windowCount = n - k + 1;
        int[] windowSums = new int[windowCount];

        // Step 1: Compute sums of all subarrays of length k
        int currentSum = 0;
        for (int i = 0; i < k; i++) {
            currentSum += nums[i];
        }
        windowSums[0] = currentSum;
        for (int i = 1; i < windowCount; i++) {
            currentSum += nums[i + k - 1] - nums[i - 1];
            windowSums[i] = currentSum;
        }

        // Step 2: Track index of max sum subarray from the left up to each position
        int[] leftBestIndex = new int[windowCount];
        int bestLeft = 0;
        for (int i = 0; i < windowCount; i++) {
            if (windowSums[i] > windowSums[bestLeft]) {
                bestLeft = i;
            }
            leftBestIndex[i] = bestLeft;
        }

        // Step 3: Track index of max sum subarray from the right starting at each position
        int[] rightBestIndex = new int[windowCount];
        int bestRight = windowCount - 1;
        for (int i = windowCount - 1; i >= 0; i--) {
            if (windowSums[i] >= windowSums[bestRight]) {
                bestRight = i;
            }
            rightBestIndex[i] = bestRight;
        }

        // Step 4: Find max sum by choosing the best left, middle, and right subarrays
        int[] result = new int[3];
        int maxTotalSum = 0;
        for (int mid = k; mid < windowCount - k; mid++) {
            int left = leftBestIndex[mid - k];
            int right = rightBestIndex[mid + k];
            int total = windowSums[left] + windowSums[mid] + windowSums[right];
            if (total > maxTotalSum) {
                maxTotalSum = total;
                result[0] = left;
                result[1] = mid;
                result[2] = right;
            }
        }

        return result;
    }
}
