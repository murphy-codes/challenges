// Source: https://leetcode.com/count-the-number-of-fair-pairs/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-18
// At the time of submission:
//   Runtime 63 ms Beats 50.60%
//   Memory 57.24 MB Beats 71.77%

/****************************************
* 
* Given a 0-indexed integer array `nums` of size `n` and two integers
* _ `lower` and `upper`, return the number of fair pairs.
* A pair `(i, j)` is fair if:
* • `0 <= i < j < n`, and
* • `lower <= nums[i] + nums[j] <= upper`
*
* Example 1:
* Input: nums = [0,1,7,4,4,5], lower = 3, upper = 6
* Output: 6
* Explanation: There are 6 fair pairs: (0,3), (0,4), (0,5), (1,3), (1,4), and (1,5).
*
* Example 2:
* Input: nums = [1,7,9,2,5], lower = 11, upper = 11
* Output: 1
* Explanation: There is a single fair pair: (2,3).
*
* Constraints:
* • 1 <= nums.length <= 10^5
* • nums.length == n
* • -10^9 <= nums[i] <= 10^9
* • -10^9 <= lower <= upper <= 10^9
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Sort the array so we can binary search efficiently for valid pairs.
    // For each i, find how many j > i satisfy:
    //   lower <= nums[i] + nums[j] <= upper
    // Use lowerBound and upperBound binary search helpers to count range.
    // Time: O(n log n) due to sort + n binary searches. Space: O(1) extra.
    public long countFairPairs(int[] nums, int lower, int upper) {
        Arrays.sort(nums);
        long count = 0;

        for (int i = 0; i < nums.length; i++) {
            int left = lowerBound(nums, i + 1, nums.length - 1, lower - nums[i]);
            int right = upperBound(nums, i + 1, nums.length - 1, upper - nums[i]);
            count += (right - left);
        }

        return count;
    }

    // Finds the first index where nums[idx] >= target
    private int lowerBound(int[] nums, int left, int right, int target) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    // Finds the first index where nums[idx] > target
    private int upperBound(int[] nums, int left, int right, int target) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }
}
