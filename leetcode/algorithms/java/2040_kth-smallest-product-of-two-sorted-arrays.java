// Source: https://leetcode.com/problems/kth-smallest-product-of-two-sorted-arrays/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-24
// At the time of submission:
//   Runtime 61 ms Beats 100.00%
//   Memory 55.03 MB Beats 91.47%

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
    // Binary search over product values to find the kth smallest product.
    // Negative products are handled separately using array splitting.
    // Arrays are divided into negative and positive parts, then transformed
    // to use two-pointer counting logic efficiently on positive values only.
    // Time: O((n + m) log(maxProduct)), Space: O(n + m)
    public long kthSmallestProduct(int[] nums1, int[] nums2, long k) {
        int zeroIdx1 = findFirstNonNegative(nums1);
        int zeroIdx2 = findFirstNonNegative(nums2);
        int len1 = nums1.length, len2 = nums2.length;

        int[] pos1 = Arrays.copyOfRange(nums1, zeroIdx1, len1);
        int[] pos2 = Arrays.copyOfRange(nums2, zeroIdx2, len2);
        int[] neg1 = reverseAndNegate(nums1, zeroIdx1);
        int[] neg2 = reverseAndNegate(nums2, zeroIdx2);

        // Count of all negative products (neg * pos or pos * neg)
        int negativeProductCount = neg1.length * pos2.length + pos1.length * neg2.length;

        long sign = 1L;
        if (k <= negativeProductCount) {
            // Flip the arrays to count negative products as positive
            int[] temp = neg2;
            neg2 = pos2;
            pos2 = temp;
            sign = -1L;
            k = negativeProductCount - k + 1;
        } else {
            k -= negativeProductCount;
        }

        long low = 0, high = (long) Math.pow(10, 10);
        while (low < high) {
            long mid = low + (high - low) / 2;
            long count = countLessEqual(neg1, neg2, mid) + countLessEqual(pos1, pos2, mid);
            if (count >= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low * sign;
    }

    // Count number of (a * b) ≤ max for sorted positive arrays a and b
    private long countLessEqual(int[] a, int[] b, long max) {
        long count = 0;
        int j = b.length - 1;
        for (int i = 0; i < a.length; i++) {
            while (j >= 0 && (long) a[i] * b[j] > max) {
                j--;
            }
            count += (j + 1);
        }
        return count;
    }

    // Find first index where value is >= 0 (binary search)
    private int findFirstNonNegative(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= 0) right = mid - 1;
            else left = mid + 1;
        }
        return left;
    }

    // Copy and reverse the first `len` elements, and negate them
    private int[] reverseAndNegate(int[] nums, int len) {
        int[] result = new int[len];
        for (int i = 0; i < len; i++) {
            result[i] = -nums[len - 1 - i];
        }
        return result;
    }
}
