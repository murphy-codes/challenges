// Source: https://leetcode.com/problems/maximum-frequency-of-an-element-after-performing-operations-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-20
// At the time of submission:
//   Runtime 5 ms Beats 100.00%
//   Memory 58.66 MB Beats 68.42%

/****************************************
* 
* You are given an integer array `nums` and two integers `k` and `numOperations`.
* You must perform an operation `numOperations` times on `nums`, where in
* _ each operation you:
* • Select an index `i` that was not selected in any previous operations.
* • Add an integer in the range `[-k, k]` to `nums[i]`.
* Return the maximum possible frequency of any element in `nums` after
* _ performing the operations.
*
* Example 1:
* Input: nums = [1,4,5], k = 1, numOperations = 2
* Output: 2
* Explanation:
* We can achieve a maximum frequency of two by:
* Adding 0 to nums[1]. nums becomes [1, 4, 5].
* Adding -1 to nums[2]. nums becomes [1, 4, 4].
*
* Example 2:
* Input: nums = [5,11,20,20], k = 5, numOperations = 1
* Output: 2
* Explanation:
* We can achieve a maximum frequency of two by:
* Adding 0 to nums[1].
*
* Constraints:
* • `1 <= nums.length <= 10^5`
* • `1 <= nums[i] <= 10^5`
* • `0 <= k <= 10^5`
* • `0 <= numOperations <= nums.length`
* 
****************************************/

class Solution {
    // Compute max achievable frequency using prefix sums for fast range queries.
    // For each possible value i, count elements within [i−k, i+k] via prefix sums.
    // Add up to numOperations elements from this range to match i, maximizing freq.
    // Time Complexity: O(n + range), where range = max(nums) − min(nums).
    // Space Complexity: O(range), for frequency and prefix arrays.
    public int maxFrequency(int[] nums, int k, int numOperations) {
        int maxVal = 0, minVal = Integer.MAX_VALUE;

        // Find global min and max values in nums
        for (int num : nums) {
            maxVal = Math.max(maxVal, num);
            minVal = Math.min(minVal, num);
        }

        // Frequency array and prefix sum array
        int[] freq = new int[maxVal + 1];
        int[] prefix = new int[maxVal + 1];
        for (int num : nums) {
            freq[num]++;
        }

        // Build prefix sum array for fast range queries
        for (int i = minVal; i <= maxVal; i++) {
            prefix[i] = prefix[i - 1] + freq[i];
        }

        int maxFreq = 0;

        // For each possible value i, compute max achievable frequency
        for (int i = minVal; i <= maxVal; i++) {
            int leftBound = Math.max(0, i - k - 1);
            int rightBound = Math.min(maxVal, i + k);

            int rangeCount = prefix[rightBound] - prefix[leftBound];
            int changeable = rangeCount - freq[i];

            // Use up to numOperations changes, but not more than available
            int totalFreq = freq[i] + Math.min(numOperations, changeable);
            maxFreq = Math.max(maxFreq, totalFreq);
        }

        return maxFreq;
    }
}
