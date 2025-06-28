// Source: https://leetcode.com/problems/find-subsequence-of-length-k-with-the-largest-sum/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-27
// At the time of submission:
//   Runtime 3 ms Beats 99.08%
//   Memory 44.17 MB Beats 96.19%

/****************************************
* 
* You are given an integer array `nums` and an integer `k`. You want to find a
* _ subsequence of `nums` of length `k` that has the largest sum.
* Return any such subsequence as an integer array of length `k`.
* A subsequence is an array that can be derived from another array by deleting
* _ some or no elements without changing the order of the remaining elements.
*
* Example 1:
* Input: nums = [2,1,3,3], k = 2
* Output: [3,3]
* Explanation:
* The subsequence has the largest sum of 3 + 3 = 6.
*
* Example 2:
* Input: nums = [-1,-2,3,4], k = 3
* Output: [-1,3,4]
* Explanation:
* The subsequence has the largest sum of -1 + 3 + 4 = 6.
*
* Example 3:
* Input: nums = [3,4,3,3], k = 2
* Output: [3,4]
* Explanation:
* The subsequence has the largest sum of 3 + 4 = 7.
* Another possible subsequence is [4, 3].
*
* Constraints:
* • 1 <= nums.length <= 1000
* • -10^5 <= nums[i] <= 10^5
* • 1 <= k <= nums.length
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Copies and sorts the array to find the k largest elements.
    // Selects those values from the original array in order,
    // tracking how many times to include the threshold value.
    // Time: O(n log n) due to sort. Space: O(n) for the copy.
    // Efficient in both performance and memory by avoiding extra structures.
    public int[] maxSubsequence(int[] nums, int k) {
        int n = nums.length;
        int[] sortedCopy = Arrays.copyOf(nums, n);

        // Sort the copy to find the k largest values
        Arrays.sort(sortedCopy);
        int threshold = sortedCopy[n - k];

        // Count how many times the threshold value appears in top-k
        int thresholdCount = 0;
        for (int i = n - k; i < n; i++) {
            if (sortedCopy[i] == threshold) {
                thresholdCount++;
            }
        }

        int[] result = new int[k];
        int index = 0;

        // Traverse original array to build subsequence in order
        for (int num : nums) {
            if (num > threshold) {
                result[index++] = num;
            } else if (num == threshold && thresholdCount > 0) {
                result[index++] = num;
                thresholdCount--;
            }
            if (index == k) break;
        }

        return result;
    }
}

