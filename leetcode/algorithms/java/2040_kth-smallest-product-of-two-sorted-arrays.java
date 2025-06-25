// Source: https://leetcode.com/problems/kth-smallest-product-of-two-sorted-arrays/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-24
// At the time of submission:
//   Runtime 763 ms Beats 34.88%
//   Memory 55.37 MB Beats 66.67%

/****************************************
* 
* Given two sorted 0-indexed integer arrays `nums1` and `nums2` as well 
* _ as an integer `k`, return the `k^th` (1-based) smallest product of
* _ `nums1[i] * nums2[j]` where `0 <= i < nums1.length`
* _ and `0 <= j < nums2.length`.
*
* Example 1:
* Input: nums1 = [2,5], nums2 = [3,4], k = 2
* Output: 8
* Explanation: The 2 smallest products are:
* - nums1[0] * nums2[0] = 2 * 3 = 6
* - nums1[0] * nums2[1] = 2 * 4 = 8
* The 2nd smallest product is 8.
*
* Example 2:
* Input: nums1 = [-4,-2,0,3], nums2 = [2,4], k = 6
* Output: 0
* Explanation: The 6 smallest products are:
* - nums1[0] * nums2[1] = (-4) * 4 = -16
* - nums1[0] * nums2[0] = (-4) * 2 = -8
* - nums1[1] * nums2[1] = (-2) * 4 = -8
* - nums1[1] * nums2[0] = (-2) * 2 = -4
* - nums1[2] * nums2[0] = 0 * 2 = 0
* - nums1[2] * nums2[1] = 0 * 4 = 0
* The 6th smallest product is 0.
*
* Example 3:
* Input: nums1 = [-2,-1,0,1,2], nums2 = [-3,-1,2,4,5], k = 3
* Output: -6
* Explanation: The 3 smallest products are:
* - nums1[0] * nums2[4] = (-2) * 5 = -10
* - nums1[0] * nums2[3] = (-2) * 4 = -8
* - nums1[4] * nums2[0] = 2 * (-3) = -6
* The 3rd smallest product is -6.
*
* Constraints:
* • 1 <= nums1.length, nums2.length <= 5 * 10^4
* • -10^5 <= nums1[i], nums2[j] <= 10^5
* • 1 <= k <= nums1.length * nums2.length
* • `nums1` and `nums2` are sorted.
* 
****************************************/

class Solution {
    // Binary search over the range of possible product values.
    // For each mid, count how many products are ≤ mid using two-pointer logic.
    // Positive and negative values are handled separately due to product rules.
    // Time: O(n * log m * log(maxVal)), Space: O(1) extra.
    // Efficient for input sizes up to 5 * 10^4.
    public long kthSmallestProduct(int[] nums1, int[] nums2, long k) {
        long low = -100_000L * 100_000L; // Minimum possible product
        long high = 100_000L * 100_000L; // Maximum possible product

        while (low < high) {
            long mid = low + (high - low) / 2;
            if (countLessOrEqual(nums1, nums2, mid) < k) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    private long countLessOrEqual(int[] nums1, int[] nums2, long target) {
        long count = 0;
        for (int a : nums1) {
            if (a > 0) {
                count += countPositive(a, nums2, target);
            } else if (a < 0) {
                count += countNegative(a, nums2, target);
            } else {
                if (target >= 0) count += nums2.length;
            }
        }
        return count;
    }

    private int countPositive(int a, int[] nums2, long target) {
        // Count how many b in nums2 such that a * b <= target, with a > 0
        int left = 0, right = nums2.length - 1, res = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if ((long) a * nums2[mid] <= target) {
                res = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return res + 1;
    }

    private int countNegative(int a, int[] nums2, long target) {
        // Count how many b in nums2 such that a * b <= target, with a < 0
        int left = 0, right = nums2.length - 1, res = nums2.length;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if ((long) a * nums2[mid] <= target) {
                res = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return nums2.length - res;
    }
}
