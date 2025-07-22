// Source: https://leetcode.com/problems/median-of-two-sorted-arrays/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2024-12-29
// At the time of submission:
//   Runtime 2 ms Beats 55.41%
//   Memory 45.95 MB Beats 84.48%

/****************************************
* 
* Given two sorted arrays nums1 and nums2 of size m and n respectively, return 
* _ the median of the two sorted arrays.
* The overall run time complexity should be O(log (m+n)).
* 
* Example 1:
* Input: nums1 = [1,3], nums2 = [2]
* Output: 2.00000
* Explanation: merged array = [1,2,3] and median is 2.
* 
* Example 2:
* Input: nums1 = [1,2], nums2 = [3,4]
* Output: 2.50000
* Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.
*  
* Constraints:
* • nums1.length == m
* • nums2.length == n
* • 0 <= m <= 1000
* • 0 <= n <= 1000
* • 1 <= m + n <= 2000
* • -10^6 <= nums1[i], nums2[i] <= 10^6
* 
****************************************/

class Solution {
    // Approach: We'll use binary search on the smaller array (nums1) to efficiently partition
    // both arrays into left and right halves. By ensuring the total number of elements on the left
    // matches half of the combined total, we can directly calculate the median. The partition in
    // nums2 is derived from the partition in nums1, keeping the complexity to O(log(min(m, n))).
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Ensure nums1 is the smaller array
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }
        
        int m = nums1.length;
        int n = nums2.length;
        int totalLeft = (m + n + 1) / 2; // Partition size for the left side
        int low = 0, high = m;
        
        while (low <= high) {
            int partition1 = (low + high) / 2; // Partition in nums1
            int partition2 = totalLeft - partition1; // Partition in nums2
            
            // Handle edges of the partitions
            int left1 = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
            int right1 = (partition1 == m) ? Integer.MAX_VALUE : nums1[partition1];
            int left2 = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
            int right2 = (partition2 == n) ? Integer.MAX_VALUE : nums2[partition2];
            
            // Check partition validity
            if (left1 <= right2 && left2 <= right1) {
                // If combined length is odd
                if ((m + n) % 2 == 1) {
                    return Math.max(left1, left2);
                }
                // If combined length is even
                return (Math.max(left1, left2) + Math.min(right1, right2)) / 2.0;
            } else if (left1 > right2) {
                // Move partition in nums1 to the left
                high = partition1 - 1;
            } else {
                // Move partition in nums1 to the right
                low = partition1 + 1;
            }
        }
        
        // If we reach here, input arrays are not sorted (invalid input)
        throw new IllegalArgumentException("Input arrays are not sorted");
    }
}
